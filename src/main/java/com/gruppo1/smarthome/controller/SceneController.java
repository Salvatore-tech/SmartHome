package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.service.SceneService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@Api(value = "Scene", description = "Rest API to manage the scenes", tags = {"Scene"})
@RequestMapping("/scene")
public class SceneController {
    private final SceneService sceneService;

    @Autowired
    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all scenes", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of scenes"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<Scene>> getAllScenes() {
        List<Scene> scenes = sceneService.findAllScene();
        return !scenes.isEmpty() ? ResponseEntity.ok(scenes) : (ResponseEntity<List<Scene>>) ResponseEntity.notFound();
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find scene by its name", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene data"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Scene> getSceneById(@ApiParam(value = "Name of a valid scene", required = true) @PathVariable("name") String name) {
        Scene scene = sceneService.findSceneByName(name);
        return Objects.nonNull(scene) ? new ResponseEntity<>(scene, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Scene> updateScene(@PathVariable("name") String name, @RequestBody Scene updatedScene) {
        Scene result = sceneService.updateScene(name, updatedScene);
        return Objects.nonNull(result) ? new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    @ApiOperation(value = "Add new scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Scene Added"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Scene> addScene(@ApiParam(value = "Scene data", required = true) @RequestBody Scene scene) {
        Scene newScene = sceneService.addScene(scene);
        return Objects.nonNull(newScene) ? new ResponseEntity<>(newScene, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteScene/{name}")
    @ApiOperation(value = "Delete scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteScene(@ApiParam(value = "Name of a valid scene", required = true) @PathVariable("name") String name) {
        return sceneService.deleteScene(name).equals(1) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add device to the scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device Added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PostMapping("/addDevice/{sceneName}/{deviceName}")
    public ResponseEntity<Condition> addDevice(@ApiParam(value = "Name of a valid scene", required = true) @PathVariable("sceneName") String sceneName,
                                               @ApiParam(value = "Name of a valid device", required = true) @PathVariable("deviceName") String deviceName,
                                               @ApiParam(value = "Automation's condition", required = true) @RequestBody Condition condition) {
        Condition newCondition = sceneService.addDeviceToScene(sceneName, deviceName, condition);
        return Objects.nonNull(newCondition) ?
                new ResponseEntity<>(newCondition, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Remove device from the scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device removed"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @DeleteMapping("/removeDevice/{sceneName}/{deviceName}")
    public ResponseEntity<?> removeDevice(@PathVariable("sceneName") String sceneName, @PathVariable("deviceName") String deviceName) {
        Integer result = sceneService.removeDeviceToScene(sceneName, deviceName);
        return !result.equals(0) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Find all devices in scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return all devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping("/findDevices/{sceneName}")
    public ResponseEntity<List<Device>> findDevices(@PathVariable("sceneName") String sceneName) {
        List<Device> devices = sceneService.findDevicesInScene(sceneName);
        return !devices.isEmpty() ? ResponseEntity.ok(devices) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findConditions/{sceneName}")
    @ApiOperation(value = "Find conditions", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return condition"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<Condition>> getConditions(@ApiParam(value = "Scene name", required = true)
                                                         @PathVariable("sceneName") String sceneName) {
        List<Condition> conditions = sceneService.findConditionsInScene(sceneName);
        return !conditions.isEmpty() ? ResponseEntity.ok(conditions) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCondition/{sceneName}/{conditionName}")
    @ApiOperation(value = "Delete automation's condition", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Condition deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteCondition(@ApiParam(value = "Name of a valid scene", required = true)
                                             @PathVariable("sceneName") String sceneName,
                                             @ApiParam(value = "Name of a valid automation's condition", required = true)
                                             @PathVariable("conditionName") String conditionName) {
        return sceneService.deleteConditionsInScene(sceneName, conditionName).equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
