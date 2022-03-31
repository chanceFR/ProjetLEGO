package fr.antoromeochrist.projetlego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;


public class Model extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Brick Designer");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}