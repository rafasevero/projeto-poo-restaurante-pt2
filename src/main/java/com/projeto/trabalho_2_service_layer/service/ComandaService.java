package com.projeto.trabalho_2_service_layer.service;

import com.projeto.trabalho_2_service_layer.dao.ComandaDAO;
import com.projeto.trabalho_2_service_layer.dao.IComandaDAO;
import com.projeto.trabalho_2_service_layer.dao.IItemComandaDAO;
import com.projeto.trabalho_2_service_layer.dao.ItemComandaDAO;
import com.projeto.trabalho_2_service_layer.model.Comanda;
import com.projeto.trabalho_2_service_layer.model.ItemComanda;
import com.projeto.trabalho_2_service_layer.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class ComandaService {

    private final IComandaDAO comandaDAO;
    private final IItemComandaDAO itemComandaDAO;

    public ComandaService() {
        this.comandaDAO = new ComandaDAO();
        this.itemComandaDAO = new ItemComandaDAO();
    }

    public void abrir(Comanda comanda) throws ServiceException {
        validar(comanda);
        try {
            comandaDAO.insere(comanda);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao abrir comanda: " + e.getMessage(), e);
        }
    }

    public void atualizar(Comanda comanda) throws ServiceException {
        validar(comanda);
        try {
            comandaDAO.atualiza(comanda);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao atualizar comanda: " + e.getMessage(), e);
        }
    }

    public void excluir(Comanda comanda) throws ServiceException {
        if (comanda == null) {
            throw new ServiceException("Selecione uma comanda para excluir!");
        }
        try {
            comandaDAO.remove(comanda);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao excluir (verifique se há itens vinculados).", e);
        }
    }

    public List<Comanda> listarTodos() throws ServiceException {
        try {
            return comandaDAO.listaTodos();
        } catch (SQLException e) {
            throw new ServiceException("Erro ao carregar comandas: " + e.getMessage(), e);
        }
    }

    public void adicionarItem(Comanda comanda, Produto produto, int quantidade) throws ServiceException {
        if (comanda == null) {
            throw new ServiceException("Selecione uma comanda na lista primeiro!");
        }
        if (produto == null) {
            throw new ServiceException("Selecione um produto do cardápio!");
        }
        if (quantidade <= 0) {
            throw new ServiceException("A quantidade deve ser maior que zero.");
        }

        ItemComanda item = new ItemComanda(
                comanda.getComandaId(),
                produto.getProdutoId(),
                quantidade,
                produto.getPreco());
        try {
            itemComandaDAO.insere(item);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao adicionar item: " + e.getMessage(), e);
        }
    }

    public List<ItemComanda> listarItens(int comandaId) throws ServiceException {
        try {
            return itemComandaDAO.listaPorComanda(comandaId);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao carregar itens da comanda: " + e.getMessage(), e);
        }
    }

    public double calcularTotal(int comandaId) throws ServiceException {
        try {
            return itemComandaDAO.calculaValorTotal(comandaId);
        } catch (SQLException e) {
            throw new ServiceException("Erro ao calcular o total da comanda: " + e.getMessage(), e);
        }
    }

    // Regra de cálculo (antes estava dentro do Controller)
    public double calcularSubtotal(ItemComanda item) {
        return item.getQuantidade() * item.getPrecoUnitario();
    }

    private void validar(Comanda comanda) throws ServiceException {
        if (comanda == null) {
            throw new ServiceException("Comanda inválida!");
        }
        if (comanda.getNumeroMesa() <= 0) {
            throw new ServiceException("O número da mesa deve ser maior que zero!");
        }
        if (comanda.getStatus() == null || comanda.getStatus().isBlank()) {
            throw new ServiceException("Informe o status da comanda!");
        }
    }
}