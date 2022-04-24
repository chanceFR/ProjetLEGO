package fr.antoromeochrist.projetlego;
import fr.antoromeochrist.projetlego.utils.CameraUtils;
import fr.antoromeochrist.projetlego.utils.DurationAngle;
import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import fr.antoromeochrist.projetlego.utils.bricks.GridBrick;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static fr.antoromeochrist.projetlego.utils.CameraUtils.angleX;
import static fr.antoromeochrist.projetlego.utils.CameraUtils.angleY;

public class Controller implements Initializable {

    public static Brick brickClicked;
    public static int axisLeftRight = 0;
    @FXML
    private AnchorPane anchorPane;

    public CameraUtils camera;

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
    public ColorPicker colorpicker;

    @FXML
    public ListView contentColors;

    @FXML
    public ListView steps;

    public ListView currentStep;

    @FXML
    public Label addStep;

    private boolean updateForced=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentStep = new ListView();
        addStep.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                steps.getItems().add(new ListView());
                currentStep=(ListView) steps.getItems().get(steps.getItems().size()-1);
                addStep.setTextFill(Color.web("#ffffff"));
                Brick.currentStepStatic=currentStep;
                Brick.stepsStatic=steps;
            }
        });

        addStep.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addStep.setTextFill(Color.web("#808080"));
            }
        });

        Brick.contentColorsStatic=contentColors;
        Brick.stepsStatic=steps;
        Brick.currentStepStatic= currentStep;
        Brick.group=group;
        camera = new CameraUtils(true);
        angleY=-30;
        camera.addRotationsX(new DurationAngle(angleY,0.4f));
        steps.setCellFactory(listView -> new ListCell<ListView>() {
            @Override
            protected void updateItem(ListView lv, boolean empty) {
                super.updateItem(lv, empty);
                if (empty) {
                    setGraphic(null);
                } else {

                    VBox vbx= new  VBox();
                    vbx.setPrefWidth(0);
                    vbx.setStyle("-fx-font-size: 10px;");
                    vbx.setStyle("-fx-background-color: transparent;");
                    HBox hbx = new HBox();
                    hbx.setSpacing(10);
                    hbx.setStyle("-fx-font-size: 10px;");
                    hbx.setStyle("-fx-background-color: transparent;");
                    TextField field = new TextField("step "+steps.getItems().size());
                    field.setPrefWidth(400);
                    field.setStyle("-fx-background-color: #121418;\n" +
                            "    -fx-text-inner-color: #808080;\n" +
                            "    -fx-font-size: 12px;\n" +
                            "    -fx-border-radius: 1px;");
                    ImageView trash = null;
                    if(steps.getItems().size() > 1) {
                        trash = new ImageView();
                        try {
                            trash.setImage(new ImagePath("trash.png"));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        trash.setFitHeight(12);
                        trash.setFitWidth(12);
                        trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                for (Object o : lv.getItems()) {
                                    Brick b = (Brick) o;
                                    b.remove();
                                }
                                steps.getItems().remove(lv);
                            }
                        });
                    }
                    lv.setStyle("-fx-background-color: transparent;");
                    lv.setPrefHeight(51);
                    lv.setCellFactory(o -> new ListCell<Brick>(){
                        @Override
                        protected void updateItem(Brick o, boolean empty) {
                            super.updateItem(o, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                    HBox hbx1 = new HBox();
                                    hbx1.setAlignment(Pos.CENTER_LEFT);
                                    hbx1.setStyle("-fx-font-size: 12px;");
                                    ImageView iv = new ImageView();
                                    iv.setImage(ImageStorage.getImage(imageStorages, o.getDim().toString()));
                                    iv.setFitHeight(20);
                                    iv.setFitWidth(20);
                                    Label lb = new Label("      " + o.getDim().toString()+"             ");
                                    lb.setStyle("-fx-text-fill: #808080;");
                                    Label lb2 = new Label("     ");
                                    o.setRect(new Rectangle(0, 0, 10, 10));
                                    System.out.println("color: " + o.getColor().getRed() * 255 + " " + o.getColor().getGreen() * 255 + " " + o.getColor().getBlue() * 255);
                                    o.getRect().setFill(o.getColor());
                                    o.getRect().setStroke(Color.BLACK);
                                    hbx1.getChildren().addAll(
                                            iv,
                                            o.getRect(),
                                            lb,
                                            o.getHidestatus(),
                                            lb2,
                                            o.getTrash()
                                    );
                                    hbx1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            if (brickClicked != null) brickClicked.setSelectMode(false);
                                            brickClicked = o;
                                            o.setSelectMode(true);
                                        }
                                    });
                                    lv.setFixedCellSize(50);
                                    lv.setPrefHeight(lv.getItems().size() * 50 + 5);
                                    setGraphic(hbx1);
                            }
                        }
                        });
                    if(steps.getItems().size() > 1){
                        hbx.getChildren().addAll(field,trash);
                    }else{
                        hbx.getChildren().addAll(field);
                    }

                    vbx.getChildren().addAll(hbx,lv);
                    setGraphic(vbx);
                }
            }
        });
        steps.getItems().add(currentStep);


        try {
            imageStorages.add(new ImageStorage("1x1",new ImagePath("1x1.png")));
            imageStorages.add(new ImageStorage("1x2",new ImagePath("1x2.png")));
            imageStorages.add(new ImageStorage("1x3",new ImagePath("1x3.png")));
            imageStorages.add(new ImageStorage("1x4",new ImagePath("1x4.png")));
            imageStorages.add(new ImageStorage("2x2",new ImagePath("2x2.png")));
            imageStorages.add(new ImageStorage("2x3",new ImagePath("2x3.png")));
            imageStorages.add(new ImageStorage("2x4",new ImagePath("2x4.png")));
            imageStorages.add(new ImageStorage("3x3",new ImagePath("3x3.png")));
            imageStorages.add(new ImageStorage("3x4",new ImagePath("3x4.png")));
            imageStorages.add(new ImageStorage("4x4",new ImagePath("4x4.png")));
            imageStorages.add(new ImageStorage("1x1x2",new ImagePath("1x1x2.png")));
            imageStorages.add(new ImageStorage("1x1x4",new ImagePath("1x1x4.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listView.getItems().addAll(imageStorages);

        listView.setCellFactory(listView -> new ListCell<ImageStorage>() {
            @Override
            protected void updateItem(ImageStorage imSto, boolean empty) {
                super.updateItem(imSto, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Create a HBox to hold our displayed value
                    HBox hbx1 = new  HBox();
                    hbx1.setAlignment(Pos.CENTER);
                    ImageView iv = new ImageView();
                    iv.setImage(imSto.getImage());
                    iv.setFitWidth(50);
                    /*if(imSto.getDimWithText().getHeight() > 1){
                        iv.setFitHeight(50*imSto.getDimWithText().getHeight());
                    }
                    else{
                        iv.setFitHeight(50);
                    }*/

                    iv.setFitHeight(50);
                    Label lb = new Label("      "+imSto.getText());
                    lb.setStyle("-fx-text-fill: #c25b11;");
                    hbx1.setStyle("-fx-font-size: 10px;");
                    hbx1.getChildren().addAll(
                            iv,
                            lb
                    );
                    setGraphic(hbx1);
                    hbx1.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            actionWithDropDone =false;
                            System.out.println("Création de la brique-view:");
                            dropSelectionData =imSto;
                            brickSelection.setFitHeight(50);
                            brickSelection.setFitWidth(50);
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

        contentColors.setCellFactory(listView -> new ListCell<Color>() {
            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Rectangle re = new Rectangle(0, 0, 10, 10);
                    re.setFill(color);
                    re.setStroke(Color.BLACK);
                    setGraphic(re);
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

        //Ajout de brick en les glissant avec la souris
        subScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(!actionWithDropDone){
                    actionWithDropDone =true;
                    clearBreakSelection();
                    if(brickClicked !=null){
                        new Brick(dropSelectionData.getDimWithText(),brickClicked.getX(), brickClicked.getY(), brickClicked.getZ(), colorpicker.getValue()).setColor(colorpicker.getValue());
                        brickClicked.setSelectMode(false);
                    }else{
                        new Brick(dropSelectionData.getDimWithText(),0, 0, 0, colorpicker.getValue()).setColor(colorpicker.getValue());
                    }
                }
            }
        });

        colorpicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(brickClicked != null){
                    Color oldColor = brickClicked.getColor();
                    brickClicked.setColor(colorpicker.getValue());
                    brickClicked.getRect().setFill(colorpicker.getValue());
                    System.out.println("Dic: "+Brick.bricksSortByColors);
                    System.out.println("Brick with hex :"+Brick.getBrickWithColor(oldColor).size());
                    if(Brick.getBrickWithColor(oldColor).isEmpty()) contentColors.getItems().remove(oldColor);
                }
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
                            case W:
                                brickClicked.down();
                                break;
                            case X:
                                brickClicked.up();
                                break;
                            case LEFT:
                                brickClicked.leftX();
                                break;
                            case RIGHT:
                                brickClicked.rightX();
                                break;
                            case UP:
                                brickClicked.rightZ();
                                break;
                            case DOWN:
                                brickClicked.leftZ();
                                break;
                        }
                    }

            }
        });

        clonee.addEventFilter(MouseEvent.MOUSE_PRESSED,mousePressedClone);
        clonee.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseReleasedClone);

        hide.addEventFilter(MouseEvent.MOUSE_PRESSED,mousePressedHide);
        hide.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseReleasedHide);

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

        bottom.addEventFilter(MouseEvent.MOUSE_PRESSED,o);
        bottom.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseBottomReleased);

        left.addEventFilter(MouseEvent.MOUSE_PRESSED,bL);
        left.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseLeftReleased);

        right.addEventFilter(MouseEvent.MOUSE_PRESSED,bR);
        right.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseRightReleased);

        plus.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseClickPlus);
        plus.addEventFilter(MouseEvent.MOUSE_RELEASED,mousePlusReleased);

        minus.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseClickMinus);
        minus.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseMinusReleased);
        subScene.setFill(Color.web("#181a1e"));
        subScene.setCamera(camera);
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

    EventHandler<MouseEvent> mousePressedHide = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (brickClicked != null) {
                if (brickClicked.isHide()) {
                    brickClicked.hide(false);
                } else {
                    brickClicked.hide(true);
                }
            }

            try {
                hideicon.setImage(new ImagePath("hidehover.jpg"));
                hidetext.setFill(Color.web("#42C0FB"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> mouseReleasedHide = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
                try {
                    hideicon.setImage(new ImagePath("hide.jpg"));
                    hidetext.setFill(Color.web("#edeeef"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

        }
    };

    EventHandler<MouseEvent> mousePressedClone = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if(brickClicked != null) {
                new Brick(brickClicked.getDim(), brickClicked.getX(), brickClicked.getY(), brickClicked.getZ(), brickClicked.getColor());
            }
            try {
                    cloneeicon.setImage(new ImagePath("clonehover.jpg"));
                    cloneetext.setFill(Color.web("#42C0FB"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
        }
    };
    EventHandler<MouseEvent> mouseReleasedClone = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
                try {
                    cloneeicon.setImage(new ImagePath("clone.jpg"));
                    cloneetext.setFill(Color.web("#edeeef"));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
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
            angleX-=11.25;
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
            angleX+=11.25;
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
            angleY+=11.25;
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
            angleY-=11.25;
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
    EventHandler<MouseEvent> o = new EventHandler<MouseEvent>() {
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

}
