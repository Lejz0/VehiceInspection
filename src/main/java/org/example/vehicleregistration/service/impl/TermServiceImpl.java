package org.example.vehicleregistration.service.impl;

import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.InspectionTerm;
import org.example.vehicleregistration.model.exceptions.InvalidCenterIdException;
import org.example.vehicleregistration.model.exceptions.InvalidTermIdException;
import org.example.vehicleregistration.repository.CenterRepository;
import org.example.vehicleregistration.repository.InspectionTermRepository;
import org.example.vehicleregistration.service.TermService;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TermServiceImpl implements TermService {

    private final InspectionTermRepository inspectionTermRepository;
    private final CenterRepository centerRepository;

    public TermServiceImpl(InspectionTermRepository inspectionTermRepository, CenterRepository centerRepository) {
        this.inspectionTermRepository = inspectionTermRepository;
        this.centerRepository = centerRepository;
    }

    @Override
    public Optional<InspectionTerm> save(Long centerId, LocalTime time, LocalDate date, boolean isScheduled) {
        InspectionCenter center = this.centerRepository.findById(centerId).orElseThrow(InvalidCenterIdException::new);
        InspectionTerm term = new InspectionTerm(center, date, time, isScheduled);

        this.inspectionTermRepository.save(term);

        return Optional.of(term);
    }

    @Override
    public List<InspectionTerm> getAllDates(Long centerId) {
        InspectionCenter center = this.centerRepository.findById(centerId).orElseThrow(InvalidCenterIdException::new);
        return this.inspectionTermRepository.findByInspectionCenterAndIsScheduledFalse(center);
    }

    @Override
    public Optional<InspectionTerm> findByDateAndTime(LocalDate date, LocalTime time, Long centerId) {
        InspectionCenter center = this.centerRepository.findById(centerId).orElseThrow(InvalidCenterIdException::new);
        InspectionTerm term = this.inspectionTermRepository.findByDateAndTimeAndInspectionCenter(date, time, center);
        return Optional.of(term);
    }

    @Override
    public void reserveDate(Long id, Long termId) {
        InspectionCenter center = this.centerRepository.findById(id).orElseThrow(InvalidCenterIdException::new);
        InspectionTerm term = this.inspectionTermRepository.findById(termId).orElseThrow(InvalidTermIdException::new);

        term.setScheduled(false);

        this.inspectionTermRepository.save(term);
    }
}
