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
public class PedidoDto extends RepresentationModel<PedidoDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long key;
    private Long idUser;
    private Long idLivro;
    private String data;
    private Integer quantidade;
}