package org.example.vehicleregistration.web.controller;


import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.Vehicle;
import org.example.vehicleregistration.service.UserService;
import org.example.vehicleregistration.service.VehicleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final UserService userService;
    private final VehicleService vehicleService;

    public VehicleController(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
    }


    @GetMapping
    public String getAllVehicles(Model model, @AuthenticationPrincipal User user)
    {
        String username = user.getUsername();

        List<Vehicle> vehicles = this.vehicleService.findForUser(username);

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("bodyContent", "vehicles");
        return "master-template";
    }


    @GetMapping("/add")
    public String addVehicle(Model model)
    {
        model.addAttribute("bodyContent", "addVehicle");
        return "master-template";
    }

    @PostMapping("/add")
    public String addVehicleConfirmed(@RequestParam String plateNumber,
                                      @RequestParam String make,
                                      @RequestParam String model,
                                      @RequestParam Integer year,
                                      @RequestParam String color,
                                      @RequestParam String vin,
                                      @AuthenticationPrincipal User user)
    {
        this.vehicleService.save(plateNumber, make, model, year, color, vin, user.getUsername());
        return "redirect:/vehicles";
    }


    @PostMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id)
    {
        this.vehicleService.deleteById(id);
        return "redirect:/vehicles";
    }
}
