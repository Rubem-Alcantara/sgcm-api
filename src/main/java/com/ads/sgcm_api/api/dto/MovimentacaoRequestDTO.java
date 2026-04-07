package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentacaoRequestDTO(
        @NotNull(message = "O ID do item é obrigatório")
        Long itemId,

        @NotNull(message = "O tipo de movimentação é obrigatório")
        TipoMovimentacao tipo,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        String motivo
) {}