package com.gruppo1.smarthome.model;

public class FactoryDevice {

    public Device getDevice(String deviceType) {

        if (deviceType.isEmpty()) {
            return null;
        }

        if (deviceType.equalsIgnoreCase("television")) {
            return new Television();
        }

        else if (deviceType.equalsIgnoreCase("lightbulb")) {
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
