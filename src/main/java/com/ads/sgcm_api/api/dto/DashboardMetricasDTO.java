package com.ads.sgcm_api.api.dto;

public record DashboardMetricasDTO(
        Long totalItens,       // Quantidade de produtos diferentes cadastrados
        Long totalEstoque,     // A soma  de todas as quantidades
        Long itensAlerta       // Quantos itens estão com estoque abaixo de 5
) {}