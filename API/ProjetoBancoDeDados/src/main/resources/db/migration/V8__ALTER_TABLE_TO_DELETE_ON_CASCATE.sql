ALTER TABLE pedido DROP FOREIGN KEY fk_usurario;
ALTER TABLE pedido DROP FOREIGN KEY fk_livro;
ALTER TABLE pedido ADD CONSTRAINT fk_usurario FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE;
ALTER TABLE pedido ADD CONSTRAINT fk_livro FOREIGN KEY (id_livro) REFERENCES livro(id) ON DELETE CASCADE;
