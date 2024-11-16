package io.github.raphael.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestValidatePasswordDTO(
        @NotBlank
        String password
) {
}
