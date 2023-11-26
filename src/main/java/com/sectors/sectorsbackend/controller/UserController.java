package com.sectors.sectorsbackend.controller;

import com.sectors.sectorsbackend.dto.UserDTO;
import com.sectors.sectorsbackend.exception.UserAlreadyExistsException;
import com.sectors.sectorsbackend.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        try {
            Iterable<UserDTO> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Error accessing data", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> processRegister(@RequestBody UserDTO user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        try {
            UserDTO createdUser = userService.saveUser(
                    UserDTO.builder()
                            .username(user.getUsername())
                            .password(encodedPassword)
                            .build());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (HttpServerErrorException.ServiceUnavailable e) {
            return new ResponseEntity<>("Service is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity<>("Faulty request body", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
