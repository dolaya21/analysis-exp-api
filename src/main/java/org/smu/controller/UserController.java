package org.smu.controller;

import org.smu.database.entity.User;
import org.smu.database.repository.UserRepository;
import org.smu.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO request) {
        if (userRepository.existsById(request.getUsername())) {
            return ResponseEntity.badRequest().body("User with this username already exists.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCountryOfBirth(request.getCountryOfBirth());
        user.setCountryOfResidence(request.getCountryOfResidence());
        user.setAge(request.getAge());
        user.setGender(request.getGender());

        userRepository.save(user);
        return ResponseEntity.ok("User created successfully.");
    }
}
