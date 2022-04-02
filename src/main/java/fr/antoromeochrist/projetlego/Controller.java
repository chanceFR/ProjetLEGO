package fr.antoromeochrist.projetlego;
import fr.antoromeochrist.projetlego.utils.CameraUtils;
import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import fr.antoromeochrist.projetlego.utils.bricks.GridUtils;
import fr.antoromeochrist.projetlego.utils.images.ImageStorage;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Group clonee;

    @FXML
    private Group hide;

    @FXML
    private ImageView cloneeicon;

    @FXML
    private ImageView hideicon;

    @FXML
    private Text cloneetext;

    @FXML
    private Text hidetext;

    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchBar.getText(), ImageStorage.getTexts(imagesLinked)));
    }



    String currentText;
    boolean isClickedClone=false;
    boolean isClickedHide=false;

    EventHandler<MouseEvent> mouseClickHide = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(isClickedHide){
                isClickedHide=false;
                try {
                    hideicon.setImage(new ImagePath("hide.jpg"));
                    hidetext.setFill(Color.web("#edeeef"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }else{
                isClickedHide=true;
                try {
                    hideicon.setImage(new ImagePath("hidehover.jpg"));
                    hidetext.setFill(Color.web("#42C0FB"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    EventHandler<MouseEvent> mouseEnterHide = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(!isClickedHide){
                try {
                    hideicon.setImage(new ImagePath("hidehover.jpg"));
                    hidetext.setFill(Color.web("#42C0FB"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    EventHandler<MouseEvent> mouseExitHide = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(!isClickedHide) {
                try {
                    hideicon.setImage(new ImagePath("hide.jpg"));
                    hidetext.setFill(Color.web("#edeeef"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    EventHandler<MouseEvent> mouseClickClone = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(isClickedClone){
                isClickedClone=false;
                try {
                    cloneeicon.setImage(new ImagePath("clone.jpg"));
                    cloneetext.setFill(Color.web("#edeeef"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }else{
                isClickedClone=true;
                try {
                    cloneeicon.setImage(new ImagePath("clonehover.jpg"));
                    cloneetext.setFill(Color.web("#42C0FB"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    EventHandler<MouseEvent> mouseEnterClone = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(!isClickedClone){
                try {
                    cloneeicon.setImage(new ImagePath("clonehover.jpg"));
                    cloneetext.setFill(Color.web("#42C0FB"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    EventHandler<MouseEvent> mouseExitClone = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(!isClickedClone) {
                try {
                    cloneeicon.setImage(new ImagePath("clone.jpg"));
                    cloneetext.setFill(Color.web("#edeeef"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };
    EventHandler<KeyEvent> keyPressedSearchBar = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            if (e.getCode().equals(KeyCode.ENTER)) {
                listView.getItems().clear();
                listView.getItems().addAll(searchList(searchBar.getText(), ImageStorage.getTexts(imagesLinked)));
            }
        }
    };


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

        clonee.addEventFilter(MouseEvent.MOUSE_CLICKED,mouseClickClone);
        clonee.addEventFilter(MouseEvent.MOUSE_ENTERED,mouseEnterClone);
        clonee.addEventFilter(MouseEvent.MOUSE_EXITED,mouseExitClone);

        hide.addEventFilter(MouseEvent.MOUSE_CLICKED,mouseClickHide);
        hide.addEventFilter(MouseEvent.MOUSE_ENTERED,mouseEnterHide);
        hide.addEventFilter(MouseEvent.MOUSE_EXITED,mouseExitHide);

        searchBar.addEventFilter(KeyEvent.KEY_PRESSED,keyPressedSearchBar);
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
        Brick.groupBricks=group;
        new Brick(new Dim(1,1),0,0,Color.RED);
        new Brick(new Dim(1,1),0,1,Color.ORANGE);
        new Brick(new Dim(1,1),0,2,Color.GREEN);
        new Brick(new Dim(1,1),1,0,Color.BLUE);
        new Brick(new Dim(1,1),1,1,Color.BLUEVIOLET);
        new Brick(new Dim(1,1),1,2,Color.WHITE);
        new Brick(new Dim(1,1),0,0,Color.RED);

        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        // Create and position camera
        CameraUtils camera = new CameraUtils(true);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -20));
        camera.delay(3);
        camera.zoom();
        camera.delay(3);
        camera.dezoom();
        camera.play();






        /*
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
        */
        Timeline timeline = new Timeline(

                new KeyFrame(
                        Duration.seconds(3),
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(10);
        timeline.play();


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

        for(Brick b:Brick.bricks){
            b.print();
        }
    }

}