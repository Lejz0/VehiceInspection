package org.example.vehicleregistration.repository;

import org.example.vehicleregistration.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByUserUsername(String username);
    Vehicle findByRegistrationNumber(String plate);

    Vehicle findByVin(String vin);
}
