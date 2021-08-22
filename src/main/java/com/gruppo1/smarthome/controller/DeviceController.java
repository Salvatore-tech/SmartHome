package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.service.DeviceService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//http://localhost:8080/device

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
    public ResponseEntity<List<Device>> getAllDevices(){
        List<Device> devices = deviceService.findAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> addDevice(@RequestBody Device device){
        Device newDevice = deviceService.addDevice(device);
        return new ResponseEntity<>(newDevice, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find device by ID", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return device"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Optional<Device>> getDeviceById(@ApiParam(value = "Device ID", required = true)
                                                              @PathVariable("id") String id){
        Optional<Device> device = deviceService.findDeviceByID(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete device by ID", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteDevice(@ApiParam(value = "Device ID", required = true)
                                              @PathVariable("id") String id){
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Device> updateDevice(@RequestBody Device device){
        Device updatedDevice = deviceService.updateDevice(device);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @GetMapping("/count")
    @ApiOperation(value = "Count devices", tags = {"Device"})
    @ApiResponse(code = 200, message = "Number of devices")
    public ResponseEntity countDevices(){
        long devices = deviceService.countDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }
}
