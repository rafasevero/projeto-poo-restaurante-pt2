package com.projeto.trabalho_2_service_layer.controller;

import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import com.projeto.trabalho_2_service_layer.model.SessaoUsuario;
import com.projeto.trabalho_2_service_layer.model.Usuario;
import com.projeto.trabalho_2_service_layer.service.ServiceException;
import com.projeto.trabalho_2_service_layer.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblMensagem;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void entrarOnAction() {
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();

        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(email, senha); //a autenticacao que antes estava aqui foi para o service, aqui só é chamada

            SessaoUsuario.setPerfilLogado(usuarioAutenticado.getPerfil());

            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/menu_principal.fxml", "Rafa's Restaurant - Menu Principal");

        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        } catch (IOException e) {
            exibirErro("Erro ao carregar a tela principal: " + e.getMessage());
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

    @FXML
    private void voltarOnAction() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/bem_vindo.fxml", "Rafa's Restaurant");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exibirErro(String msg) {
        lblMensagem.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
        lblMensagem.setText(msg);
    }
}