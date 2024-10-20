package com.joao_lucas_felix.ProjetoBancoDeDados.repositories;

import com.joao_lucas_felix.ProjetoBancoDeDados.domain.Escreve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscreveRepository extends JpaRepository<Escreve, Long> {
}
