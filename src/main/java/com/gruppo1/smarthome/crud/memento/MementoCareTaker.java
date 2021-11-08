package com.gruppo1.smarthome.crud.memento;

import com.gruppo1.smarthome.model.SmartHomeItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MementoCareTaker {

    private List<Map<SmartHomeItem, Memento>> mementoList = new ArrayList<>();

    public Boolean add(Memento mementoOperation, SmartHomeItem homeItem) {
        Map<SmartHomeItem, Memento> mementoMap = new HashMap<>();
        mementoMap.put(homeItem, mementoOperation);
        return mementoList.add(mementoMap);
    }

    public boolean add(Memento mementoOperation) {
        Map<SmartHomeItem, Memento> mementoMap = new HashMap<>();
        mementoMap.put(null, mementoOperation);
        return mementoList.add(mementoMap);
    }

    public List<Map<SmartHomeItem, Memento>> getMementoList() {
        return mementoList;
    }

    public Map<SmartHomeItem, Memento> getMemento(int index) {
        return mementoList.get(index);
    }

}
