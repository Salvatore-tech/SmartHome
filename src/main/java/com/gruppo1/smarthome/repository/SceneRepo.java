package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Scene;
import org.springframework.stereotype.Repository;

@Repository
public interface SceneRepo extends BaseSmartHomeRepository<Scene, String> {
}
