package com.app.ipsearch;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ARPController {

    boolean setFindMac = false;

    @FXML
    private TextArea arpOutputArea;

    @FXML
    private TextField macTextField;

    @FXML
    public void runArpCommand() {
        // Implementierung des ARP -a-Befehls
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("arp", "-a");
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                //
                String macTextFieldreplaced = macTextField.getText().replaceAll(":", "-").replaceAll(" ", "");
                if (setFindMac) {

                    if (line.contains(macTextFieldreplaced.toLowerCase())) {
                        output.append(line).append("\n");
                        break;
                    }
                } else {
                    output.append(line).append("\n");
                }
            }

            arpOutputArea.setText(output.toString());
        } catch (Exception e) {
            arpOutputArea.setText("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void findMac() {
        // Check that the IP address is not empty
        String macAddress = macTextField.getText();
        if (macAddress == null || macAddress.isEmpty() || !isValidMacAddress(macAddress) ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid MAC-Address");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Mac address entered. Please check and try again.");
            alert.showAndWait();
            return;
        }
        setFindMac = true;
        runArpCommand();
        setFindMac = false;

    }

    private boolean isValidMacAddress(String mac) {
        String macPattern = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$|^([0-9A-Fa-f]{12})$";
        return mac.matches(macPattern);
    }
    @FXML
    public void clearTextarea() {
        setFindMac = false;
        macTextField.setText("");
        arpOutputArea.setText("");
    }

}

