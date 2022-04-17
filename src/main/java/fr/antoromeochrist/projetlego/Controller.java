package fr.antoromeochrist.projetlego;
import fr.antoromeochrist.projetlego.utils.CameraUtils;
import fr.antoromeochrist.projetlego.utils.DurationAngle;
import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import fr.antoromeochrist.projetlego.utils.images.ImageStorage;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static fr.antoromeochrist.projetlego.utils.CameraUtils.angleX;
import static fr.antoromeochrist.projetlego.utils.CameraUtils.angleY;

public class Controller implements Initializable {

    public static Brick brickClicked;
    public static int axisLeftRight = 0;

    private boolean isClickedClone=false;
    private boolean isClickedHide=false;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<ImageStorage> listView;

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
    private ImageView plus;

    @FXML
    private ImageView minus;


    @FXML
    private ImageView rtop;

    @FXML
    private ImageView rleft;
    @FXML
    private ImageView rright;
    @FXML
    private ImageView rbottom;

    @FXML
    private ImageView top;

    @FXML
    private ImageView left;
    @FXML
    private ImageView right;
    @FXML
    private ImageView bottom;

    private ArrayList<ImageStorage> imageStorages = new ArrayList<>();

    private boolean actionWithDropDone = true;
    @FXML
    public ImageView brickSelection;

    public ImageStorage dropSelectionData;
    @FXML
    private ColorPicker colorpicker;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            imageStorages.add(new ImageStorage("1x1",new ImagePath("pomme.jpg")));
            imageStorages.add(new ImageStorage("1x2",new ImagePath("pomme2.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listView.getItems().addAll(imageStorages);
        ArrayList<String> texts = ImageStorage.getTexts(imageStorages);
        listView.setCellFactory(listView -> new ListCell<ImageStorage>() {
            @Override
            protected void updateItem(ImageStorage piece, boolean empty) {
                super.updateItem(piece, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Create a HBox to hold our displayed value
                    HBox hbx = new  HBox();
                    hbx.setAlignment(Pos.CENTER);
                    ImageView iv = new ImageView();
                    iv.setImage(piece.getImage());
                    iv.setFitHeight(100);
                    iv.setFitWidth(100);
                    Label lb = new Label("      "+piece.getText());
                    lb.setStyle("-fx-text-fill: #c25b11;");
                    hbx.setStyle("-fx-font-size: 10px;");
                    hbx.getChildren().addAll(
                            iv,
                            lb
                    );
                    setGraphic(hbx);
                    hbx.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            actionWithDropDone =false;
                            System.out.println("Création de la brique-view:");
                            dropSelectionData =piece;
                            brickSelection.setFitHeight(100);
                            brickSelection.setFitWidth(100);
                            brickSelection.setImage(iv.getImage());
                            brickSelection.setOpacity(100);
                            brickSelection.setX(e.getX());
                            brickSelection.setY(e.getY());
                            brickSelection.setLayoutX(iv.getLayoutX());
                            brickSelection.setLayoutY(iv.getLayoutY());
                        }
                    });
                }
            }
        });
        anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!actionWithDropDone){
                    brickSelection.setX(mouseEvent.getX());
                    brickSelection.setY(mouseEvent.getY());
                }
            }
        });

        subScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(!actionWithDropDone){
                    actionWithDropDone =true;
                    clearBreakSelection();
                    if(brickClicked !=null){
                        new Brick(dropSelectionData.getDimWithText(),brickClicked.getX(), brickClicked.getY(), brickClicked.getZ(), "").setColor(colorpicker.getValue());
                    }else{
                        new Brick(dropSelectionData.getDimWithText(),0, 0, 0, "").setColor(colorpicker.getValue());
                    }
                }
            }
        });

        colorpicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(brickClicked != null) brickClicked.setColor(colorpicker.getValue());
            }
        });

        anchorPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.UNDO) || keyEvent.getCode().equals(KeyCode.ESCAPE)){
                    if(actionWithDropDone == false) {
                        actionWithDropDone = true;
                        clearBreakSelection();
                    }
                }
                    if(brickClicked != null){
                        switch(keyEvent.getCode()){
                            case X:
                                axisLeftRight=1-axisLeftRight;
                                break;
                            case LEFT:
                                brickClicked.left();
                                break;
                            case RIGHT:
                                brickClicked.right();
                                break;
                            case UP:
                                brickClicked.up();
                                break;
                            case DOWN:
                                brickClicked.down();
                                break;
                        }
                    }

            }
        });

        clonee.addEventFilter(MouseEvent.MOUSE_CLICKED,mouseClickClone);
        clonee.addEventFilter(MouseEvent.MOUSE_ENTERED,mouseEnterClone);
        clonee.addEventFilter(MouseEvent.MOUSE_EXITED,mouseExitClone);

        hide.addEventFilter(MouseEvent.MOUSE_CLICKED,mouseClickHide);
        hide.addEventFilter(MouseEvent.MOUSE_ENTERED,mouseEnterHide);
        hide.addEventFilter(MouseEvent.MOUSE_EXITED,mouseExitHide);

        searchBar.addEventFilter(KeyEvent.KEY_PRESSED,keyPressedSearchBar);

        rleft.addEventFilter(MouseEvent.MOUSE_PRESSED,rL);
        rleft.addEventFilter(MouseEvent.MOUSE_RELEASED, rmouseLeftReleased);

        rright.addEventFilter(MouseEvent.MOUSE_PRESSED,rR);
        rright.addEventFilter(MouseEvent.MOUSE_RELEASED, rmouseRightReleased);

        rtop.addEventFilter(MouseEvent.MOUSE_PRESSED,rT);
        rtop.addEventFilter(MouseEvent.MOUSE_RELEASED, rmouseTopReleased);

        rbottom.addEventFilter(MouseEvent.MOUSE_PRESSED,rB);
        rbottom.addEventFilter(MouseEvent.MOUSE_RELEASED, rmouseBottomReleased);


        top.addEventFilter(MouseEvent.MOUSE_PRESSED,bT);
        top.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseTopReleased);

        bottom.addEventFilter(MouseEvent.MOUSE_PRESSED,bB);
        bottom.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseBottomReleased);

        left.addEventFilter(MouseEvent.MOUSE_PRESSED,bL);
        left.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseLeftReleased);

        right.addEventFilter(MouseEvent.MOUSE_PRESSED,bR);
        right.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseRightReleased);

        plus.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseClickPlus);
        plus.addEventFilter(MouseEvent.MOUSE_RELEASED,mousePlusReleased);

        minus.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseClickMinus);
        minus.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseMinusReleased);
        createContent();
    }

    private void clearBreakSelection() {
        brickSelection.setFitHeight(1);
        brickSelection.setFitWidth(1);
        brickSelection.setLayoutX(1046);
        brickSelection.setLayoutY(648);
        brickSelection.setOpacity(0);
    }


    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchBar.getText(),imageStorages));
    }

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
                listView.getItems().addAll(searchList(searchBar.getText(), imageStorages));
            }
        }
    };


    EventHandler<MouseEvent> mouseClickPlus = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            camera.zoom(2.5);
            try {
                plus.setImage(new ImagePath("plusHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> mouseClickMinus = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            camera.dezoom(2.5);
            try {
                minus.setImage(new ImagePath("minusHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> mousePlusReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                plus.setImage(new ImagePath("plus.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> mouseMinusReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                minus.setImage(new ImagePath("minus.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };


    EventHandler<MouseEvent> rmouseLeftReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                rleft.setImage(new ImagePath("rleft.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };


    EventHandler<MouseEvent> rmouseRightReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                rright.setImage(new ImagePath("rright.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };



    EventHandler<MouseEvent> rmouseTopReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                rtop.setImage(new ImagePath("rtop.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };



    EventHandler<MouseEvent> rmouseBottomReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                rbottom.setImage(new ImagePath("rbottom.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> rL = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleX-=22.5;
            camera.addRotationsY(new DurationAngle(angleX,0.4f));
            try {
                rleft.setImage(new ImagePath("rleftHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> rR = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleX+=22.5;
            camera.addRotationsY(new DurationAngle(angleX,0.4f));
            try {
                rright.setImage(new ImagePath("rrightHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> rT = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleY+=22.5;
            camera.addRotationsX(new DurationAngle(angleY,0.4f));
            try {
                rtop.setImage(new ImagePath("rtopHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> rB = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleY-=22.5;
            camera.addRotationsX(new DurationAngle(angleY,0.4f));
            try {
                rbottom.setImage(new ImagePath("rbottomHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> mouseLeftReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                left.setImage(new ImagePath("left.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };


    EventHandler<MouseEvent> mouseRightReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                right.setImage(new ImagePath("right.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };



    EventHandler<MouseEvent> mouseTopReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                top.setImage(new ImagePath("top.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };



    EventHandler<MouseEvent> mouseBottomReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                bottom.setImage(new ImagePath("bottom.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> bL = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            camera.left();
            try {
                left.setImage(new ImagePath("leftHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> bR = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            camera.right();
            try {
                right.setImage(new ImagePath("rightHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> bT = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
           camera.up();
            try {
                top.setImage(new ImagePath("topHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> bB = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            camera.down();
            try {
                bottom.setImage(new ImagePath("bottomHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    private List<ImageStorage> searchList(String searchWords, List<ImageStorage> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.getText().toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }


    public CameraUtils camera;
    private void createContent(){
        Brick.group =group;
        new Brick (new Dim(1,1),0,0);
        //Translate pivot = new Translate();
        // Create and position camera
        camera = new CameraUtils(true);
        // set the pivot for the camera position animation base upon mouse clicks on objects
        /*group.getChildren().stream()
                .filter(node -> !(node instanceof Camera))
                .forEach(node ->
                        node.setOnMouseClicked(event -> {
                            pivot.setX(node.getTranslateX());
                            pivot.setY(node.getTranslateY());
                            pivot.setZ(node.getTranslateZ());
                        })
                );*/
        subScene.setFill(Color.web("#181a1e"));
        subScene.setCamera(camera);
        System.out.println("--> "+Brick.environnement);
    }

}
