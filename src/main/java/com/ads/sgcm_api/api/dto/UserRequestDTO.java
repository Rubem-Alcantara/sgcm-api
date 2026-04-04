package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record  UserRequestDTO (
    @NotBlank(message = "O nome não pode estar em branco")
    String nome,

    @NotBlank(message = "O e-mail não pode estar em brenco")
    @Email(message = "Formato de e-mail inválido")
    String email,

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String senha,

    UserRole role
){
}
