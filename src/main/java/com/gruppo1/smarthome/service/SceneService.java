package com.gruppo1.smarthome.service;

import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.repository.SceneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SceneService {
    private final SceneRepo sceneRepo;

    @Autowired
    public SceneService(SceneRepo sceneRepo) {
        this.sceneRepo = sceneRepo;
    }

    public Scene addScene(Scene scene) {
        return sceneRepo.save(scene);
    }

    public List<Scene> findAllScene() {
        return (List<Scene>) sceneRepo.findAll();
    }

    public Scene updateScene(Scene scene) {
        if (sceneRepo.findById(scene.getId()).isPresent()) {
            sceneRepo.save(scene);
            return scene;
        }
        return null;
    }

    public Optional<Scene> findSceneByID(String id) {
        return sceneRepo.findById(id);
    }

    public Boolean deleteScene(String id) {
        Optional<Scene> scene = sceneRepo.findById(id);
        if (scene.isPresent()) {
            sceneRepo.deleteSceneById(id);
            return true;
        }
        return false;
    }
}
