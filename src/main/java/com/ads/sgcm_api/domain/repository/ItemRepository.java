package com.ads.sgcm_api.domain.repository;

import com.ads.sgcm_api.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByDescricaoIgnoreCase(String descricao);

    // Somando a coluna 'quantidade' de todos os itens. Se não tiver nada, retorna 0
    @Query("SELECT COALESCE(SUM(i.quantidade), 0) FROM Item i")
    Long somarTotalEstoque();

    // Contando quantos itens possuem a quantidade menor ou igual a um valor específico
    Long countByQuantidadeLessThanEqual(Integer quantidade);

    List<Item> findByDescricaoContainingIgnoreCase(String termoBusca);
}
