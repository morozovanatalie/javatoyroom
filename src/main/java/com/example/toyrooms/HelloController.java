package com.example.toyrooms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button btn_admin;
    @FXML
    private Button btn_user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_admin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = (Stage) btn_admin.getScene().getWindow();
                    stage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    stage.setTitle("Admin toy rooms");
                    stage.setScene(scene);
                    stage.show();

                    AdminView controller = fxmlLoader.getController();
                    stage.setOnCloseRequest(controller.getCloseEventHandler());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_user.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = (Stage) btn_user.getScene().getWindow();
                    stage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    stage.setTitle("User toy rooms");
                    stage.setScene(scene);
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}