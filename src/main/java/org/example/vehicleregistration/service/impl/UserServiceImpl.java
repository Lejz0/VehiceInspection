package org.example.vehicleregistration.service.impl;

import org.example.vehicleregistration.model.User;
import org.example.vehicleregistration.model.enumerations.Role;
import org.example.vehicleregistration.model.exceptions.InvalidArgumentsException;
import org.example.vehicleregistration.model.exceptions.InvalidUsernameOrPasswordException;
import org.example.vehicleregistration.model.exceptions.PasswordsDoNotMatchException;
import org.example.vehicleregistration.model.exceptions.UsernameAlreadyExistsException;
import org.example.vehicleregistration.repository.UserRepository;
import org.example.vehicleregistration.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
        {
            throw new InvalidUsernameOrPasswordException();
        }
        if (!password.equals(repeatPassword))
        {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent())
        {
            throw new UsernameAlreadyExistsException(username);
        }
        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return userRepository.save(user);


    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
