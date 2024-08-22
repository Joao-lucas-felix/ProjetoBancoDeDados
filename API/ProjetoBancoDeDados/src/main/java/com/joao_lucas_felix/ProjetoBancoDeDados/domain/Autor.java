package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "autor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
}
