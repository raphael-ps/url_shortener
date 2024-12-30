package io.github.raphael.url_shortener.controller;

import io.github.raphael.url_shortener.dto.RequestShortUrlPostDTO;
import io.github.raphael.url_shortener.dto.RequestValidatePasswordDTO;
import io.github.raphael.url_shortener.model.OriginalUrl;
import io.github.raphael.url_shortener.model.ShortUrl;
import io.github.raphael.url_shortener.repository.OriginalUrlRepository;

import io.github.raphael.url_shortener.repository.ShortUrlRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class ShortUrlController {

    private final OriginalUrlRepository originalUrlRepository;
    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public ShortUrlController(ShortUrlRepository shortUrlRepository, OriginalUrlRepository originalUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.originalUrlRepository = originalUrlRepository;
    }

    @GetMapping("/stats/{nickname}")
    public ResponseEntity<ShortUrl> urlStats(@PathVariable String nickname){
        System.out.println(nickname);
        ShortUrl url = shortUrlRepository.findByNickname(nickname).orElseThrow();

        return ResponseEntity.ok(url);
    }
    @PostMapping("/shorten")
    public ResponseEntity<String> registerUrl(@RequestBody @Valid RequestShortUrlPostDTO newUrl){

        Optional<OriginalUrl> existingOriginalUrl = originalUrlRepository.findByUrl(newUrl.originalUrl());
        OriginalUrl originalUrl;

        if (existingOriginalUrl.isPresent()) {
            originalUrl = existingOriginalUrl.get();
            originalUrl.setTimes_shortened(originalUrl.getTimes_shortened() + 1);
        } else {
            originalUrl = new OriginalUrl(newUrl.originalUrl());
            originalUrlRepository.save(originalUrl);
        }

        ShortUrl shortUrl = new ShortUrl(newUrl);
        shortUrl.setOriginalUrl(originalUrl);
        shortUrlRepository.save(shortUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl.getNickname());
    }

    @GetMapping("/{url_nickname}")
    @Transactional
    public ResponseEntity<String> fetchSorthenedUrl(@PathVariable String url_nickname){
        Optional<ShortUrl> urlOptional = shortUrlRepository.findByNickname(url_nickname);
        if (urlOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ShortUrl shortUrl = urlOptional.get();
        shortUrl.setClicksCount(shortUrl.getClicksCount() + 1);

        if (shortUrl.getPassword() != null){
            String requestId = UUID.randomUUID().toString(); // Identificador único
            System.out.println(LocalDateTime.now() + " - Request ID: " + requestId + ", protected");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Shortened link protected with password.");
        }

        shortUrl.setAccesses_count(shortUrl.getAccesses_count() + 1);
        System.out.println("to aqui");
        return ResponseEntity.status(HttpStatus.OK).body(shortUrl.getOriginalUrl().getUrl());
    }

    @Transactional
    @PostMapping("/{nickname}/validate-password")
    public ResponseEntity<String> validatePassword(@PathVariable String nickname, @RequestBody RequestValidatePasswordDTO info){
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByNickname(nickname);

        if (optionalShortUrl.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ShortUrl shortenedUrl = optionalShortUrl.get();
        System.out.println("validando");
        if (BCrypt.checkpw(info.password(), shortenedUrl.getPassword())){
            shortenedUrl.setAccesses_count(shortenedUrl.getAccesses_count() + 1);
            return ResponseEntity.status(HttpStatus.OK).body(shortenedUrl.getOriginalUrl().getUrl());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INCORRECT PASSWORD.");
    }
}
