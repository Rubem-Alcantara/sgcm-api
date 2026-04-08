package com.ads.sgcm_api.api.controller;

import com.ads.sgcm_api.api.dto.DashboardMetricasDTO;
import com.ads.sgcm_api.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/metricas")
public class DashboardController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public DashboardMetricasDTO obterMetricas() {
        // Conta o total de linhas na tabela de itens
        Long totalItens = itemRepository.count();

        // Chama a nossa Query customizada para somar todas as quantidades
        Long totalEstoque = itemRepository.somarTotalEstoque();

        // Conta quantos itens estão com estoque crítico (5 ou menos unidades)
        Long itensAlerta = itemRepository.countByQuantidadeLessThanEqual(5);

        // Retorna tudo empacotado para o React
        return new DashboardMetricasDTO(totalItens, totalEstoque, itensAlerta);
    }
}