package com.gruppo1.smarthome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppo1.smarthome.model.Device;
import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.service.RoomService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<String> getAllRooms() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(roomService.findAllRoom());
        return new ResponseEntity(result, HttpStatus.OK);
    }



    @GetMapping("/find/{name}")
    @ApiOperation(value = "Find room by name", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return room"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found")})
    public ResponseEntity<Optional<Room>> getRoomById(@ApiParam(value = "Room ID", required = true)
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
                new ResponseEntity<>(updatedRoom, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        return roomService.deleteRoom(name).equals(1) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO SS: not yet implemented
    @PostMapping("/addDevice/{roomName}/{deviceName}")
    @ApiOperation(value = "Add device in a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Device added"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> addDevice(@PathVariable("roomName") String roomName, @PathVariable("deviceName") String deviceName) {
        Device device = roomService.addDevice(deviceName, roomName);
        return Objects.nonNull(device) ?
                new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findDevices/{roomName}")
    @ApiOperation(value = "Find all devices in a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return list devices"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<List<Device>> findDevices(@PathVariable("roomName") String roomName) {
        List<Device> devices = roomService.findDevicesInRoom(roomName);
        return devices.size() > 0 ?  new ResponseEntity<>(devices, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteDevice/{nameDevice}")
    @ApiOperation(value = "Delete device from a room", tags = {"Room"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Device deleted"),
            @ApiResponse(code = 404, message = "Not Found - returned on resource not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 405, message = "Method Not Allowed"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<Device> deleteDevice(@PathVariable("nameDevice") String nameDevice) {
        Device device = roomService.deleteDevice(nameDevice);
        return Objects.nonNull(device) ?
                new ResponseEntity(device, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}