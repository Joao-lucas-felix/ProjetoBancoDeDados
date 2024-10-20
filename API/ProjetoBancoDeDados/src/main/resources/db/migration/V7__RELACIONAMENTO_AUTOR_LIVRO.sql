CREATE  TABLE IF NOT EXISTS escreve(
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_autor BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT `fk_autor` FOREIGN KEY (`id_autor`) REFERENCES `autor` (`id`),
    CONSTRAINT `fk_livro_autor` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id`)
)