package com.ads.sgcm_api.domain.repository;

import com.ads.sgcm_api.domain.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByItemIdOrderByDataDesc(Long itemId);
}