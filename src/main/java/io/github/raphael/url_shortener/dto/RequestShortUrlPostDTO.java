package io.github.raphael.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;


public record RequestShortUrlPostDTO(
    @NotBlank
    String originalUrl,
    @NotEmpty
    String nickname,
    String userId,
    String password,
    Instant expirationDate
)
{
}
