package com.app.ipsearch;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert;

public class WebController {

    @FXML
    private TextField ipAddressField;

    @FXML
    private WebView webView;

    @FXML
    public void loadWebPage() {
        // Getting text from a text field
        String ipAddress = ipAddressField.getText();
        // Generate URL based on IP address
        String url = "http://" + ipAddress;

        // Checking the validity of an IP address (optional)
        if (isValidIPAddress(ipAddress)) {
            // Loading the page into WebView
            WebEngine webEngine = webView.getEngine();
            webEngine.load(url);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid IP Address");
            alert.setHeaderText(null);
            alert.setContentText("Invalid IP address entered. Please check and try again.");
            alert.showAndWait();
        }
    }

    // Method for IP address validation (optional)
    private boolean isValidIPAddress(String ip) {
        String ipPattern =
                "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }

    @FXML
    public void setIpAddressField() {
        ipAddressField.setText(AppModel.ipAddress);
        System.out.println("this is webCont " + AppModel.ipAddress);
    }

}



