package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends BaseSmartHomeRepository<Profile, String> {
}