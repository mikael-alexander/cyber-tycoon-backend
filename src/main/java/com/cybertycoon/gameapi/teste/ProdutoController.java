package com.cybertycoon.gameapi.teste;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final List<Produto> produtos = List.of(
            new Produto(1, "Notebook", "Notebook para uso diario", new BigDecimal("3499.90")),
            new Produto(2, "Mouse", "Mouse sem fio", new BigDecimal("89.90")),
            new Produto(3, "Teclado", "Teclado USB", new BigDecimal("129.90")));

    @GetMapping
    public List<Produto> listar() {
        return produtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable long id) {
        return produtos.stream()
                .filter(produto -> produto.id() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public record Produto(long id, String nome, String descricao, BigDecimal preco) {
    }
}