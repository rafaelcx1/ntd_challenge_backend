package com.ntd.challenge.auth.v1.services.impl;

import com.ntd.challenge.auth.v1.controllers.public_apis.requests.UserCreateRequest;
import com.ntd.challenge.auth.v1.entities.User;
import com.ntd.challenge.auth.v1.entities.enums.UserStatusEnum;
import com.ntd.challenge.auth.v1.exceptions.types.ExistentUserException;
import com.ntd.challenge.auth.v1.repositories.UserRepository;
import com.ntd.challenge.auth.v1.services.UserService;
import com.ntd.challenge.auth.v1.utils.AuthContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registerUser(UserCreateRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ExistentUserException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatusEnum.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public User get() {
        User loggedUser = AuthContextUtils.getLoggedUser();

        if (loggedUser == null) {
            throw new AccessDeniedException("User not logged in");
        }

        return userRepository.getReferenceById(loggedUser.getId());
    }
}
