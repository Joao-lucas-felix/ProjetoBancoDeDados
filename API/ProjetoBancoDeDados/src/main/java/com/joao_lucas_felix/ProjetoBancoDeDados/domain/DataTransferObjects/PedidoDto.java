package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PedidoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long idUser;
    private Long idLivro;
    private Date data;
    private Integer quantidade;
}