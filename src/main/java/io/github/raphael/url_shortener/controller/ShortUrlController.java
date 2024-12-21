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


import java.util.Optional;

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
        shortUrl.setUrl_id(originalUrl.getId());
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
        shortUrl.setClicks_count(shortUrl.getClicks_count() + 1);

        if (shortUrl.getPassword() != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Shortened link protected with password.");
        }

        shortUrl.setAccesses_count(shortUrl.getAccesses_count() + 1);
        Optional<OriginalUrl> originalUrlOptional = originalUrlRepository.findById(shortUrl.getUrl_id());

        if (originalUrlOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(originalUrlOptional.get().getUrl());
    }

    @Transactional
    @PostMapping("/{nickname}/validate-password")
    public ResponseEntity<String> validatePassword(@PathVariable String nickname, @RequestBody RequestValidatePasswordDTO info){
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByNickname(nickname);

        if (optionalShortUrl.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ShortUrl shortenedUrl = optionalShortUrl.get();

        if (BCrypt.checkpw(info.password(), shortenedUrl.getPassword())){
            Optional<OriginalUrl> optionalUrl = originalUrlRepository.findById(shortenedUrl.getUrl_id());

            if (optionalUrl.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            shortenedUrl.setAccesses_count(shortenedUrl.getAccesses_count() + 1);
            return ResponseEntity.status(HttpStatus.OK).body(optionalUrl.get().getUrl());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INCORRECT PASSWORD.");
    }
}
