package com.gruppo1.smarthome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.SmartHomeItem;
import com.gruppo1.smarthome.service.DeviceService;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.json.JSONObject;
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

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all devices installed", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<String> getAllDevices() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(deviceService.findAllDevices());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> addDevice(@RequestBody String deviceString) throws JSONException {
        JSONObject device = new JSONObject(deviceString);
        Device newDevice = deviceService.addDevice(device);
        return Objects.nonNull(newDevice) ? new ResponseEntity<>(newDevice, HttpStatus.CREATED) : new ResponseEntity<>(newDevice, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return device"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<SmartHomeItem>> getDeviceByName(@ApiParam(value = "Device name", required = true)
                                                               @PathVariable("name") String name) {
       Device device = deviceService.findDeviceByName(name);
        return Objects.nonNull(device) ? new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{name}")
    @ApiOperation(value = "Delete device by name", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteDevice(@ApiParam(value = "Device ID", required = true)
                                          @PathVariable("name") String name) {
        return deviceService.deleteDevice(name).equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Device> updateDevice(@PathVariable("name") String name, @RequestBody Device updatedDevice) {
        Device result = deviceService.updateDevice(name, updatedDevice);
        return Objects.nonNull(result) ?
                new ResponseEntity<>(result, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO Internal error
    @GetMapping("/count")
    @ApiOperation(value = "Count devices", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Number of devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity countDevices() {
        long devices = deviceService.countDevices();
        return new ResponseEntity(devices, HttpStatus.OK);
    }
}