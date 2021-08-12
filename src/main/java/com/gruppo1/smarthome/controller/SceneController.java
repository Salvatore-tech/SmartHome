package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.service.SceneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> origin/master

@RestController
@RequestMapping("/scene/")
public class SceneController {
    private final SceneService sceneService;

    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

<<<<<<< HEAD
    @GetMapping("/allScenes/")
=======
    @GetMapping("/all/")
>>>>>>> origin/master
    public ResponseEntity<List<Scene>> getAllScenes(){
        List<Scene> scenes = sceneService.findAllScene();
        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

<<<<<<< HEAD
    @GetMapping("/sceneById/")
    public ResponseEntity<Optional<Scene>> getSceneById(Long id){
        Optional<Scene> scene = sceneService.findSceneByID(id);
        return new ResponseEntity<>(scene, HttpStatus.OK);
    }

    @GetMapping("/updateScene/")
    public ResponseEntity<Scene> updateScene(Scene scene){
        Scene updatedScene = sceneService.updateScene(scene);
        return new ResponseEntity<>(updatedScene, HttpStatus.OK);
    }

    @GetMapping("/addScene/")
    public ResponseEntity<Scene> addScene(Scene scene){
        Scene newScene = sceneService.addScene(scene);
        return new ResponseEntity<>(newScene, HttpStatus.OK);
    }


=======
    //TODO
    //I don't know what else I have to implement, there is no controller in the sample
>>>>>>> origin/master
}
