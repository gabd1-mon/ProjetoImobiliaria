package com.example.imobiliaria.service;

import com.example.imobiliaria.exception.EntidadeNaoEncontradaException;
import com.example.imobiliaria.model.Locacao;
import com.example.imobiliaria.repository.LocacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;

    public LocacaoService(LocacaoRepository locacaoRepository) {
        this.locacaoRepository = locacaoRepository;
    }

    public Locacao salvar(Locacao locacao) {
        return locacaoRepository.save(locacao);
    }

    public List<Locacao> listarLocacao() {
        return locacaoRepository.findAll();
    }

    public Locacao buscarPorId(Integer id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Locação não encontrada com o ID: " + id));
    }

    public Locacao atualizar(Integer id, Locacao locacaoAtualizada) {
        Locacao locacaoExistente = buscarPorId(id);

        locacaoExistente.setImoveis(locacaoAtualizada.getImoveis());
        locacaoExistente.setCliente(locacaoAtualizada.getCliente());
        locacaoExistente.setAtivo(locacaoAtualizada.getAtivo());
        locacaoExistente.setDataFim(locacaoAtualizada.getDataFim());
        locacaoExistente.setDataInicio(locacaoAtualizada.getDataInicio());
        locacaoExistente.setDiaVencimento(locacaoAtualizada.getDiaVencimento());
        locacaoExistente.setPercMulta(locacaoAtualizada.getPercMulta());
        locacaoExistente.setValorAluguel(locacaoAtualizada.getValorAluguel());
        locacaoExistente.setObs(locacaoAtualizada.getObs());

        return locacaoRepository.save(locacaoExistente);
    }

    public void deletar(Integer id) {
        buscarPorId(id); // Garante 404 se não existir
        locacaoRepository.deleteById(id);
    }
}
