package io.github.raphael.url_shortener.domain.urls;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;


public record ResquestUrlPostDTO(
    @NotBlank
    String url_original,
    @NotEmpty
    String url_nickname,
    Instant expiration_date
)
{
}
