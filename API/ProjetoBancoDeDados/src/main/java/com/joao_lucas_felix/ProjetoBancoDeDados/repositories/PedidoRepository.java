package com.joao_lucas_felix.ProjetoBancoDeDados.repositories;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.idUser = :userId")
    Page<Pedido> findPedidosDoUsuario(@Param("userId") Long userId, Pageable pageable);
    @Query("SELECT p FROM Pedido p WHERE p.idFuncionario = :funcionarioId")
    Page<Pedido> findPedidosDoFuncionario(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
