package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "description")
    private String description;
    @Column(name = "preco")
    private Double preco;
    @Column(name = "data_lancamento")
    private Date dataLacamento;
    @Column(name = "qtd_estoque")
    private Integer qtdEstoque;
}
