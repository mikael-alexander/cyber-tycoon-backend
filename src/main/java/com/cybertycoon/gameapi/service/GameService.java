package com.cybertycoon.gameapi.service;

import com.cybertycoon.gameapi.model.Jogador;
import com.cybertycoon.gameapi.model.Missao;
import com.cybertycoon.gameapi.repository.JogadorRepository;
import com.cybertycoon.gameapi.repository.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    private final Random random = new Random();

    public String executarMissao(Long jogadorId, Long missaoId, int poderAtaqueEquipe) {
        // 1. Busca o jogador e a missão no PostgreSQL
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));

        // 2. Regra do Jogo: Calcular a chance de sucesso
        // Exemplo: Se o poder da equipe é 40 e a dificuldade é 30, a chance base aumenta.
        int fatorSorte = random.nextInt(100); // Sorteia um número de 0 a 99 (como um dado de RPG de 100 lados)
        int scoreFinal = poderAtaqueEquipe + fatorSorte;

        // Se o score final da equipe superar a dificuldade da missão, é um SUCESSO
        if (scoreFinal >= missao.getDificuldade()) {
            // Recompensa o jogador
            jogador.modificarDinheiro(missao.getRecompensaDinheiro());
            jogador.setReputacao(jogador.getReputacao() + (missao.getDificuldade() / 5)); // Ganha reputação proporcional
            
            // Salva as alterações no banco
            jogadorRepository.save(jogador);
            
            return "SUCESSO! Sua equipe conseguiu " + missao.getDescricao() + 
                   " Vocês ganharam R$ " + missao.getRecompensaDinheiro();
        } else {
            // FALHA: O jogador perde uma taxa de "limpeza de rastros digitais" ou multa
            Double prejuizo = missao.getRecompensaDinheiro() * 0.20; // Perde 20% do valor que ganharia
            jogador.modificarDinheiro(-prejuizo);
            
            jogadorRepository.save(jogador);
            
            return "FALHA! A empresa descobriu a invasão. Você teve que gastar R$ " + prejuizo + 
                   " contratando advogados e limpando seus rastros digitais.";
        }
    }
}
