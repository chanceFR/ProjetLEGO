package fr.antoromeochrist.projetlego.home;

import fr.antoromeochrist.projetlego.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHome implements Initializable {

    @FXML
    private Label close;
    @FXML
    private Button newB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnMouseClicked(e-> Main.home.close());
        newB.setOnMouseClicked(e-> Main.home.close());
    }
}
