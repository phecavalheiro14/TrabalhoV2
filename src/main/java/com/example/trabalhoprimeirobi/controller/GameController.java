package com.example.trabalhoprimeirobi.controller;

import com.example.trabalhoprimeirobi.model.Game;
import com.example.trabalhoprimeirobi.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @PostMapping
    public ResponseEntity<Game> cadastrar(@RequestBody Game game) {

        Game gameSalvo = gameRepository.save(game);

        return ResponseEntity.status(HttpStatus.CREATED).body(gameSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Game>> listarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String plataforma) {

        List<Game> resultado = gameRepository.findByFiltros(titulo, genero, plataforma);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> buscarPorId(@PathVariable Long id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> atualizar(@PathVariable Long id, @RequestBody Game gameAtualizado) {
        Optional<Game> gameExistente = gameRepository.findById(id);
        if (gameExistente.isPresent()) {
            Game game = gameExistente.get();
            game.setTitulo(gameAtualizado.getTitulo());
            game.setGenero(gameAtualizado.getGenero());
            game.setPlataforma(gameAtualizado.getPlataforma());
            game.setPreco(gameAtualizado.getPreco());
            gameRepository.save(game);
            return ResponseEntity.ok(game);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }}
