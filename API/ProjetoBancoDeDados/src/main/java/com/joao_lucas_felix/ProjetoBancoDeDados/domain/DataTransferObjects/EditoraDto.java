package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;
import org.springframework.hateoas.EntityModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EditoraDto extends EntityModel<EditoraDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long key;
    private String nome;
}