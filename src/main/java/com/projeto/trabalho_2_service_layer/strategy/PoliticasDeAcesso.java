package com.projeto.trabalho_2_service_layer.strategy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PADRÃO STRATEGY - seleção da estratégia em tempo de execução.
 * Registro único dos perfis do sistema: devolve a estratégia correspondente
 * ao perfil do usuário logado e é a ÚNICA fonte da lista de perfis válidos
 * (usada pelo UsuarioService para validar e pelo UsuarioController para o ComboBox).
 *
 * Para criar um novo perfil basta criar uma nova ConcreteStrategy e registrá-la aqui.
 */
public final class PoliticasDeAcesso {

    // LinkedHashMap mantém a ordem de registro (a ordem exibida no ComboBox)
    private static final Map<String, PoliticaAcesso> POLITICAS = new LinkedHashMap<>();

    static {
        registrar(new GerentePolitica());
        registrar(new GarcomPolitica());
        registrar(new CozinheiroPolitica());
    }

    private PoliticasDeAcesso() {}

    private static void registrar(PoliticaAcesso politica) {
        POLITICAS.put(politica.getPerfil(), politica);
    }

    /** Retorna a estratégia do perfil, ou null se o perfil for nulo/desconhecido. */
    public static PoliticaAcesso obter(String perfil) {
        return perfil == null ? null : POLITICAS.get(perfil);
    }

    public static boolean contem(String perfil) {
        return perfil != null && POLITICAS.containsKey(perfil);
    }

    public static List<String> perfisValidos() {
        return List.copyOf(POLITICAS.keySet());
    }
}
