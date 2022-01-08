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
    private static final String DAILY = "daily";
    private static final String WEEKLY = "weekly";
    private static final String MONTHLY = "monthly";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final int ONE_DAY = 1;
    private static final int SEVEN_DAYS = 7;
    private static final int THIRTY_DAYS = 30;
    private static final int ONE_MINUTE = 60000;

    private final ConditionRepo conditionRepo;
    private final ActionExecutor actionExecutor;
    private final Random rand;

    @Autowired
    public ConditionExecutor(ConditionRepo conditionRepo, ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
        this.conditionRepo = conditionRepo;
        this.rand = new Random();
    }

    @Scheduled(fixedDelay = ONE_MINUTE)
    public void run() {
        Iterable<Condition> conditionList = conditionRepo.findAll();
        conditionList.forEach(
                condition -> {
                    Scene scene = condition.getScene();
                    Device device = condition.getDevice();
                    String period = scene.getPeriod();
                    if (scene.getStatus() && device.getStatus()) {
                        if (isVerifiedActivationCondition(condition)) {
                            actionExecutor.execute(device, condition.getAction());
                            if (Objects.nonNull(period))
                                updateTemporalCondition(condition, period);
                        }
                    }
                }
        );
    }

    private Boolean isVerifiedActivationCondition(Condition condition) {
        Date date = condition.getActivationDate();
        Integer threshold = condition.getThreshold();
        long currentMillisecond = System.currentTimeMillis();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        if (Objects.nonNull(date) && Objects.nonNull(threshold)) {
            return threshold == rand.nextInt(30) || date.toString().contains(dateFormatter.format(currentMillisecond));
        } else if (Objects.isNull(threshold)) {
            assert date != null;
            return date.toString().contains(dateFormatter.format(currentMillisecond));
        } else {
            return threshold == rand.nextInt(30);
        }
    }

    private void updateTemporalCondition(Condition condition, String period) {
        long currentMillisecond = System.currentTimeMillis();
        if (period.equalsIgnoreCase(DAILY)) {
            long day = TimeUnit.DAYS.toMillis(ONE_DAY);
            condition.setActivationDate(new Date(currentMillisecond + day));
            conditionRepo.save(condition);
        } else if (period.equalsIgnoreCase(WEEKLY)) {
            long week = TimeUnit.DAYS.toMillis(SEVEN_DAYS);
            condition.setActivationDate(new Date(currentMillisecond + week));
            conditionRepo.save(condition);
        } else if (period.equalsIgnoreCase(MONTHLY)) {
            long month = TimeUnit.DAYS.toMillis(THIRTY_DAYS);
            condition.setActivationDate(new Date(currentMillisecond + month));
            conditionRepo.save(condition);
        }
    }
}


