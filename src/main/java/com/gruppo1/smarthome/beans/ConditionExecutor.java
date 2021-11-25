package com.gruppo1.smarthome.beans;

import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.repository.ConditionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

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

    @Scheduled(fixedDelay = 5000)
    public void run() {
        Iterable<Condition> conditionList = conditionRepo.findAll();

        conditionList.forEach(
                condition -> {
                    if (condition.getThreshold() > rand.nextInt(20) || new Date(System.currentTimeMillis()).after(condition.getActivationDate())) {
                        Device device = condition.getDevice();
                        actionExecutor.execute(device, condition.getAction());
                    }
                }
        );
    }
}


