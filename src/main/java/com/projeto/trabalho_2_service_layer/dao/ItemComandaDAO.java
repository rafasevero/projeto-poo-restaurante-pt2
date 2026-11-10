package com.projeto.trabalho_2_service_layer.dao;

import com.projeto.trabalho_2_service_layer.model.ItemComanda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemComandaDAO implements IItemComandaDAO {

    @Override
    public void insere(ItemComanda item) throws SQLException {
        String sql = "INSERT INTO item_comanda (comanda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, item.getComandaId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<ItemComanda> listaPorComanda(int comandaId) throws SQLException {
        List<ItemComanda> lista = new ArrayList<>();
        String sql = "SELECT ic.*, p.nome AS nome_produto FROM item_comanda ic " +
                "JOIN produto p ON ic.produto_id = p.produto_id WHERE ic.comanda_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, comandaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemComanda item = new ItemComanda();
                    item.setItemComandaId(rs.getInt("item_comanda_id"));
                    item.setComandaId(rs.getInt("comanda_id"));
                    item.setProdutoId(rs.getInt("produto_id"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setPrecoUnitario(rs.getDouble("preco_unitario"));
                    item.setNomeProduto(rs.getString("nome_produto"));
                    lista.add(item);
                }
            }
        }
        return lista;
    }

    @Override
    public double calculaValorTotal(int comandaId) throws SQLException {
        String sql = "SELECT SUM(quantidade * preco_unitario) AS total FROM item_comanda WHERE comanda_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, comandaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }
}