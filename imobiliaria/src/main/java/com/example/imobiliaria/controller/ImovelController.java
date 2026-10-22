package com.example.imobiliaria.controller;

import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.service.ImoveisService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    private final ImoveisService imoveisService;

    public ImovelController(ImoveisService imoveisService) {
        this.imoveisService = imoveisService;
    }

    @PostMapping
    public ResponseEntity<Imoveis> cadastrarImovel(@Valid @RequestBody Imoveis imoveis) {
        Imoveis imoveissalvo = imoveisService.salvar(imoveis);
        return ResponseEntity.status(HttpStatus.CREATED).body(imoveissalvo);
    }

    @GetMapping
    public ResponseEntity<List<Imoveis>> findAll() {
        return ResponseEntity.ok(imoveisService.listarImoveis());
    }

    // Busca paginada no formato das aulas do IFMA: /imoveis/paginacao/{numPagina}/{qtdPagina}
    @GetMapping("/paginacao/{numPagina}/{qtdPagina}")
    public ResponseEntity<Page<Imoveis>> buscaPaginadaPorPath(
            @PathVariable int numPagina,
            @PathVariable int qtdPagina) {
        if (qtdPagina > 10) {
            qtdPagina = 10; // Limite de proteção por página
        }
        PageRequest pageRequest = PageRequest.of(numPagina, qtdPagina);
        return ResponseEntity.ok(imoveisService.buscaPaginada(pageRequest));
    }

    // Busca paginada com query params padrão do Spring: /imoveis/paginada?page=0&size=5&sort=tipoImovel,asc
    @GetMapping("/paginada")
    public ResponseEntity<Page<Imoveis>> buscaPaginada(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(imoveisService.buscaPaginada(pageable));
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
