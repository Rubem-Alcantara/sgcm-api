package com.ads.sgcm_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb-item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "A descrição do item é obrigatória")
    private String descricao;

    @NotNull(message = "O tipo do item é obrigatório")
    private TipoItem tipo;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private Integer quantidade;

    //Marca a data e hora em que o item foi cadastrado
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    //Relacionando: vários intens podem ser cadastrados por um usuário
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario;

    //Função que o hibernate roda antes de salvar no banco
    @PrePersist
    protected void onCreate(){
        this.dataCadastro = LocalDateTime.now();
    }
}
