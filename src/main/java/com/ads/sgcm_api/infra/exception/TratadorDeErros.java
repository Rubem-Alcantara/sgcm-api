package com.ads.sgcm_api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErrosDeRegraDeNegocio(RuntimeException ex) {

        // Em vez de deixar o Spring redirecionar para o /error e gerar um 403,
        // interceptamos e devolvemos um Erro 400 (Bad Request) com a mensagem certa
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}