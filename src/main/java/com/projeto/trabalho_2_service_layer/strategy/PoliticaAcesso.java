package com.projeto.trabalho_2_service_layer.strategy;

/**
 * PADRÃO STRATEGY - papel: Strategy (interface da estratégia).
 * Define o "contrato" de uma política de acesso por perfil de usuário.
 * Cada perfil (Gerente, Garçom, Cozinheiro) é uma estratégia concreta.
 */
public interface PoliticaAcesso {

    /** Nome do perfil como é gravado no banco (ex.: "Garçom"). */
    String getPerfil();

    boolean podeAcessarUsuarios();

    boolean podeAcessarProdutos();

    boolean podeAcessarCardapio();

    boolean podeAcessarComandas();
}
