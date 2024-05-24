package org.example.vehicleregistration.service;

import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
