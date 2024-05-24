package org.example.vehicleregistration.model.dto;

import lombok.Data;
import org.example.vehicleregistration.model.enumerations.Currency;

@Data
public class ChargeRequest {
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;

}
