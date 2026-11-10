package com.projeto.trabalho_2_service_layer.model;

public class Produto {
    private int produtoId;
    private String nome;
    private double preco;
    private int estoqueId;

    public Produto() {}

    public Produto(String nome, double preco, int estoqueId) {
        this.nome = nome;
        this.preco = preco;
        this.estoqueId = estoqueId;
    }

    public Produto(int produtoId, String nome, double preco, int estoqueId) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.preco = preco;
        this.estoqueId = estoqueId;
    }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getEstoqueId() { return estoqueId; }
    public void setEstoqueId(int estoqueId) { this.estoqueId = estoqueId; }

    @Override
    public String toString() {
        return nome + " (R$ " + String.format("%.2f", preco) + ")";
    }
}