package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LivroDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String titulo;
    private String description;
    private Double preco;
    private Date dataLacamento;
    private Integer qtdEstoque;
}