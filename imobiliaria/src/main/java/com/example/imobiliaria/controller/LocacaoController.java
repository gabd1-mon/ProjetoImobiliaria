package com.example.imobiliaria.controller;

import com.example.imobiliaria.model.Locacao;
import com.example.imobiliaria.service.LocacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locacao")
public class LocacaoController {

    private final LocacaoService locacaoService;

    public LocacaoController(LocacaoService locacaoService) {
        this.locacaoService = locacaoService;
    }

    @PostMapping
    public ResponseEntity<Locacao> salvar(@Valid @RequestBody Locacao locacao){
        Locacao salvarLocacao = locacaoService.salvar(locacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarLocacao);
    }

    @GetMapping
    public ResponseEntity<List<Locacao>> listar(){
        return ResponseEntity.ok(locacaoService.listarLocacao());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscarPorId(@PathVariable Integer id) {
        Locacao locacao = locacaoService.buscarPorId(id);
        return ResponseEntity.ok(locacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locacao> atualizar(@PathVariable Integer id, @Valid @RequestBody Locacao locacaoAtualizada) {
        Locacao locacao = locacaoService.atualizar(id, locacaoAtualizada);
        return ResponseEntity.ok(locacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        locacaoService.deletar(id);
        return ResponseEntity.noContent().build(); // Código 204
    }
}