package io.github.raphael.url_shortener.domain.short_url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {

    public Optional<ShortUrl> findByNickname(String nickname);
}
