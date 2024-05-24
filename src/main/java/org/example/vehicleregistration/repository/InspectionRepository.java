package org.example.vehicleregistration.repository;

import org.example.vehicleregistration.model.VehicleInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<VehicleInspection, Long> {
    List<VehicleInspection> findAllByUserUsername(String username);
}
