package com.cybertycoon.gameapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Integer dificuldade;
    private Double recompensaDinheiro;
    private String status; // Ex: "DISPONIVEL", "CONCLUIDA"
}
