package org.example.vehicleregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.example.vehicleregistration.model.enumerations.City;
import org.example.vehicleregistration.model.exceptions.InvalidCenterIdException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity
public class InspectionCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private City city;

    private String contactDetails;

    @OneToMany(mappedBy = "inspectionCenter")
    @JsonIgnore
    private List<VehicleInspection> inspections;

    @OneToMany(mappedBy = "inspectionCenter")
    @JsonIgnore
    private List<InspectionTerm> availableDates;

    public InspectionCenter() {
    }
    public InspectionCenter(String name, City city, String contactDetails) {
        this.name = name;
        this.city = city;
        this.contactDetails = contactDetails;
    }



}