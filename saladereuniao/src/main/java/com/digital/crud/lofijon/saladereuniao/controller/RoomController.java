package com.digital.crud.lofijon.saladereuniao.controller;

import com.digital.crud.lofijon.saladereuniao.exceptions.ObjectNotFoundException;
import com.digital.crud.lofijon.saladereuniao.model.Room;
import com.digital.crud.lofijon.saladereuniao.repository.RoomRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/v1")
@Api(value="Room Meeting")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    @ApiOperation(value = "List")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search by id")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") Long id)
            throws ObjectNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Room not found" + id
                ));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    @ApiOperation(value = "Create")
    public Room createRoom (@Valid @RequestBody Room room) {
        return roomRepository.save(room);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id,
                                           @Valid @RequestBody Room roomDetails)
            throws ObjectNotFoundException {

            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                        "Room not found!"
                    ));
            room.setName(roomDetails.getName());
            room.setDate(roomDetails.getDate());
            room.setStartHour(roomDetails.getStartHour());
            room.setEndHour(roomDetails.getEndHour());
        final Room updateRoom = roomRepository.save(room);
        return ResponseEntity.ok(updateRoom);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete")
    public Map<String, Boolean> deleteRoom(@PathVariable Long id) throws ObjectNotFoundException{
        Room room = roomRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException(
                        "Room not found for this action, please try again"
                ));
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

}

