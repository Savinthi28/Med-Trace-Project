package org.example.medtracebackend.service;

import org.example.medtracebackend.dto.UserDTO;
import org.example.medtracebackend.entity.User;
import java.util.List;

public interface UserService {
    UserDTO registerUser(User user);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    UserDTO login(String username, String password);
}
