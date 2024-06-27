package org.example.vehicleregistration.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.vehicleregistration.model.InspectionCenter;
import org.example.vehicleregistration.model.InspectionTerm;
import org.example.vehicleregistration.model.Vehicle;
import org.example.vehicleregistration.model.enumerations.City;
import org.example.vehicleregistration.model.enumerations.Role;
import org.example.vehicleregistration.model.exceptions.InvalidArgumentsException;
import org.example.vehicleregistration.service.InspectionCenterService;
import org.example.vehicleregistration.service.TermService;
import org.example.vehicleregistration.service.UserService;
import org.example.vehicleregistration.service.VehicleService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DataInitializer {
    private final VehicleService vehicleService;
    private final UserService userService;
    private final TermService termService;
    private final InspectionCenterService inspectionCenterService;

    public DataInitializer(VehicleService vehicleService, UserService userService, TermService termService, InspectionCenterService inspectionCenterService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.termService = termService;
        this.inspectionCenterService = inspectionCenterService;
    }


    @PostConstruct
    public void init() {
        try {
            if (!userExists("test3")) {
                this.userService.register("test3", "test3", "test3", "Lazo", "Nikoloski", Role.ROLE_ADMIN);
            }
            if (!userExists("test")) {
                this.userService.register("test", "test", "test", "Lazo", "Nikoloski", Role.ROLE_ADMIN);
            }

            if (!userExists("lazo")) {
                this.userService.register("lazo", "lazo", "lazo", "lazo", "lazo", Role.ROLE_USER);
            }

            if (!userExists("test2")) {
                this.userService.register("test2", "test2", "test2", "test2", "test2", Role.ROLE_USER);
            }

            if (vehicleService.findByPlate("KI 2656 AB").isEmpty()) {
                this.vehicleService.save("KI 2656 AB", "OPEL", "Astra", 1998, "SIVA", "1ASD12313", "lazo");
            }
            InspectionCenter center = new InspectionCenter("Технички преглед - 1", City.Кичево, "број?");
            InspectionCenter center2 = new InspectionCenter("Технички преглед - 2", City.Кичево, "број?");
            InspectionCenter savedCenter = this.inspectionCenterService.save(center.getName(), center.getCity(), center.getContactDetails()).orElseThrow(() -> new RuntimeException("Failed to save InspectionCenter"));
            InspectionCenter savedCenter2 = this.inspectionCenterService.save(center2.getName(), center2.getCity(), center2.getContactDetails()).orElseThrow(() -> new RuntimeException("Failed to save InspectionCenter"));

            this.inspectionCenterService.generateYearlyTerms(savedCenter.getId());
            this.inspectionCenterService.generateYearlyTerms(savedCenter2.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to check if user already exists
    private boolean userExists(String username) {
        try {
            this.userService.findByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

}
