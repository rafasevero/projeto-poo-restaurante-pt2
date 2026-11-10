package com.projeto.trabalho_2_service_layer.model;

public class Cardapio {
    private int cardapioId;
    private String nomeSecao;
    private String descricao;
    private int produtoId;

    public Cardapio() {}

    public Cardapio(String nomeSecao, String descricao, int produtoId) {
        this.nomeSecao = nomeSecao;
        this.descricao = descricao;
        this.produtoId = produtoId;
    }

    public Cardapio(int cardapioId, String nomeSecao, String descricao, int produtoId) {
        this.cardapioId = cardapioId;
        this.nomeSecao = nomeSecao;
        this.descricao = descricao;
        this.produtoId = produtoId;
    }

    public int getCardapioId() { return cardapioId; }
    public void setCardapioId(int cardapioId) { this.cardapioId = cardapioId; }

    public String getNomeSecao() { return nomeSecao; }
    public void setNomeSecao(String nomeSecao) { this.nomeSecao = nomeSecao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }
}