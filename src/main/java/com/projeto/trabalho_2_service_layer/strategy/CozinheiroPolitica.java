package com.projeto.trabalho_2_service_layer.strategy;

/** PADRÃO STRATEGY - papel: ConcreteStrategy. O Cozinheiro acessa apenas Comandas. */
public class CozinheiroPolitica implements PoliticaAcesso {

    @Override public String getPerfil() { return "Cozinheiro"; }
    @Override public boolean podeAcessarUsuarios() { return false; }
    @Override public boolean podeAcessarProdutos() { return false; }
    @Override public boolean podeAcessarCardapio() { return false; }
    @Override public boolean podeAcessarComandas() { return true; }
}
