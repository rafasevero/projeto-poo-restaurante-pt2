package com.projeto.trabalho_2_service_layer.dao;
import com.projeto.trabalho_2_service_layer.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO {

    @Override
    public void insere(Produto produto) throws SQLException {
        String sqlEstoque = "INSERT INTO estoque (quantidade_atual, quantidade_minima) VALUES (0, 5) RETURNING estoque_id";
        int estoqueId = 0;

        try (Connection con = Conexao.getConexao();
             PreparedStatement stmtEstoque = con.prepareStatement(sqlEstoque);
             ResultSet rsEstoque = stmtEstoque.executeQuery()) {
            if (rsEstoque.next()) {
                estoqueId = rsEstoque.getInt("estoque_id");
            }
        }

        String sqlProduto = "INSERT INTO produto (nome, preco, estoque_id) VALUES (?, ?, ?) RETURNING produto_id";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmtProduto = con.prepareStatement(sqlProduto)) {
            stmtProduto.setString(1, produto.getNome());
            stmtProduto.setDouble(2, produto.getPreco());
            stmtProduto.setInt(3, estoqueId);

            try (ResultSet rs = stmtProduto.executeQuery()) {
                if (rs.next()) {
                    produto.setProdutoId(rs.getInt("produto_id"));
                    produto.setEstoqueId(estoqueId);
                }
            }
        }
    }

    @Override
    public void atualiza(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco = ? WHERE produto_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getProdutoId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void remove(Produto produto) throws SQLException {
        String sql = "DELETE FROM produto WHERE produto_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, produto.getProdutoId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Produto buscaPorCodigo(int produtoId) throws SQLException {
        String sql = "SELECT * FROM produto WHERE produto_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("produto_id"),
                            rs.getString("nome"),
                            rs.getDouble("preco"),
                            rs.getInt("estoque_id")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Produto> listaTodos() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getInt("produto_id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque_id")
                ));
            }
        }
        return lista;
    }
}