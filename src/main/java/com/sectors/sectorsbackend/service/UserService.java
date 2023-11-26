package com.sectors.sectorsbackend.service;

import com.sectors.sectorsbackend.domain.User;
import com.sectors.sectorsbackend.dto.UserDTO;
import com.sectors.sectorsbackend.exception.UserAlreadyExistsException;
import com.sectors.sectorsbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        userRepository.findByName(userDTO.getUsername())
                .ifPresent(u -> {
                    System.out.println(userDTO.getUsername());
                    throw new UserAlreadyExistsException("User already exists with name: " + userDTO.getUsername());
                });

        User user = new User();
        user.setName(userDTO.getUsername());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return UserDTO.builder()
                .username(user.getName())
                .password(encodedPassword)
                .build();
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .password(user.getPassword())
                .build();
    }

}
