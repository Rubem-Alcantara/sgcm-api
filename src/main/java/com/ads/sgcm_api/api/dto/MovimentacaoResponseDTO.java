package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.Movimentacao;
import java.time.LocalDateTime;

public record MovimentacaoResponseDTO(
        Long id,
        Long itemId,
        String nomeItem,
        String tipo,
        Integer quantidade,
        LocalDateTime data,
        String motivo,
        String nomeUsuarioLogado
) {
    // Construtor que converte a Entidade no DTO automaticamente
    public MovimentacaoResponseDTO(Movimentacao m) {
        this(
                m.getId(),
                m.getItem().getId(),
                m.getItem().getDescricao(),
                m.getTipo().name(),
                m.getQuantidade(),
                m.getData(),
                m.getMotivo(),
                m.getUsuario().getNome()
        );
    }
}