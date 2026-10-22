package com.example.imobiliaria.repository;

import com.example.imobiliaria.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {

    // Tarefa 7: Verifica se existe outra locação ativa/confirmada para o mesmo imóvel com sobreposição de datas
    @Query("""
        SELECT count(l) > 0 FROM Locacao l
        WHERE l.imoveis.id = :imovelId
          AND l.ativo = true
          AND (:idLocacaoAtual IS NULL OR l.id <> :idLocacaoAtual)
          AND l.dataInicio <= :dataFim
          AND l.dataFim >= :dataInicio
    """)
    boolean existeConflitoDePeriodo(
            @Param("imovelId") Integer imovelId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("idLocacaoAtual") Integer idLocacaoAtual
    );

    // Tarefa 9: Verifica se o imóvel possui locações ativas ou com término hoje/futuro
    @Query("""
        SELECT count(l) > 0 FROM Locacao l
        WHERE l.imoveis.id = :imovelId
          AND (l.ativo = true OR l.dataFim >= :dataReferencia)
    """)
    boolean possuiLocacoesAtivasOuFuturas(
            @Param("imovelId") Integer imovelId,
            @Param("dataReferencia") LocalDate dataReferencia
    );
}
