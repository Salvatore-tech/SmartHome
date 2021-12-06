package com.gruppo1.smarthome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Scene;
import com.gruppo1.smarthome.model.device.Device;
import com.gruppo1.smarthome.service.SceneService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@Api(value = "Scene", description = "Rest API for Scene", tags = {"Room"})
@RequestMapping("/scene")
public class SceneController {
    private final SceneService sceneService;

    @Autowired
    public SceneController(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all scenes", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return scenes"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<String> getAllScenes() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Scene> scenes = sceneService.findAllScene();
        return scenes.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(scenes), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find scene by ID", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return scene"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Scene> getSceneById(@PathVariable("name") String name) {
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

    @PostMapping("/add")
    @ApiOperation(value = "Add new scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Scene Added"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Scene> addScene(@RequestBody Scene scene) {
        Scene newScene = sceneService.addScene(scene);
        return Objects.nonNull(newScene) ? new ResponseEntity<>(newScene, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteScene/{name}")
    @ApiOperation(value = "Delete scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Scene deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteScene(@PathVariable("name") String name) {
        return sceneService.deleteScene(name).equals(1) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Add device in scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device Added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PostMapping("/addDevice/{sceneName}/{deviceName}")
    public ResponseEntity<Condition> addDevice(@PathVariable("sceneName") String sceneName,
                                               @PathVariable("deviceName") String deviceName,
                                               @RequestBody Condition condition) {
        Condition newCondition = sceneService.addDeviceToScene(sceneName, deviceName, condition);
        return Objects.nonNull(newCondition) ?
                new ResponseEntity<>(newCondition, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Remove device in scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device removed"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @DeleteMapping("/removeDevice/{sceneName}/{deviceName}")
    public ResponseEntity<Integer> removeDevice(@PathVariable("sceneName") String sceneName, @PathVariable("deviceName") String deviceName) {
        Integer result = sceneService.removeDeviceToScene(sceneName, deviceName);
        return result.equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Find all devices in scene", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return all devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @GetMapping("/findDevices/{sceneName}")
    public ResponseEntity<String> findDevices(@PathVariable("sceneName") String sceneName) throws JsonProcessingException {
        List<Device> devices = sceneService.findDevicesInScene(sceneName);
        ObjectMapper objectMapper = new ObjectMapper();
        return devices.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(devices), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findConditions/{sceneName}")
    @ApiOperation(value = "Find conditions", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return condition"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<String> getConditions(@ApiParam(value = "Scene name", required = true)
                                                @PathVariable("sceneName") String sceneName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Condition> conditions = sceneService.findConditionsInScene(sceneName);
        return conditions.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(conditions), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCondition/{sceneName}/{conditionName}")
    @ApiOperation(value = "Delete condition by scene name and condition name", tags = {"Scene"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Condition deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteCondition(@ApiParam(value = "Scene name", required = true)
                                             @PathVariable("sceneName") String sceneName,
                                             @ApiParam(value = "Condition name", required = true)
                                             @PathVariable("conditionName") String conditionName) {
        return sceneService.deleteConditionsInScene(sceneName, conditionName).equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
