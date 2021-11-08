package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProfileService {
    private final CrudOperationExecutor operationExecutor;
    private MementoCareTaker mementoCareTaker;

    @Autowired
    public ProfileService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Profile addProfile(Profile profile) {
        //TODO check if already exists
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), profile);
        return (Profile) operationExecutor.execute(operationToPerform, profile);
    }

    public List<Profile> findAllProfile() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), null);
        return (List<Profile>) operationExecutor.execute(operationToPerform, this);
    }

    public Profile findProfileByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), null); //TODO
        return (Profile) operationExecutor.execute(operationToPerform, name, this);
    }

    public Profile updateProfile(String profileNameToUpdate, Profile updatedProfile) {
        //TODO: hide more the id handling
        Profile oldProfile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), profileNameToUpdate, this);
        if (Objects.nonNull(oldProfile)) {
            updatedProfile.setId(oldProfile.getId());
            CrudOperation operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(operationToPerform.generateMemento(), oldProfile); //TODO
            return (Profile) operationExecutor.execute(operationToPerform, updatedProfile);
        }
        return null;
    }

    public Integer deleteProfile(String name) {
        //TODO check if already exists
        CrudOperation operationToPerform = new DeleteOperationImpl();
        mementoCareTaker.add(operationToPerform.generateMemento(), null); //TODO
        return (Integer) operationExecutor.execute(operationToPerform, name, this);
    }

}
