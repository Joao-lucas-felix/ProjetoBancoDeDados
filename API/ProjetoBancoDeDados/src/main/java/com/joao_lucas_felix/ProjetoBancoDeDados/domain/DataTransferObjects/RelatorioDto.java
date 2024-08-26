package com.joao_lucas_felix.ProjetoBancoDeDados.domain.DataTransferObjects;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RelatorioDto extends RepresentationModel<RelatorioDto> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer numeroLivrosCadastrados;
    private Integer numeroClientesCadastrados;
    private Integer numeroPedidosFeitos;
    private Integer numeroLivrosEstoque;
    private Double faturamentoTotal;
}
