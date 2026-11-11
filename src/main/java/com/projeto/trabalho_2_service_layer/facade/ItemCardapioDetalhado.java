package com.projeto.trabalho_2_service_layer.facade;

import com.projeto.trabalho_2_service_layer.model.Cardapio;

/**
 * Objeto de transferência devolvido pela CardapioFacade: um item do cardápio
 * já acompanhado do nome do produto correspondente (dado que vem de outro Service).
 */
public class ItemCardapioDetalhado {

    private final Cardapio cardapio;
    private final String nomeProduto;

    public ItemCardapioDetalhado(Cardapio cardapio, String nomeProduto) {
        this.cardapio = cardapio;
        this.nomeProduto = nomeProduto;
    }

    public Cardapio getCardapio() { return cardapio; }

    public String getNomeProduto() { return nomeProduto; }
}
