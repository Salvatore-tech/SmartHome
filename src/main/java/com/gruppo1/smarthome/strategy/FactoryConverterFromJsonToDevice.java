package com.gruppo1.smarthome.strategy;

import java.util.HashMap;
import java.util.Map;

public class FactoryConverterFromJsonToDevice {

    private static Map<String, ConverterFromJsonToDevice> converters = new HashMap<>();

    static {
        converters.put("alarmclock",
                new AlarmClockStrategy());
        converters.put("conditioner",
                new ConditionerStrategy());
        converters.put("lightbulb",
                new LightBulbStrategy());
        converters.put("speaker",
                new SpeakerStrategy());
        converters.put("television",
                new TelevisionStrategy());
    }

    public static ConverterFromJsonToDevice getInstance(String type) {
        return converters.get(type);
    }
}