package com.example.demo.repository;

import com.example.demo.entity.PlaceForRent;
import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

   @Query("select r from Reservation r where r.startDate = :startDate and r.endDate = :endDate and r.placeForRent.id = :placeForRentId")
   Set<Reservation> findExistingReservation(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("placeForRentId") Long placeForRentId);

}
