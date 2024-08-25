package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AutorDto extends RepresentationModel<AutorDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long Key;
    private String nome;
}