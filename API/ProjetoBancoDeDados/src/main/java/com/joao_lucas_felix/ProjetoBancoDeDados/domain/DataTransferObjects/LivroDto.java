package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LivroDto extends RepresentationModel<LivroDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long key;
    private String titulo;
    private String description;
    private Double preco;
    private String dataLancamento;
    private Integer qtdEstoque;
}