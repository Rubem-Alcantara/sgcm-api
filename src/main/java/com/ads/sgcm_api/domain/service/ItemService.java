package com.ads.sgcm_api.domain.service;

import com.ads.sgcm_api.api.dto.ItemRequestDTO;
import com.ads.sgcm_api.domain.model.Item;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Item salvar(Item item){
        return itemRepository.save(item);
    }

    //Busca o item pelo ID ou lança erro 404
    public Item buscarPorId(Long id){
        return  itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    //Atualiazndo os dados, mas só depois de verrificar quem pediu
    @Transactional
    public Item atualizar(Long id, ItemRequestDTO dto, User usuarioLogado){
        Item item = buscarPorId(id); //Buscando o item antigo no banco

        //Camada de segurança
        verificarPermissao(item, usuarioLogado);

        //Se passar da camada, atualiza os dados
        item.setDescricao(dto.descricao());
        item.setTipo(dto.tipo());
        item.setQuantidade(dto.quantidade());

        return itemRepository.save(item);
    }

    //Deleta o item, utilizando a mesma camada de segurança
    @Transactional
    public void deletar(Long id, User usuarioLogado){
        Item item = buscarPorId(id);

        verificarPermissao(item, usuarioLogado);

        itemRepository.delete(item);
    }

    //Camada de segurança que valida se o usuário logado tem o perfil certo
    private void verificarPermissao(Item item, User usuarioLogado){
        //Verificando se o ID de quem criou o item é o mesmo ID do usuário no token
        boolean isDonoDoItem = item.getUsuario().getId().equals(usuarioLogado.getId());

        //Verifica se o usuário do token é um ADM
        boolean isAdmin = usuarioLogado.getRole().name().equals("ADMIN");

        //Se não for dono e nem admin, bloqueia
        if (!isDonoDoItem && !isAdmin){
            throw new RuntimeException("Acesso negado: Você não tem permissão para modificar este item.");
        }
    }
}
