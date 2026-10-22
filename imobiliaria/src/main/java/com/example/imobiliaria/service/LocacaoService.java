package com.example.imobiliaria.service;

import com.example.imobiliaria.exception.EntidadeNaoEncontradaException;
import com.example.imobiliaria.exception.NegocioException;
import com.example.imobiliaria.model.Cliente;
import com.example.imobiliaria.model.Imoveis;
import com.example.imobiliaria.model.Locacao;
import com.example.imobiliaria.repository.ClienteRepository;
import com.example.imobiliaria.repository.ImoveisRepository;
import com.example.imobiliaria.repository.LocacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LocacaoService {

    private final LocacaoRepository locacaoRepository;
    private final ImoveisRepository imoveisRepository;
    private final ClienteRepository clienteRepository;

    public LocacaoService(LocacaoRepository locacaoRepository,
                          ImoveisRepository imoveisRepository,
                          ClienteRepository clienteRepository) {
        this.locacaoRepository = locacaoRepository;
        this.imoveisRepository = imoveisRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Locacao salvar(Locacao locacao) {
        validarEAplicarRegras(locacao, null);
        return locacaoRepository.save(locacao);
    }

    public List<Locacao> listarLocacao() {
        return locacaoRepository.findAll();
    }

    public Locacao buscarPorId(Integer id) {
        return locacaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Locação não encontrada com o ID: " + id));
    }

    @Transactional
    public Locacao atualizar(Integer id, Locacao locacaoAtualizada) {
        Locacao locacaoExistente = buscarPorId(id);

        locacaoExistente.setImoveis(locacaoAtualizada.getImoveis());
        locacaoExistente.setCliente(locacaoAtualizada.getCliente());
        locacaoExistente.setDataInicio(locacaoAtualizada.getDataInicio());
        locacaoExistente.setDataFim(locacaoAtualizada.getDataFim());
        locacaoExistente.setDiaVencimento(locacaoAtualizada.getDiaVencimento());
        locacaoExistente.setPercMulta(locacaoAtualizada.getPercMulta());
        locacaoExistente.setObs(locacaoAtualizada.getObs());
        if (locacaoAtualizada.getAtivo() != null) {
            locacaoExistente.setAtivo(locacaoAtualizada.getAtivo());
        }

        validarEAplicarRegras(locacaoExistente, id);
        return locacaoRepository.save(locacaoExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        buscarPorId(id); // Garante 404 se não existir
        locacaoRepository.deleteById(id);
    }

    // Método centralizador das regras de negócio (Tarefas 7 e 8)
    private void validarEAplicarRegras(Locacao locacao, Integer idLocacaoAtual) {
        // 1. Validação de presença do Imóvel e Inquilino
        if (locacao.getImoveis() == null || locacao.getImoveis().getId() == null) {
            throw new NegocioException("O imóvel deve ser informado.");
        }
        if (locacao.getCliente() == null || locacao.getCliente().getId() == null) {
            throw new NegocioException("O inquilino deve ser informado.");
        }

        Imoveis imovel = imoveisRepository.findById(locacao.getImoveis().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Imóvel informado não encontrado."));

        Cliente cliente = clienteRepository.findById(locacao.getCliente().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inquilino informado não encontrado."));

        locacao.setImoveis(imovel);
        locacao.setCliente(cliente);

        // 2. Tarefa 8: Validar se a data final é posterior à data inicial
        if (locacao.getDataInicio() == null || locacao.getDataFim() == null) {
            throw new NegocioException("As datas de início e fim são obrigatórias.");
        }
        if (!locacao.getDataFim().isAfter(locacao.getDataInicio())) {
            throw new NegocioException("A data final deve ser posterior à data inicial.");
        }

        // 3. Tarefa 7: Verificação de disponibilidade (não permitir sobreposição de locações ativas)
        boolean haConflito = locacaoRepository.existeConflitoDePeriodo(
                imovel.getId(),
                locacao.getDataInicio(),
                locacao.getDataFim(),
                idLocacaoAtual
        );

        if (haConflito) {
            throw new NegocioException("O imóvel selecionado já possui locação confirmada para o período informado.");
        }

        // 4. Tarefa 8: Calcular quantidade de diárias e valor total automaticamente com BigDecimal
        long quantidadeDiarias = ChronoUnit.DAYS.between(locacao.getDataInicio(), locacao.getDataFim());

        BigDecimal valorDiaria = imovel.getValorAluguelSug();
        if (valorDiaria == null || valorDiaria.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("O imóvel não possui valor de diária/aluguel válido para cálculo.");
        }

        BigDecimal valorTotal = valorDiaria.multiply(BigDecimal.valueOf(quantidadeDiarias));
        locacao.setValorAluguel(valorTotal);

        if (locacao.getAtivo() == null) {
            locacao.setAtivo(true);
        }
    }
}
