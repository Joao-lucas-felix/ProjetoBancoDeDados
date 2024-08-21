CREATE  TABLE IF NOT EXISTS pedido(
    id_usuario BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    data DATE NOT NULL,
    PRIMARY KEY(id_livro, id_usuario, data),
    CONSTRAINT `fk_usurario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
    CONSTRAINT `fk_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id`)
)
