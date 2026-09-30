package com.cybertycoon.gameapi.controller;

import com.cybertycoon.gameapi.model.Funcionario;
import com.cybertycoon.gameapi.model.Jogador;
import com.cybertycoon.gameapi.repository.FuncionarioRepository;
import com.cybertycoon.gameapi.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private JogadorRepository jogadorRepository;

    // 1. Lista os hackers que estão livres no mercado
    @GetMapping("/mercado")
    public List<Funcionario> listarMercado() {
        return funcionarioRepository.findByJogadorIsNull();
    }

    // 2. Contratar um funcionário (Abre um POST ligando o Funcionario ao Jogador)
    // Ex: POST /api/funcionarios/2/contratar?jogadorId=1
    @PostMapping("/{funcionarioId}/contratar")
    public ResponseEntity<String> contratarFuncionario(@PathVariable Long funcionarioId, @RequestParam Long jogadorId) {
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        if (funcionario.getJogador() != null) {
            return ResponseEntity.badRequest().body("Este funcionário já trabalha para outra agência!");
        }

        // Associa o funcionário ao jogador
        funcionario.setJogador(jogador);
        funcionarioRepository.save(funcionario);

        return ResponseEntity.ok(funcionario.getNome() + " foi contratado com sucesso por sua agência!");
    }
}
