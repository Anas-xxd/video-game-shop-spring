package com.anasxxd.videogameshop.users.service;

import com.anasxxd.videogameshop.users.User;
import com.anasxxd.videogameshop.users.UserRole;
import com.anasxxd.videogameshop.users.dto.CreateUserRequest;
import com.anasxxd.videogameshop.users.dto.UpdateUserRequest;
import com.anasxxd.videogameshop.users.dto.UserResponse;
import com.anasxxd.videogameshop.users.repo.UserRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> listUsers() {
        List<UserResponse> users = new ArrayList<>();

        for (User user : userRepository.listAllUsers()){
            users.add(getUserResponse(user));
        }

        return users;
    }

    public UserResponse getUserById(Long id){
        return getUserResponse(findUserOrThrow(id));
    }

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

    public UserResponse update(Long id, UpdateUserRequest request){
        User user = findUserOrThrow(id);

        if (request.getLoginKey() != null){
            user.setLoginKey(request.getLoginKey());
        }

        if (request.getName() != null){
            user.setName(request.getName());
        }

        if (request.getPassword() != null){
            user.setPassword(request.getPassword());
        }

        if (request.getEmail() != null){
            user.setEmail(request.getEmail());
        }

        userValidate(user);
        userRepository.update(user);
        return getUserResponse(user);
    }

    public void delete(Long id){
        findUserOrThrow(id);
        userRepository.delete(id);
    }

    private User findUserOrThrow(Long id){
        Optional<User> optionalUser = userRepository.findUser(id);

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }

        throw new IllegalArgumentException("The user with the id: " + id + " does not exist.");
    }

    private void userValidate(User u) {
        if (u.getRole() == null) {
            throw new IllegalArgumentException("User type is required");
        }

        if (u.getLoginKey() == null || u.getLoginKey().isBlank()) {
            throw new IllegalArgumentException("User company is required");
        }

        if (u.getName() == null || u.getName().isBlank()) {
            throw new IllegalArgumentException("User name is required");
        }

        if (u.getPassword() == null || u.getPassword().isBlank()) {
            throw new IllegalArgumentException("User company is required");
        }

        switch (u.getRole()) {
            case ADMIN -> {

            }

            case CUSTOMER -> {
                if (u.getEmail() == null || u.getEmail().isBlank()) {
                    throw new IllegalArgumentException("User company is required");
                }
            }

            default -> throw new IllegalArgumentException("Unknown User type: " + u.getRole());
        }
    }
}
