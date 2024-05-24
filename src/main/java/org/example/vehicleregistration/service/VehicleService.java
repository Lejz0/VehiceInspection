package org.example.vehicleregistration.service;

import org.aspectj.apache.bcel.classfile.Module;
import org.example.vehicleregistration.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    List<Vehicle> findAll();

    List<Vehicle> findForUser(String username);

    Optional<Vehicle> findById(Long id);

    Optional<Vehicle> findByPlate(String plate);

    Optional<Vehicle> findByVin(String vin);

    Optional<Vehicle> save(String registrationNumber, String make, String model, int year, String color, String vin, String username);

    Optional<Vehicle> edit(Long id, String registrationNumber, String make, String model, int year, String color, String vin, String username);

    void deleteById(Long id);
}
