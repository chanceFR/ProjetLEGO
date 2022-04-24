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
        /*primaryStage.setMinWidth(1060);
        primaryStage.setMinHeight(710);
        primaryStage.setMaxWidth(1060);
        primaryStage.setMaxHeight(710);*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}