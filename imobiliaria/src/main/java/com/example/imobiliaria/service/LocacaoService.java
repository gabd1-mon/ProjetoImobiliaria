package com.example.imobiliaria.service;

import org.springframework.stereotype.Service;
import com.example.imobiliaria.model.Locacao;
import com.example.imobiliaria.repository.LocacaoRepository;

import java.util.List;

@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;

    public LocacaoService(LocacaoRepository locacaoRepository) {
        this.locacaoRepository = locacaoRepository;
    }

    public Locacao salvar(Locacao locacao){
        return locacaoRepository.save(locacao);
    }

    public List<Locacao> listarLocacao(){
        return locacaoRepository.findAll();
    }

    public Locacao buscarPorId(Integer id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada com o ID: " + id));
    }

    public Locacao atualizar(Integer id, Locacao locacaoAtualizada) {
        Locacao locacaoExistente = buscarPorId(id);

        // Atualiza os dados da locação com os novos valores
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
        locacaoRepository.deleteById(id);
    }
}