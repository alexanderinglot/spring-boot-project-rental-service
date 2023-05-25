package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.entity.Lessee;
import com.example.demo.entity.Lessor;
import com.example.demo.entity.PlaceForRent;
import com.example.demo.entity.Reservation;
import com.example.demo.exceptions.*;
import com.example.demo.repository.LesseeRepository;
import com.example.demo.repository.LessorRepository;
import com.example.demo.repository.PlaceForRentRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RentingService {

    @Autowired
    private LessorRepository lessorRepository;

    @Autowired
    private LesseeRepository lesseeRepository;

    @Autowired
    private PlaceForRentRepository placeForRentRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationDTO addReservation(ReservationDTO reservation) {
        Set<Reservation> existingReservations = reservationRepository.findExistingReservation(reservation.getStartDate(), reservation.getEndDate(), reservation.getPlaceForRentId());

        if (!existingReservations.isEmpty())
            throw new ReservationAlreadyExistsException("This place is already booked in given period - please select another date");

        Reservation save = reservationRepository.save(convertReservationDTOtoEntity(reservation));

        return convertReservationEntityToDTO(save);
    }

    public ReservationDTO updateReservation(ReservationDTO reservation) {
        Optional<Reservation> existingReservation = reservationRepository.findById(reservation.getId());

        if (existingReservation.isEmpty())
            throw new ReservationNotFoundException("Reservation id not found - " + reservation.getId());

        Reservation save = reservationRepository.save(convertReservationDTOtoEntity(reservation));

        return convertReservationEntityToDTO(save);
    }

    public Set<ReservationDTO> getReservationsByLessorId(Long lessorId) {
        Optional<Lessor> foundLessor = lessorRepository.findById(lessorId);
        Set<ReservationDTO> reservationDTOS = new HashSet<>();

        if (foundLessor.isEmpty())
            throw new LessorNotFoundException("Lessor id not found - " + lessorId);

        foundLessor.get().getReservations().forEach(reservation -> {
            ReservationDTO reservationDTO = convertReservationEntityToDTO(reservation);
            reservationDTOS.add(reservationDTO);
        });

        return reservationDTOS;
    }

    public Set<ReservationDTO> getReservationsByPlaceForRentId(Long placeForRentId) {
        Optional<PlaceForRent> foundPlaceForRent = placeForRentRepository.findById(placeForRentId);
        Set<ReservationDTO> reservationDTOS = new HashSet<>();

        if (foundPlaceForRent.isEmpty())
            throw new PlaceForRentNotFoundException("Place for rent id not found - " + placeForRentId);

        foundPlaceForRent.get().getReservations().forEach(reservation -> {
            ReservationDTO reservationDTO = convertReservationEntityToDTO(reservation);
            reservationDTOS.add(reservationDTO);
        });

        return reservationDTOS;
    }

    private Reservation convertReservationDTOtoEntity(ReservationDTO reservationDTO) {
        Optional<Lessor> foundLessor = lessorRepository.findById(reservationDTO.getLessorId());
        Optional<Lessee> foundLessee = lesseeRepository.findById(reservationDTO.getLesseeId());

        if (foundLessor.isEmpty())
            throw new LessorNotFoundException("Lessor id not found - " + reservationDTO.getLessorId());

        if (foundLessee.isEmpty())
            throw new LesseeNotFoundException("Lessee id not found - " + reservationDTO.getLesseeId());

        Optional<PlaceForRent> foundPlaceForRent = placeForRentRepository.findById(reservationDTO.getPlaceForRentId());

        if (foundPlaceForRent.isEmpty())
            throw new PlaceForRentNotFoundException("Place for rent id not found - " + reservationDTO.getPlaceForRentId());

        return new Reservation(null, foundLessor.get(), foundLessee.get(), foundPlaceForRent.get(), reservationDTO.getStartDate(), reservationDTO.getEndDate(), calculateCost(reservationDTO));
    }

    private ReservationDTO convertReservationEntityToDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(), reservation.getLessee().getId(), reservation.getLessor().getId(), reservation.getPlaceForRent().getId(), reservation.getStartDate(), reservation.getEndDate(), ChronoUnit.DAYS.between(reservation.getEndDate(), reservation.getStartDate()), reservation.getCost());
    }

    private double calculateCost(ReservationDTO reservationDTO) {
        Optional<PlaceForRent> foundPlaceForRent = placeForRentRepository.findById(reservationDTO.getPlaceForRentId());
        PlaceForRent placeForRent = null;

        if (foundPlaceForRent.isPresent())
            placeForRent = foundPlaceForRent.get();

        return placeForRent != null ? reservationDTO.getLeaseTerm() * placeForRent.getUnitPrice() * placeForRent.getArea() : 0.0;
    }
}
