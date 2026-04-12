package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.model.UserRole;
import com.ads.sgcm_api.domain.model.UserStatus;

public record UserResponseDTO (
        Long id,
        String nome,
        String email,
        UserRole role,
        UserStatus status 
){
    public UserResponseDTO(User user){
        this(user.getId(), user.getNome(), user.getEmail(), user.getRole(), user.getStatus());
    }
}