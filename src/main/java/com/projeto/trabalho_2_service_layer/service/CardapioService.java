package com.projeto.trabalho_2_service_layer.service;

import com.projeto.trabalho_2_service_layer.dao.CardapioDAO;
import com.projeto.trabalho_2_service_layer.dao.ICardapioDAO;
import com.projeto.trabalho_2_service_layer.dao.IProdutoDAO;
import com.projeto.trabalho_2_service_layer.dao.ProdutoDAO;
import com.projeto.trabalho_2_service_layer.model.Cardapio;

import java.sql.SQLException;
import java.util.List;

public class CardapioService {

    private final ICardapioDAO cardapioDAO;
    private final IProdutoDAO produtoDAO;

    public CardapioService() {
        this.cardapioDAO = new CardapioDAO();
        this.produtoDAO = new ProdutoDAO();
    }

    public void cadastrar(Cardapio item) throws ServiceException {
        validar(item);
        try {
            cardapioDAO.insere(item);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao adicionar item ao cardápio!", e);
        }
    }

    public void atualizar(Cardapio item) throws ServiceException {
        validar(item);
        try {
            cardapioDAO.atualiza(item);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao atualizar item do cardápio!", e);
        }
    }

    public void excluir(Cardapio item) throws ServiceException {
        if (item == null) {
            throw new ServiceException("Selecione um item para excluir!");
        }
        try {
            cardapioDAO.remove(item);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao excluir item do cardápio!", e);
        }
    }

    public List<Cardapio> listarTodos() throws ServiceException {
        try {
            return cardapioDAO.listaTodos();
        } catch (SQLException e) {
            throw new ServiceException("Erro ao carregar cardápio!", e);
        }
    }

    private void validar(Cardapio item) throws ServiceException {
        if (item == null) {
            throw new ServiceException("Item do cardápio inválido!");
        }
        if (item.getNomeSecao() == null || item.getNomeSecao().isBlank()) {
            throw new ServiceException("Informe o nome da seção!");
        }
        if (item.getProdutoId() <= 0) {
            throw new ServiceException("Selecione o produto do cardápio!");
        }
        // Coordenação com outro DAO: o produto precisa existir
        try {
            if (produtoDAO.buscaPorCodigo(item.getProdutoId()) == null) {
                throw new ServiceException("O produto selecionado não existe mais!");
            }
        } catch (SQLException e) {
            throw new ServiceException("Erro ao validar o produto do cardápio!", e);
        }
    }
}