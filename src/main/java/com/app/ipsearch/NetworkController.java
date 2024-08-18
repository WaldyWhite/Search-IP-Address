package com.app.ipsearch;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NetworkController {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField subnetMaskField;

    @FXML
    private TextField gatewayField;


    @FXML
    public void applyNetworkSettings() {
        try {
            // Getting IP address, subnet mask and gateway values ​​from text fields
            String ipAddress = ipTextField.getText();
            String subnetMask = subnetMaskField.getText();
            String gateway = gatewayField.getText();

            // Checking the validity of the entered data
            if (!isValidIPAddress(ipAddress) || !isValidIPAddress(subnetMask) || !isValidIPAddress(gateway)) {
                showAlert("Invalid Input", "Please enter valid IP addresses for all fields.");
                return;
            }

            // Executing a command to change TCP/IP v4 settings
            String command = String.format("netsh interface ip set address name=\"Ethernet\" static %s %s %s",
                    ipAddress, subnetMask, gateway);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            // Show message about successful application of settings
            showAlert("Success", "Network settings have been applied successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to apply network settings: " + e.getMessage());
        }
    }

    private boolean isValidIPAddress(String ip) {
        // Example of checking the validity of an IP address
        String ipPattern =
                "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

