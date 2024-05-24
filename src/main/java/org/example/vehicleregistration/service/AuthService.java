package org.example.vehicleregistration.service;

import org.example.vehicleregistration.model.User;

public interface AuthService {
    User login(String username, String password);
}
