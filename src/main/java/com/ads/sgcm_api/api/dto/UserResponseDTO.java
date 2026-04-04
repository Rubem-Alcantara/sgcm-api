package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.model.UserRole;

public record UserResponseDTO (
    Long id,
    String nome,
    String email,
    UserRole role
){
    public UserResponseDTO(User user){
        //PEgando dados importantes do User no banco para passar ao DTO.
        this(user.getId(), user.getNome(), user.getEmail(), user.getRole());
    }
}
