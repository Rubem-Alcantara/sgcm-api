package com.ads.sgcm_api.api.controller;

import com.ads.sgcm_api.api.dto.UserResponseDTO;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.model.UserRole;
import com.ads.sgcm_api.domain.model.UserStatus;
import com.ads.sgcm_api.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/usuarios")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDTO> listarTodos() {
        return userService.listarTodos().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id, @RequestParam UserStatus status, @AuthenticationPrincipal User adminLogado) {
        userService.alterarStatus(id, status, adminLogado);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reset-senha")
    public ResponseEntity<Void> resetarSenha(@PathVariable Long id, @AuthenticationPrincipal User adminLogado) {
        userService.resetarSenha(id, adminLogado);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id, @AuthenticationPrincipal User adminLogado) {
        userService.excluirUsuario(id, adminLogado);
        return ResponseEntity.noContent().build();
    }
}