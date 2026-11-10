package com.projeto.trabalho_2_service_layer.dao;
import com.projeto.trabalho_2_service_layer.model.Cardapio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardapioDAO implements ICardapioDAO {

    @Override
    public void insere(Cardapio cardapio) throws SQLException {
        String sql = "INSERT INTO cardapio (nome_secao, descricao, produto_id) VALUES (?, ?, ?) RETURNING cardapio_id";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cardapio.getNomeSecao());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setInt(3, cardapio.getProdutoId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cardapio.setCardapioId(rs.getInt("cardapio_id"));
                }
            }
        }
    }

    @Override
    public void atualiza(Cardapio cardapio) throws SQLException {
        String sql = "UPDATE cardapio SET nome_secao = ?, descricao = ?, produto_id = ? WHERE cardapio_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cardapio.getNomeSecao());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setInt(3, cardapio.getProdutoId());
            stmt.setInt(4, cardapio.getCardapioId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void remove(Cardapio cardapio) throws SQLException {
        String sql = "DELETE FROM cardapio WHERE cardapio_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cardapio.getCardapioId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Cardapio> listaTodos() throws SQLException {
        List<Cardapio> lista = new ArrayList<>();
        String sql = "SELECT * FROM cardapio";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cardapio(
                        rs.getInt("cardapio_id"),
                        rs.getString("nome_secao"),
                        rs.getString("descricao"),
                        rs.getInt("produto_id")
                ));
            }
        }
        return lista;
    }
}