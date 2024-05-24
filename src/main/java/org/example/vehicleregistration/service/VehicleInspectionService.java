package org.example.vehicleregistration.service;

import org.example.vehicleregistration.model.VehicleInspection;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleInspectionService {
    List<VehicleInspection> findAll();

    List<VehicleInspection> findAllForUser(String username);
    Optional<VehicleInspection> findById(Long id);

    Optional<VehicleInspection> reserve(Long termId, String username, Long vehicleId, Long centerId);


}
