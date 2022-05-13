module com.example.toyrooms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.toyrooms to javafx.fxml;
    exports com.example.toyrooms;
}