package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.service.SceneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scene/")
public class SceneController {
    private final SceneService sceneService;

    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/all/")
    public ResponseEntity<List<Scene>> getAllScenes(){
        List<Scene> scenes = sceneService.findAllScene();
        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

    //TODO
    //I don't know what else I have to implement, there is no controller in the sample
}
