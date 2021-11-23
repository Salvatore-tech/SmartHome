package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
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
        Profile profileToCheck = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), profile.getName(), this);
        if(Objects.nonNull(profileToCheck))
            return null;
        CrudOperation operationToPerform = new AddOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, profile, "Add a profile"));
        return (Profile) operationExecutor.execute(operationToPerform, profile);
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
        CrudOperation getByName = new GetByNameOperationImpl();
        Profile oldProfile = (Profile) operationExecutor.execute(getByName, profileNameToUpdate, this);
        Profile profileToCheck = (Profile) operationExecutor.execute(getByName, updatedProfile.getName(), this);
        if (Objects.nonNull(oldProfile) && Objects.isNull(profileToCheck)) {
            setProfile(oldProfile, updatedProfile);
            CrudOperation operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, oldProfile, "Update a profile")); //TODO
            return (Profile) operationExecutor.execute(operationToPerform, oldProfile);
        }
        return null;
    }

    public Integer deleteProfile(String name) {
        Profile profile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
        if (Objects.nonNull(profile)) {
            CrudOperation operationToPerform = new DeleteOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, profile, "Delete a profile")); //TODO
            return (Integer) operationExecutor.execute(operationToPerform, name, this);
        }
        return 0;
    }

    private void setProfile(Profile oldProfile, Profile updatedProfile) {
        oldProfile.setName(updatedProfile.getName());
        oldProfile.setSurname(updatedProfile.getSurname());
        oldProfile.setEmail(updatedProfile.getEmail());
        oldProfile.setPassword(updatedProfile.getPassword());
    }


}
