package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LivroDto {
    private Long id;
    private String titulo;
    private String description;
    private Double preco;
    private Date dataLacamento;
    private Integer qtdEstoque;
}