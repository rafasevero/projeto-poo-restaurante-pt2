package com.projeto.trabalho_2_service_layer.model;

public class ItemComanda {
    private int itemComandaId;
    private int comandaId;
    private int produtoId;
    private int quantidade;
    private double precoUnitario;
    private String nomeProduto; // Apenas para exibição na tela

    public ItemComanda() {}

    public ItemComanda(int comandaId, int produtoId, int quantidade, double precoUnitario) {
        this.comandaId = comandaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getItemComandaId() { return itemComandaId; }
    public void setItemComandaId(int itemComandaId) { this.itemComandaId = itemComandaId; }

    public int getComandaId() { return comandaId; }
    public void setComandaId(int comandaId) { this.comandaId = comandaId; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
}