package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.api.SmartHomeItemLight;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
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
        if (Objects.nonNull(this.findProfileByName(profile.getName())))
            return null;
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, profile, "Add a profile"));
        return (Profile) operationExecutor.execute(operationToPerform, profile);
    }

    private SmartHomeItemLight getHomeItemLight(String name, String description) {
        return new SmartHomeItemLight(name, "Profile");
    }

    public List<Profile> findAllProfile() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Find all profiles"));
        return (List<Profile>) operationExecutor.execute(operationToPerform, this);
    }

    public Profile findProfileByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Profile result = (Profile) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a profile given a name")); //TODO
        return result;
    }

    public Profile updateProfile(String profileNameToUpdate, Profile updatedProfile) {
        //TODO: hide more the id handling
        Profile oldProfile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), profileNameToUpdate, this);
        if (Objects.nonNull(oldProfile)) {
            updatedProfile.setId(oldProfile.getId());
            CrudOperation operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, oldProfile, "Update a profile")); //TODO
            return (Profile) operationExecutor.execute(operationToPerform, updatedProfile);
        }
        return null;
    }

    public Integer deleteProfile(String name) {
        //TODO not implemented
        CrudOperation operationToPerform = new DeleteOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Delete a profile")); //TODO
        return (Integer) operationExecutor.execute(operationToPerform, name, this);
    }
}
