package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.findAllRoom();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        Room newRoom = roomService.addRoom(room);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @GetMapping("/roomById")
    public ResponseEntity<Optional<Room>> getRoomById(String id){
        Optional<Room> room = roomService.findRoomByID(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoom/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") String id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateRoom")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room){
        Room updatedRoom = roomService.updateRoom(room);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }
}