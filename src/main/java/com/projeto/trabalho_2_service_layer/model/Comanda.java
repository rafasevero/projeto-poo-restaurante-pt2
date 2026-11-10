package com.projeto.trabalho_2_service_layer.model;

public class Comanda {
    private int comandaId;
    private int numeroMesa;
    private String status;
    private int usuarioId;

    public Comanda() {}

    public Comanda(int numeroMesa, String status) {
        this.numeroMesa = numeroMesa;
        this.status = status;
    }

    public Comanda(int comandaId, int numeroMesa, String status) {
        this.comandaId = comandaId;
        this.numeroMesa = numeroMesa;
        this.status = status;
    }

    public int getComandaId() { return comandaId; }
    public void setComandaId(int comandaId) { this.comandaId = comandaId; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}