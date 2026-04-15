package com.ads.sgcm_api.domain.service;

import com.ads.sgcm_api.api.dto.MovimentacaoRequestDTO;
import com.ads.sgcm_api.domain.model.Item;
import com.ads.sgcm_api.domain.model.Movimentacao;
import com.ads.sgcm_api.domain.model.TipoMovimentacao;
import com.ads.sgcm_api.domain.model.User;
import com.ads.sgcm_api.domain.repository.ItemRepository;
import com.ads.sgcm_api.domain.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Movimentacao registrar(MovimentacaoRequestDTO dto, User usuarioLogado) {
        // Buscando o item que será movimentado
        Item item = itemService.buscarPorId(dto.itemId());

        // Calculando a nova quantidade e aplicando as regras de negócio
        if (dto.tipo() == TipoMovimentacao.SAIDA) {
            if (item.getQuantidade() < dto.quantidade()) {
                throw new RuntimeException("Quantidade em estoque insuficiente para realizar a saída.");
            }
            item.setQuantidade(item.getQuantidade() - dto.quantidade());
        } else {
            item.setQuantidade(item.getQuantidade() + dto.quantidade());
        }

        // Atualizando o saldo do Item no banco
        itemRepository.save(item);

        // Criando e salvando o registro histórico da Movimentação
        Movimentacao novaMovimentacao = new Movimentacao();
        novaMovimentacao.setTipo(dto.tipo());
        novaMovimentacao.setQuantidade(dto.quantidade());
        novaMovimentacao.setMotivo(dto.motivo());
        novaMovimentacao.setData(LocalDateTime.now());
        novaMovimentacao.setItem(item);
        novaMovimentacao.setUsuario(usuarioLogado);

        return movimentacaoRepository.save(novaMovimentacao);
    }

    public List<Movimentacao> listarTodas() {
        // Usa a consulta otimizada para evitar erro 500
        return movimentacaoRepository.findAllComTudo();
    }
}