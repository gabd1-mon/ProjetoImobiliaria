package com.example.imobiliaria;

import com.example.imobiliaria.exception.NegocioException;
import com.example.imobiliaria.model.Cliente;
import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.model.Locacao;
import com.example.imobiliaria.service.ClienteService;
import com.example.imobiliaria.service.ImoveisService;
import com.example.imobiliaria.service.LocacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImobiliariaApplicationTests {

    @Autowired
    private ImoveisService imoveisService;

    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private ClienteService clienteService;

    @Test
    void contextLoads() {
    }

    @Test
    void deveBuscarImoveisComPaginacao() {
        // Tarefa 6: Busca a página 0 com 5 elementos
        Page<Imoveis> pagina = imoveisService.buscaPaginada(PageRequest.of(0, 5));

        assertNotNull(pagina);
        assertEquals(5, pagina.getNumberOfElements(), "Deve trazer 5 imóveis na primeira página");
        assertEquals(9, pagina.getTotalElements(), "Deve ter 9 imóveis no total cadastrados no banco");
        assertEquals(2, pagina.getTotalPages(), "Deve ter 2 páginas no total");
    }

    @Test
    void deveCalcularAutomaticamenteOValorDaLocacao() {
        // Tarefa 8: Locação de 10 diárias para o Imóvel 2 (R$ 2200.00 sugerido)
        Imoveis imovel = imoveisService.buscarPorId(2);
        Cliente cliente = clienteService.buscarPorId(2);

        Locacao novaLocacao = new Locacao();
        novaLocacao.setImoveis(imovel);
        novaLocacao.setCliente(cliente);
        novaLocacao.setDataInicio(LocalDate.of(2027, 1, 1));
        novaLocacao.setDataFim(LocalDate.of(2027, 1, 11)); // 10 dias
        novaLocacao.setDiaVencimento(5);
        novaLocacao.setPercMulta(new BigDecimal("2.00"));

        Locacao salva = locacaoService.salvar(novaLocacao);

        // 10 diárias * 2200 = 22000.00
        BigDecimal valorEsperado = imovel.getValorAluguelSug().multiply(BigDecimal.valueOf(10));
        assertEquals(0, valorEsperado.compareTo(salva.getValorAluguel()), "O valor deve ser calculado pelo sistema");
        assertTrue(salva.getAtivo(), "A locação deve nascer ativa");
    }

    @Test
    void deveRejeitarLocacaoComDataFinalAnteriorOuIgualAInicial() {
        // Tarefa 8: Data inválida
        Imoveis imovel = imoveisService.buscarPorId(2);
        Cliente cliente = clienteService.buscarPorId(2);

        Locacao locacaoInvalida = new Locacao();
        locacaoInvalida.setImoveis(imovel);
        locacaoInvalida.setCliente(cliente);
        locacaoInvalida.setDataInicio(LocalDate.of(2027, 5, 10));
        locacaoInvalida.setDataFim(LocalDate.of(2027, 5, 5)); // Anterior à inicial!
        locacaoInvalida.setDiaVencimento(10);
        locacaoInvalida.setPercMulta(new BigDecimal("2.00"));

        assertThrows(NegocioException.class, () -> locacaoService.salvar(locacaoInvalida),
                "Deve lançar NegocioException quando a data final for anterior à data inicial");
    }

    @Test
    void deveRejeitarLocacaoComConflitoDePeriodo() {
        // Tarefa 7: O Imóvel 1 já possui locação de 2026-11-01 a 2026-11-30
        Imoveis imovel1 = imoveisService.buscarPorId(1);
        Cliente cliente = clienteService.buscarPorId(3);

        Locacao locacaoConflitante = new Locacao();
        locacaoConflitante.setImoveis(imovel1);
        locacaoConflitante.setCliente(cliente);
        // Tenta alugar de 10 a 20 de novembro de 2026 (período ocupado!)
        locacaoConflitante.setDataInicio(LocalDate.of(2026, 11, 10));
        locacaoConflitante.setDataFim(LocalDate.of(2026, 11, 20));
        locacaoConflitante.setDiaVencimento(10);
        locacaoConflitante.setPercMulta(new BigDecimal("2.00"));

        NegocioException ex = assertThrows(NegocioException.class, () -> locacaoService.salvar(locacaoConflitante));
        assertTrue(ex.getMessage().contains("já possui locação confirmada para o período informado"));
    }

    @Test
    void deveImpedirExclusaoDeImovelComLocacaoAtivaOuFutura() {
        // Tarefa 9: Imóvel 1 possui locação ativa em novembro de 2026
        NegocioException ex = assertThrows(NegocioException.class, () -> imoveisService.deletar(1));
        assertTrue(ex.getMessage().contains("Não é permitido excluir imóvel"));
    }
}
