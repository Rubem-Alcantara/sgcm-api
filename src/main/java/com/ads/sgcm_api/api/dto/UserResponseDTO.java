package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.model.UserRole;
import com.ads.sgcm_api.domain.model.UserStatus;

public record UserResponseDTO (
        Long id,
        String nome,
        String email,
        UserRole role,
        UserStatus status,
        Boolean senhaProvisoria
){
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                // Se for null no banco de dados, o React recebe false
                user.getSenhaProvisoria() != null ? user.getSenhaProvisoria() : false
        );
    }
}