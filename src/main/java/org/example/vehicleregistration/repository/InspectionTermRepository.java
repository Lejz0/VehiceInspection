package org.example.vehicleregistration.repository;

import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.InspectionTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface InspectionTermRepository extends JpaRepository<InspectionTerm, Long> {

    List<InspectionTerm> findByInspectionCenterAndIsScheduledFalse(InspectionCenter center);

    InspectionTerm findByDateAndTimeAndInspectionCenter(LocalDate date, LocalTime time, InspectionCenter center);
}
