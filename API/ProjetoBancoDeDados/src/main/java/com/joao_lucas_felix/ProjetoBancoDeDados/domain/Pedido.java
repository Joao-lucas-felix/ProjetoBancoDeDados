package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_usuario")
    private Long idUser;

    @Column(name = "id_livro")
    private Long idLivro;

    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name = "data")
    private Date data;

    @Column(name = "quantidade")
    private Integer quantidade;
}



