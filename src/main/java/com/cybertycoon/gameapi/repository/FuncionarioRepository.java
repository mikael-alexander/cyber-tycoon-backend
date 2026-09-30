package com.cybertycoon.gameapi.repository;

import com.cybertycoon.gameapi.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    // Busca funcionários que ainda NÃO foram contratados por nenhum jogador (jogador_id é NULL)
    List<Funcionario> findByJogadorIsNull();

    // Busca funcionários contratados por um jogador específico
    List<Funcionario> findByJogadorId(Long jogadorId);
}