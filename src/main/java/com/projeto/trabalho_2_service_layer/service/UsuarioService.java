package com.projeto.trabalho_2_service_layer.service;

import com.projeto.trabalho_2_service_layer.dao.IUsuarioDAO;
import com.projeto.trabalho_2_service_layer.dao.UsuarioDAO;
import com.projeto.trabalho_2_service_layer.model.Usuario;
import com.projeto.trabalho_2_service_layer.strategy.PoliticasDeAcesso;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    private final IUsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String senha) throws ServiceException {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            throw new ServiceException("Preencha o e-mail e a senha!");
        }
        try {
            Usuario usuario = usuarioDAO.autenticar(email, senha);
            if (usuario == null) {
                throw new ServiceException("E-mail ou senha incorretos!");
            }
            return usuario;
        } catch (SQLException e) {
            throw new ServiceException("Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }

    public void cadastrar(Usuario usuario) throws ServiceException {
        validar(usuario);
        try {
            for (Usuario existente : usuarioDAO.listaTodos()) {
                if (usuario.getEmail().equalsIgnoreCase(existente.getEmail())) {
                    throw new ServiceException("Já existe um usuário cadastrado com esse e-mail!");
                }
            }
            usuarioDAO.insere(usuario);
        } catch (SQLException e) {
            throw new ServiceException("Erro no banco de dados: " + e.getMessage(), e);
        }
    }

    public List<Usuario> listarTodos() throws ServiceException {
        try {
            return usuarioDAO.listaTodos();
        } catch (SQLException e) {
            throw new ServiceException("Erro ao carregar usuários!", e);
        }
    }

    private void validar(Usuario usuario) throws ServiceException {
        if (usuario == null) {
            throw new ServiceException("Usuário inválido!");
        }
        if (usuario.getNome() == null || usuario.getNome().isBlank() || usuario.getEmail() == null || usuario.getEmail().isBlank() || usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new ServiceException("Preencha todos os campos obrigatórios!");
        }
        if (usuario.getPerfil() == null || usuario.getPerfil().isBlank()) {
            throw new ServiceException("Por favor, selecione um cargo!");
        }
        // PADRÃO STRATEGY: a lista de perfis válidos vem do registro de estratégias
        // (antes era a constante PERFIS_VALIDOS duplicada neste Service).
        if (!PoliticasDeAcesso.contem(usuario.getPerfil())) {
            throw new ServiceException("Cargo inválido!");
        }
    }
}