package fr.antoromeochrist.projetlego.notifWindows;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static fr.antoromeochrist.projetlego.Controller.controller;
import static fr.antoromeochrist.projetlego.Controller.model;

public class ControllerNotif implements Initializable {

    @FXML
    private Label close;
    @FXML
    private Button newB;

    @FXML
    private Button openB;
    @FXML
    private Label message;

    @FXML
    private ImageView template1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (message != null) message.setText(model.lastMessageNotif);
        close.setOnMouseClicked(e -> {
            if (Main.home.isShowing()) {
                Main.home.close();
            } else {
                Main.notifWindow.close();
            }
        });
        if (openB != null) openB.setOnMouseClicked(e -> {
            model.loadData(false);
        });
        if (newB != null) newB.setOnMouseClicked(e -> Main.home.close());

        if (template1 != null) {
            template1.setOnMouseClicked(e -> {
                Main.home.close();
                try {
                    model.project = new File("src/main/resources/templates/house.bd");
                } catch (Exception ignored) {
                }
                if (model.project == null) {
                    controller.sendNotif("Le template n'est plus disponible.");
                } else {
                    model.loadData(true);
                    model.project = null;
                }
            });
        }

    }
}
