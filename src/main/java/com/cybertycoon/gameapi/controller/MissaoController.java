package com.cybertycoon.gameapi.controller;

import com.cybertycoon.gameapi.model.Missao;
import com.cybertycoon.gameapi.repository.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/missoes")
public class MissaoController {

    @Autowired
    private MissaoRepository missaoRepository;

    // Retorna todas as missões cadastradas no PostgreSQL
    @GetMapping
    public List<Missao> listarTodas() {
        return missaoRepository.findAll();
    }

    @Autowired
    private com.cybertycoon.gameapi.service.GameService gameService;

    // Endpoint para o jogador tentar fazer uma missão
    // Exemplo de chamada: POST /api/missoes/1/executar?jogadorId=1&poderAtaque=45
    @PostMapping("/{missaoId}/executar")
    public org.springframework.http.ResponseEntity<String> tentarExecutarMissao(
            @PathVariable Long missaoId,
            @RequestParam Long jogadorId,
            @RequestParam int poderAtaque) {

        try {
            String resultado = gameService.executarMissao(jogadorId, missaoId, poderAtaque);
            return org.springframework.http.ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
