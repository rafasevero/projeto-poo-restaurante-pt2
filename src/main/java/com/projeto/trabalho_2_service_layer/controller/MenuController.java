package com.projeto.trabalho_2_service_layer.controller;

import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import com.projeto.trabalho_2_service_layer.model.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class MenuController {

    @FXML private Button btnUsuarios;
    @FXML private Button btnProdutos;
    @FXML private Button btnCardapio;
    @FXML private Button btnComandas;

    @FXML
    public void initialize() {
        String perfil = SessaoUsuario.getPerfilLogado();

        if (perfil == null) return;
        switch (perfil) {
            case "Garçom":
                if (btnUsuarios != null) btnUsuarios.setVisible(false);
                if (btnProdutos != null) btnProdutos.setVisible(false);
                break;

            case "Cozinheiro":
                if (btnUsuarios != null) btnUsuarios.setVisible(false);
                if (btnProdutos != null) btnProdutos.setVisible(false);
                if (btnCardapio != null) btnCardapio.setVisible(false);
                break;

            case "Gerente":
                break;
        }
    }

    @FXML
    private void irParaComandas() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/comanda.fxml", "Rafa's Restaurant - Comandas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irParaCardapio() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/cardapio.fxml", "Rafa's Restaurant - Cardápio");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irParaProdutos() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/produto.fxml", "Rafa's Restaurant - Produtos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irParaUsuarios() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/usuario.fxml", "Rafa's Restaurant - Usuários");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sairOnAction() {
        SessaoUsuario.limpar();
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/login.fxml", "Rafa's Restaurant - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}