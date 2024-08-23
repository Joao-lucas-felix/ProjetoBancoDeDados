package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PedidoId id;

    @Column(name = "quantidade")
    private Integer quantidade;
}



