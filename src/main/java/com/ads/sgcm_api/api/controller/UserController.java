package com.ads.sgcm_api.api.controller;

import com.ads.sgcm_api.api.dto.LoginRequest;
import com.ads.sgcm_api.api.dto.LoginResponse;
import com.ads.sgcm_api.api.dto.UserRequestDTO;
import com.ads.sgcm_api.api.dto.UserResponseDTO;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.service.UserService;
import com.ads.sgcm_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // <-- Importação adicionada
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO adicionar(@Valid @RequestBody UserRequestDTO dto){
        // Transformando DTO em entidade JPA
        User novoUser = new User();
        novoUser.setNome(dto.nome());
        novoUser.setEmail(dto.email());
        novoUser.setSenha(dto.senha());

        // Enviando para o Service (que vai forçar a Role VOLUNTARIO e Status PENDENTE)
        User userSalvo = userService.salvar(novoUser);

        return new UserResponseDTO(userSalvo);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        User user = userService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
        String token = tokenService.generateToken(user);
        return new LoginResponse(token);
    }

    @GetMapping("/me")
    public UserResponseDTO obterDadosDoUsuarioLogado(@AuthenticationPrincipal User usuarioLogado) {
        return new UserResponseDTO(usuarioLogado);
    }
}