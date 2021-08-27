package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.service.SceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@Api(value = "Scene", description = "Rest API for Scene", tags = {"Room"})
@RequestMapping("/scene")
public class SceneController {
    private final SceneService sceneService;

    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all scenes", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return scenes"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<Scene>> getAllScenes() {
        List<Scene> scenes = sceneService.findAllScene();
        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find scene by ID", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return scene"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Optional<Scene>> getSceneById(@PathVariable("id") String id) {
        Optional<Scene> scene = sceneService.findSceneByID(id);
        return scene.isPresent() ? new ResponseEntity<>(scene, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    //TODO: 500 INTERNAL ERROR
    public ResponseEntity<Scene> updateScene(@RequestBody Scene scene) {
        Scene updatedScene = sceneService.updateScene(scene);
        return Objects.nonNull(updatedScene) ? new ResponseEntity<>(updatedScene, HttpStatus.OK) : new ResponseEntity<>(updatedScene, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Scene Added"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Scene> addScene(@RequestBody Scene scene) {
        Scene newScene = sceneService.addScene(scene);
        return new ResponseEntity<>(newScene, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteScene/{id}")
    @ApiOperation(value = "Delete scene by ID", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    //TODO: remove id in the URL
    public ResponseEntity<?> deleteScene(@PathVariable("id") String id) {
        return sceneService.deleteScene(id) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
