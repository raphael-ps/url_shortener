package io.github.raphael.url_shortener.domain.urls;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, String> {

    public Optional<Url> findByUrlNickname(String url_nickname);
}
