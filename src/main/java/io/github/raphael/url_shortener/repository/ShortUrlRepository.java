package io.github.raphael.url_shortener.repository;

import io.github.raphael.url_shortener.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {

    Optional<ShortUrl> findByNickname(String nickname);
    ShortUrl findByUserId(String userId);
    List<ShortUrl> findAllByUserId(String userId);

}
