package com.gruppo1.smarthome.memento;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MementoCareTaker {

    private List<Memento> mementoList = new ArrayList<>();

    public Boolean add(Memento mementoOperation) {
        return mementoList.add(mementoOperation);
    }


    public List<Memento> getMementoList() {
        return mementoList;
    }

    public Memento getMemento(int index) {
        return mementoList.get(index);
    }

    public Memento getLastMementoOperation (){
        return mementoList.get(mementoList.size() - 2);
    }
}
