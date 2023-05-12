package com.example.demo.dto;

import java.time.LocalDate;

public class ReservationDTO {

    private Long id;
    private Long lesseeId;
    private Long placeForRentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int leaseTerm;
    private double cost;

    public ReservationDTO() {

    }

    public ReservationDTO(Long id, Long lesseeId, Long placeForRentId, LocalDate startDate, LocalDate endDate, int leaseTerm, double cost) {
        this.id = id;
        this.lesseeId = lesseeId;
        this.placeForRentId = placeForRentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaseTerm = leaseTerm;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(Long lesseeId) {
        this.lesseeId = lesseeId;
    }

    public Long getPlaceForRentId() {
        return placeForRentId;
    }

    public void setPlaceForRentId(Long placeForRentId) {
        this.placeForRentId = placeForRentId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(int leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
