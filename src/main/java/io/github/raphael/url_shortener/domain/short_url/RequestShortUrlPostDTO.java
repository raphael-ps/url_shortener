package io.github.raphael.url_shortener.domain.short_url;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;


public record RequestShortUrlPostDTO(
    @NotBlank
    String originalUrl,
    @NotEmpty
    String nickname,
    String user_id,
    String password,
    Instant expirationDate
)
{
}
