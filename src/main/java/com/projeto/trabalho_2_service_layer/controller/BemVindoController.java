package com.projeto.trabalho_2_service_layer.controller;
import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import javafx.fxml.FXML;
import java.io.IOException;

public class BemVindoController {

    @FXML
    private void irParaLogin() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/login.fxml", "Rafa's Restaurant - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irParaCadastro() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/usuario.fxml", "Rafa's Restaurant - Cadastro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}