package com.projeto.trabalho_2_service_layer.strategy;

/** PADRÃO STRATEGY - papel: ConcreteStrategy. O Garçom acessa Cardápio e Comandas. */
public class GarcomPolitica implements PoliticaAcesso {

    @Override public String getPerfil() { return "Garçom"; }
    @Override public boolean podeAcessarUsuarios() { return false; }
    @Override public boolean podeAcessarProdutos() { return false; }
    @Override public boolean podeAcessarCardapio() { return true; }
    @Override public boolean podeAcessarComandas() { return true; }
}
