package com.app.ipsearch;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;

import java.io.IOException;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private BorderPane arpTab, pingTab, webTab, networkTab;

    @FXML
    public void initialize() {
        try {
            // Loading FXML for ARP tab
            Parent arpContent = FXMLLoader.load(getClass().getResource("ArpTab.fxml"));
            arpTab.setCenter(arpContent);

            // Loading FXML for Ping tab
            Parent pingContent = FXMLLoader.load(getClass().getResource("PingTab.fxml"));
            pingTab.setCenter(pingContent);

            // Loading FXML for Web Tab
            Parent webContent = FXMLLoader.load(getClass().getResource("WebTab.fxml"));
            webTab.setCenter(webContent);

            // Loading FXML for the Network tab
            Parent networkContent = FXMLLoader.load(getClass().getResource("NetworkTab.fxml"));
            networkTab.setCenter(networkContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
