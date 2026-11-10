package com.projeto.trabalho_2_service_layer.controller;

import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import com.projeto.trabalho_2_service_layer.model.Produto;
import com.projeto.trabalho_2_service_layer.service.ProdutoService;
import com.projeto.trabalho_2_service_layer.service.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class ProdutoController {

    @FXML private TextField txtNome;
    @FXML private TextField txtPreco;
    @FXML private ListView<String> listProdutos;
    @FXML private Label lblMensagem;

    // O Controller conhece apenas o Service (nunca o DAO)
    private final ProdutoService produtoService = new ProdutoService();
    private List<Produto> listaProdutosBanco;
    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        carregarListaProdutos();

        // Quando clicar em um item da lista, preenche os campos automaticamente
        listProdutos.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (listaProdutosBanco != null && index >= 0 && index < listaProdutosBanco.size()) {
                produtoSelecionado = listaProdutosBanco.get(index);
                txtNome.setText(produtoSelecionado.getNome());
                txtPreco.setText(String.format("%.2f", produtoSelecionado.getPreco()));
                lblMensagem.setText("");
            }
        });
    }

    private void carregarListaProdutos() {
        try {
            listaProdutosBanco = produtoService.listarTodos();
            ObservableList<String> itensExibicao = FXCollections.observableArrayList();

            for (Produto p : listaProdutosBanco) {
                itensExibicao.add(p.getNome() + " - R$ " + String.format("%.2f", p.getPreco()));
            }

            listProdutos.setItems(itensExibicao);
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void salvarOnAction() {
        String nome = txtNome.getText().trim();
        String precoStr = txtPreco.getText().trim();

        if (precoStr.isEmpty()) {
            exibirErro("Informe o preço!");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr.replace(",", "."));
            Produto novo = new Produto(nome, preco, 0);
            produtoService.cadastrar(novo); // regras de negócio: no Service

            exibirSucesso("Produto '" + nome + "' cadastrado com sucesso!");
            limparCampos();
            carregarListaProdutos();
        } catch (NumberFormatException e) {
            exibirErro("Digite um preço válido.");
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void atualizarOnAction() {
        if (produtoSelecionado == null) {
            exibirErro("Selecione um produto da lista para atualizar!");
            return;
        }

        try {
            String novoNome = txtNome.getText().trim();
            double novoPreco = Double.parseDouble(txtPreco.getText().trim().replace(",", "."));

            produtoSelecionado.setNome(novoNome);
            produtoSelecionado.setPreco(novoPreco);

            produtoService.atualizar(produtoSelecionado);
            exibirSucesso("Produto atualizado com sucesso!");
            limparCampos();
            carregarListaProdutos();
        } catch (NumberFormatException e) {
            exibirErro("Preço inválido.");
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void deletarOnAction() {
        if (produtoSelecionado == null) {
            exibirErro("Selecione um produto da lista para excluir!");
            return;
        }

        try {
            produtoService.excluir(produtoSelecionado);
            exibirSucesso("Produto excluído com sucesso!");
            limparCampos();
            carregarListaProdutos();
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
        txtPreco.clear();
        listProdutos.getSelectionModel().clearSelection();
        produtoSelecionado = null;
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
            GerenciadorTelas.trocarTela("/com/projeto/trabalho_2_service_layer/view/menu_principal.fxml", "Rafa's Restaurant - Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}