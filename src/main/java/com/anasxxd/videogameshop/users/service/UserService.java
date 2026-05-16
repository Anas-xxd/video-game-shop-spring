package com.anasxxd.videogameshop.users.service;

import com.anasxxd.videogameshop.users.User;
import com.anasxxd.videogameshop.users.UserRole;
import com.anasxxd.videogameshop.users.dto.CreateUserRequest;
import com.anasxxd.videogameshop.users.dto.UserResponse;
import com.anasxxd.videogameshop.users.repo.UserRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.listAllUsers();
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        UserRole role = request.getRole();
        String loginName = request.getLoginKey();
        String name = request.getName();
        String password = request.getPassword();
        String email = request.getEmail();

        User user = new User(null, role, loginName, name, password, email);
        userValidate(user);

        Long newId = userRepository.insert(user);
        user.setUserID(newId);

        return getUserResponse(user);
    }

    private static @NonNull UserResponse getUserResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getUserID());
        response.setUserRole(user.getRole().name());
        response.setLoginKey(user.getLoginKey());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }

    private void userValidate(User u) {
        if (u.getRole() == null) {
            throw new IllegalArgumentException("Product type is required");
        }

        if (u.getLoginKey() == null || u.getLoginKey().isBlank()) {
            throw new IllegalArgumentException("Product company is required");
        }

        if (u.getName() == null || u.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (u.getPassword() == null || u.getPassword().isBlank()) {
            throw new IllegalArgumentException("Product company is required");
        }

        switch (u.getRole()) {
            case ADMIN -> {

            }

            case CUSTOMER -> {
                if (u.getEmail() == null || u.getEmail().isBlank()) {
                    throw new IllegalArgumentException("Product company is required");
                }
            }

            default -> throw new IllegalArgumentException("Unknown product type: " + u.getRole());
        }
    }
}
