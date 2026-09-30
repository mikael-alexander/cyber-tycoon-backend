package com.cybertycoon.gameapi.controller;

import com.cybertycoon.gameapi.model.Jogador;
import com.cybertycoon.gameapi.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

    // 0. Endpoint para listar todos os jogadores (usado na tela de login)
    @GetMapping
    public java.util.List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    // 1. Endpoint para buscar os dados de um jogador específico (ex: GET /api/jogadores/1)
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> obterStatus(@PathVariable Long id) {
        return jogadorRepository.findById(id)
                .map(jogador -> ResponseEntity.ok().body(jogador))
                .orElse(ResponseEntity.notFound().build());
    }

    // 2. Endpoint para criar um novo jogador/novo jogo (ex: POST /api/jogadores?nome=NerdHacker)
    @PostMapping
    public Jogador iniciarNovoJogo(@RequestParam String nome) {
        Jogador novoJogador = new Jogador();
        novoJogador.setNome(nome);
        novoJogador.setDinheiro(10000.00);
        novoJogador.setReputacao(0);
        novoJogador.setDiaAtual(1);
        return jogadorRepository.save(novoJogador);
    }

    // 2b. Endpoint para deletar um jogador (ex: DELETE /api/jogadores/1)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogador(@PathVariable Long id) {
        if (!jogadorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogadorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 3. Endpoint da Lógica do Jogo: Avançar Turno / Passar o Dia (ex: POST /api/jogadores/1/avancar-turno)
    @PostMapping("/{id}/avancar-turno")
    public ResponseEntity<Jogador> avancarTurno(@PathVariable Long id) {
        return jogadorRepository.findById(id).map(jogador -> {
            jogador.setDiaAtual(jogador.getDiaAtual() + 1);
            jogador.setDinheiro(jogador.getDinheiro() - 100.00);
            jogador.setMissoesHoje(0); // ← Reseta o contador de missões para o novo dia
            Jogador jogadorAtualizado = jogadorRepository.save(jogador);
            return ResponseEntity.ok().body(jogadorAtualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
}
