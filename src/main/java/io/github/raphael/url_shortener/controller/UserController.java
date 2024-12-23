package io.github.raphael.url_shortener.controller;

import io.github.raphael.url_shortener.config.security.TokenService;
import io.github.raphael.url_shortener.dto.RequestLoginDTO;
import io.github.raphael.url_shortener.dto.RequestRegisterPostDTO;
import io.github.raphael.url_shortener.dto.ResponseLoginDTO;
import io.github.raphael.url_shortener.model.User;
import io.github.raphael.url_shortener.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestLoginDTO body){
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseLoginDTO(user.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RequestRegisterPostDTO newUser){
        Optional<User> existingUserEmail = userRepository.findByEmail(newUser.email().trim());
        if (existingUserEmail.isPresent()){
            return ResponseEntity.badRequest().body("101");
        }

        Optional<User> existingUsername = userRepository.findByUsername(newUser.username().trim());
        if (existingUsername.isPresent()){
            return ResponseEntity.badRequest().body("102");
        }

        User acceptedNewUser = new User(newUser);
        userRepository.save(acceptedNewUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
