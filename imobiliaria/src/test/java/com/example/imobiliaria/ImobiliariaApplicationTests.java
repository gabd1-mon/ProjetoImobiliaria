package com.example.imobiliaria;

import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.service.ImoveisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ImobiliariaApplicationTests {

    @Autowired
    private ImoveisService imoveisService;

    @Test
    void contextLoads() {
    }

    @Test
    void deveBuscarImoveisComPaginacao() {
        // Busca a página 0 com 5 elementos
        Page<Imoveis> pagina = imoveisService.buscaPaginada(PageRequest.of(0, 5));

        assertNotNull(pagina);
        assertEquals(5, pagina.getNumberOfElements(), "Deve trazer 5 imóveis na primeira página");
        assertEquals(9, pagina.getTotalElements(), "Deve ter 9 imóveis no total cadastrados no banco");
        assertEquals(2, pagina.getTotalPages(), "Deve ter 2 páginas no total");
    }
}
