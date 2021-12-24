package com.gruppo1.smarthome.beans;

import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.repository.ConditionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class ConditionExecutor {
    private final ConditionRepo conditionRepo;
    private final ActionExecutor actionExecutor;
    private final Random rand;

    @Autowired
    public ConditionExecutor(ConditionRepo conditionRepo, ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
        this.conditionRepo = conditionRepo;
        this.rand = new Random();
    }

    @Scheduled(fixedDelay = 60000)
    public void run() {
        Iterable<Condition> conditionList = conditionRepo.findAll();

        conditionList.forEach(
                condition -> {
                    Scene scene = condition.getScene();
                    Device device = condition.getDevice();
                    Date date = condition.getActivationDate();
                    Integer threshold = condition.getThreshold();
                    String period = scene.getPeriod();
                    if (scene.getStatus() && device.getStatus()) {
                        long currentMillisecond = System.currentTimeMillis();
                        if(controlExecute(date, threshold, currentMillisecond)){
                            actionExecutor.execute(device, condition.getAction());
                            if (Objects.nonNull(period))
                                setRoutine(condition, currentMillisecond, period);
                        }
                    }
                }
        );
    }

    private Boolean controlExecute(Date date, Integer threshold, long currentMillisecond){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(Objects.nonNull(date) && Objects.nonNull(threshold)){
            return threshold == rand.nextInt(30) || date.toString().contains(dateFormatter.format(currentMillisecond));
        }else if(Objects.isNull(threshold)){
            return date.toString().contains(dateFormatter.format(currentMillisecond));
        } else {
            return threshold == rand.nextInt(30);
        }
    }

    private void setRoutine(Condition condition, long currentMillisecond, String period) {
        if (period.equalsIgnoreCase("daily")) {
            long day = TimeUnit.DAYS.toMillis(1);
            condition.setActivationDate(new Date(currentMillisecond + day));
            conditionRepo.save(condition);
        } else if (period.equalsIgnoreCase("weekly")) {
            long week = TimeUnit.DAYS.toMillis(7);
            condition.setActivationDate(new Date(currentMillisecond + week));
            conditionRepo.save(condition);
        } else if (period.equalsIgnoreCase("monthly")) {
            long month = TimeUnit.DAYS.toMillis(30);
            condition.setActivationDate(new Date(currentMillisecond + month));
            conditionRepo.save(condition);
        }
    }
}


