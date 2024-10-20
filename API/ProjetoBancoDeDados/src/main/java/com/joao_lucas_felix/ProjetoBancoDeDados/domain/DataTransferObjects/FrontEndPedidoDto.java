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
public class FrontEndPedidoDto  extends RepresentationModel<FrontEndPedidoDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long idLivro;
    private String userName;
    private String cpf;
    private String end;
    private Integer quantidade;
    private String data;
    private String funcionarioName;

}
