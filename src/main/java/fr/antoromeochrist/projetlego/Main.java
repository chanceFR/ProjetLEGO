package fr.antoromeochrist.projetlego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.util.Objects;

/*
 *
 *
 *
 *       ["]
 *      /| |\  Brick Designer
 *       |I|
 * .     |I|.
 *
 *
 *
 * */

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Brick Designer");
        primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view.fxml")))));
        primaryStage.show();
        primaryStage.setMinWidth(1062);
        primaryStage.setMinHeight(710);
        primaryStage.setMaxWidth(1062);
        primaryStage.setMaxHeight(710);
    }

    public static void main(String[] args) {
        launch(args);
    }
}