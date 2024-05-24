package org.example.vehicleregistration.service.impl;


import org.example.vehicleregistration.model.*;
import org.example.vehicleregistration.model.exceptions.InvalidCenterIdException;
import org.example.vehicleregistration.model.exceptions.InvalidInspectionId;
import org.example.vehicleregistration.model.exceptions.InvalidTermIdException;
import org.example.vehicleregistration.model.exceptions.InvalidVehicleIdException;
import org.example.vehicleregistration.repository.*;
import org.hibernate.annotations.processing.Find;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleInspectionService implements org.example.vehicleregistration.service.VehicleInspectionService {

    private final UserRepository userRepository;

    private final VehicleRepository vehicleRepository;
    private final CenterRepository centerRepository;
    private final InspectionRepository inspectionRepository;

    private final InspectionTermRepository inspectionTermRepository;

    public VehicleInspectionService(UserRepository userRepository, VehicleRepository vehicleRepository, CenterRepository centerRepository, InspectionRepository inspectionRepository, InspectionTermRepository inspectionTermRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.centerRepository = centerRepository;
        this.inspectionRepository = inspectionRepository;
        this.inspectionTermRepository = inspectionTermRepository;
    }

    @Override
    public List<VehicleInspection> findAll() {
        return this.inspectionRepository.findAll();
    }

    public List<VehicleInspection> findAllForUser(String username){
        return this.inspectionRepository.findAllByUserUsername(username);
    }

    @Override
    public Optional<VehicleInspection> findById(Long id) {
        VehicleInspection vehicleInspection = this.inspectionRepository.findById(id).orElseThrow(InvalidInspectionId::new);
        return Optional.of(vehicleInspection);
    }

    @Override
    public Optional<VehicleInspection> reserve(Long termId, String username, Long vehicleId, Long centerId) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(InvalidVehicleIdException::new);
        InspectionCenter center = this.centerRepository.findById(centerId).orElseThrow(InvalidCenterIdException::new);
        InspectionTerm term = this.inspectionTermRepository.findById(termId).orElseThrow(InvalidTermIdException::new);

        vehicle.setHadInspection(true);
        term.setScheduled(true);

        VehicleInspection vehicleInspection = new VehicleInspection(term, user, vehicle, center);

        this.vehicleRepository.save(vehicle);
        this.inspectionTermRepository.save(term);
        this.centerRepository.save(center);
        this.inspectionRepository.save(vehicleInspection);
        return Optional.of(vehicleInspection);
    }
}
