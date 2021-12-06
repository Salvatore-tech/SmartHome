package com.gruppo1.smarthome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.device.Device;
import com.gruppo1.smarthome.service.DeviceService;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

// http://localhost:8080/device/all
// http://localhost:8080/swagger-ui.html#/

@RestController
@Api(value = "Device", description = "Rest API for Device", tags = {"Device"})
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all devices installed", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<String> getAllDevices() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Device> devices = deviceService.findAllDevices();
        return devices.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(devices), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> addDevice(@RequestBody String deviceString) throws JSONException {
        JSONObject deviceJson = new JSONObject(deviceString);
        Device newDevice = deviceService.addDevice(deviceJson);
        return Objects.nonNull(newDevice) ? new ResponseEntity<>(newDevice, HttpStatus.CREATED) : new ResponseEntity<>(newDevice, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return device"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Device> getDeviceByName(@ApiParam(value = "Device name", required = true)
                                                  @PathVariable("name") String name) {
        Device device = deviceService.findDeviceByName(name);
        return Objects.nonNull(device) ? new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{name}")
    @ApiOperation(value = "Delete device by name", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteDevice(@ApiParam(value = "Device name", required = true)
                                          @PathVariable("name") String name) {
        return deviceService.deleteDevice(name) == 1 ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Device> updateDevice(@PathVariable("name") String name, @RequestBody String deviceString) throws JSONException {
        JSONObject deviceJson = new JSONObject(deviceString);
        Device result = deviceService.updateDevice(name, deviceJson);
        return Objects.nonNull(result) ?
                new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Add condition", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Condition Added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PostMapping("/addCondition/{deviceName}/{sceneName}")
    public ResponseEntity<Condition> addDevice(@PathVariable("deviceName") String deviceName,
                                               @PathVariable("sceneName") String sceneName,
                                               @RequestBody Condition condition) {
        Condition newCondition = deviceService.addConditionByDeviceName(deviceName, sceneName, condition);
        return Objects.nonNull(newCondition) ?
                new ResponseEntity<>(newCondition, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findConditions/{deviceName}")
    @ApiOperation(value = "Find conditions", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return condition"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<String> getConditions(@ApiParam(value = "Device name", required = true)
                                                @PathVariable("deviceName") String deviceName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Condition> conditions = deviceService.findConditionsInDevice(deviceName);
        return conditions.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(conditions), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCondition/{deviceName}/{conditionName}")
    @ApiOperation(value = "Delete condition by device name and condition name", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Condition deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteCondition(@ApiParam(value = "Device name", required = true)
                                             @PathVariable("deviceName") String deviceName,
                                             @ApiParam(value = "Condition name", required = true)
                                             @PathVariable("conditionName") String conditionName) {
        return deviceService.deleteConditionsInDevice(deviceName, conditionName).equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}