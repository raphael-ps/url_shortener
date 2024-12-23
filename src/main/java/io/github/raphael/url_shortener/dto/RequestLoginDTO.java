package io.github.raphael.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestLoginDTO(@NotBlank String email, @NotBlank String password) {
}
