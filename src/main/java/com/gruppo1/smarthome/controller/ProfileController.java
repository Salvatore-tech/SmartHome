package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Profile;
import com.gruppo1.smarthome.service.ProfileService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@Api(value = "Profile", description = "Rest API to manage the profiles", tags = {"Profile"})
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of profiles"),
            @ApiResponse(code = 404, message = "Not Found - the user has not added a profile yet")})
    public ResponseEntity<List<Profile>> getAllProfile() {
        List<Profile> profiles = profileService.findAllProfile();
        return !profiles.isEmpty() ? ResponseEntity.ok(profiles) : (ResponseEntity<List<Profile>>) ResponseEntity.notFound();
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile data"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Profile> getProfileByName(@ApiParam(value = "Name of a valid profile", required = true)
                                                    @PathVariable("name") String name) {
        Profile profile = profileService.findProfileByName(name);
        return Objects.nonNull(profile) ? new ResponseEntity(profile, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update device", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Profile> updateProfile(@ApiParam(value = "Name of a valid profile", required = true) @PathVariable("name") String name,
                                                 @ApiParam(value = "Updated data for the profile", required = true) @RequestBody Profile updatedProfile) {

        Profile result = profileService.updateProfile(name, updatedProfile);

        return Objects.nonNull(result) ?
                new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    @ApiOperation(value = "Add new profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Profile added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Profile> addProfile(@ApiParam(value = "Profile data to add", required = true) @RequestBody Profile profile) {
        Profile newProfile = profileService.addProfile(profile);
        return Objects.nonNull(newProfile) ? new ResponseEntity<>(newProfile, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteProfile/{name}")
    @ApiOperation(value = "Delete profile", tags = {"Profile"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Profile deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteProfile(@ApiParam(value = "Name of a valid profile", required = true)
                                           @PathVariable("name") String name) {
        return profileService.deleteProfile(name).equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
