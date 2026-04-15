package com.ads.sgcm_api.domain.repository;

import com.ads.sgcm_api.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByDescricaoIgnoreCase(String descricao);

    @Query("SELECT COALESCE(SUM(i.quantidade), 0) FROM Item i")
    Long somarTotalEstoque();

    Long countByQuantidadeLessThanEqual(Integer quantidade);

    // ==========================================
    // SOLUÇÃO PRO: Consultas com JOIN FETCH
    // Traz o Item e o Usuário dono dele na mesma viagem ao banco!
    // ==========================================
    @Query("SELECT i FROM Item i JOIN FETCH i.usuario")
    List<Item> findAllComUsuario();

    @Query("SELECT i FROM Item i JOIN FETCH i.usuario WHERE LOWER(i.descricao) LIKE LOWER(CONCAT('%', :termoBusca, '%'))")
    List<Item> findByDescricaoComUsuario(@Param("termoBusca") String termoBusca);
}