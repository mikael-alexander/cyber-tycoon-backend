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

    private static final int LIMITE_MISSOES_POR_DIA = 4;

    public String executarMissao(Long jogadorId, Long missaoId, int poderAtaqueEquipe) {
        // 1. Busca o jogador e a missão no PostgreSQL
        Jogador jogador = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada"));

        // 2. Verifica se o jogador ainda tem ações disponíveis no dia
        int missoesFeitas = jogador.getMissoesHoje() == null ? 0 : jogador.getMissoesHoje();
        if (missoesFeitas >= LIMITE_MISSOES_POR_DIA) {
            return "DIA_ENCERRADO: Você já realizou " + LIMITE_MISSOES_POR_DIA + " missões hoje. Avance o turno para continuar.";
        }

        // 3. Incrementa o contador de missões do dia
        jogador.setMissoesHoje(missoesFeitas + 1);

        // 4. Regra do Jogo: Calcular a chance de sucesso
        int fatorSorte = random.nextInt(100);
        int scoreFinal = poderAtaqueEquipe + fatorSorte;

        String resultado;
        if (scoreFinal >= missao.getDificuldade()) {
            jogador.modificarDinheiro(missao.getRecompensaDinheiro());
            jogador.setReputacao(jogador.getReputacao() + (missao.getDificuldade() / 5));
            resultado = "SUCESSO! Sua equipe conseguiu " + missao.getDescricao() +
                       " Vocês ganharam R$ " + missao.getRecompensaDinheiro();
        } else {
            Double prejuizo = missao.getRecompensaDinheiro() * 2; // Prejuízo é o dobro da recompensa
            jogador.modificarDinheiro(-prejuizo);
            resultado = "FALHA! A empresa descobriu a invasão. Você teve que gastar R$ " + prejuizo +
                       " contratando advogados e limpando seus rastros digitais.";
        }

        // 5. Se for a última missão do dia, avisa o jogador
        if (jogador.getMissoesHoje() >= LIMITE_MISSOES_POR_DIA) {
            if (jogador.getDinheiro() < 0) {
                resultado += " | 🚨 VOCÊ FOI PRESO! Seus fundos estão negativos e a polícia rastreou sua localização.";
            } else {
                resultado += " | ⚠️ Limite diário atingido! Avance o turno para continuar.";
            }
        } else {
            resultado += " | Ações restantes hoje: " + (LIMITE_MISSOES_POR_DIA - jogador.getMissoesHoje());
        }

        jogadorRepository.save(jogador);
        return resultado;
    }
}
