package com.ads.sgcm_api.api.controller;

import com.ads.sgcm_api.api.dto.MovimentacaoRequestDTO;
import com.ads.sgcm_api.api.dto.MovimentacaoResponseDTO;
import com.ads.sgcm_api.domain.model.Movimentacao;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimentacaoResponseDTO registrar(
            @Valid @RequestBody MovimentacaoRequestDTO dto,
            @AuthenticationPrincipal User usuarioLogado
    ) {
        Movimentacao movimentacaoSalva = movimentacaoService.registrar(dto, usuarioLogado);
        return new MovimentacaoResponseDTO(movimentacaoSalva);
    }
    @GetMapping
    public List<MovimentacaoResponseDTO> listarTodas() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarTodas();
        return movimentacoes.stream()
                .map(MovimentacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

}