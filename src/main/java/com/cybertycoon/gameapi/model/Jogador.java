package com.cybertycoon.gameapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data // Se não usar Lombok, crie manualmente os Getters, Setters e Construtores
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double dinheiro;
    private Integer reputacao;
    private Integer diaAtual;

    public void modificarDinheiro(Double valor) {
    if (this.dinheiro == null) this.dinheiro = 0.0;
    this.dinheiro += valor;
}
}
