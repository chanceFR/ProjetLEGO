package fr.antoromeochrist.projetlego;
import fr.antoromeochrist.projetlego.utils.ImageStorage;
import fr.antoromeochrist.projetlego.utils.ImagePath;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    ArrayList<ImageStorage> imagesLinked;

    {
        try {
            imagesLinked = new ArrayList<>(
                    Arrays.asList( new ImageStorage("p1",
                                   new ImagePath("pomme.jpg")),
                                    new ImageStorage("p2",
                                    new ImagePath("pomme2.jpg"))
                    )
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    private ImageView imageView;

    @FXML
    private Group group;
    @FXML
    private SubScene subScene;

    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchBar.getText(), ImageStorage.getTexts(imagesLinked)));
    }



    String currentText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(ImageStorage.getTexts(imagesLinked));

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentText = listView.getSelectionModel().getSelectedItem();
                imageView.setImage(ImageStorage.getImage(imagesLinked,currentText));
            }
        });
        createContent();
    }


    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

    private void createContent(){
        Sphere sphere = new Sphere(2.5);
        sphere.setMaterial(new PhongMaterial(Color.ANTIQUEWHITE));
        sphere.setTranslateZ(7);
        sphere.setTranslateX(4);

        Sphere sphere2 = new Sphere(3.5);
        sphere2.setMaterial(new PhongMaterial(Color.RED));
        sphere2.setTranslateZ(16);
        sphere2.setTranslateX(-8);


        Sphere sphere3 = new Sphere(3);
        sphere3.setMaterial(new PhongMaterial(Color.BLUE));
        sphere3.setTranslateZ(8);
        sphere3.setTranslateX(-8);

        Box box = new Box(5, 5, 5);
        box.setMaterial(new PhongMaterial(Color.BROWN));

        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -50)
        );

        // animate the camera position.
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15),
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        group.getChildren().add(camera);
        group.getChildren().add(box);
        group.getChildren().add(sphere);
        group.getChildren().add(sphere2);
        group.getChildren().add(sphere3);
        // set the pivot for the camera position animation base upon mouse clicks on objects
        group.getChildren().stream()
                .filter(node -> !(node instanceof Camera))
                .forEach(node ->
                        node.setOnMouseClicked(event -> {
                            pivot.setX(node.getTranslateX());
                            pivot.setY(node.getTranslateY());
                            pivot.setZ(node.getTranslateZ());
                        })
                );
        subScene.setFill(Color.web("#181a1e"));
        subScene.setCamera(camera);
    }

}