package com.joao_lucas_felix.ProjetoBancoDeDados.repositories;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT f FROM Funcionario f WHERE f.nome LIKE LOWER(:nome)")
    Optional<Funcionario> findFuncionarioByNome(@Param("nome") String name);
    @Query("SELECT f FROM Funcionario f WHERE f.nome LIKE LOWER(CONCAT ('%',:nome,'%'))")
    Page<Funcionario> findAllFuncionariosByNome(String nome, Pageable pageable);
}
