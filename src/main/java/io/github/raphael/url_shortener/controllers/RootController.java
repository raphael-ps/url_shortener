package io.github.raphael.url_shortener.controllers;

import io.github.raphael.url_shortener.domain.urls.*;

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

    private final UrlRepository urlRepository;

    @Autowired
    public RootController(UrlRepository productRepository) {
        this.urlRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<String> registerUrl(@RequestBody @Valid ResquestUrlPostDTO newUrl){
        Url url = new Url(newUrl);
        urlRepository.save(url);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{url_nickname}")
    @Transactional
    public ResponseEntity<String> fetchSorthenedUrl(@PathVariable String url_nickname){
        Optional<Url> urlOptional = urlRepository.findByUrlNickname(url_nickname);

        if (urlOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Url url = urlOptional.get();
        url.setClicks_count(url.getClicks_count() + 1);
        url.setAccesses_count(url.getAccesses_count() + 1);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", url.getUrl_original())
                .build();
    }
}
