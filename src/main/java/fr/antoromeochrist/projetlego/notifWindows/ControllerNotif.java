package fr.antoromeochrist.projetlego.notifWindows;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(message != null) message.setText(model.lastMessageNotif);
        close.setOnMouseClicked(e->{
            if(Main.home.isShowing()){
                Main.home.close();
            }
            else{
                Main.notifWindow.close();
            }
        });
        if(openB != null) openB.setOnMouseClicked(e-> {
            model.loadData();
        });
        if(newB != null ) newB.setOnMouseClicked(e-> Main.home.close());
    }
}
