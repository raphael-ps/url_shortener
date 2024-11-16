package io.github.raphael.url_shortener.controllers;

import io.github.raphael.url_shortener.domain.original_url.OriginalUrl;
import io.github.raphael.url_shortener.domain.original_url.OriginalUrlRepository;
import io.github.raphael.url_shortener.domain.short_url.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/")
public class RootController {

    private final OriginalUrlRepository originalUrlRepository;
    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public RootController(ShortUrlRepository shortUrlRepository, OriginalUrlRepository originalUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.originalUrlRepository = originalUrlRepository;
    }

    @PostMapping
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

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
        shortUrl.setAccesses_count(shortUrl.getAccesses_count() + 1);

        if (shortUrl.getPassword() != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Shortened link protected with password.");
        }

        Optional<OriginalUrl> originalUrlOptional = originalUrlRepository.findById(shortUrl.getUrl_id());

        if (originalUrlOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrlOptional.get().getUrl()).build();
    }
}
