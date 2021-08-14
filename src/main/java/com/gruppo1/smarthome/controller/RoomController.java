package com.gruppo1.smarthome.controller;

import com.gruppo1.smarthome.model.Room;
import com.gruppo1.smarthome.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room/")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/allRooms/")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.findAllRoom();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/roomById/")
    public ResponseEntity<Optional<Room>> getRoomById(Long id){
        Optional<Room> room = roomService.findRoomByID(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping("/updateRoom/")
    public ResponseEntity<Room> updateScene(Room room){
        Room updatedRoom = roomService.updateRoom(room);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @GetMapping("/addRoom/")
    public ResponseEntity<Room> addScene(Room room){
        Room newRoom = roomService.addRoom(room);
        return new ResponseEntity<>(newRoom, HttpStatus.OK);
    }

}