package com.projeto.trabalho_2_service_layer.controller;

import com.projeto.trabalho_2_service_layer.facade.CardapioFacade;
import com.projeto.trabalho_2_service_layer.facade.ItemCardapioDetalhado;
import com.projeto.trabalho_2_service_layer.main.GerenciadorTelas;
import com.projeto.trabalho_2_service_layer.model.Cardapio;
import com.projeto.trabalho_2_service_layer.model.Produto;
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

public class CardapioController {

    @FXML private TextField txtNomeSecao;
    @FXML private TextField txtDescricao;
    @FXML private ComboBox<String> cbProdutos;
    @FXML private ListView<String> listCardapio;
    @FXML private Label lblMensagem;

    // PADRÃO FACADE - papel: Client. O Controller conhece apenas a fachada
    // (antes instanciava CardapioService e ProdutoService e coordenava os dois).
    private final CardapioFacade cardapioFacade = new CardapioFacade();

    private List<ItemCardapioDetalhado> listaCardapioBanco;
    private List<Produto> listaProdutosBanco;
    private Cardapio itemSelecionado;

    @FXML
    public void initialize() {
        carregarProdutosCombo();
        carregarListaCardapio();

        listCardapio.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (listaCardapioBanco != null && index >= 0 && index < listaCardapioBanco.size()) {
                itemSelecionado = listaCardapioBanco.get(index).getCardapio();
                txtNomeSecao.setText(itemSelecionado.getNomeSecao());
                txtDescricao.setText(itemSelecionado.getDescricao() != null ? itemSelecionado.getDescricao() : "");
                selecionarProdutoNoCombo(itemSelecionado.getProdutoId());
                lblMensagem.setText("");
            }
        });
    }

    private void carregarProdutosCombo() {
        try {
            listaProdutosBanco = cardapioFacade.listarProdutos();
            ObservableList<String> itens = FXCollections.observableArrayList();
            for (Produto p : listaProdutosBanco) {
                itens.add(p.getProdutoId() + " - " + p.getNome());
            }
            cbProdutos.setItems(itens);
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    private void carregarListaCardapio() {
        try {
            // A junção item do cardápio + nome do produto agora vem pronta da fachada
            listaCardapioBanco = cardapioFacade.listarCardapioDetalhado();
            ObservableList<String> exibicao = FXCollections.observableArrayList();

            for (ItemCardapioDetalhado detalhe : listaCardapioBanco) {
                Cardapio c = detalhe.getCardapio();
                String descText = (c.getDescricao() != null && !c.getDescricao().isEmpty()) ? " - " + c.getDescricao() : "";
                exibicao.add("[" + c.getNomeSecao() + "] " + detalhe.getNomeProduto() + descText);
            }

            listCardapio.setItems(exibicao);
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    private void selecionarProdutoNoCombo(int produtoId) {
        if (listaProdutosBanco == null) return;
        for (int i = 0; i < listaProdutosBanco.size(); i++) {
            if (listaProdutosBanco.get(i).getProdutoId() == produtoId) {
                cbProdutos.getSelectionModel().select(i);
                break;
            }
        }
    }

    @FXML
    private void salvarOnAction() {
        String secao = txtNomeSecao.getText().trim();
        String desc = txtDescricao.getText().trim();
        int selectedIndex = cbProdutos.getSelectionModel().getSelectedIndex();

        if (selectedIndex < 0) {
            exibirErro("Selecione o produto do cardápio!");
            return;
        }

        try {
            int produtoId = listaProdutosBanco.get(selectedIndex).getProdutoId();
            Cardapio novo = new Cardapio(secao, desc, produtoId);
            cardapioFacade.cadastrar(novo); // fachada -> Service (regras no Service)

            exibirSucesso("Item adicionado ao cardápio com sucesso!");
            limparCampos();
            carregarListaCardapio();
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void atualizarOnAction() {
        if (itemSelecionado == null) {
            exibirErro("Selecione um item da lista para atualizar!");
            return;
        }

        int selectedIndex = cbProdutos.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            exibirErro("Selecione um produto associado!");
            return;
        }

        try {
            itemSelecionado.setNomeSecao(txtNomeSecao.getText().trim());
            itemSelecionado.setDescricao(txtDescricao.getText().trim());
            itemSelecionado.setProdutoId(listaProdutosBanco.get(selectedIndex).getProdutoId());

            cardapioFacade.atualizar(itemSelecionado);
            exibirSucesso("Item do cardápio atualizado!");
            limparCampos();
            carregarListaCardapio();
        } catch (ServiceException e) {
            exibirErro(e.getMessage());
        }
    }

    @FXML
    private void deletarOnAction() {
        if (itemSelecionado == null) {
            exibirErro("Selecione um item para excluir!");
            return;
        }

        try {
            cardapioFacade.excluir(itemSelecionado);
            exibirSucesso("Item removido do cardápio!");
            limparCampos();
            carregarListaCardapio();
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
        txtNomeSecao.clear();
        txtDescricao.clear();
        cbProdutos.getSelectionModel().clearSelection();
        listCardapio.getSelectionModel().clearSelection();
        itemSelecionado = null;
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