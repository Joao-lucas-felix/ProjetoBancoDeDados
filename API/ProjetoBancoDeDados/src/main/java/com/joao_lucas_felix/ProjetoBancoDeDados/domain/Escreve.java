package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "escreve")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Escreve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    // getters e setters
}