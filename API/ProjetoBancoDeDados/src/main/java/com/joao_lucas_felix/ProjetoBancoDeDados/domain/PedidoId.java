package com.joao_lucas_felix.ProjetoBancoDeDados.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PedidoId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_livro")
    private Long idLivro;

    @Column(name = "data")
    private Date data;
}
