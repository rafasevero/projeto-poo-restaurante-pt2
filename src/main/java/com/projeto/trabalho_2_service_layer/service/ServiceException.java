package com.projeto.trabalho_2_service_layer.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    // Regra de negócio violada (não há exceção anterior)
    public ServiceException(String mensagem) {
        super(mensagem);
    }

    // Falha técnica: preserva a causa original para depuração
    public ServiceException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}