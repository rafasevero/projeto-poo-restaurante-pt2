package com.projeto.trabalho_2_service_layer.facade;

import com.projeto.trabalho_2_service_layer.model.Cardapio;
import com.projeto.trabalho_2_service_layer.model.Produto;
import com.projeto.trabalho_2_service_layer.service.CardapioService;
import com.projeto.trabalho_2_service_layer.service.ProdutoService;
import com.projeto.trabalho_2_service_layer.service.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PADRÃO FACADE - papel: Facade.
 * Oferece ao CardapioController uma interface única e simples para o subsistema
 * formado por CardapioService e ProdutoService (subsystem classes).
 * O Controller deixa de conhecer e coordenar os dois Services; a fachada só orquestra,
 * as regras de negócio continuam nos Services e o acesso a dados continua nos DAOs
 * (fluxo: Controller -> Facade -> Service -> DAO).
 */
public class CardapioFacade {

    private final CardapioService cardapioService;
    private final ProdutoService produtoService;

    public CardapioFacade() {
        this.cardapioService = new CardapioService();
        this.produtoService = new ProdutoService();
    }

    /** Produtos disponíveis para escolha no ComboBox do cardápio. */
    public List<Produto> listarProdutos() throws ServiceException {
        return produtoService.listarTodos();
    }

    /**
     * Orquestração entre os dois Services: carrega o cardápio e associa a cada item
     * o nome do seu produto. Antes essa junção era feita por um laço aninhado dentro do Controller.
     */
    public List<ItemCardapioDetalhado> listarCardapioDetalhado() throws ServiceException {
        Map<Integer, String> nomesPorProduto = new HashMap<>();
        for (Produto p : produtoService.listarTodos()) {
            nomesPorProduto.put(p.getProdutoId(), p.getNome());
        }

        List<ItemCardapioDetalhado> resultado = new ArrayList<>();
        for (Cardapio c : cardapioService.listarTodos()) {
            String nome = nomesPorProduto.getOrDefault(c.getProdutoId(), "Produto não encontrado");
            resultado.add(new ItemCardapioDetalhado(c, nome));
        }
        return resultado;
    }

    public void cadastrar(Cardapio item) throws ServiceException {
        cardapioService.cadastrar(item);
    }

    public void atualizar(Cardapio item) throws ServiceException {
        cardapioService.atualizar(item);
    }

    public void excluir(Cardapio item) throws ServiceException {
        cardapioService.excluir(item);
    }
}
