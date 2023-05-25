package com.example.demo.exceptions;

public class PlaceForRentNotFoundException extends RuntimeException {
    public PlaceForRentNotFoundException(String message) {
        super(message);
    }

    public PlaceForRentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaceForRentNotFoundException(Throwable cause) {
        super(cause);
    }
}
