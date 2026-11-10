package com.projeto.trabalho_2_service_layer.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GerenciadorTelas {
    private static Stage stage;

    public static void setStage(Stage stagePrincipal) {
        stage = stagePrincipal;
    }

    public static void trocarTela(String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(GerenciadorTelas.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load(), 600, 650);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}