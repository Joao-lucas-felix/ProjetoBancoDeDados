package com.joao_lucas_felix.ProjetoBancoDeDados.repositories;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT l FROM Livro l WHERE l.titulo LIKE LOWER(CONCAT ('%',:titulo,'%'))")
    Page<Livro> findLivroByTitulo(@Param("titulo") String titulo, Pageable pageable);
}
