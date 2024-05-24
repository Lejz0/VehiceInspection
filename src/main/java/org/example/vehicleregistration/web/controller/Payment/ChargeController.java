package org.example.vehicleregistration.web.controller.Payment;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.AllArgsConstructor;
import org.example.vehicleregistration.model.InspectionTerm;
import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.dto.ChargeRequest;
import org.example.vehicleregistration.model.enumerations.Currency;
import org.example.vehicleregistration.model.exceptions.InvalidTermIdException;
import org.example.vehicleregistration.service.StripePaymentService;
import org.example.vehicleregistration.service.TermService;
import org.example.vehicleregistration.service.VehicleInspectionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@AllArgsConstructor
public class ChargeController {

    private final StripePaymentService paymentService;

    private final VehicleInspectionService vehicleInspectionService;
    private final TermService termService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest,
                         @RequestParam Long vehicleId,
                         @RequestParam Long center,
                         @RequestParam LocalDate date,
                         @RequestParam LocalTime time,
                         @AuthenticationPrincipal User user) throws StripeException {
        chargeRequest.setDescription("Inspection Payment");
        chargeRequest.setCurrency(Currency.EUR);
        Charge charge = paymentService.charge(chargeRequest);

        if (charge.getStatus().equals("succeeded")) {
            InspectionTerm term = termService.findByDateAndTime(date, time, center).orElseThrow(InvalidTermIdException::new);
            vehicleInspectionService.reserve(term.getId(), user.getUsername(), vehicleId, center);
            return "redirect:/inspection";
        } else {
            return "payment-failed"; // redirect to a payment failure page
        }
    }
}
