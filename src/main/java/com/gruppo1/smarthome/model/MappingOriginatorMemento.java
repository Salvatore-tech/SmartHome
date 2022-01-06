package com.gruppo1.smarthome.model;

import java.util.HashMap;
import java.util.Map;

public class MappingOriginatorMemento {
    private final Map<String, SmartHomeItem> mappings = new HashMap<>();

    public MappingOriginatorMemento() {
        mappings.put(AlarmClock.MementoAlarmClock.class.toString(), new AlarmClock());
        mappings.put(Condition.MementoCondition.class.toString(), new Condition());
        mappings.put(Conditioner.MementoConditioner.class.toString(), new Conditioner());
        mappings.put(LightBulb.MementoLightBulb.class.toString(), new LightBulb());
        mappings.put(Profile.MementoProfile.class.toString(), new Profile());
        mappings.put(Room.MementoRoom.class.toString(), new Room());
        mappings.put(Scene.MementoScene.class.toString(), new Scene());
        mappings.put(Speaker.MementoSpeaker.class.toString(), new Speaker());
        mappings.put(Television.MementoTelevision.class.toString(), new Television());
    }

    public SmartHomeItem getOriginator(String mementoStringKey) {
        return mappings.get(mementoStringKey);
    }
}
