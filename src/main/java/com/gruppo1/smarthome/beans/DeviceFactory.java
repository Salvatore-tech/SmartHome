package com.gruppo1.smarthome.beans;

import com.gruppo1.smarthome.model.device.*;
import org.springframework.stereotype.Component;

@Component
public class DeviceFactory {

    public Device create(String deviceType) {
        if (deviceType.isEmpty()) {
            return null;
        }
        if (deviceType.equalsIgnoreCase("television")) {
            return new Television(deviceType);
        } else if (deviceType.equalsIgnoreCase("lightbulb")) {
            return new LightBulb(deviceType);
        } else if (deviceType.equalsIgnoreCase("speaker")) {
            return new Speaker(deviceType);
        } else if (deviceType.equalsIgnoreCase("conditioner")) {
            return new Conditioner(deviceType);
        } else if (deviceType.equalsIgnoreCase("alarmclock")) {
            return new AlarmClock(deviceType);
        }
        return null;
    }
}
