package com.app.ipsearch;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingController {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextArea pingOutputArea;

    @FXML
    private Button stopPingButton;

    private Process pingProcess;
    private boolean isPinging = false;

    @FXML
    public void runPing() {
        // Implementation of Ping command for the specified IP
        String ipAddress = ipTextField.getText(); // Get IP address from text field

        // Check that the IP address is not empty
        if (ipAddress == null || ipAddress.isEmpty() || !isValidIPAddress(ipAddress) ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid IP Address");
            alert.setHeaderText(null);
            alert.setContentText("Invalid IP address entered. Please check and try again.");
            alert.showAndWait();
            return;
        }

        // Create a process to execute the ping command
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-n", "4", ipAddress);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Reading the output of the ping command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder pingResult = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                pingResult.append(line).append("\n");
            }

            // Waiting for process to complete
            process.waitFor();

            // Output the result to a text field
            pingOutputArea.setText(pingResult.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void runPingContinuous() {
        String ipAddress = ipTextField.getText();

        // Check that the IP address is not empty
        if (ipAddress == null || ipAddress.isEmpty() || !isValidIPAddress(ipAddress) ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid IP Address");
            alert.setHeaderText(null);
            alert.setContentText("Invalid IP address entered. Please check and try again.");
            alert.showAndWait();
            return;
        }

        // Let's make sure the previous process is stopped.
        if (pingProcess != null && pingProcess.isAlive()) {
            pingProcess.destroy();
        }

        // Create a new thread to execute the ping -t command
        Thread pingThread = new Thread(() -> {
            try {
                // Run the command ping -t
                pingProcess = new ProcessBuilder("ping", "-t", ipAddress).start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(pingProcess.getInputStream()));
                String line;
                isPinging = true;

                // Read and display the ping result
                while ((line = reader.readLine()) != null && isPinging) {
                    String finalLine = line;
                    Platform.runLater(() -> pingOutputArea.appendText(finalLine + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pingThread.setDaemon(true);
        pingThread.start();

        // Activate the stop ping button
        stopPingButton.setDisable(false);
        stopPingButton.setOnAction(event -> stopPing());
    }

    @FXML
    private void stopPing() {
        // Stop the ping process
        isPinging = false;
        if (pingProcess != null && pingProcess.isAlive()) {
            pingProcess.destroy();
        }

        // Disable the stop ping button
        stopPingButton.setDisable(true);
    }
    // Method for IP address validation (optional)
    private boolean isValidIPAddress(String ip) {
        String ipPattern =
                "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }
}