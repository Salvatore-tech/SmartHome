package com.gruppo1.smarthome.beans;

import com.gruppo1.smarthome.command.api.Actions;
import com.gruppo1.smarthome.model.device.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class ActionExecutor {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute(Device device, Actions action) {

        if (Objects.isNull(action)) {
            return;
        }

        if (device instanceof Speaker) {
            executeSpeakerAction((Speaker) device, action);
        } else if (device instanceof Television) {
            executeTelevisionAction((Television) device, action);
        } else if (device instanceof LightBulb) {
            executeLightBulbAction((LightBulb) device, action);
        } else if (device instanceof Conditioner) {
            executeConditionerAction((Conditioner) device, action);
        } else if (device instanceof AlarmClock) {
            executeAlarmClockAction((AlarmClock) device, action);
        }

        entityManager.merge(device);
    }


    private void executeSpeakerAction(Speaker speaker, Actions action) {
        switch (action) {
            case POWER_OFF:
                speaker.setStatus(false);
                break;
            case POWER_ON:
                speaker.setStatus(true);
            case RAISE_VOLUME_UP:
                speaker.setPower(speaker.getPower() + 5);
                break;
            case LOWER_VOLUME_DOWN:
                speaker.setPower(speaker.getPower() - 5);
            default:
                System.out.println("Invalid action on " + speaker.toString());
        }
    }

    private void executeTelevisionAction(Television television, Actions action) {
        switch (action) {
            case POWER_OFF:
                television.setStatus(false);
                break;
            case POWER_ON:
                television.setStatus(true);
            default:
                System.out.println("Invalid action on " + television.toString());
        }
    }

    private void executeLightBulbAction(LightBulb lightBulb, Actions action) {
        switch (action) {
            case POWER_OFF:
                lightBulb.setStatus(false);
                break;
            case POWER_ON:
                lightBulb.setStatus(true);
                break;
            case WARMER:
                lightBulb.setColorTemperature("Warm");
                break;
            case COLDER:
                lightBulb.setColorTemperature("Cold");
            default:
                System.out.println("Invalid action on " + lightBulb.toString());
        }
    }

    private void executeConditionerAction(Conditioner conditioner, Actions action) {
        switch (action) {
            case POWER_OFF:
                conditioner.setStatus(false);
                break;
            case POWER_ON:
                conditioner.setStatus(true);
                break;
            case WARMER:
                conditioner.setTemperature(conditioner.getTemperature() + 1);
                break;
            case COLDER:
                conditioner.setTemperature(conditioner.getTemperature() + 1);
            default:
                System.out.println("Invalid action on " + conditioner.toString());
        }
    }

    private void executeAlarmClockAction(AlarmClock alarmClock, Actions action) {
        switch (action) {
            case POWER_OFF:
                alarmClock.setStatus(false);
                break;
            case POWER_ON:
                alarmClock.setStatus(true);
            default:
                System.out.println("Invalid action on " + alarmClock.toString());
        }
    }
}
