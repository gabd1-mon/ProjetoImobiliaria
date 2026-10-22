package com.example.imobiliaria.service;

import com.example.imobiliaria.exception.EntidadeNaoEncontradaException;
import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.repository.ImoveisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImoveisService {

    private final ImoveisRepository imoveisRepository;

    public ImoveisService(ImoveisRepository imoveisRepository) {
        this.imoveisRepository = imoveisRepository;
    }

    public Imoveis salvar(Imoveis imoveis) {
        return imoveisRepository.save(imoveis);
    }

    public List<Imoveis> listarImoveis() {
        return imoveisRepository.findAll();
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

    public void deletar(Integer id) {
        buscarPorId(id); // Garante 404 se não existir
        imoveisRepository.deleteById(id);
    }
}
