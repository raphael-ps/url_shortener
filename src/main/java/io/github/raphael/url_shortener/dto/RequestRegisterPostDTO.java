package io.github.raphael.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestRegisterPostDTO(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
