package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Livro implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "description")
    private String description;
    @Column(name = "preco")
    private Double preco;
    @Column(name = "data_lacamento")
    @Temporal(TemporalType.DATE)
    private Date dataLacamento;
    @Column(name = "qtd_estoque")
    private Integer qtdEstoque;
}
