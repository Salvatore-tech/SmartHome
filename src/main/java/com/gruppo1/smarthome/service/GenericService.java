package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GenericService {

    private final MementoCareTaker mementoCareTaker;

    @Autowired
    public GenericService(MementoCareTaker mementoCareTaker) {
        this.mementoCareTaker = mementoCareTaker;
    }

    public int undo() {
        return mementoCareTaker.undo();
    }

    public List<ImmutablePair<CrudOperation, Memento>> getHistory() {
        return mementoCareTaker.getMementoCommandList();
    }

}
