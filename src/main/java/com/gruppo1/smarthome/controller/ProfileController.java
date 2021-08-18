package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/scene")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Profile>> getAllProfile(){
        List<Profile> scenes = profileService.findAllProfile();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/profileById")
    public ResponseEntity<Optional<Profile>> getProfileById(String id){
        Optional<Profile> profile = profileService.findProfileByID(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile){
        Profile updatedProfile = profileService.updateProfile (profile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile){
        Profile newProfile = profileService.addProfile(profile);
        return new ResponseEntity<>(newProfile, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProfile/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable("id") String id){
        profileService.deleteProfile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}