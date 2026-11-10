package com.projeto.trabalho_2_service_layer.dao;

import com.projeto.trabalho_2_service_layer.model.Comanda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComandaDAO implements IComandaDAO {

    @Override
    public void insere(Comanda comanda) throws SQLException {
        String sql = "INSERT INTO comanda (numero_mesa, status) VALUES (?, ?) RETURNING comanda_id";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, comanda.getNumeroMesa());
            stmt.setString(2, comanda.getStatus());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comanda.setComandaId(rs.getInt("comanda_id"));
                }
            }
        }
    }

    @Override
    public void atualiza(Comanda comanda) throws SQLException {
        String sql = "UPDATE comanda SET numero_mesa = ?, status = ? WHERE comanda_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, comanda.getNumeroMesa());
            stmt.setString(2, comanda.getStatus());
            stmt.setInt(3, comanda.getComandaId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void remove(Comanda comanda) throws SQLException {
        String sql = "DELETE FROM comanda WHERE comanda_id = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, comanda.getComandaId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Comanda> listaTodos() throws SQLException {
        List<Comanda> lista = new ArrayList<>();
        String sql = "SELECT * FROM comanda";
        try (Connection con = Conexao.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Comanda(
                        rs.getInt("comanda_id"),
                        rs.getInt("numero_mesa"),
                        rs.getString("status")
                ));
            }
        }
        return lista;
    }
}