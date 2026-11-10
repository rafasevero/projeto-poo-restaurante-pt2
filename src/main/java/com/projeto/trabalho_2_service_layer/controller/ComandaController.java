package com.projeto.trabalho_2_service_layer.controller;

import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import com.projeto.trabalho_2_service_layer.model.Comanda;
import com.projeto.trabalho_2_service_layer.model.ItemComanda;
import com.projeto.trabalho_2_service_layer.model.Produto;
import com.projeto.trabalho_2_service_layer.service.ComandaService;
import com.projeto.trabalho_2_service_layer.service.ProdutoService;
import com.projeto.trabalho_2_service_layer.service.ServiceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class ComandaController {

    @FXML private TextField txtMesa;
    @FXML private TextField txtStatus;
    @FXML private TextField txtValorTotal;
    @FXML private TextField txtQuantidade;
    @FXML private ListView<String> listComandas;
    @FXML private ListView<String> listItensComanda;
    @FXML private ComboBox<Produto> cbProdutos;
    @FXML private Label lblMensagem;

    private final ComandaService comandaService = new ComandaService();
    private final ProdutoService produtoService = new ProdutoService();

    private List<Comanda> listaComandasBanco;
    private List<Produto> listaProdutosBanco;
    private List<ItemComanda> listaItensBanco;
    private Comanda comandaSelecionada;

    @FXML
    public void initialize() {
        carregarListaComandas();
        carregarProdutosComboBox();

        listComandas.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (listaComandasBanco != null && index >= 0 && index < listaComandasBanco.size()) {
                comandaSelecionada = listaComandasBanco.get(index);
                txtMesa.setText(String.valueOf(comandaSelecionada.getNumeroMesa()));
                txtStatus.setText(comandaSelecionada.getStatus());
                carregarItensComandaSelecionada();
                lblMensagem.setText("");
            }
        });
    }

    private void carregarListaComandas() {
        try {
            listaComandasBanco = comandaService.listarTodos();
            ObservableList<String> exibicao = FXCollections.observableArrayList();

            for (Comanda c : listaComandasBanco) {
                double total = comandaService.calcularTotal(c.getComandaId());
                exibicao.add("Mesa " + c.getNumeroMesa() + " | Status: " + c.getStatus() + " | Total: R$ " + String.format("%.2f", total));
            }

            listComandas.setItems(exibicao);
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    private void carregarProdutosComboBox() {
        try {
            listaProdutosBanco = produtoService.listarTodos();
            ObservableList<Produto> produtosObs = FXCollections.observableArrayList(listaProdutosBanco);
            cbProdutos.setItems(produtosObs);
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    private void carregarItensComandaSelecionada() {
        if (comandaSelecionada == null) return;

        try {
            listaItensBanco = comandaService.listarItens(comandaSelecionada.getComandaId());
            ObservableList<String> exibicaoItens = FXCollections.observableArrayList();

            for (ItemComanda item : listaItensBanco) {
                double subtotal = comandaService.calcularSubtotal(item);
                exibicaoItens.add(item.getQuantidade() + "x " + item.getNomeProduto() + " (R$ " + String.format("%.2f", item.getPrecoUnitario()) + " un) -> Subtotal: R$ " + String.format("%.2f", subtotal));
            }

            listItensComanda.setItems(exibicaoItens);

            double totalGeral = comandaService.calcularTotal(comandaSelecionada.getComandaId());
            txtValorTotal.setText(String.format("%.2f", totalGeral));

        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void adicionarItemOnAction() {
        if (comandaSelecionada == null) {
            exibirErro("Selecione uma comanda na lista acima primeiro!");
            return;
        }

        Produto produtoEscolhido = cbProdutos.getSelectionModel().getSelectedItem();
        if (produtoEscolhido == null) {
            exibirErro("Selecione um produto do cardápio!");
            return;
        }

        String qtdStr = txtQuantidade.getText().trim();
        if (qtdStr.isEmpty()) {
            qtdStr = "1";
        }

        try {
            int quantidade = Integer.parseInt(qtdStr);
            comandaService.adicionarItem(comandaSelecionada, produtoEscolhido, quantidade);

            exibirSucesso("Produto adicionado à comanda!");
            txtQuantidade.clear();
            cbProdutos.getSelectionModel().clearSelection();

            carregarItensComandaSelecionada();
            carregarListaComandas();

        } catch (NumberFormatException e) {
            exibirErro("Quantidade inválida.");
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void criarOnAction() {
        String mesaStr = txtMesa.getText().trim();
        String status = txtStatus.getText().trim();

        if (mesaStr.isEmpty()) {
            exibirErro("Informe o número da mesa!");
            return;
        }

        try {
            int mesa = Integer.parseInt(mesaStr);
            Comanda nova = new Comanda(mesa, status);
            comandaService.abrir(nova);

            exibirSucesso("Comanda aberta com sucesso!");
            limparCampos();
            carregarListaComandas();
        } catch (NumberFormatException e) {
            exibirErro("O número da mesa deve ser um número inteiro.");
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void atualizarOnAction() {
        if (comandaSelecionada == null) {
            exibirErro("Selecione uma comanda da lista para atualizar!");
            return;
        }

        try {
            int mesa = Integer.parseInt(txtMesa.getText().trim());
            String status = txtStatus.getText().trim();

            comandaSelecionada.setNumeroMesa(mesa);
            comandaSelecionada.setStatus(status);

            comandaService.atualizar(comandaSelecionada);
            exibirSucesso("Comanda atualizada com sucesso!");
            limparCampos();
            carregarListaComandas();
        } catch (NumberFormatException e) {
            exibirErro("Número da mesa inválido.");
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void deletarOnAction() {
        if (comandaSelecionada == null) {
            exibirErro("Selecione uma comanda da lista para excluir!");
            return;
        }

        try {
            comandaService.excluir(comandaSelecionada);
            exibirSucesso("Comanda excluída com sucesso!");
            limparCampos();
            carregarListaComandas();
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
        txtMesa.clear();
        txtStatus.clear();
        txtValorTotal.clear();
        txtQuantidade.clear();
        cbProdutos.getSelectionModel().clearSelection();
        listComandas.getSelectionModel().clearSelection();
        listItensComanda.getItems().clear();
        comandaSelecionada = null;
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