package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pedido {
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "id_livro")
    private Long idLivro;
    @Column(name = "data")
    private Date data;
    @Column(name = "quantidade")
    private Integer quantidade;

}
