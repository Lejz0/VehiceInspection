package org.example.vehicleregistration.web.controller;

import org.example.vehicleregistration.model.*;
import org.example.vehicleregistration.model.enumerations.Currency;
import org.example.vehicleregistration.model.exceptions.InvalidTermIdException;
import org.example.vehicleregistration.model.exceptions.InvalidVehicleIdException;
import org.example.vehicleregistration.service.InspectionCenterService;
import org.example.vehicleregistration.service.TermService;
import org.example.vehicleregistration.service.VehicleInspectionService;
import org.example.vehicleregistration.service.VehicleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inspection")
public class VehicleInspectionController {
    private final VehicleService vehicleService;
    private final VehicleInspectionService vehicleInspectionService;
    private final InspectionCenterService inspectionCenterService;
    private final TermService termService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    public VehicleInspectionController(VehicleService vehicleService, VehicleInspectionService vehicleInspectionService, InspectionCenterService inspectionCenterService, TermService termService) {
        this.vehicleService = vehicleService;
        this.vehicleInspectionService = vehicleInspectionService;
        this.inspectionCenterService = inspectionCenterService;
        this.termService = termService;
    }

    @GetMapping
    public String listAllVehicleInspectionsForUser(@AuthenticationPrincipal User user, Model model)
    {
        List<VehicleInspection> inspections = this.vehicleInspectionService.findAllForUser(user.getUsername());
        model.addAttribute("inspections", inspections);
        model.addAttribute("bodyContent", "vehicleInspections");
        return "master-template";
    }

    @GetMapping("/all")
    public String listAllVehicleInspections(Model model)
    {
        List<VehicleInspection> inspections = this.vehicleInspectionService.findAll();
        model.addAttribute("inspections", inspections);
        model.addAttribute("bodyContent", "vehicleInspections");
        return "master-template";
    }

    @GetMapping("/create/{id}")
    public String createInspection (@PathVariable Long id, Model model)
    {
        Vehicle vehicle = this.vehicleService.findById(id).orElseThrow(InvalidVehicleIdException::new);
        List<InspectionCenter> inspectionCenters = this.inspectionCenterService.findAll();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("centers", inspectionCenters);
        model.addAttribute("amount", 50 * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", Currency.EUR);
        model.addAttribute("bodyContent", "createInspection");
        return "master-template";    }

    @PostMapping("/create/{id}")
    public String saveInspection(@PathVariable Long id,
                                 @RequestParam Long center,
                                 @RequestParam LocalDate date,
                                 @RequestParam LocalTime time,
                                 @AuthenticationPrincipal User user)
    {
        System.out.println(date);
        System.out.println(time);
        InspectionTerm term = this.termService.findByDateAndTime(date, time, center).orElseThrow(InvalidTermIdException::new);

        this.vehicleInspectionService.reserve(term.getId(), user.getUsername(), id, center);
        return "redirect:/inspection";
    }

}
