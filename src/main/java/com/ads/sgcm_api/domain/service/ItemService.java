package com.ads.sgcm_api.domain.service;

import com.ads.sgcm_api.api.dto.ItemRequestDTO;
import com.ads.sgcm_api.domain.model.Item;
import com.ads.sgcm_api.domain.model.Movimentacao;
import com.ads.sgcm_api.domain.model.TipoMovimentacao;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.repository.ItemRepository;
import com.ads.sgcm_api.domain.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    //Repositório de movimentações para poder registrar o estoque inicial
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public Item salvar(Item item) {
        // Evitar itens duplicados
        if (itemRepository.existsByDescricaoIgnoreCase(item.getDescricao())) {
            throw new RuntimeException("Já existe um item cadastrado com a descrição: " + item.getDescricao());
        }

        // Salva o item primeiramente para ele ganhar um ID no banco de dados
        Item itemSalvo = itemRepository.save(item);

        // Criar a movimentação automática de ENTRADA
        Movimentacao estoqueInicial = new Movimentacao();
        estoqueInicial.setTipo(TipoMovimentacao.ENTRADA);
        estoqueInicial.setQuantidade(itemSalvo.getQuantidade());
        estoqueInicial.setMotivo("Estoque Inicial (Cadastro de Item)");
        estoqueInicial.setData(LocalDateTime.now());
        estoqueInicial.setItem(itemSalvo);
        estoqueInicial.setUsuario(itemSalvo.getUsuario()); // Pega o usuário que criou o item

        // Salva a movimentação no banco
        movimentacaoRepository.save(estoqueInicial);

        return itemSalvo;
    }

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Item buscarPorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    @Transactional
    public Item atualizar(Long id, ItemRequestDTO dto, User usuarioLogado) {
        Item item = buscarPorId(id);

        verificarPermissao(item, usuarioLogado);

        // Se o usuário tentar mudar o nome para um que já existe (e não for o dele mesmo), bloqueia
        if (!item.getDescricao().equalsIgnoreCase(dto.descricao()) &&
                itemRepository.existsByDescricaoIgnoreCase(dto.descricao())) {
            throw new RuntimeException("Já existe outro item cadastrado com esta descrição.");
        }

        // Atualiza apenas os dados permitidos
        item.setDescricao(dto.descricao());
        item.setTipo(dto.tipo());

        return itemRepository.save(item);
    }

    @Transactional
    public void deletar(Long id, User usuarioLogado) {
        Item item = buscarPorId(id);
        verificarPermissao(item, usuarioLogado);

        itemRepository.delete(item);
    }

    private void verificarPermissao(Item item, User usuarioLogado) {
        boolean isDonoDoItem = item.getUsuario().getId().equals(usuarioLogado.getId());
        boolean isAdmin = usuarioLogado.getRole().name().equals("ADMIN");

        if (!isDonoDoItem && !isAdmin) {
            throw new RuntimeException("Acesso negado: Você não tem permissão para modificar este item.");
        }
    }
}