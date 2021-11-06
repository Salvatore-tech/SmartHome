package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.crud.beans.CrudOperationExecutor;
import com.gruppo1.smarthome.crud.impl.*;
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

    @Autowired
    public ProfileService(CrudOperationExecutor operationExecutor) {
        this.operationExecutor = operationExecutor;
    }

    public Profile addProfile(Profile profile) {
//        profile.setId(UUID.randomUUID().toString());
//        return profileRepo.save(profile);
        //TODO check if already exists
        return (Profile) operationExecutor.execute(new AddOperationImpl(), profile);
    }

    public List<Profile> findAllProfile() {
//        return (List<Profile>) profileRepo.findAll();
        return (List<Profile>) operationExecutor.execute(new GetOperationImpl(), this);
    }

    public Profile findProfileByName(String name) {
        return (Profile) operationExecutor.execute(new GetByNameOperationImpl(), name, this);
    }

    public Profile updateProfile(String profileNameToUpdate, Profile updatedProfile) {
//        return profileRepo.save(profile);
        //TODO: hide more the id handling

        Profile oldProfile = (Profile) operationExecutor.execute(new GetByNameOperationImpl(), profileNameToUpdate, this);
        if (Objects.nonNull(oldProfile)) {
            updatedProfile.setId(oldProfile.getId());
            return (Profile) operationExecutor.execute(new UpdateOperationImpl(), updatedProfile);
        }
        return null;
    }

    public Integer deleteProfile(String name) {
//        profileRepo.deleteProfileById(id);
        //TODO check if already exists
        return (Integer) operationExecutor.execute(new DeleteOperationImpl(), name, this);
    }

}
