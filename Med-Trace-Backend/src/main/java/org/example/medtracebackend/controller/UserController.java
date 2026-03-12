package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.UserDTO;
import org.example.medtracebackend.entity.User;
import org.example.medtracebackend.service.UserService;
import org.example.medtracebackend.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public APIResponse<UserDTO> register(@RequestBody User user) {
        UserDTO savedUser = userService.registerUser(user);
        return new APIResponse<>(201, "User registered successfully", savedUser);
    }

    @GetMapping
    public APIResponse<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.getAllUsers();
        return new APIResponse<>(200, "All users fetched", users);
    }

    @GetMapping("/{id}")
    public APIResponse<UserDTO> getById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new APIResponse<>(200, "User found", user);
    }

    @PutMapping("/{id}")
    public APIResponse<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return new APIResponse<>(200, "User updated successfully", updatedUser);
    }

    @DeleteMapping("/{id}")
    public APIResponse<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return new APIResponse<>(200, "User deleted successfully", "ID: " + id);
    }

    @PostMapping("/login")
    public APIResponse<UserDTO> login(@RequestParam String username, @RequestParam String password) {
        UserDTO user = userService.login(username, password);
        return new APIResponse<>(200, "Login successful", user);
    }
}