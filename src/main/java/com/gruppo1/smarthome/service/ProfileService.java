package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.api.CrudOperation;
import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
import com.gruppo1.smarthome.crud.memento.Memento;
import com.gruppo1.smarthome.crud.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.model.SmartHomeItem;
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
    private CrudOperation operationToPerform;

    @Autowired
    public ProfileService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Profile addProfile(Profile profile) {
        operationToPerform = new GetByNameOperationImpl();
        if (!isPresent((Profile) operationExecutor.execute(operationToPerform, profile.getName(), this))) {
            operationToPerform = new AddOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, profile, "Add a profile"));
            return (Profile) operationExecutor.execute(operationToPerform, profile);
        }
        return null;
    }

    public List<Profile> findAllProfile() {
        operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Find all profiles"));
        return (List<Profile>) operationExecutor.execute(operationToPerform, this);
    }

    public Profile findProfileByName(String name) {
        operationToPerform = new GetByNameOperationImpl();
        Profile result = (Profile) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a profile given a name")); //TODO
        return result;
    }

    public Profile updateProfile(String profileNameToUpdate, Profile updatedProfile) {
        operationToPerform = new GetByNameOperationImpl();
        Profile oldProfile = (Profile) operationExecutor.execute(operationToPerform, profileNameToUpdate, this);
        if (validateUpdate(oldProfile, updatedProfile)) {
            setProfile(oldProfile, updatedProfile);
            operationToPerform = new UpdateOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, oldProfile, "Update a profile")); //TODO
            return (Profile) operationExecutor.execute(operationToPerform, oldProfile);
        }
        return null;
    }

    public Integer deleteProfile(String profileName) {
        operationToPerform = new GetByNameOperationImpl();
        Profile profile = (Profile) operationExecutor.execute(operationToPerform, profileName, this);
        if (isPresent(profile)) {
            operationToPerform = new DeleteOperationImpl();
            mementoCareTaker.add(new Memento(operationToPerform, profile, "Delete a profile")); //TODO
            return (Integer) operationExecutor.execute(operationToPerform, profileName, this);
        }
        return 0;
    }

    private Boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item) ? true : false;
    }

    private Boolean validateUpdate(Profile oldProfile, Profile updatedProfile) {
        if (isPresent(oldProfile)) {
            operationToPerform = new GetByNameOperationImpl();
            Profile profileToCheck = (Profile) operationExecutor.execute(operationToPerform, updatedProfile.getName(), this);
            if (!isPresent(profileToCheck)) {
                return true;
            }
        }
        return false;
    }

    private void setProfile(Profile oldProfile, Profile updatedProfile) {
        oldProfile.setName(updatedProfile.getName());
        oldProfile.setSurname(updatedProfile.getSurname());
        oldProfile.setEmail(updatedProfile.getEmail());
        oldProfile.setPassword(updatedProfile.getPassword());
    }

}
