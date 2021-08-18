package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.repository.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProfileService {
    private final ProfileRepo profileRepo;

    @Autowired
    public ProfileService(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    public Profile addProfile(Profile profile) {
        profile.setId(UUID.randomUUID().toString());
        return profileRepo.save(profile);
    }

    public List<Profile> findAllProfile() {
        return (List<Profile>) profileRepo.findAll();
    }

    public Optional<Profile> findProfileByID(String id){
        return profileRepo.findById(id);
    }

    public Profile updateProfile(Profile profile){
        return profileRepo.save(profile);
    }

    public void deleteProfile(String id){
        profileRepo.deleteProfileById(id);
    }

}
