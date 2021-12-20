package com.gruppo1.smarthome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Api(value = "Room", description = "Rest API for Room", tags = {"Room"})
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "List all rooms", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return rooms"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<String> getAllRooms() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Room> rooms = roomService.findAllRoom();
        return rooms.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(rooms), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find room by name", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return room"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Room> getRoomById(@ApiParam(value = "Room ID", required = true)
                                            @PathVariable("name") String name) {
        Room room = roomService.findRoomByName(name);
        return Objects.nonNull(room) ? new ResponseEntity(room, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{name}")
    @ApiOperation(value = "Update room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Room updated"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<Room> updateRoom(@PathVariable("name") String name, @RequestBody Room updatedRoom) {
        Room result = roomService.updateRoom(name, updatedRoom);
        return Objects.nonNull(result) ?
                new ResponseEntity<>(updatedRoom, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add new room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Room Added"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room newRoom = roomService.addRoom(room);
        return Objects.nonNull(newRoom) ?
                new ResponseEntity<>(newRoom, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{name}")
    @ApiOperation(value = "Delete room by name", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Room deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> deleteRoom(@ApiParam(value = "Room ID", required = true)
                                        @PathVariable("name") String name) {
        return roomService.deleteRoom(name).equals(1) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/findDevices/{roomName}")
    @ApiOperation(value = "Find all devices in a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return list devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<String> findDevices(@PathVariable("roomName") String roomName) throws JsonProcessingException {
        List<Device> devices = roomService.findDevicesInRoom(roomName);
        ObjectMapper objectMapper = new ObjectMapper();
        return devices.size() > 0 ? new ResponseEntity<>(objectMapper.writeValueAsString(devices), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addDevice/{roomName}/{deviceName}")
    @ApiOperation(value = "Add device in a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> addDevice(@PathVariable("roomName") String roomName, @PathVariable("deviceName") String deviceName) {
        Device device = roomService.addDeviceToRoom(deviceName, roomName);
        return Objects.nonNull(device) ?
                new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteDevice/{deviceName}")
    @ApiOperation(value = "Delete device from a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> deleteDevice(@PathVariable("deviceName") String deviceName) {
        Device device = roomService.deleteDeviceFromRoom(deviceName);
        return Objects.nonNull(device) ?
                new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}