package com.gruppo1.smarthome.model;

public class FactoryDevice {

    public Device getDevice(String deviceType) {

        if (deviceType.isEmpty()) {
            return null;
        }

        if (deviceType.equalsIgnoreCase("Television")) {
            return new Television();
        }

        else if (deviceType.equalsIgnoreCase("LightBulb")) {
            return new LightBulb();
        }

        else if (deviceType.equalsIgnoreCase("Speaker")) {
            return new Speaker();
        }

        else if (deviceType.equalsIgnoreCase("Conditioner")) {
            return new Conditioner();
        }

        else if (deviceType.equalsIgnoreCase("AlarmClock")) {
            return new AlarmClock();
        }

        return null;
    }
}
