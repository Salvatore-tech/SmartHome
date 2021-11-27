package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.Memento;
import com.gruppo1.smarthome.memento.MementoCareTaker;
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

    @Autowired
    public ProfileService(CrudOperationExecutor operationExecutor, MementoCareTaker mementoCareTaker) {
        this.operationExecutor = operationExecutor;
        this.mementoCareTaker = mementoCareTaker;
    }

    public Profile addProfile(Profile profile) {
        CrudOperation operationToPerform = new AddOperationImpl();
        if (!isPresent((Profile) operationExecutor.execute(new GetByNameOperationImpl(), profile.getName(), this))) {
            mementoCareTaker.add(new Memento(operationToPerform, profile, "Add a profile"));
            return (Profile) operationExecutor.execute(operationToPerform, profile);
        }
        return null;
    }

    public List<Profile> findAllProfile() {
        CrudOperation operationToPerform = new GetOperationImpl();
        mementoCareTaker.add(new Memento(operationToPerform, null, "Find all profiles"));
        return (List<Profile>) operationExecutor.execute(operationToPerform, this);
    }

    public Profile findProfileByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl();
        Profile result = (Profile) operationExecutor.execute(operationToPerform, name, this);
        mementoCareTaker.add(new Memento(operationToPerform, result, "Find a profile given a name"));
        return result;
    }

    public Profile updateProfile(String profileNameToUpdate, Profile updatedProfile) {
        CrudOperation operationToPerform = new UpdateOperationImpl();
        Profile oldProfile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), profileNameToUpdate, this);
        if (validateUpdate(oldProfile, updatedProfile)) {
            setProfile(oldProfile, updatedProfile);
            mementoCareTaker.add(new Memento(operationToPerform, oldProfile, "Update a profile"));
            return (Profile) operationExecutor.execute(operationToPerform, oldProfile);
        }
        return null;
    }

    public Integer deleteProfile(String profileName) {
        CrudOperation operationToPerform = new DeleteOperationImpl();
        Profile profile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), profileName, this);
        if (isPresent(profile)) {
            mementoCareTaker.add(new Memento(operationToPerform, profile, "Delete a profile"));
            return (Integer) operationExecutor.execute(operationToPerform, profileName, this);
        }
        return 0;
    }

    private boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateUpdate(Profile oldProfile, Profile updatedProfile) {
        if (!isPresent(oldProfile)) // No profile to update
            return false;
        Profile persistentProfile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), updatedProfile.getName(), this);
        return !isPresent(persistentProfile) || persistentProfile.getName().equalsIgnoreCase(updatedProfile.getName()); // Check if the new name violates unique constraint
    }

    private void setProfile(Profile oldProfile, Profile updatedProfile) {
        oldProfile.setName(updatedProfile.getName());
        oldProfile.setSurname(updatedProfile.getSurname());
        oldProfile.setEmail(updatedProfile.getEmail());
        oldProfile.setPassword(updatedProfile.getPassword());
    }

}
