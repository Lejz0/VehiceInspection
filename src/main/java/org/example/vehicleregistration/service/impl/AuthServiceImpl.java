package org.example.vehicleregistration.service.impl;

import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.exceptions.InvalidArgumentsException;
import org.example.vehicleregistration.model.exceptions.InvalidUserCredentialsException;
import org.example.vehicleregistration.repository.UserRepository;
import org.example.vehicleregistration.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
        {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
