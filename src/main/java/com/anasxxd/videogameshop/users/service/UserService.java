package com.anasxxd.videogameshop.users.service;

import com.anasxxd.videogameshop.users.User;
import com.anasxxd.videogameshop.users.UserRole;
import com.anasxxd.videogameshop.users.dto.AddUserRequest;
import com.anasxxd.videogameshop.users.dto.UpdateUserRequest;
import com.anasxxd.videogameshop.users.dto.UserResponse;
import com.anasxxd.videogameshop.users.repo.UserRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse addUser(AddUserRequest request) {
        UserRole role = request.getUserRole();
        String loginName = request.getLoginKey();
        String name = request.getName();
        String password = request.getPassword();
        String email = request.getEmail();

        User user = new User(null, role, loginName, name, password, email);
        validateUser(user);

        Long newUserId = userRepository.addUser(user);
        user.setUserID(newUserId);

        return getUserResponse(user);
    }

    public List<UserResponse> listUsers() {
        List<UserResponse> users = new ArrayList<>();

        for (User user : userRepository.listUsers()){
            users.add(getUserResponse(user));
        }

        return users;
    }

    public UserResponse getUser(Long userId){
        return getUserResponse(findUserOrThrow(userId));
    }

    public UserResponse updateUser(Long userId, UpdateUserRequest request){
        User user = findUserOrThrow(userId);

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

        validateUser(user);
        userRepository.updateUser(user);
        return getUserResponse(user);
    }

    public void deleteUser(Long userId){
        findUserOrThrow(userId);
        userRepository.deleteUser(userId);
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

    private User findUserOrThrow(Long userId){
        Optional<User> optionalUser = userRepository.findUser(userId);

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }

        throw new IllegalArgumentException("The user with the id: " + userId + " does not exist.");
    }

    private void validateUser(User user) {
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role is required");
        }

        if (user.getLoginKey() == null || user.getLoginKey().isBlank()) {
            throw new IllegalArgumentException("User login key is required");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("User name is required");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("User password is required");
        }

        switch (user.getRole()) {
            case ADMIN -> {

            }

            case CUSTOMER -> {
                if (user.getEmail() == null || user.getEmail().isBlank()) {
                    throw new IllegalArgumentException("User email is required");
                }
            }

            default -> throw new IllegalArgumentException("Unknown User type: " + user.getRole());
        }
    }
}
