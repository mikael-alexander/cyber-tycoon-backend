package com.cybertycoon.gameapi.config;

import com.cybertycoon.gameapi.model.Funcionario;
import com.cybertycoon.gameapi.model.Missao;
import com.cybertycoon.gameapi.repository.FuncionarioRepository;
import com.cybertycoon.gameapi.repository.MissaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(MissaoRepository missaoRepository, FuncionarioRepository funcionarioRepository) {
        return args -> {
            // Verifica se o banco de dados já possui missões para não duplicar toda vez que reiniciar
            if (missaoRepository.count() == 0) {
                
                // Missão 1: Fácil
                Missao m1 = new Missao();
                m1.setDescricao("Invadir o sistema de Wi-Fi da cafeteria local para roubar cupons de desconto.");
                m1.setDificuldade(10); // Dificuldade baixa
                m1.setRecompensaDinheiro(350.00);
                m1.setStatus("DISPONIVEL");

                // Missão 2: Média
                Missao m2 = new Missao();
                m2.setDescricao("Infiltrar-se no servidor de e-mails de uma startup concorrente.");
                m2.setDificuldade(35); // Dificuldade média
                m2.setRecompensaDinheiro(1500.00);
                m2.setStatus("DISPONIVEL");

                // Missão 3: Difícil
                Missao m3 = new Missao();
                m3.setDescricao("Descriptografar o banco de dados principal de um grande banco nacional.");
                m3.setDificuldade(75); // Dificuldade alta
                m3.setRecompensaDinheiro(10000.00);
                m3.setStatus("DISPONIVEL");

                // Missão 4: Muito Difícil
                Missao m4 = new Missao();
                m4.setDescricao("Infiltrar-se no sistema do governo para roubar documentos sensíveis.");
                m4.setDificuldade(99); // Dificuldade alta
                m4.setRecompensaDinheiro(50000.00);
                m4.setStatus("DISPONIVEL");

                // Salvando todas na tabela 'missao' do PostgreSQL
                missaoRepository.saveAll(List.of(m1, m2, m3, m4));
                    System.out.println("🚀 [Cyber Tycoon] Banco de dados populado com as missões iniciais com sucesso!");
            } else {
                System.out.println("ℹ️ [Cyber Tycoon] O banco já possui missões cadastradas. Pulando etapa de seed.");
            }

                // --- SEED DE FUNCIONÁRIOS (MERCADO DE TALENTOS) ---
            if (funcionarioRepository.count() == 0) {
                Funcionario f1 = new Funcionario();
                f1.setNome("Neo_Nerd (Estagiário)");
                f1.setAtaque(15);
                f1.setDefesa(5);
                f1.setSalarioDiario(80.00);
                f1.setJogador(null); // NULL significa que está livre no mercado para ser contratado

                Funcionario f2 = new Funcionario();
                f2.setNome("Trinity_Sec (Especialista)");
                f2.setAtaque(45);
                f2.setDefesa(30);
                f2.setSalarioDiario(350.00);
                f2.setJogador(null);

                Funcionario f3 = new Funcionario();
                f3.setNome("Morpheus_Net (Lenda)");
                f3.setAtaque(85);
                f3.setDefesa(70);
                f3.setSalarioDiario(1200.00);
                f3.setJogador(null);

                funcionarioRepository.saveAll(List.of(f1, f2, f3));
                System.out.println("👥 [Cyber Tycoon] Banco de dados populado com os candidatos a Hacker!");
            } else {
                System.out.println("ℹ️ [Cyber Tycoon] O banco já possui funcionários cadastrados. Pulando etapa de seed.");
            }
            
        };
    }
}
