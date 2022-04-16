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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static fr.antoromeochrist.projetlego.utils.CameraUtils.angleX;
import static fr.antoromeochrist.projetlego.utils.CameraUtils.angleY;

public class Controller implements Initializable {

    public static Brick brickClicked;
    public static  int axisLeftRight = 0;

    private String currentText;
    private boolean isClickedClone=false;
    private boolean isClickedHide=false;
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
    private ImageView top;

    @FXML
    private ImageView left;
    @FXML
    private ImageView right;
    @FXML
    private ImageView bottom;

    private ArrayList<ImageStorage> imageStorages = new ArrayList<>();

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

        left.addEventFilter(MouseEvent.MOUSE_PRESSED,rL);
        left.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseLeftReleased);

        right.addEventFilter(MouseEvent.MOUSE_PRESSED,rR);
        right.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseRightReleased);

        top.addEventFilter(MouseEvent.MOUSE_PRESSED,rT);
        top.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseTopReleased);

        bottom.addEventFilter(MouseEvent.MOUSE_PRESSED,rB);
        bottom.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseBottomReleased);

        plus.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseClickPlus);
        plus.addEventFilter(MouseEvent.MOUSE_RELEASED,mousePlusReleased);

        minus.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseClickMinus);
        minus.addEventFilter(MouseEvent.MOUSE_RELEASED,mouseMinusReleased);
        subScene.addEventFilter(DragEvent.DRAG_DROPPED,subSceneDrag);
        subScene.setOnKeyPressed(e -> {
            System.out.println("key");
            if(brickClicked != null){
                switch(e.getCode()){
                    case SPACE:
                        axisLeftRight=1-axisLeftRight;
                    case LEFT:
                        brickClicked.left();
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
        });
        /*subScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("Subscene "+e.getX()+" "+e.getY()+" "+e.getZ());
            }
        });*/

        createContent();
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

    EventHandler<MouseEvent> rL = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleX-=45;
            camera.addRotationsY(new DurationAngle(angleX,0.3f));
            try {
                left.setImage(new ImagePath("leftHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> rR = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleX+=45;
            camera.addRotationsY(new DurationAngle(angleX,0.3f));
            try {
                right.setImage(new ImagePath("rightHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    EventHandler<MouseEvent> rT = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleY+=45;
            camera.addRotationsX(new DurationAngle(angleY,0.3f));
            try {
                top.setImage(new ImagePath("topHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };
    EventHandler<MouseEvent> rB = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            angleY-=45;
            camera.addRotationsX(new DurationAngle(angleY,0.3f));
            try {
                bottom.setImage(new ImagePath("bottomHover.png"));
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

    EventHandler<DragEvent> subSceneDrag = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent e) {
                e.acceptTransferModes(TransferMode.ANY);
                new Brick();
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

        ArrayList<String> colors = new ArrayList<>();
        colors.add("#dd61ec");
        colors.add("#b4490c");
        colors.add("#5ac2d3");
        ArrayList<Dim> dims = new ArrayList<>();
        dims.add(new Dim(1,1));
        dims.add(new Dim(1,1,2));
        Random rd = new Random();
        for(int i =0; i < 40;i++) {
            String c = colors.get(rd.nextInt(colors.size()));
            Dim d = dims.get(rd.nextInt(dims.size()));
            int x = rd.nextInt(10)-5;
            int z = rd.nextInt(10)-5;
            new Brick(d,x,z,c);
        }
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