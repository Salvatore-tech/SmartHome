package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//http://localhost:8080/smarthome/device

@RestController
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/allDevices")
    public ResponseEntity<List<Device>> getAllDevices(){
        List<Device> devices = deviceService.findAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping("/addDevice")
    public ResponseEntity<Device> addDevice(@RequestBody Device device){
        Device newDevice = deviceService.addDevice(device);
        return new ResponseEntity<>(newDevice, HttpStatus.CREATED);
    }

    @GetMapping("/deviceById")
    public ResponseEntity<Optional<Device>> getDeviceById(Long id){
        Optional<Device> device = deviceService.findDeviceByID(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDevice/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") String id){
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateDevice")
    public ResponseEntity<List<Device>> updateDevice(@RequestBody Device device){
        Device updatedDevice = deviceService.updateDevice(device);
        return new ResponseEntity(updatedDevice, HttpStatus.OK);
    }

    @GetMapping("/countDevice")
    public ResponseEntity<List<Device>> countDevices(){
        long devices = deviceService.countDevices();
        return new ResponseEntity(devices, HttpStatus.OK);
    }

}
