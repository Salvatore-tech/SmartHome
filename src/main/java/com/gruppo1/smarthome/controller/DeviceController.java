package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/addDevice")
    public ResponseEntity<Device> addDevice(Device device){
        Device newDevice = deviceService.addDevice(device);
        return new ResponseEntity<>(newDevice, HttpStatus.OK);
    }

    @GetMapping("/deviceById")
    public ResponseEntity<Optional<Device>> getDeviceById(Long id){
        Optional<Device> device = deviceService.findDeviceByID(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @GetMapping("/deleteDevice")
    public ResponseEntity<List<Device>> deleteDevice(Long id){
        List<Device> devices = deviceService.deleteDevice(id);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @GetMapping("/updateDevice/")
    public ResponseEntity<List<Device>> updateDevice(Device device){
        Device updatedDevice = deviceService.updateDevice(device);
        return new ResponseEntity(updatedDevice, HttpStatus.OK);
    }

    @GetMapping("/countDevice")
    public ResponseEntity<List<Device>> countDevices(){
        long devices = deviceService.countDevices();
        return new ResponseEntity(devices, HttpStatus.OK);
    }

}
