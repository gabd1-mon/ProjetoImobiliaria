package com.example.imobiliaria.controller;

import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.service.ImoveisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    private final ImoveisService imoveisService;

    public ImovelController(ImoveisService imoveisService){
        this.imoveisService = imoveisService;
    }

    @PostMapping
    public ResponseEntity<Imoveis> cadastrarImovel(@Valid @RequestBody Imoveis imoveis){
        Imoveis imoveissalvo = imoveisService.salvar(imoveis);
        return ResponseEntity.status(HttpStatus.CREATED).body(imoveissalvo);
    }

    @GetMapping
    public ResponseEntity<List<Imoveis>> findAll(){
        return ResponseEntity.ok(imoveisService.listarImoveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imoveis> buscarPorId(@PathVariable Integer id) {
        Imoveis imovel = imoveisService.buscarPorId(id);
        return ResponseEntity.ok(imovel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imoveis> atualizarImovel(@PathVariable Integer id, @Valid @RequestBody Imoveis imovelAtualizado) {
        Imoveis imovel = imoveisService.atualizar(id, imovelAtualizado);
        return ResponseEntity.ok(imovel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImovel(@PathVariable Integer id) {
        imoveisService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}