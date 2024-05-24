package org.example.vehicleregistration.service.impl;

import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.Vehicle;
import org.example.vehicleregistration.model.exceptions.InvalidVehicleIdException;
import org.example.vehicleregistration.repository.UserRepository;
import org.example.vehicleregistration.repository.VehicleRepository;
import org.example.vehicleregistration.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;


    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Vehicle> findAll() {
        return this.vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> findForUser(String username) {
        return this.vehicleRepository.findAllByUserUsername(username);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(InvalidVehicleIdException::new);
        return Optional.of(vehicle);
    }

    @Override
    public Optional<Vehicle> findByPlate(String plate) {
        Vehicle vehicle = this.vehicleRepository.findByRegistrationNumber(plate);
        return Optional.of(vehicle);
    }

    @Override
    public Optional<Vehicle> findByVin(String vin) {
        Vehicle vehicle = this.vehicleRepository.findByVin(vin);
        return Optional.of(vehicle);
    }

    @Override
    public Optional<Vehicle> save(String registrationNumber, String make, String model, int year, String color, String vin, String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Vehicle vehicle = new Vehicle(registrationNumber, make, model, year, color, vin, user);
        this.vehicleRepository.save(vehicle);
        return Optional.of(vehicle);

    }

    @Override
    public Optional<Vehicle> edit(Long id, String registrationNumber, String make, String model, int year, String color, String vin, String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(InvalidVehicleIdException::new);

        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setVehicleYear(year);
        vehicle.setVin(vin);
        vehicle.setUser(user);

        this.vehicleRepository.save(vehicle);
        return Optional.of(vehicle);
    }

    @Override
    public void deleteById(Long id) {
        this.vehicleRepository.deleteById(id);
    }
}
