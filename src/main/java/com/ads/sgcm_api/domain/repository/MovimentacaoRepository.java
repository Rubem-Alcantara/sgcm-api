package com.ads.sgcm_api.domain.repository;

import com.ads.sgcm_api.domain.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT m FROM Movimentacao m JOIN FETCH m.item JOIN FETCH m.usuario WHERE m.item.id = :itemId ORDER BY m.data DESC")
    List<Movimentacao> findByItemIdOrderByDataDescComTudo(@Param("itemId") Long itemId);

    @Query("SELECT m FROM Movimentacao m JOIN FETCH m.item JOIN FETCH m.usuario ORDER BY m.data DESC")
    List<Movimentacao> findAllComTudo();
}