package com.projeto.trabalho_2_service_layer.dao;

import com.projeto.trabalho_2_service_layer.model.Comanda;
import java.sql.SQLException;
import java.util.List;

public interface IComandaDAO {
    void insere(Comanda comanda) throws SQLException;
    void atualiza(Comanda comanda) throws SQLException;
    void remove(Comanda comanda) throws SQLException;
    List<Comanda> listaTodos() throws SQLException;
}