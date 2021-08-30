package com.gruppo1.smarthome.repository;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface SceneRepo extends CrudRepository<Scene, String> {

    void deleteSceneById(String id);

    Optional<Scene> findByName(String sceneName);

    @Query("select c.device from Conditions c where c.scene=:scene")
    List<Device> findAllDevices(Optional<Scene> scene);

}
