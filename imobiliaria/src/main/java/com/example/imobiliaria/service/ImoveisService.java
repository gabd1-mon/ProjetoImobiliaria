package com.example.imobiliaria.service;

import com.example.imobiliaria.exception.EntidadeNaoEncontradaException;
import com.example.imobiliaria.exception.NegocioException;
import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.repository.ImoveisRepository;
import com.example.imobiliaria.repository.LocacaoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ImoveisService {

    private final ImoveisRepository imoveisRepository;
    private final LocacaoRepository locacaoRepository;

    public ImoveisService(ImoveisRepository imoveisRepository, LocacaoRepository locacaoRepository) {
        this.imoveisRepository = imoveisRepository;
        this.locacaoRepository = locacaoRepository;
    }

    public Imoveis salvar(Imoveis imoveis) {
        return imoveisRepository.save(imoveis);
    }

    public List<Imoveis> listarImoveis() {
        return imoveisRepository.findAll();
    }

    // Busca paginada usando facilidades do Spring Data JPA (Tarefa 6)
    public Page<Imoveis> buscaPaginada(Pageable pageable) {
        return imoveisRepository.findAll(pageable);
    }

    // Tarefa 10: Consultas dinâmicas com Query by Example (QBE)
    public List<Imoveis> pesquisarPorExample(String tipo, String cidade, Integer dormitorios) {
        Imoveis probe = new Imoveis();
        probe.setTipoImovel(tipo);
        probe.setEndereco(cidade);
        probe.setDormitorios(dormitorios);

        // Configuração do Matcher: ignora nulos, case-insensitive e correspondência parcial (LIKE %termo%)
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Imoveis> example = Example.of(probe, matcher);
        return imoveisRepository.findAll(example);
    }

    public Imoveis buscarPorId(Integer id) {
        return imoveisRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Imóvel não encontrado com o ID: " + id));
    }

    public Imoveis atualizar(Integer id, Imoveis imovelAtualizado) {
        Imoveis imovelExistente = buscarPorId(id);

        imovelExistente.setTipoImovel(imovelAtualizado.getTipoImovel());
        imovelExistente.setEndereco(imovelAtualizado.getEndereco());
        imovelExistente.setCep(imovelAtualizado.getCep());
        imovelExistente.setDormitorios(imovelAtualizado.getDormitorios());
        imovelExistente.setBanheiros(imovelAtualizado.getBanheiros());
        imovelExistente.setSuites(imovelAtualizado.getSuites());
        imovelExistente.setMetragem(imovelAtualizado.getMetragem());
        imovelExistente.setValorAluguelSug(imovelAtualizado.getValorAluguelSug());
        imovelExistente.setObs(imovelAtualizado.getObs());

        return imoveisRepository.save(imovelExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        buscarPorId(id); // Garante 404 se não existir

        // Tarefa 9: Impedir a exclusão de imóvel que possua locações ativas ou futuras
        boolean possuiLocacoes = locacaoRepository.possuiLocacoesAtivasOuFuturas(id, LocalDate.now());
        if (possuiLocacoes) {
            throw new NegocioException("Não é permitido excluir imóvel que possua locações ativas ou futuras.");
        }

        imoveisRepository.deleteById(id);
    }
}
