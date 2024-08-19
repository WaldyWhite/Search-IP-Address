module com.example.ipsearch {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;
    requires java.xml.crypto;

    opens com.app.ipsearch to javafx.fxml;
    exports com.app.ipsearch;
}