package com.example.demo.controller;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.service.RentingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/reservations")
public class RentingController {

    @Autowired
    private RentingService rentingService;

    //TODO - return DTO + id!
    @PostMapping
    public Reservation addReservation(@RequestBody ReservationDTO reservationDTO) {
        return rentingService.addReservation(reservationDTO);
    }

    @PutMapping
    public Reservation updateReservation(@RequestBody ReservationDTO reservationDTO) {
        return rentingService.updateReservation(reservationDTO);
    }

    @GetMapping
    public Set<Reservation> getReservationsByParam(@RequestParam Long lessorId, @RequestParam Long placeForRentId) {

        if (lessorId != null) {
            return rentingService.getReservationsByLesseeId(lessorId);
        } else if (placeForRentId != null) {
            return rentingService.getReservationsByPlaceForRentId(placeForRentId);
        } else {
            return Set.of(); //TODO
        }
    }
}
