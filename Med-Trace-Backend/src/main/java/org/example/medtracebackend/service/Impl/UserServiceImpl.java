package org.example.medtracebackend.service.Impl;

import org.example.medtracebackend.dto.UserDTO;
import org.example.medtracebackend.entity.User;
import org.example.medtracebackend.exception.ResourceNotFoundException;
import org.example.medtracebackend.repository.UserRepo;
import org.example.medtracebackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO registerUser(User user) {
        if (user == null) {
            throw new NullPointerException("User object cannot be null");
        }
        return modelMapper.map(userRepo.save(user), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long id, User userDetails) {
        if (userDetails == null) {
            throw new NullPointerException("User update details cannot be null");
        }
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingUser.setFullName(userDetails.getFullName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setRole(userDetails.getRole());

        return modelMapper.map(userRepo.save(existingUser), UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null) {
            throw new NullPointerException("User ID cannot be null");
        }
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found for deletion");
        }
        userRepo.deleteById(id);
    }

    @Override
    public UserDTO login(String username, String password) {
        if (username == null || password == null) {
            throw new NullPointerException("Username or Password cannot be null");
        }
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        if (user.getPassword().equals(password)) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new ResourceNotFoundException("Invalid username or password");
        }
    }
}