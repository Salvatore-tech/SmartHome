package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Device;
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
        return new ResponseEntity(sceneService.findAllScene(), HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find scene by ID", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return scene"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Optional<Scene>> getSceneById(@PathVariable("name") String name) {
        Scene scene = sceneService.findSceneByName(name);
        return Objects.nonNull(scene) ? new ResponseEntity(scene, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    //TODO: 500 INTERNAL ERROR
    public ResponseEntity<Scene> updateScene(@PathVariable("name") String name, @RequestBody Scene updatedScene) {
        Scene result = sceneService.updateScene(name, updatedScene);
        return Objects.nonNull(updatedScene) ? new ResponseEntity<>(updatedScene, HttpStatus.OK) : new ResponseEntity<>(updatedScene, HttpStatus.NOT_FOUND);

    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Scene Added"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Scene> addScene(@RequestBody Scene scene) {
        Scene newScene = sceneService.addScene(scene);
        return Objects.nonNull(newScene) ? new ResponseEntity<>(newScene, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{name}")
    @ApiOperation(value = "Delete scene by ID", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    //TODO: remove id in the URL
    public ResponseEntity<?> deleteScene(@PathVariable("name") String name) {
        return sceneService.deleteScene(name).equals(1) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO SS: not yet implemented
    @PostMapping("/addDevice/{sceneName}/{deviceName}")
    public ResponseEntity<Optional<Device>> addDevice(@PathVariable("sceneName") String sceneName, @PathVariable("deviceName") String deviceName) {
        Optional<Device> device = sceneService.addDevice(sceneName, deviceName);
        return device.isPresent() ?
                new ResponseEntity<>(device, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/removeDevice/{sceneName}/{deviceName}")
    public ResponseEntity<Optional<Device>> removeDevice(@PathVariable("sceneName") String sceneName, @PathVariable("deviceName") String deviceName) {
        Optional<Device> device = sceneService.removeDevice(sceneName, deviceName);
        return device.isPresent() ?
                new ResponseEntity<>(device, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findDevices/{sceneName}")
    public ResponseEntity<List<Device>> findDevices(@PathVariable("sceneName") String sceneName){
        List<Device> devices = sceneService.findDevices(sceneName);
        return devices.size() > 0 ?  new ResponseEntity<>(devices, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
