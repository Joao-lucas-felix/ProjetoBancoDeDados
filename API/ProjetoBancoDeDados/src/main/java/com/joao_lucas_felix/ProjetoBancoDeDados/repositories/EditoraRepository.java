package com.joao_lucas_felix.ProjetoBancoDeDados.repositories;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Editora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
    @Query("SELECT e FROM Editora e WHERE e.nome LIKE LOWER(CONCAT ('%',:nome,'%'))")
    Page<Editora> findEditoraByName(@Param("nome") String nome, Pageable pageable);
}
