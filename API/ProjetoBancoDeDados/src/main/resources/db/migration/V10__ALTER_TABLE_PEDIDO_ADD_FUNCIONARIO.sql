ALTER TABLE pedido
ADD COLUMN id_funcionario BIGINT,
ADD CONSTRAINT fk_funcionario FOREIGN KEY (id_funcionario) REFERENCES funcionario(id);
