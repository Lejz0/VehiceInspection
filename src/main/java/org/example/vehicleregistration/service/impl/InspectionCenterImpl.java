package org.example.vehicleregistration.service.impl;

import jakarta.transaction.Transactional;
import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.InspectionTerm;
import org.example.vehicleregistration.model.enumerations.City;
import org.example.vehicleregistration.model.exceptions.InvalidCenterIdException;
import org.example.vehicleregistration.repository.CenterRepository;
import org.example.vehicleregistration.repository.InspectionTermRepository;
import org.example.vehicleregistration.service.InspectionCenterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InspectionCenterImpl implements InspectionCenterService {
    private final CenterRepository centerRepository;

    private final InspectionTermRepository inspectionTermRepository;

    public InspectionCenterImpl(CenterRepository centerRepository, InspectionTermRepository inspectionTermRepository) {
        this.centerRepository = centerRepository;
        this.inspectionTermRepository = inspectionTermRepository;
    }

    @Override
    public List<InspectionCenter> findAll() {
        return this.centerRepository.findAll();
    }

    @Override
    public Optional<InspectionCenter> findById(Long id) {
        InspectionCenter center = this.centerRepository.findById(id).orElseThrow(InvalidCenterIdException::new);
        return Optional.of(center);
    }

    @Override
    @Transactional
    public Optional<InspectionCenter> save(String name, City city, String contactDetails) {
        InspectionCenter center = new InspectionCenter(name, city, contactDetails);
        this.centerRepository.save(center);
        return Optional.of(center);
    }

    @Override
    public List<InspectionCenter> findAllByCity(City city) {
        return this.centerRepository.findAllByCity(city);
    }

    @Override
    @Transactional
    public void generateYearlyTerms(Long centerId) {
        InspectionCenter center = this.centerRepository.findById(centerId).orElseThrow(InvalidCenterIdException::new);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        LocalTime openingTime = LocalTime.of(8, 0);
        LocalTime closingTime = LocalTime.of(16, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");

        List<InspectionTerm> terms = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            LocalTime currentTime = openingTime;
            while (!currentTime.isAfter(closingTime)) {
                InspectionTerm term = new InspectionTerm();
                term.setInspectionCenter(center);
                LocalDate inspectionDate = startDate;
                LocalTime inspectionTime = currentTime;
                term.setDate(inspectionDate);
                term.setTime(inspectionTime);
                term.setScheduled(false);

                terms.add(term);
                currentTime = currentTime.plusMinutes(30);
            }
            startDate = startDate.plusDays(1);
        }

        center.getAvailableDates().clear();

        this.inspectionTermRepository.saveAll(terms);

        center.setAvailableDates(terms);
        this.centerRepository.save(center);
    }


}
