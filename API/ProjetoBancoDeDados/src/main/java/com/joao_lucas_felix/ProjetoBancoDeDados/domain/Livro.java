package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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


    //Relacionamentos:
    @ManyToOne
    @JoinColumn(name = "id_editora", nullable = false)
    private Editora idEditora;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "escreve",
            joinColumns = {@JoinColumn(name = "id_livro")},
            inverseJoinColumns = {@JoinColumn(name = "id_autor")}
    )
    private List<Autor> autors;

    public Livro(Long id, String titulo, String description, Double preco, Date dataLacamento, Integer qtdEstoque, Editora idEditora) {
        this.id = id;
        this.titulo = titulo;
        this.description = description;
        this.preco = preco;
        this.dataLacamento = dataLacamento;
        this.qtdEstoque = qtdEstoque;
        this.idEditora = idEditora;
    }
}
