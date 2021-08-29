package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "Room", description = "Rest API for Room", tags = {"Room"})
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all rooms", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return rooms"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.findAllRoom();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Room Added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        Room newRoom = roomService.addRoom(room);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find room by name", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return room"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Optional<Room>> getRoomById(@ApiParam(value = "Room ID", required = true)
                                                          @PathVariable("name") String name){
        Optional<Room> room = roomService.findRoomByName(name);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{name}")
    @ApiOperation(value = "Delete room by name", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Room deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteRoom(@ApiParam(value = "Room ID", required = true)
                                            @PathVariable("name") String name){
        return roomService.deleteRoom(name) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Room updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Room> updateRoom(@PathVariable("name") String name, @RequestBody Room room){
        Room updatedRoom = roomService.updateRoom(name, room);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @PostMapping("/addDevice/{nameRoom}")
    @ApiOperation(value = "Add device in a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Optional<Device>> addDevice(@PathVariable("nameRoom") String nameRoom, @RequestBody String nameDevice){
        Optional<Device> device = roomService.addDevice(nameDevice, nameRoom);
        return device.isPresent() ?
                new ResponseEntity<>(device, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findDevices/{nameRoom}")
    @ApiOperation(value = "Find all devices in a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return list devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Optional<List<Device>>> findDevices(@PathVariable("nameRoom") String nameRoom){
        Optional<List<Device>> listDevices = roomService.findDevices(nameRoom);
        return new ResponseEntity<>(listDevices, HttpStatus.OK);
    }

    @DeleteMapping ("/deleteDevice/{nameDevice}")
    @ApiOperation(value = "Delete device from a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Optional<Device>> deleteDevice(@PathVariable("nameDevice") String nameDevice){
        Optional<Device> device = roomService.deleteDevice(nameDevice);
        return device.isPresent() ?
                new ResponseEntity<>(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}