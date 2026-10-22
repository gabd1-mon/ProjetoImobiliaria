package com.example.imobiliaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "locacao")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "O imóvel é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_imovel")
    private Imoveis imoveis;

    @NotNull(message = "O inquilino (cliente) é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_inquilino")
    private Cliente cliente;

    @NotNull(message = "O status da locação (ativo) é obrigatório")
    @Column(nullable = false)
    private Boolean ativo;

    @NotNull(message = "A data de fim é obrigatória")
    @Column(nullable = false)
    private LocalDate dataFim;

    @NotNull(message = "A data de início é obrigatória")
    @Column(nullable = false)
    private LocalDate dataInicio;

    @NotNull(message = "O dia de vencimento é obrigatório")
    @Min(value = 1, message = "O dia de vencimento não pode ser menor que 1")
    @Max(value = 31, message = "O dia de vencimento não pode ser maior que 31")
    @Column(nullable = false, name = "dia_vencimento")
    private Integer diaVencimento;

    @NotNull(message = "O percentual de multa é obrigatório")
    @PositiveOrZero(message = "O percentual de multa não pode ser negativo")
    @Column(nullable = false)
    private BigDecimal percMulta;

    @NotNull(message = "O valor do aluguel é obrigatório")
    @PositiveOrZero(message = "O valor do aluguel não pode ser negativo")
    @Column(nullable = false)
    private BigDecimal valorAluguel;

    @Column(columnDefinition = "TEXT")
    private String obs;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Imoveis getImoveis() {
        return imoveis;
    }

    public void setImoveis(Imoveis imoveis) {
        this.imoveis = imoveis;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public BigDecimal getPercMulta() {
        return percMulta;
    }

    public void setPercMulta(BigDecimal percMulta) {
        this.percMulta = percMulta;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}