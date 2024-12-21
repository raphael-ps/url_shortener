package io.github.raphael.url_shortener.controller;

import io.github.raphael.url_shortener.dto.RequestUserPostDTO;
import io.github.raphael.url_shortener.model.User;
import io.github.raphael.url_shortener.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RequestUserPostDTO newUser){
        Optional<User> existingUserEmail = userRepository.findByEmail(newUser.email().trim());
        if (existingUserEmail.isPresent()){
            return ResponseEntity.badRequest().body("101");
        }

        Optional<User> existingUsername = userRepository.findByUsername(newUser.username().trim());
        if (existingUsername.isPresent()){
            return ResponseEntity.badRequest().body("102");
        }

        User accepetedNewUser = new User(newUser);
        userRepository.save(accepetedNewUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
