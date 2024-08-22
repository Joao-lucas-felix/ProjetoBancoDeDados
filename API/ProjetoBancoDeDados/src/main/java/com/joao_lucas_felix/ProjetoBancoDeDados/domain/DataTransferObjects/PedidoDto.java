package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PedidoDto {
    private Long idUser;
    private Long idLivro;
    private Date data;
    private Integer quantidade;
}