package com.ads.sgcm_api.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank(message = "A senha atual é obrigatória")
        String senhaAtual,

        @NotBlank(message = "A nova senha não pode estar em branco")
        @Size(min = 6, message = "A nova senha deve ter no mínimo 6 caracteres")
        String novaSenha
) {}