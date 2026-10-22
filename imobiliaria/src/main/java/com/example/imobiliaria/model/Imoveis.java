package com.example.imobiliaria.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
@Entity
@Table(name = "imoveis")

public class Imoveis {
    // Adiciona estas anotações aos atributos na classe Imoveis.java
    // Lembra-te de importar de jakarta.validation.constraints.*

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O tipo de imóvel é obrigatório")
    private String tipoImovel;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "O CEP é obrigatório")
    private String cep;

    @NotNull(message = "A quantidade de dormitórios é obrigatória")
    private Integer dormitorios;

    @NotNull(message = "A quantidade de banheiros é obrigatória")
    private Integer banheiros;

    @Column ( nullable = false)
    private Integer suites;

    @Column ( nullable = false)
    private Integer metragem;

    @Column(name = "valor_aluguel_sug", precision = 10, scale = 2)
    private BigDecimal valorAluguelSug;

    @Column(columnDefinition = "TEXT")
    private String obs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Integer getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(Integer banheiros) {
        this.banheiros = banheiros;
    }

    public Integer getSuites() {
        return suites;
    }

    public void setSuites(Integer suites) {
        this.suites = suites;
    }

    public Integer getMetragem() {
        return metragem;
    }

    public void setMetragem(Integer metragem) {
        this.metragem = metragem;
    }

    public BigDecimal getValorAluguelSug() {
        return valorAluguelSug;
    }

    public void setValorAluguelSug(BigDecimal valorAluguelSug) {
        this.valorAluguelSug = valorAluguelSug;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
