package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Condition;
import com.gruppo1.smarthome.model.Device;
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
@Api(value = "Device", description = "Rest API to manage the devices", tags = {"Device"})
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
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.findAllDevices();
        return !devices.isEmpty() ? ResponseEntity.ok(devices) : (ResponseEntity<List<Device>>) ResponseEntity.notFound();
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
        return Objects.nonNull(newDevice) ? new ResponseEntity<>(newDevice, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return device"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Device> getDeviceByName(@ApiParam(value = "Name of a valid device", required = true)
                                                  @PathVariable("name") String name) {
        Device device = deviceService.findDeviceByName(name);
        return Objects.nonNull(device) ? new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{name}")
    @ApiOperation(value = "Delete device by its name", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteDevice(@ApiParam(value = "Name of a valid device", required = true)
                                          @PathVariable("name") String name) {
        return deviceService.deleteDevice(name) == 1 ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Device> updateDevice(@ApiParam(value = "Name of a valid device") @PathVariable("name") String name, @RequestBody String deviceString) throws JSONException {
        JSONObject deviceJson = new JSONObject(deviceString);
        Device result = deviceService.updateDevice(name, deviceJson);
        return Objects.nonNull(result) ? ResponseEntity.ok(result) : (ResponseEntity<Device>) ResponseEntity.badRequest();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add automation's condition to a device", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Condition Added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PostMapping("/addCondition/{deviceName}/{sceneName}")
    public ResponseEntity<Condition> addCondition(@ApiParam(value = "Name of a valid device", required = true) @PathVariable("deviceName") String deviceName,
                                                  @ApiParam(value = "Name of a valid scene", required = true) @PathVariable("sceneName") String sceneName,
                                                  @ApiParam(value = "Automation condition", required = true) @RequestBody Condition condition) {
        Condition newCondition = deviceService.addConditionByDeviceName(deviceName, sceneName, condition);
        return Objects.nonNull(newCondition) ?
                new ResponseEntity<>(newCondition, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findConditions/{deviceName}")
    @ApiOperation(value = "Find conditions", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return condition"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<Condition>> getConditions(@ApiParam(value = "Name of a valid device", required = true)
                                                         @PathVariable("deviceName") String deviceName) {
        List<Condition> conditions = deviceService.findConditionsInDevice(deviceName);
        return !conditions.isEmpty() ? ResponseEntity.ok(conditions) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCondition/{deviceName}/{conditionName}")
    @ApiOperation(value = "Delete condition by device name and condition name", tags = {"Device"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Condition deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteCondition(@ApiParam(value = "Name of a valid device", required = true)
                                             @PathVariable("deviceName") String deviceName,
                                             @ApiParam(value = "Name of an existing automation's condition on the device", required = true)
                                             @PathVariable("conditionName") String conditionName) {
        return deviceService.deleteConditionsInDevice(deviceName, conditionName).equals(1) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}