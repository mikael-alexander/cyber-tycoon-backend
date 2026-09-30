package com.cybertycoon.gameapi.controller;

import com.cybertycoon.gameapi.model.Jogador;
import com.cybertycoon.gameapi.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jogadores")
@CrossOrigin(origins = "http://localhost:4200") // ⚠️ Permite que o Angular (porta 4200) acesse o Java sem erros de CORS
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

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
        novoJogador.setDinheiro(10000.00); // Começa com R$ 10.000 de saldo inicial
        novoJogador.setReputacao(0);
        novoJogador.setDiaAtual(1);
        
        return jogadorRepository.save(novoJogador);
    }

    // 3. Endpoint da Lógica do Jogo: Avançar Turno / Passar o Dia (ex: POST /api/jogadores/1/avancar-turno)
    @PostMapping("/{id}/avancar-turno")
    public ResponseEntity<Jogador> avancarTurno(@PathVariable Long id) {
        return jogadorRepository.findById(id).map(jogador -> {
            // LÓGICA DO TURNO:
            // 1. Avança o dia do jogo
            jogador.setDiaAtual(jogador.getDiaAtual() + 1);
            
            // 2. Cobra custos fixos diários (Ex: -R$ 100 de custos de servidor e luz)
            jogador.setDinheiro(jogador.getDinheiro() - 100.00);
            
            // 3. Salva o novo estado alterado no PostgreSQL
            Jogador jogadorAtualizado = jogadorRepository.save(jogador);
            
            return ResponseEntity.ok().body(jogadorAtualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
}
