package com.projeto.trabalho_2_service_layer.dao;

import com.projeto.trabalho_2_service_layer.model.Cardapio;
import java.sql.SQLException;
import java.util.List;

public interface ICardapioDAO {
    void insere(Cardapio cardapio) throws SQLException;
    void atualiza(Cardapio cardapio) throws SQLException;
    void remove(Cardapio cardapio) throws SQLException;
    List<Cardapio> listaTodos() throws SQLException;
}