package org.example.vehicleregistration.model.exceptions;

public class InvalidVehicleIdException extends RuntimeException{
    public InvalidVehicleIdException() {
        super("Invalid Vehicle Id Exception");
    }
}
