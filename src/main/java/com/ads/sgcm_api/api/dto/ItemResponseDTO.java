package com.ads.sgcm_api.api.dto;

import com.ads.sgcm_api.domain.model.Item;
import com.ads.sgcm_api.domain.model.TipoItem;
import java.time.LocalDateTime;
public record ItemResponseDTO(
        Long id,
        String descricao,
        TipoItem tipo,
        Integer quantidade,
        LocalDateTime dataCadastro,
        String nomeResponsavel
) {
    public ItemResponseDTO(Item item){
        this(
                item.getId(),
                item.getDescricao(),
                item.getTipo(),
                item.getQuantidade(),
                item.getDataCadastro(),
                item.getUsuario().getNome()
        );
    }
}
