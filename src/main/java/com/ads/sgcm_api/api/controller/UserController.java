package com.ads.sgcm_api.api.controller;

import com.ads.sgcm_api.api.dto.*;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.service.UserService;
import com.ads.sgcm_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        User user = userService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
        String token = tokenService.generateToken(user);
        return new LoginResponse(token);
    }

    @PatchMapping("/perfil/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@Valid @RequestBody PasswordChangeRequest request, @AuthenticationPrincipal User usuarioLogado) {
        userService.alterarSenha(request.senhaAtual(), request.novaSenha(), usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public UserResponseDTO obterDadosDoUsuarioLogado(@AuthenticationPrincipal User usuarioLogado) {
        return new UserResponseDTO(usuarioLogado);
    }
}