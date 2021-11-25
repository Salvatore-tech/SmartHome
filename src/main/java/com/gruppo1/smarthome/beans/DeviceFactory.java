package com.gruppo1.smarthome.beans;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.device.*;
import org.springframework.stereotype.Component;

@Component
public class DeviceFactory {

    public Device create(String deviceType) {

        if (deviceType.isEmpty()) {
            return null;
        }

        if (deviceType.equalsIgnoreCase("television")) {
            return new Television();
        } else if (deviceType.equalsIgnoreCase("lightbulb")) {
            return new LightBulb();
        }

        else if (deviceType.equalsIgnoreCase("speaker")) {
            return new Speaker();
        }

        else if (deviceType.equalsIgnoreCase("conditioner")) {
            return new Conditioner();
        }

        else if (deviceType.equalsIgnoreCase("alarmclock")) {
            return new AlarmClock();
        }

        return null;
    }
}
