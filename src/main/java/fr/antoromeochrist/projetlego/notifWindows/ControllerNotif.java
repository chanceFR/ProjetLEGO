package fr.antoromeochrist.projetlego.notifWindows;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    private ImageView template2;

    @FXML
    private ImageView template3;

    @FXML
    private ImageView template4;

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
            model.loadData();
        });
        if (newB != null) newB.setOnMouseClicked(e -> Main.home.close());

        if (template1 != null) {
            template1.setOnMouseClicked(e -> {

            });
        }
        if (template2 != null) {
            template2.setOnMouseClicked(e -> {

            });
        }
        if (template3 != null) {
            template3.setOnMouseClicked(e -> {

            });
        }
        if (template4 != null) {
            template4.setOnMouseClicked(e -> {

            });
        }
    }
}
