package com.example.imobiliaria.controller;

import com.example.imobiliaria.exception.EntidadeNaoEncontradaException;
import com.example.imobiliaria.exception.NegocioException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    private final MessageSource messageSource;

    public TratadorDeErros(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // 1. Trata erros de Bean Validation (@Valid, @NotBlank, etc.) -> Retorna HTTP 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrosDeValidacao handleValidation(MethodArgumentNotValidException exception) {
        ErrosDeValidacao erros = new ErrosDeValidacao(
                LocalDateTime.now(),
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente"
        );

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(field -> {
            String mensagem = messageSource.getMessage(field, LocaleContextHolder.getLocale());
            erros.adiciona(new Erro(field.getField(), mensagem));
        });

        return erros;
    }

    // 2. Trata erros de regras de negócio -> Retorna HTTP 400
    @ExceptionHandler(NegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrosDeValidacao handleNegocio(NegocioException ex) {
        return new ErrosDeValidacao(LocalDateTime.now(), ex.getMessage());
    }

    // 3. Trata entidade não encontrada (busca por ID inexistente) -> Retorna HTTP 404
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrosDeValidacao handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return new ErrosDeValidacao(LocalDateTime.now(), ex.getMessage());
    }
}
