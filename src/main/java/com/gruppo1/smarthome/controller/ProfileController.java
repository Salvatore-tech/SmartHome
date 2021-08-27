package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.service.ProfileService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Api(value = "Profile", description = "Rest API for Profile", tags = {"Profile"})
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "return profile"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<Profile>> getAllProfile(){
        List<Profile> profiles = profileService.findAllProfile();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return profile"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Optional<Profile>> getProfileById(@ApiParam(value = "Profile ID", required = true)
                                                                @PathVariable("id") String id){
        Optional<Profile> profile = profileService.findProfileByID(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile){
        Profile updatedProfile = profileService.updateProfile (profile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Profile added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile){
        Profile newProfile = profileService.addProfile(profile);
        return new ResponseEntity<>(newProfile, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteProfile/{id}")
    @ApiOperation(value = "Delete profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteProfile(@ApiParam(value = "Profile ID", required = true)
                                               @PathVariable("id") String id){
        profileService.deleteProfile(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
