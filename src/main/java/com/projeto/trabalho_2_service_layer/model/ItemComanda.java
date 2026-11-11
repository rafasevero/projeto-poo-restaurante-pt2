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

    // PADRÃO BUILDER - construtor privado: o único caminho para ele é o Builder
    private ItemComanda(Builder b) {
        this.itemComandaId = b.itemComandaId;
        this.comandaId = b.comandaId;
        this.produtoId = b.produtoId;
        this.quantidade = b.quantidade;
        this.precoUnitario = b.precoUnitario;
        this.nomeProduto = b.nomeProduto;
    }

    public static Builder builder() {
        return new Builder();
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

    /**
     * PADRÃO BUILDER - papel: Builder (Product = ItemComanda).
     * Monta o ItemComanda passo a passo, com métodos nomeados (fluent interface),
     * e valida os campos obrigatórios no build(), impedindo objetos incompletos.
     * Campos obrigatórios: comandaId, produtoId, quantidade e precoUnitario.
     * Campos opcionais: itemComandaId (gerado pelo banco) e nomeProduto (só exibição).
     */
    public static class Builder {
        private int itemComandaId;
        private int comandaId;
        private int produtoId;
        private int quantidade;
        private Double precoUnitario; // Double (nulo = "não informado")
        private String nomeProduto;

        private Builder() {}

        public Builder itemComandaId(int itemComandaId) {
            this.itemComandaId = itemComandaId;
            return this;
        }

        public Builder comandaId(int comandaId) {
            this.comandaId = comandaId;
            return this;
        }

        public Builder produtoId(int produtoId) {
            this.produtoId = produtoId;
            return this;
        }

        public Builder quantidade(int quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public Builder precoUnitario(double precoUnitario) {
            this.precoUnitario = precoUnitario;
            return this;
        }

        public Builder nomeProduto(String nomeProduto) {
            this.nomeProduto = nomeProduto;
            return this;
        }

        /** @throws IllegalStateException se algum campo obrigatório estiver ausente ou inválido */
        public ItemComanda build() {
            if (comandaId <= 0) {
                throw new IllegalStateException("O item precisa estar vinculado a uma comanda válida.");
            }
            if (produtoId <= 0) {
                throw new IllegalStateException("O item precisa estar vinculado a um produto válido.");
            }
            if (quantidade <= 0) {
                throw new IllegalStateException("A quantidade do item deve ser maior que zero.");
            }
            if (precoUnitario == null || precoUnitario < 0) {
                throw new IllegalStateException("O preço unitário do item não foi informado ou é inválido.");
            }
            return new ItemComanda(this);
        }
    }
}