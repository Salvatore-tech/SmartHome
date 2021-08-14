package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.service.SceneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/scene/")
public class SceneController {
    private final SceneService sceneService;

    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/allScenes")
    public ResponseEntity<List<Scene>> getAllScenes(){
        List<Scene> scenes = sceneService.findAllScene();
        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

    @GetMapping("/sceneById")
    public ResponseEntity<Optional<Scene>> getSceneById(String id){
        Optional<Scene> scene = sceneService.findSceneByID(id);
        return new ResponseEntity<>(scene, HttpStatus.OK);
    }

    @PutMapping("/updateScene")
    public ResponseEntity<Scene> updateScene(@RequestBody Scene scene){
        Scene updatedScene = sceneService.updateScene(scene);
        return new ResponseEntity<>(updatedScene, HttpStatus.OK);
    }

    @PostMapping("/addScene")
    public ResponseEntity<Scene> addScene(@RequestBody Scene scene){
        Scene newScene = sceneService.addScene(scene);
        return new ResponseEntity<>(newScene, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteScene/{id}")
    public ResponseEntity<?> deleteScene(@PathVariable("id") String id){
        sceneService.deleteScene(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}