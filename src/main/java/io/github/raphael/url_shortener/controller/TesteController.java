package io.github.raphael.url_shortener.controller;

import io.github.raphael.url_shortener.dto.RequestShortUrlPostDTO;
import io.github.raphael.url_shortener.model.ShortUrl;
import io.github.raphael.url_shortener.repository.OriginalUrlRepository;
import io.github.raphael.url_shortener.repository.ShortUrlRepository;
import io.github.raphael.url_shortener.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class TesteController {

    private final ShortUrlRepository shortUrlRepository;
    private final OriginalUrlRepository originalUrlRepository;
    private final UserRepository userRepository;

    @PostMapping("/delete-url")
    public ResponseEntity<String> deleteUrl(@RequestBody String urlId){
        System.out.println(urlId);
        this.shortUrlRepository.deleteById(urlId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teste")
    public ResponseEntity<List<ShortUrl>> getUserHistory(
            @RequestParam("userId") @NotBlank String userId){

        List<ShortUrl> userShortenUrls = shortUrlRepository.findAllByUserId(userId);
        return ResponseEntity.ok(userShortenUrls);
    }
}
