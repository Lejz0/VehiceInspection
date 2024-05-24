package org.example.vehicleregistration.web.controller.Rest;

import org.example.vehicleregistration.model.InspectionTerm;
import org.example.vehicleregistration.service.TermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/terms")
public class TermsRestController {

    private final TermService termService;

    public TermsRestController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping("/{centerId}")
    public List<InspectionTerm> getAvailableTerms(@PathVariable Long centerId) {
       return this.termService.getAllDates(centerId);
    }
}
