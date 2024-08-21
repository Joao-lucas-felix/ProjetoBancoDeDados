CREATE TABLE IF NOT EXISTS livro (
    id BIGINT not null AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    data_lacamento DATE NOT NULL,
    qtd_estoque INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT qtd_estoque_maior_igual_0 CHECK ( qtd_estoque >= 0)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;