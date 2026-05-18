package com.anasxxd.videogameshop.users;

import com.anasxxd.videogameshop.users.dto.AddUserRequest;
import com.anasxxd.videogameshop.users.dto.UpdateUserRequest;
import com.anasxxd.videogameshop.users.dto.UserResponse;
import com.anasxxd.videogameshop.users.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public UserResponse addUser(@Valid @RequestBody AddUserRequest request) {
        return userService.addUser(request);
    }

    @GetMapping()
    public List<UserResponse> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponse updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request
            ){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
