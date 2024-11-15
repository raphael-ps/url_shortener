package io.github.raphael.url_shortener.domain.original_url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OriginalUrlRepository extends JpaRepository<OriginalUrl, String> {
    Optional<OriginalUrl> findByUrl(String url);
}
