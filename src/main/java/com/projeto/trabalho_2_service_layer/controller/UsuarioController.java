package com.projeto.trabalho_2_service_layer.controller;

import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import com.projeto.trabalho_2_service_layer.model.Usuario;
import com.projeto.trabalho_2_service_layer.service.ServiceException;
import com.projeto.trabalho_2_service_layer.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class UsuarioController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private ComboBox<String> cbPerfil; // Mapeado corretamente do FXML
    @FXML private ListView<String> listUsuarios;
    @FXML private Label lblMensagem;

    private final UsuarioService usuarioService = new UsuarioService();
    private List<Usuario> listaUsuariosBanco;
    private Usuario usuarioSelecionado;

    @FXML
    public void initialize() {
        cbPerfil.setItems(FXCollections.observableArrayList(
                "Gerente",
                "Garçom",
                "Cozinheiro"
        ));

        carregarUsuarios();

        if (listUsuarios != null) {
            listUsuarios.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
                int index = newVal.intValue();
                if (listaUsuariosBanco != null && index >= 0 && index < listaUsuariosBanco.size()) {
                    usuarioSelecionado = listaUsuariosBanco.get(index);
                    txtNome.setText(usuarioSelecionado.getNome());
                    txtEmail.setText(usuarioSelecionado.getEmail());
                    txtSenha.setText(usuarioSelecionado.getSenha());
                    cbPerfil.setValue(usuarioSelecionado.getPerfil());
                    lblMensagem.setText("");
                }
            });
        }
    }

    private void carregarUsuarios() {
        try {
            listaUsuariosBanco = usuarioService.listarTodos();
            ObservableList<String> exibicao = FXCollections.observableArrayList();

            for (Usuario u : listaUsuariosBanco) {
                exibicao.add(u.getNome() + " (" + u.getEmail() + ") - Cargo: " + u.getPerfil());
            }

            if (listUsuarios != null) {
                listUsuarios.setItems(exibicao);
            }
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void salvarOnAction() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = txtSenha.getText().trim();
        String perfil = cbPerfil.getSelectionModel().getSelectedItem();

        try {
            Usuario novoUsuario = new Usuario(nome, email, senha, perfil);
            usuarioService.cadastrar(novoUsuario);

            try {
                GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/login.fxml", "Rafa's Restaurant - Login");
            } catch (IOException e) {
                exibirErro("Erro ao carregar a tela de login: " + e.getMessage());
            }

        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void limparOnAction() {
        limparCampos();
        lblMensagem.setText("");
    }

    private void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtSenha.clear();
        cbPerfil.getSelectionModel().clearSelection();
        if (listUsuarios != null) {
            listUsuarios.getSelectionModel().clearSelection();
        }
        usuarioSelecionado = null;
    }

    private void exibirSucesso(String msg) {
        lblMensagem.setStyle("-fx-text-fill: #006600; -fx-font-weight: bold;");
        lblMensagem.setText(msg);
    }

    private void exibirErro(String msg) {
        lblMensagem.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
        lblMensagem.setText(msg);
    }

    @FXML
    private void voltarOnAction() {
        try {
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/bem_vindo.fxml", "Rafa's Restaurant");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}