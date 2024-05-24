package org.example.vehicleregistration.repository;

import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.enumerations.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<InspectionCenter, Long> {
    List<InspectionCenter> findAllByCity(City city);
}
