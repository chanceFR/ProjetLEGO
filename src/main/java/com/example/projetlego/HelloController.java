package com.example.projetlego;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.PerspectiveCamera;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HelloController implements Initializable {

    ArrayList<ImageStorage> imagesLinked;

    {
        try {
            imagesLinked = new ArrayList<>(
                    Arrays.asList( new ImageStorage("p1",
                                   new ImageURL("pomme.jpg")),
                                    new ImageStorage("p2",
                                    new ImageURL("pomme2.jpg"))
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


    }


    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
}