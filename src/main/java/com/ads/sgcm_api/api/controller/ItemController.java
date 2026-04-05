package com.ads.sgcm_api.api.controller;

import com.ads.sgcm_api.api.dto.ItemRequestDTO;
import com.ads.sgcm_api.api.dto.ItemResponseDTO;
import com.ads.sgcm_api.domain.model.Item;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponseDTO adicionar(@Valid @RequestBody ItemRequestDTO dto,  @AuthenticationPrincipal User usuarioLogado
    ){
        //convertendo dto em entidade
        Item novoItem = new Item();
        novoItem.setDescricao(dto.descricao());
        novoItem.setTipo(dto.tipo());
        novoItem.setQuantidade(dto.quantidade());

        //Vinculando item ao usuário que enviou o token
        novoItem.setUsuario(usuarioLogado);

        //Salvando no banco
        Item itemSalvo = itemService.salvar(novoItem);

        //Convertendo Entidade em dto seguro
        return new ItemResponseDTO(itemSalvo);
    }

    @PutMapping("/{id}")
    public ItemResponseDTO atualiazr(@PathVariable Long id, //Capturando ID da url
                                     @Valid @RequestBody ItemRequestDTO dto, //Captura JSON com dados novos
                                     @AuthenticationPrincipal User usuarioLogado //Captura quem esta logado pelo token
    ){
        Item itemAtualizado = itemService.atualizar(id, dto, usuarioLogado);
        return  new ItemResponseDTO(itemAtualizado);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) //retorna status 204
    public void deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal User usarioLogado
    ){
        itemService.deletar(id, usarioLogado);
    }
}
