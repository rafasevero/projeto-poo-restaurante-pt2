module com.projeto.trabalho_2_service_layer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.projeto.trabalho_2_service_layer.main to javafx.graphics;
    opens com.projeto.trabalho_2_service_layer.controller to javafx.fxml;
}