package org.example.vehicleregistration.service;

import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.VehicleInspection;
import org.example.vehicleregistration.model.enumerations.City;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InspectionCenterService {

    List<InspectionCenter> findAll();


    Optional<InspectionCenter> findById(Long id);

    Optional<InspectionCenter> save(String name, City city, String contactDetails);

    List<InspectionCenter> findAllByCity(City city);

    void generateYearlyTerms(Long centerId);

}
