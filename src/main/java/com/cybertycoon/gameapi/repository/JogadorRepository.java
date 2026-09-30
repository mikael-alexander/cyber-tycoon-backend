package com.cybertycoon.gameapi.repository;

import com.cybertycoon.gameapi.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    // O JpaRepository já nos dá métodos prontos como: save(), findById(), delete(), etc.
}
