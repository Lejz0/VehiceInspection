package org.example.vehicleregistration.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.example.vehicleregistration.model.dto.ChargeRequest;

public interface StripePaymentService {
    public Charge charge(ChargeRequest chargeRequest) throws StripeException;
}
