package com.projeto.trabalho_2_service_layer.dao;
import com.projeto.trabalho_2_service_layer.model.Produto;
import java.sql.SQLException;
import java.util.List;

public interface IProdutoDAO {
    void insere(Produto produto) throws SQLException;
    void atualiza(Produto produto) throws SQLException;
    void remove(Produto produto) throws SQLException;
    Produto buscaPorCodigo(int produtoId) throws SQLException;
    List<Produto> listaTodos() throws SQLException;
}