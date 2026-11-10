package com.projeto.trabalho_2_service_layer.dao;

import com.projeto.trabalho_2_service_layer.model.ItemComanda;
import java.sql.SQLException;
import java.util.List;

public interface IItemComandaDAO {
    void insere(ItemComanda item) throws SQLException;
    List<ItemComanda> listaPorComanda(int comandaId) throws SQLException;
    double calculaValorTotal(int comandaId) throws SQLException;
}