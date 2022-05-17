package fr.antoromeochrist.projetlego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    /**
     * Coordonnéés pour bouger la fenêtre (logiciel en entier)
     */
    public static double xOffset = 0;
    public static double yOffset = 0;

    /**
     * Coordonnéés pour bouger la petite fenêtre d'acceuil ("open project ....")
     */

    public static double xOffsetHome = 0;
    public static double yOffsetHome = 0;

    /**
     * Stockage des instances de fenêtres
     * On pourra les fermer depuis chacun de leur controller.
     */

    public static Stage software;
    //Fenêtre de l'ensemble du logiciel
    public static Stage home;
    //Petite fenêtre popup qui demande si on veut ouvrir un projet ou en créé un nouveau
    //Cette fenêtre n'est pas indispensable

    @Override
    public void start(Stage stage) throws Exception {
        /*
         *
         *
         * Interface du logiciel
         *
         *
         * */
        software = stage;
        Parent par = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view.fxml")));
        software.setTitle("Brick Designer");
        software.setScene(new Scene(par));
        software.setMinWidth(1062);
        software.setMinHeight(710);
        software.setMaxWidth(1062);
        software.setMaxHeight(710);
        software.initStyle(StageStyle.UNDECORATED);
        software.show();

        /*
         *
         *   Fenetre d'ouverture bonus qui propose si on veut ouvrir/créé un nouveau projet
         *
         *   Cette fenêtre n'est pas essentielle.
         *
         * */
        home = new Stage();
        Parent pHome = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homeview.fxml")));
        home.setScene(new Scene(pHome));
        pHome.setOnMousePressed(event -> {
            xOffsetHome = event.getSceneX();
            yOffsetHome = event.getSceneY();
        });
        pHome.setOnMouseDragged(event -> {
            home.setX(event.getScreenX() - xOffsetHome);
            home.setY(event.getScreenY() - yOffsetHome);
        });
        home.setTitle("Welcome");
        home.setMinWidth(387);
        home.setMinHeight(254);
        home.setMaxWidth(387);
        home.setMaxHeight(254);
        home.initStyle(StageStyle.UNDECORATED);
        home.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}