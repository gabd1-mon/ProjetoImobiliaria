package com.example.imobiliaria.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ErrosDeValidacao {

    private LocalDateTime dataHora;
    private String titulo;
    private List<Erro> erros = new ArrayList<>();

    public ErrosDeValidacao(LocalDateTime dataHora, String titulo) {
        this.dataHora = dataHora;
        this.titulo = titulo;
    }

    public void adiciona(Erro erro) {
        erros.add(erro);
    }

    public String getDataHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHora.format(formatter);
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Erro> getErros() {
        return erros;
    }
}
