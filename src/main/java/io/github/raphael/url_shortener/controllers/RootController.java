package io.github.raphael.url_shortener.controllers;

import io.github.raphael.url_shortener.domain.urls.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
