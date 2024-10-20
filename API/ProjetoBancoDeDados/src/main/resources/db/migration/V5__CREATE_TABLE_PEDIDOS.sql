CREATE  TABLE IF NOT EXISTS pedido(
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    data DATE NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT `fk_usurario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
    CONSTRAINT `fk_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id`),
    CONSTRAINT `quantidade_maior_0` CHECK (quantidade > 0 )
)
