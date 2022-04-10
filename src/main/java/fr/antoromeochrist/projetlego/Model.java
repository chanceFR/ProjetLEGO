package fr.antoromeochrist.projetlego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;


public class Model extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Brick Designer");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view.fxml"))));
        primaryStage.show();
        primaryStage.setMinWidth(955);
        primaryStage.setMinHeight(660);
        primaryStage.setMaxWidth(955);
        primaryStage.setMaxHeight(660);
    }

    public static void main(String[] args) {
        launch(args);
    }
}