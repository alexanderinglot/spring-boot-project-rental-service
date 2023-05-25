package com.example.demo.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "lessors")
public class Lessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName;
    public String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_for_rent_id")
    public Set<PlaceForRent> placesForRent;

    @OneToMany(mappedBy = "lessor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    public Set<Reservation> reservations;

    public Lessor() {

    }

    public Lessor(Long id, String firstName, String lastName, Set<PlaceForRent> placeForRent) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.placesForRent = placeForRent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<PlaceForRent> getPlacesForRent() {
        return placesForRent;
    }

    public void setPlacesForRent(Set<PlaceForRent> placesForRent) {
        this.placesForRent = placesForRent;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
