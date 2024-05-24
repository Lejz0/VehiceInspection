package org.example.vehicleregistration.service;

import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.InspectionTerm;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TermService {
    Optional<InspectionTerm> save(Long centerId, LocalTime time, LocalDate date, boolean isScheduled);

    List<InspectionTerm> getAllDates(Long centerId);

    Optional<InspectionTerm> findByDateAndTime(LocalDate date, LocalTime time, Long centerId);

     void reserveDate(Long id, Long termId);

}
