package com.gruppo1.smarthome.crud.memento;

import com.gruppo1.smarthome.crud.api.SmartHomeItemLight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MementoCareTaker {

    private List<Map<SmartHomeItemLight, Memento>> mementoList = new ArrayList<>();

    public Boolean add(Memento mementoOperation, SmartHomeItemLight homeItemLight) {
        Map<SmartHomeItemLight, Memento> mementoMap = new HashMap<>();
        mementoMap.put(homeItemLight, mementoOperation);
        return mementoList.add(mementoMap);
    }

    public boolean add(Memento mementoOperation) {
        Map<SmartHomeItemLight, Memento> mementoMap = new HashMap<>();
        mementoMap.put(null, mementoOperation);
        return mementoList.add(mementoMap);
    }

    public List<Map<SmartHomeItemLight, Memento>> getMementoList() {
        return mementoList;
    }

    public Map<SmartHomeItemLight, Memento> getMemento(int index) {
        return mementoList.get(index);
    }

}
