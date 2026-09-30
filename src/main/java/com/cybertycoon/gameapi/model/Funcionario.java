package com.cybertycoon.gameapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer ataque;
    private Integer defesa;
    private Double salarioDiario;

    @ManyToOne
    @JoinColumn(name = "jogador_id") // Cria uma chave estrangeira ligando ao Jogador
    private Jogador jogador;
}
