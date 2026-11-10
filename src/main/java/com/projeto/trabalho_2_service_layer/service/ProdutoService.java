package com.projeto.trabalho_2_service_layer.service;

import com.projeto.trabalho_2_service_layer.dao.IProdutoDAO;
import com.projeto.trabalho_2_service_layer.dao.ProdutoDAO;
import com.projeto.trabalho_2_service_layer.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private final IProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void cadastrar(Produto produto) throws ServiceException {
        validar(produto);
        try {
            produtoDAO.insere(produto);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao cadastrar produto!", e);
        }
    }

    public void atualizar(Produto produto) throws ServiceException {
        validar(produto);
        try {
            produtoDAO.atualiza(produto);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao atualizar produto!", e);
        }
    }

    public void excluir(Produto produto) throws ServiceException {
        if (produto == null) {
            throw new ServiceException("Selecione um produto para excluir!");
        }
        try {
            produtoDAO.remove(produto);
        } catch (SQLException e) {
            throw new ServiceException(
                    "Erro ao excluir o produto. Verifique se ele não está em uma comanda ou no cardápio.", e);
        }
    }

    public List<Produto> listarTodos() throws ServiceException {
        try {
            return produtoDAO.listaTodos();
        } catch (SQLException e) {
            throw new ServiceException("Erro ao carregar produtos!", e);
        }
    }

    private void validar(Produto produto) throws ServiceException {
        if (produto == null) {
            throw new ServiceException("Produto inválido!");
        }
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new ServiceException("Informe o nome do produto!");
        }
        if (produto.getPreco() <= 0) {
            throw new ServiceException("O preço deve ser maior que zero!");
        }
    }
}