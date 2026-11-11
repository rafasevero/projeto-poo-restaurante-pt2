package com.projeto.trabalho_2_service_layer.strategy;

/** PADRÃO STRATEGY - papel: ConcreteStrategy. O Gerente acessa todas as telas. */
public class GerentePolitica implements PoliticaAcesso {

    @Override public String getPerfil() { return "Gerente"; }
    @Override public boolean podeAcessarUsuarios() { return true; }
    @Override public boolean podeAcessarProdutos() { return true; }
    @Override public boolean podeAcessarCardapio() { return true; }
    @Override public boolean podeAcessarComandas() { return true; }
}
