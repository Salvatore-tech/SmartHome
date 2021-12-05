package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.command.api.CrudOperation;
import com.gruppo1.smarthome.command.impl.*;
import com.gruppo1.smarthome.memento.MementoCareTaker;
import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.repository.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProfileService {
    private final MementoCareTaker mementoCareTaker;
    private final ProfileRepo profileRepo;

    @Autowired
    public ProfileService(MementoCareTaker mementoCareTaker, ProfileRepo profileRepo) {
        this.mementoCareTaker = mementoCareTaker;
        this.profileRepo = profileRepo;
    }

    public Profile addProfile(Profile profile) {
        CrudOperation addOperation = new AddOperationImpl(profileRepo);
        CrudOperation getOperation = new GetOperationImpl(profileRepo);
        mementoCareTaker.push(addOperation, profile.createMemento());
        if (!isPresent(getOperation.execute(profile.getName()).get(0))) {
            return (Profile) addOperation.execute(profile);
        }
        return null;
    }

    public List<Profile> findAllProfile() {
        CrudOperation operationToPerform = new GetOperationImpl(profileRepo);
        mementoCareTaker.push(operationToPerform, null);
        return (List<Profile>) (List<?>) operationToPerform.execute();
    }

    public Profile findProfileByName(String name) {
        CrudOperation operationToPerform = new GetByNameOperationImpl(profileRepo);
        mementoCareTaker.push(operationToPerform, null); // TODO SS
        return (Profile) operationToPerform.execute(name);
    }

    public Profile updateProfile(String profileNameToUpdate, Profile updatedProfile) {
        CrudOperation updateOperation = new UpdateOperationImpl(profileRepo);
        CrudOperation getOperation = new GetOperationImpl(profileRepo);
        Profile oldProfile = (Profile) getOperation.execute(profileNameToUpdate);
        if (validateUpdate(oldProfile, updatedProfile)) {
            mementoCareTaker.push(updateOperation, oldProfile.createMemento());
            setProfile(oldProfile, updatedProfile);
            return (Profile) updateOperation.execute(oldProfile);
        }
        return null;
    }

    public SmartHomeItem deleteProfile(String profileName) {
        CrudOperation deleteOperation = new DeleteOperationImpl(profileRepo);
        CrudOperation getOperation = new GetByNameOperationImpl(profileRepo);
        Profile profile = (Profile) getOperation.execute(profileName);
        if (isPresent(profile)) {
            mementoCareTaker.push(deleteOperation, null); // TODO SS
            return deleteOperation.execute(profileName).get(0);
        }
        return null;
    }

    private boolean isPresent(SmartHomeItem item) {
        return Objects.nonNull(item);
    }

    private Boolean validateUpdate(Profile oldProfile, Profile updatedProfile) {
        CrudOperation getOperation = new GetOperationImpl(profileRepo);
        if (!isPresent(oldProfile)) // No profile to update
            return false;
        Profile persistentProfile = (Profile) getOperation.execute(updatedProfile.getName());
        return !isPresent(persistentProfile) || persistentProfile.getName().equalsIgnoreCase(updatedProfile.getName()); // Check if the new name violates unique constraint
    }

    private void setProfile(Profile oldProfile, Profile updatedProfile) {
        oldProfile.setName(updatedProfile.getName());
        oldProfile.setSurname(updatedProfile.getSurname());
        oldProfile.setEmail(updatedProfile.getEmail());
        oldProfile.setPassword(updatedProfile.getPassword());
    }

}
