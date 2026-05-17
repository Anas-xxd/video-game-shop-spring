package com.anasxxd.videogameshop.users;

import com.anasxxd.videogameshop.users.dto.CreateUserRequest;
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

    @GetMapping()
    public List<UserResponse> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping()
    public UserResponse creatUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PatchMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
            ){
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }
}
