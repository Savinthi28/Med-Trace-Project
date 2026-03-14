package org.example.medtracebackend.controller;

import org.example.medtracebackend.dto.UserDTO;
import org.example.medtracebackend.entity.User;
import org.example.medtracebackend.service.UserService;
import org.example.medtracebackend.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<UserDTO>> register(@RequestBody User user) {
        UserDTO savedUser = userService.registerUser(user);
        return new ResponseEntity<>(new APIResponse<>(201, "User registered successfully", savedUser), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<UserDTO>>> getAll() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(new APIResponse<>(200, "All users fetched", users), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> getById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(new APIResponse<>(200, "User found", user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserDTO>> update(@PathVariable Long id, @RequestBody User user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(new APIResponse<>(200, "User updated successfully", updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new APIResponse<>(200, "User deleted successfully", "ID: " + id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<UserDTO>> login(@RequestParam String username, @RequestParam String password) {
        UserDTO user = userService.login(username, password);
        return new ResponseEntity<>(new APIResponse<>(200, "Login successful", user), HttpStatus.OK);
    }
}