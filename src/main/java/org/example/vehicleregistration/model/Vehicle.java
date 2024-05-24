package org.example.vehicleregistration.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;

    private String make;

    private String model;

    private Integer vehicleYear;

    private String color;

    private String vin;

    private boolean hadInspection = false;

    @ManyToOne
    private User user;

    public Vehicle() {
    }

    public Vehicle(String registrationNumber, String make, String model, Integer vehicleYear, String color, String vin, User user) {
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.vehicleYear = vehicleYear;
        this.color = color;
        this.vin = vin;
        this.user = user;
    }
}
