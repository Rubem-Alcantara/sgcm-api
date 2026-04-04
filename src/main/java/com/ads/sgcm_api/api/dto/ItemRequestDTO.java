package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.TipoItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
public record ItemRequestDTO (

        @NotBlank(message = "A descrição não pode estar em branco")
        String descricao,

        @NotNull(message = "O tipo do item é obrigatório")
        TipoItem tipo,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        Integer quantidade
){
}
