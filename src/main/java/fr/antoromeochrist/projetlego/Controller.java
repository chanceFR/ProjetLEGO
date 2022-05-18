package fr.antoromeochrist.projetlego;

import fr.antoromeochrist.projetlego.pieces.Figurine;
import fr.antoromeochrist.projetlego.utils.CameraUtils;
import fr.antoromeochrist.projetlego.utils.ColorPick;
import fr.antoromeochrist.projetlego.utils.DurationAngle;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.bricks.*;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import fr.antoromeochrist.projetlego.utils.images.ImageStorage;
import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static fr.antoromeochrist.projetlego.Main.notifWindow;
import static fr.antoromeochrist.projetlego.utils.CameraUtils.x_axis;
import static fr.antoromeochrist.projetlego.utils.CameraUtils.y_axis;

public class Controller implements Initializable {

    /**
     * Permet l'accès aux variables du modèle depuis des classes externes. Exemple: {@link Brick}.
     * sans que ces classes externes aient à appeler le contrôleur dans le constructeur car ces classes externes auront besoin de ses variables.
     *
     * <p>
     * Exemple:
     * Nous n'avons pas besoin de faire : new Brick(model,...) ; nous pouvons simplement faire new Brick(..) ;
     * Et être toujours capable d'utiliser les variables du modèle dans la classe Brick.
     */
    public static Model model;

    /**
     * Permet l'accès aux variables du contrôleur(this) depuis des classes externes. Exemple: {@link Brick}.
     * sans que ces classes externes aient à appeler le contrôleur dans le constructeur car ces classes externes auront besoin de ses variables.
     * <p>
     * Exemple:
     * Nous n'avons pas besoin de faire : new Brick(controller,...) ; nous pouvons simplement faire new Brick(..) ;
     * Et être toujours capable d'utiliser les variables du contrôleur dans la classe Brick.
     */
    public static Controller controller;

    /*
     Listes des composants graphiques provenant du fichier "view.fxml"
     */
    @FXML
    private AnchorPane anchorPane;

    public CameraUtils camera;

    @FXML
    public TextField searchBar;

    @FXML
    private ListView<ImageStorage> listView;

    @FXML
    public Group group;
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
    @FXML
    public ImageView imageOfBrickSelectedInSearchMenu;

    @FXML
    public ColorPicker colorpicker;

    @FXML
    public ListView contentColors;

    @FXML
    public ListView steps;

    @FXML
    public Label addStep;

    /**
     * Permet de fermer la fenêtre
     */
    @FXML
    public Label close;

    @FXML
    public CheckBox plateCB;

    @FXML
    public CheckBox cylindricalCB;

    @FXML
    public CheckBox smoothCB;


    /**
     * Cette variable permet de stocker l'étape actuelle pour ainsi
     * insérer chaque nouvelle brique que l'on pose dedans.
     * <p>
     * Cette variable peut pointer vers une autre {@link ListView} si jamais
     * on clique le bouton sur le bouton "addStep"
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Step
     */
    public ListView currentStep;


    /**
     * La grille permet de faire bouger une {@link Brick} lors du drag and drop.
     * <p>
     * La grille ne permet pas la superposition des briques puisqu'elle
     * fonctionne avec l'évènement suivant: la souris entre dans l'une des cases de la grille,
     * ensuite le programme récupère les coordonnées dans la grille, delà où se situe la souris
     * en fonction de la case concerné par l'évènement.
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Grid
     * @see fr.antoromeochrist.projetlego.utils.bricks.Volume
     */
    public static Grid grid;

    /**
     * Permet de savoir si on est dans la subscene
     */
    public boolean inSubsceneAndListViewAndSteps = false;

    @FXML
    private MenuItem newProject;

    @FXML
    private MenuItem openProject;

    @FXML
    private MenuItem saveAs;

    @FXML
    private MenuItem save;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //init//
        controller = this;
        model = new Model();
        currentStep = new ListView();
        //init//


        /*
         * Menu de droite - gestion des étapes
         * Object concerné: addStep
         *
         * Quand on clique sur le bouton "+ add step"
         * On change la variable currentStep par une nouvelle étape comme ça quand on pose
         * une nouvelle brique elle ira dans la nouvelle étape.
         **/
        addStep.setOnMousePressed(mouseEvent -> {
            steps.getItems().add(new ListView());
            currentStep = (ListView) steps.getItems().get(steps.getItems().size() - 1);
            addStep.setTextFill(Color.web("#ffffff"));
        });
        /*
         * Menu de droite - gestion des étapes
         * Object concerné: addStep
         *
         * Juste pour remettre la couleur normal du texte du bouton "+ addstep"
         * quand le click est fini.
         **/
        addStep.setOnMouseReleased(mouseEvent -> addStep.setTextFill(Color.web("#808080")));

        /*
         * Création de la grille
         *
         **/
        grid = new Grid(20, 20, Color.web("#616161"));

        /*
         *
         * Création de la caméra avec un angle de rotation prédéfini pour avoir une
         * vision du haut de la grille lorsque le logiciel est allumé.
         *
         * */
        camera = new CameraUtils(true);
        camera.setAngleY(-30);
        camera.addRotationsY(new DurationAngle(0, 0.4f));
        camera.addRotationsX(new DurationAngle(camera.getAngleY(), 0.4f));
        camera.timeline.play();

        /*
         * Menu de droite - gestion des étapes
         * Object concerné: steps
         *
         *
         * Rappel:
         *
         *   L'objet ListView est semblabe à une arrayList (sauf que c un objet graphique javafx).
         *
         *   La variable steps est une ListView< ListView<Brick> >
         *   instruction est une ArrayList<Step> qui se situe dans modèle.
         *
         *   Step est une classe qui contient un booléen (si l'étape est caché)
         *   et une String qui stocke le nom de l'étape.
         *
         *
         * Explication de cette événement:
         *
         *   Mise à jour graphique des étapes (ListView<Brick>)(step 0,step 1...) de la "steps"
         *   en concervant le texte de chaque étape grâce à l'indice de l'étape qui permet
         *   de récupérer le texte de instruction.get(i).
         *
         *
         * */
        steps.setCellFactory(listView -> new ListCell<ListView>() {

            /*
             * Menu de droite - gestion des étapes
             * Object concerné: steps
             *
             * Explication graphique de la formation de "steps.
             * (en conversant les mêmes noms de variables utilisé (par la suite) )
             *
             * Rappel: - vbx est une boite avec un alignement vertical.
             *         - hbx,hbx1 sont des boite savec un alignement horizontal.
             *         - field est une zone de texte   /!\ éditable avec le clavier /!\
             *         - lb, lb2 est une zone de texte /!\ pas éditable avec le clavier /!\
             *         - view est une image (oeil) avec un évènement de click relié
             *         - trash est une image (oeilbarré) avec un évènement de click relié
             *
             * Schéma de formation avec n étapes:
             *
             * steps[
             *
             *   vbx[ //PREMIERE ETAPE
             *      hbx[field,view] //pas de trash(bouton corbeille) pour la première étape
             *      lv[
             *          hbx1[iv,o.getRect(),lb,o.getHidestatus(),lb2,o.getTrash()]
             *
             *          ...
             *
             *          hbx1[iv,o.getRect(),lb,o.getHidestatus(),lb2,o.getTrash()]
             *      ]
             *   ]
             *
             *   ...
             *
             *   vbx[ //DERNIERE ETAPE
             *      hbx[field, view,trash) ]
             *      lv[
             *          hbx1[iv,o.getRect(),lb,o.getHidestatus(),lb2,o.getTrash()]
             *
             *          ...
             *
             *          hbx1[iv,o.getRect(),lb,o.getHidestatus(),lb2,o.getTrash()]
             *      ]
             *   ]
             *
             * ]
             *
             * ///////////////////////////////////////////////////////////////////
             *
             * SCHEMA avec 2 étapes quelconques et la première étapes qui contient
             * 2 briques et la seconde étape contient que 1 brique.
             *
             * ///////////////////////////////////////////////////////////////////
             *
             *  Légende:
             *  |
             *  |    OEIL ça représente l'image view.png
             *  |
             *  |   OEILBARRE ça représente l'image noview.png
             *  |
             *  |   CORBEILLE ça représente l'image trash.png
             *
             *  Le reste c'est les mêmes noms de variables qui seront utilisé dessus.
             *
             *  NB: les images sont consultables dans le dossier ressources.
             *
             *
             * steps[
             *
             *   vbx[
             *      hbx["étape 1 de la construction",OEIL]
             *      lv[
             *          hbx1[RECTANGLEBLEU,"   1X1  ",OEIL      ,"      ", CORBEILLE]
             *          hbx1[RECTANGLEROUGE,"  1X2  ",OEILBARRE ,"      ",  CORBEILLE]
             *      ]
             *   ]
             *
             *   vbx[
             *      (Rappel: Si l'oeil est barré pour l'étape: alors tout est barré, les briques seront donc
             *      caché !)
             *      hbx["étape 2 Bétonnage ",OEILBARRE]
             *      lv[
             *          hbx1[RECTANGLEVERT,"   1X1x3  ",OEILBARRE      ,"     ", CORBEILLE]
             *      ]
             *   ]
             *
             * ]
             *
             *
             *
             *
             * */
            @Override
            protected void updateItem(ListView lv, boolean empty) {
                super.updateItem(lv, empty);
                if (empty) setGraphic(null);
                else {

                    /*
                     * WARNING
                     *
                     * /!\
                     *
                     * Voir le schéma au-dessus pour comprendre graphiquement ce que fait
                     * le code
                     *
                     * /!\
                     *
                     * */

                    /*
                     * Création de hbx
                     *
                     * */
                    HBox hbx = new HBox();
                    hbx.setSpacing(10);
                    hbx.setStyle("-fx-font-size: 10px;");
                    hbx.setStyle("-fx-background-color: transparent;");

                    /*
                     * Menu de droite - gestion des étapes
                     * Création de field
                     *
                     * */

                    TextField field = new TextField();
                    field.setPrefWidth(200);
                    field.setStyle("""
                            -fx-background-color: #121418;
                                -fx-text-inner-color: #808080;
                                -fx-font-size: 12px;
                                -fx-border-radius: 1px;""");
                    int i = steps.getItems().indexOf(lv);

                    /*
                     * On regarde si c'est la première fois qu'on construit field avec le try catch
                     * Si c'est la première fois faut le relier à instruction !
                     **/
                    try {
                        //Fast.log("L'instruction existe !");
                        model.instruction.get(i);
                        model.instruction.get(i).setTf(field);
                        model.instruction.get(i).setName(model.instruction.get(i).getName(), 123);
                    } catch (Exception e) {
                        model.instruction.add(new Step("step " + i, (ListView<Brick>) steps.getItems().get(i), field));
                    }
                    for (int i2 = 0; i2 < steps.getItems().size(); i2++) {
                        model.instruction.get(i2).setBricks((ListView<Brick>) steps.getItems().get(i));
                    }
                    /*
                     * Maintenant qu'on sait que la valeur du texte de field est forcément relié à instruction
                     *
                     * On la récupère:
                     * */
                    final Step current = model.instruction.get(i);

                    /*
                     * Menu de droite - gestion des étapes
                     * Objet concerné: field
                     *
                     * On met à jour sa valeur textuelle quand l'utilisateur change le texte
                     *
                     *
                     * */
                    //Fast.log("instructions i : " + model.instruction.get(i).getName());
                    //field prendra forcément la valeur contenue dans current lors de sa création.
                    field.setText(current.getName());
                    field.textProperty().addListener((observable, oldValue, newValue) -> current.setName(newValue, 34));

                    /*
                     * Menu de droite - gestion des étapes
                     * Création de view
                     *
                     * */
                    ImageView view = new ImageView();
                    try {
                        if (current.isHide())
                            view.setImage(new ImagePath("noview.png"));
                        else
                            view.setImage(new ImagePath("view.png"));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    view.setFitHeight(20);
                    view.setFitWidth(20);

                    /*
                     * Menu de droite - gestion des étapes
                     * Objet concerné: view
                     *
                     * Si on veut caché l'étape, alors toutes les briques
                     * doivent se caché et inversement.
                     *
                     * */
                    view.setOnMousePressed(mouseEvent -> {

                        if (current.isHide()) {
                            current.hide(false);
                            try {
                                view.setImage(new ImagePath("view.png"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            current.hide(true);
                            try {
                                view.setImage(new ImagePath("noview.png"));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //Les briques seront caché + leurs boutons passeront de view.png à noview.png.
                        for (Object obj : lv.getItems()) ((Brick) obj).hide(current.isHide());
                    });

                    /*
                    Lors de la création:
                    Les briques seront caché/visible + leurs boutons(view) qui switch entre 2 images.
                    Oeil normal et oeil barré
                     */
                    /*
                     * Menu de droite - gestion des étapes
                     *
                     * Création de trahs
                     *
                     *
                     * */
                    ImageView trash = null;
                    if (steps.getItems().indexOf(lv) > 0) { //on ajoute pas trash si il y a que l'étape 1
                        trash = new ImageView();
                        try {
                            trash.setImage(new ImagePath("trash.png"));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        trash.setFitHeight(12);
                        trash.setFitWidth(12);

                        /*
                         * Menu de droite - gestion des étapes
                         * Objet concerné: trash
                         *
                         * Evement si on veut caché une brique
                         *
                         * */
                        trash.setOnMousePressed(mouseEvent -> {
                            int size = lv.getItems().size();
                            for (int o = 0; o < size; o++) {
                                if (lv.getItems().get(o) instanceof Brick b) b.remove();
                            }
                            steps.getItems().remove(lv);
                        });
                    }

                    /*
                     * Ajustement de lv (presque égale à sa création)
                     *
                     * */
                    lv.setStyle("-fx-background-color: transparent;");
                    lv.setFixedCellSize(50);
                    //ajustement de la taille de lv en fonction du nombre de ses élements
                    if (lv.getItems().isEmpty()) lv.setPrefHeight(55);
                    if (lv.getItems().size() > 0) lv.setPrefHeight(lv.getItems().size() * 50 + 5);
                    lv.setCellFactory(o -> new ListCell<Brick>() {
                        @Override
                        protected void updateItem(Brick brk, boolean empty) {
                            super.updateItem(brk, empty);
                            if (empty) setGraphic(null);
                            else {
                                /*
                                 * Création de hbx1
                                 *
                                 * */
                                HBox hbx1 = new HBox();
                                hbx1.setAlignment(Pos.CENTER_LEFT);
                                hbx1.setStyle("-fx-font-size: 12px;");
                                ImageView iv = new ImageView();
                                if (!brk.isPiece()) {
                                    iv.setImage(ImageStorage.getImage(model.imageStorages, brk.getDim().toString()));
                                } else {
                                    try {
                                        iv.setImage(new ImagePath("figurine.png"));
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                iv.setFitHeight(20);
                                iv.setFitWidth(20);

                                /*
                                 * Création de lb
                                 * */
                                //D2 permet d'afficher l'inversement de la dimension si la brick a été rotate
                                Dim d2 = new Dim(brk.getDim());
                                d2.rotate();
                                Label lb = new Label("  " + d2 + "  ");
                                lb.setStyle("-fx-text-fill: #808080;");

                                /*
                                 * Création de lb2
                                 * */

                                Label lb2 = new Label("     ");
                                hbx1.getChildren().addAll(
                                        iv,
                                        brk.getRect(),
                                        lb,
                                        brk.getHidestatus(),
                                        lb2,
                                        brk.getTrash()
                                );

                                /*
                                 Si on clique sur une brique depuis le menu des étapes ça la sélectionne
                                 */
                                hbx1.setOnMousePressed(mouseEvent -> {
                                    if (model.brickClicked != null) model.brickClicked.setState(State.NONE, 12);
                                    brk.setState(State.SHOW_IS_SELECT, 13);
                                    model.brickClicked = brk;
                                });
                                setGraphic(hbx1);
                                lv.setPrefHeight(lv.getItems().size() * 50 + 5);
                            }
                        }
                    });

                    /*
                     * On ajoute la possibilité de supprimer l'étape avec le bouton corbeille "trash"
                     * que si on est pas à l'étape 1 !
                     */
                    if (steps.getItems().indexOf(lv) > 0)
                        hbx.getChildren().addAll(field, view, trash);
                    else {
                        Rectangle rect = new Rectangle(0, 0, 10, 10);
                        rect.setOpacity(0);
                        hbx.getChildren().addAll(field, view, rect);
                    }

                    /*
                     * Création de vbx
                     *
                     * */
                    VBox vbx = new VBox();
                    vbx.setPrefWidth(0);
                    vbx.setStyle("-fx-font-size: 10px;");
                    vbx.setStyle("-fx-background-color: transparent;");
                    vbx.getChildren().addAll(hbx, lv);
                    setGraphic(vbx);
                }
            }
        });

        /*
         *
         * Menu de droite - steps
         *
         * Lors du démarrage du programme on ajoute la première étape qui est vide.
         *
         * */
        steps.getItems().add(currentStep);


        ////////////////////////////////////////////////////////////////////////////

        /*
         *
         * Menu de gauche - listView
         *
         * On ajoute toutes les images.
         *
         * Elles permettreront de choisr des catégories de briques.
         *
         *
         **/
        listView.getItems().addAll(model.imageStorages);
        /*
         *
         * Création de la listView
         *
         *
         * Schéma avec n briques:
         *
         * listView[
         *   hbx1[iv,lb]
         *   ...
         *   hbx1[iv,lb]
         * ]
         *
         * Schéma avec un menu de gauche avec 2 briques:
         *
         * Légende:
         * | 1X1 est l'image 1x1.png
         * | 1x2 est l'image 1x2.png
         *
         * listView[
         *   hbx1[1X1,"    "1x1"]
         *   ...
         *   hbx1[1X2,"    1x2"]
         * ]
         *
         *
         *
         * */
        listView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(ImageStorage imSto, boolean empty) {
                super.updateItem(imSto, empty);
                if (empty) setGraphic(null);
                else {
                    /*
                     * Création de hbx1
                     *
                     * */
                    HBox hbx1 = new HBox();
                    hbx1.setAlignment(Pos.CENTER);

                    /*
                     *
                     * Création de iv
                     *
                     * */

                    ImageView iv = new ImageView();
                    iv.setImage(imSto.getImage());

                    iv.setFitWidth(50);
                    iv.setFitHeight(50);

                    /*
                     *
                     * Création de lb
                     *
                     * */

                    Label lb = new Label("      " + imSto.getText());
                    lb.setStyle("-fx-text-fill: #c25b11;");
                    hbx1.setStyle("-fx-font-size: 10px;");
                    hbx1.getChildren().addAll(
                            iv,
                            lb
                    );
                    setGraphic(hbx1);
                    /*
                     *
                     * Lancement du drag
                     *
                     * */
                    hbx1.setOnMousePressed(e -> {

                        if (e.getButton().equals(MouseButton.PRIMARY)) { //clique gauche
                            model.dropInProgress = true;
                            model.dropSelectionData = imSto;
                            imageOfBrickSelectedInSearchMenu.setFitHeight(50);
                            imageOfBrickSelectedInSearchMenu.setFitWidth(50);
                            imageOfBrickSelectedInSearchMenu.setImage(iv.getImage());
                            imageOfBrickSelectedInSearchMenu.setOpacity(100);
                            imageOfBrickSelectedInSearchMenu.setX(e.getSceneX() - 50);
                            imageOfBrickSelectedInSearchMenu.setY(e.getSceneY() - 50);
                            imageOfBrickSelectedInSearchMenu.setLayoutX(iv.getLayoutX());
                            imageOfBrickSelectedInSearchMenu.setLayoutY(iv.getLayoutY());
                        } else { //clique droit
                            if (model.dropInProgress) {
                                //Si on avait lancé un drop ça l'annule
                                clearBreakSelection();
                                model.dropInProgress = false;
                            }
                            if (model.brickClicked != null && !model.brickClicked.isPiece()) {
                                P3D first = new P3D(model.brickClicked.getVolume().get(0));
                                Color oldColor = model.brickClicked.getColor();
                                boolean plate = model.brickClicked.isPlate();
                                boolean cylindrical = model.brickClicked.isCylindrical();
                                boolean smooth = model.brickClicked.isSmooth();
                                model.brickClicked.remove();
                                if (model.brickClicked != null) model.brickClicked.removeBorder();
                                model.brickClicked = new Brick(Dim.getDimWithText(imSto.getText()), first.getX(), first.getY(), first.getZ(), oldColor);
                                model.brickClicked.setCylindrical(cylindrical, 678);
                                model.brickClicked.setSmooth(smooth, 4567);
                                model.brickClicked.setPlate(plate, 78564);
                                model.brickClicked.setState(State.SHOW_IS_SELECT);
                            }
                        }
                    });
                }
            }
        });


        /*
         * Menu de droite - contentcolors
         *
         *
         * On met à jour les couleurs qui sont utilisé dans le projet.
         *
         * - Si on modifie une couleur du contentcolors, toutes les briques de la même couleur
         * prendront la nouvelle couleur !
         *
         *
         * ::::::::::::::::
         * C'est l'intêret du content color il permet de modifier la couleur de toutes les briques
         * de la même couleur
         * ::::::::::::::::
         * */
        contentColors.setCellFactory(listView -> new ListCell<ColorPick>() {
            @Override
            protected void updateItem(ColorPick cp, boolean empty) {
                super.updateItem(cp, empty);
                if (empty) setGraphic(null);
                else {
                    cp.setPrefSize(10, 10);
                    cp.setOnAction(actionEvent -> {
                        if (cp.getOldValue().equals(cp.getValue())) return;
                        /*
                         * Toutes les briques qui ont comme couleur (oldColor)
                         * vont avoir leur couleur mis à jour en (newColor)
                         *
                         * */
                        for (Brick b : model.bricks)
                            if (b.getColor().equals(cp.getOldValue()))
                                b.setColor(cp.getValue());
                        /*
                         * Suppresion de doublon si il en trouve
                         * */
                        if (hasDuplicate(cp.getValue())) contentColorsRemoveColor(cp.getValue());
                    });
                    setGraphic(cp);
                }
            }
        });

        /*
         * Menu de gauche - colorPicker
         *
         * Si nous changeons la valeur du colorPicker, la prochaine brique que nous placerons ou bien
         * la brique déjà sélectionnée, prendra la nouvelle couleur.
         *
         * */

        colorpicker.setOnAction(event -> {
            /*
             * Si vous aviez déjà sélectionner une brique
             *
             * */
            if (model.brickClicked != null) {
                Color oldColor = model.brickClicked.getColor();
                //pas de changement de couleur
                if (oldColor.equals(colorpicker.getValue())) return;
                /*
                 * La brique prendra la nouvelle couleur du colorpicker(à gauche du menu)/
                 *
                 */
                /*
                 * On ajoute la nouvelle couleur si elle est pas dans contentColors
                 */
                if (notColorInContentColors(colorpicker.getValue()))
                    contentColorAddColor(colorpicker.getValue());
                model.brickClicked.setColor(colorpicker.getValue());
                /*
                 *
                 * On supprime les couleurs qui ne sont plus utilisé par les briques
                 * dans le content colors(à droite de l'écran)
                 *
                 * */
                if (model.getBrickWithColor(oldColor).isEmpty()) contentColorsRemoveColor(oldColor);
            }
        });
        /*

            Prévisualisation de couleur sans avoir confirmé !

         */

        colorpicker.valueProperty().addListener((o, oldVal, newVal) -> {
            if (model.brickClicked != null) {
                for (MinBrick b : model.brickClicked) {
                    b.setMaterial(new PhongMaterial(newVal));
                    b.getCylinder().setMaterial(new PhongMaterial(newVal));
                }
            }
        });

        /*
         * Si la couleur de prévisualisation a pas été confirmé dans le setOnAction de colorpicker
         * Il faut la retirer
         * */
        colorpicker.setOnHidden(event -> {
            if (model.brickClicked != null) {
                if (!model.brickClicked.getColor().equals(colorpicker.getValue()))
                    for (MinBrick b : model.brickClicked) {
                        b.setMaterial(new PhongMaterial(model.brickClicked.getColor()));
                        b.getCylinder().setMaterial(new PhongMaterial(model.brickClicked.getColor()));
                    }
            }
        });

        /*
         * Menu de gauche - listview
         * Temps que la souris rentre pas dans la subScene, l'image sélectionné bouge avec les
         * coordonnées de la souris
         *
         * Le drag continue temps qu'il y a pas de drop ou qu'on a pas appuiyé sur échap.
         *
         * */
        listView.setOnMouseMoved(e -> {
            if (model.dropInProgress) {
                imageOfBrickSelectedInSearchMenu.setX(e.getSceneX() - 50);
                imageOfBrickSelectedInSearchMenu.setY(e.getSceneY() - 50);
            }
        });

        listView.setOnMouseEntered(e -> inSubsceneAndListViewAndSteps = true);
        steps.setOnMouseEntered(e -> inSubsceneAndListViewAndSteps = true);
        listView.setOnMouseExited(e -> inSubsceneAndListViewAndSteps = false);
        steps.setOnMouseExited(e -> inSubsceneAndListViewAndSteps = false);

        anchorPane.setOnMouseMoved(e -> {
            if (model.dropInProgress) {
                imageOfBrickSelectedInSearchMenu.setX(e.getSceneX() - 50);
                imageOfBrickSelectedInSearchMenu.setY(e.getSceneY() - 50);
            }
        });

        /*
         *
         * Si la souris rentre dans la subscene --> drop
         *
         * */
        subScene.setOnMouseEntered(mouseEvent -> {
            inSubsceneAndListViewAndSteps = true;
            searchBar.setDisable(true); //évites que si on appuie sur des touches ça ajoute le texte
            listView.setDisable(true); //évites que si on appuie sur des touches ça bouge légèrement les images
            /*
             * Si le drop vient de commencé
             *
             * On supprime l'image qui bougeait et on insère la brique dans la grille
             *
             * */
            if (model.dropInProgress) {
                clearBreakSelection();
                if (model.brickClicked != null) model.brickClicked.setState(State.NONE, 14);
                String text = model.dropSelectionData.getText();
                Dim dim = Dim.getDimWithText(text);

                if (text.contains("Figurine")) {
                    model.brickClicked = new Figurine(grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2]);
                } else {
                    model.brickClicked = new Brick(dim, grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2], colorpicker.getValue(), (dim.getHeight() == 0.5), false);
                    model.brickClicked.setCylindrical(text.contains("cylinder"), 36788);
                    model.brickClicked.setSmooth(text.contains("smooth"), 36789);
                }
                model.brickClicked.setState(State.FOLLOW_THE_MOUSE);
                model.dropInProgress = false;
            }
        });

        subScene.setOnMousePressed(e -> {
            model.mouseX = e.getX();
            model.mouseY = e.getY();
            //on commence à maintenir clique droit
            if (e.getButton().equals(MouseButton.SECONDARY)) model.rightClickActive = true;
        });
        subScene.setOnMouseReleased(e -> {
            //on arrête de maintenir clique droit
            if (e.getButton().equals(MouseButton.SECONDARY)) model.rightClickActive = false;
            camera.setAngleY(camera.getAngleY());
        });


        /*
         * Temps qu'on a pas cliqué sur la brique, la brique bouge dans la grille.
         *
         * Si il y a pas eu de drop: l'image sélectionné bouge temps qu'on ne rentre pas dans la subscene
         *
         * */
        subScene.setOnMouseMoved(e -> {
            if (model.brickClicked != null && model.brickClicked.getState().equals(State.FOLLOW_THE_MOUSE)) {
                model.brickClicked.moveWhereIsMouseIn(grid);
            }
        });
        subScene.setOnMouseDragged(e -> {
            if (model.rightClickActive) {
                float newAngleX = camera.getAngleY();
                float newAngleY = camera.getAngleX();
                DurationAngle dx = new DurationAngle(newAngleX, 0.001f);
                KeyFrame newX = new KeyFrame(Duration.seconds(dx.getDuration()), new KeyValue(x_axis.angleProperty(), dx.getAngle()));


                camera.setAngleY((camera.getAngleY() - ((float) e.getY() - (float) model.mouseY) / 10));
                camera.timeline.getKeyFrames().add(newX);


                DurationAngle dy = new DurationAngle(newAngleY, 0.001f);
                KeyFrame newY = new KeyFrame(Duration.seconds(dy.getDuration()), new KeyValue(y_axis.angleProperty(), dy.getAngle()));
                camera.setAngleX((camera.getAngleX() + ((float) e.getX() - (float) model.mouseX) / 10));
                camera.timeline.getKeyFrames().add(newY);
                model.mouseX = e.getX();
                model.mouseY = e.getY();

                camera.timeline.play();

            }
        });


        subScene.setOnMouseExited(mouseEvent -> {
            searchBar.setDisable(false);
            listView.setDisable(false);
            inSubsceneAndListViewAndSteps = false;
        });

        /*
         Ajout du zoom quand on utilise la molette
         */
        subScene.setOnScroll(scrollEvent -> camera.zoom(scrollEvent.getDeltaY() / 10));


        anchorPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.CONTROL)) model.ctrlActive = true;
        });


        /*
         * Si on appuie sur échap et que la brique pouvait bougé dans la grille
         * On vient de faire le drop.
         *
         * */
        anchorPane.setOnKeyReleased(keyEvent -> {
            //si on appuie plus sur la touche ctrl
            if (keyEvent.getCode().equals(KeyCode.CONTROL)) model.ctrlActive = false;
            //evites que du texte se tapes quand on bouge la brique avec les touches
            if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                /*
                 * * La brique cesse de suivre la souris
                 */
                if (model.dropInProgress)
                    clearBreakSelection();
            if (model.brickClicked != null) {
                if (model.ctrlActive) {
                    switch (keyEvent.getCode()) {
                        case I -> model.brickClicked.setSmooth(!model.brickClicked.isSmooth(), 230345);
                        case O -> model.brickClicked.setCylindrical(!model.brickClicked.isCylindrical(), 95728);
                        case P -> model.brickClicked.setPlate(!model.brickClicked.isPlate(), 34567);
                        case H -> model.brickClicked.hide(!model.brickClicked.isHide());
                        case D -> model.brickClicked.createClone();
                        case R -> model.brickClicked.rotate();
                        case UP -> model.brickClicked.up();
                        case DOWN -> model.brickClicked.down();
                        case S -> {
                            try {
                                model.saveAllData();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                switch (keyEvent.getCode()) {
                    case Y -> model.brickClicked = new Figurine(grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2]);
                    case LEFT -> model.brickClicked.leftX();
                    case RIGHT -> model.brickClicked.rightX();
                    case UP -> model.brickClicked.rightZ();
                    case DOWN -> model.brickClicked.leftZ();
                    case DELETE -> model.brickClicked.remove();
                }
            }
        });
        /*
         *
         * Mise à jour de la list view avec notre fonction de recherche
         *
         *
         * */
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            listView.getItems().clear();
            listView.getItems().addAll(searchList(searchBar.getText(), model.imageStorages));
        });
        steps.setOnMouseClicked(event -> controller.steps.refresh());


        /*
         *
         *
         * Gestion évenementielle du reste des boutons de l'interface:
         *
         *
         * */
        hide.setOnMousePressed(e -> {
            if (model.brickClicked != null) model.brickClicked.hide(!model.brickClicked.isHide());
            try {
                hideicon.setImage(new ImagePath("hidehover.jpg"));
                hidetext.setFill(Color.web("#42C0FB"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        hide.setOnMouseReleased(e -> {
            try {
                hideicon.setImage(new ImagePath("hide.jpg"));
                hidetext.setFill(Color.web("#edeeef"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });

        clonee.setOnMousePressed(e -> {
            if (model.brickClicked != null) model.brickClicked.createCloneOver();
            try {
                cloneeicon.setImage(new ImagePath("clonehover.jpg"));
                cloneetext.setFill(Color.web("#42C0FB"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        clonee.setOnMouseReleased(e -> {
            try {
                cloneeicon.setImage(new ImagePath("clone.jpg"));
                cloneetext.setFill(Color.web("#edeeef"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        plus.setOnMousePressed(e -> {
            camera.zoom(2.5);
            try {
                plus.setImage(new ImagePath("plusHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        minus.setOnMousePressed(e -> {
            camera.dezoom(2.5);
            try {
                minus.setImage(new ImagePath("minusHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        plus.setOnMouseReleased(e -> {
            try {
                plus.setImage(new ImagePath("plus.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        minus.setOnMouseReleased(e -> {
            try {
                minus.setImage(new ImagePath("minus.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        left.setOnMouseReleased(e -> {
            try {
                left.setImage(new ImagePath("left.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });


        right.setOnMouseReleased(e -> {
            try {
                right.setImage(new ImagePath("right.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        top.setOnMouseReleased(e -> {
            try {
                top.setImage(new ImagePath("top.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        bottom.setOnMouseReleased(e -> {
            try {
                bottom.setImage(new ImagePath("bottom.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        left.setOnMousePressed(e -> {
            camera.left();
            try {
                left.setImage(new ImagePath("leftHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        right.setOnMousePressed(e -> {
            camera.right();
            try {
                right.setImage(new ImagePath("rightHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        top.setOnMousePressed(e -> {
            camera.up();
            try {
                top.setImage(new ImagePath("topHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        bottom.setOnMousePressed(e -> {
            camera.down();
            try {
                bottom.setImage(new ImagePath("bottomHover.png"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        close.setOnMouseClicked(e -> System.exit(0));


        plateCB.setOnAction(e -> {
            listView.getItems().clear();
            String sb = searchBar.getText() + " ";

            //cyl plate smooth
            if (plateCB.isSelected() && cylindricalCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //cyl smooth
            else if (cylindricalCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //plate cyl
            else if (plateCB.isSelected() && cylindricalCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder plate", model.imageStorages));
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //plate smooth
            else if (plateCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
                //plate
            } else if (plateCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "plate", model.imageStorages));
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
                //cylinder
            } else if (cylindricalCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder", model.imageStorages));
                //smooth
            } else if (smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
            } else {
                listView.getItems().addAll(searchList(sb + "", model.imageStorages));
            }
        });

        smoothCB.setOnAction(e -> {
            listView.getItems().clear();
            String sb = searchBar.getText() + " ";
            //cyl plate smooth
            if (plateCB.isSelected() && cylindricalCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //cyl smooth
            else if (cylindricalCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //plate cyl
            else if (plateCB.isSelected() && cylindricalCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder plate", model.imageStorages));
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //plate smooth
            else if (plateCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
                //plate
            } else if (plateCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "plate", model.imageStorages));
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
                //cylinder
            } else if (cylindricalCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder", model.imageStorages));
                //smooth
            } else if (smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
            } else {
                listView.getItems().addAll(searchList(sb + "", model.imageStorages));
            }
        });

        cylindricalCB.setOnAction(e -> {
            listView.getItems().clear();
            String sb = searchBar.getText() + " ";
            //cyl plate smooth
            if (plateCB.isSelected() && cylindricalCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //cyl smooth
            else if (cylindricalCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //plate cyl
            else if (plateCB.isSelected() && cylindricalCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder plate", model.imageStorages));
                listView.getItems().addAll(searchList(sb + "cylinder smooth", model.imageStorages));
            }
            //plate smooth
            else if (plateCB.isSelected() && smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
                //plate
            } else if (plateCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "plate", model.imageStorages));
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
                //cylinder
            } else if (cylindricalCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "cylinder", model.imageStorages));
                //smooth
            } else if (smoothCB.isSelected()) {
                listView.getItems().addAll(searchList(sb + "smooth", model.imageStorages));
            } else {
                listView.getItems().addAll(searchList(sb + "", model.imageStorages));
            }
        });
        newProject.setOnAction(e -> {
            if(model.project != null) {
                try {
                    model.saveAllData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                sendNotif("L'ancien projet a été sauvegardé.");
            }
            reset();
            model.project =null;
        });
        openProject.setOnAction(e -> model.loadData(false));

        saveAs.setOnAction(e -> {
            try {
                File temp = model.selectSaveProject();
                if (temp != null) {
                    model.project = temp;
                    model.saveAllData();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        save.setOnAction(e -> {
            try {
                model.saveAllData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        subScene.setFill(Color.web("#181a1e"));
        subScene.setCamera(camera);
    }


    /**
     * Mise à jour de l'image qui représente la {@link Brick}
     */
    private void clearBreakSelection() {
        imageOfBrickSelectedInSearchMenu.setFitHeight(1);
        imageOfBrickSelectedInSearchMenu.setFitWidth(1);
        imageOfBrickSelectedInSearchMenu.setLayoutX(1046);
        imageOfBrickSelectedInSearchMenu.setLayoutY(648);
        imageOfBrickSelectedInSearchMenu.setOpacity(0);
    }

    /**
     * Retourne une liste (souvent plus petite)
     * <p>
     * composé {@link ImageStorage} satisfaisant aux critères de recherche
     *
     * @param searchWords   Critère de recherche
     * @param listOfStrings La liste entière
     * @return une liste d'imageStorage
     */
    private List<ImageStorage> searchList(String searchWords, List<ImageStorage> listOfStrings) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfStrings.stream().filter(input -> searchWordsArray.stream().allMatch(word ->
                input.getText().toLowerCase().contains(word.toLowerCase()))).collect(Collectors.toList());
    }

    /**
     * Retourne vrai si la {@link Color} est présente dans le contentcolors.
     *
     * @param c la couleur
     * @return un boolean
     */
    public boolean notColorInContentColors(Color c) {
        return numberOfColorPickerWith(c) <= 0;
    }

    public boolean hasDuplicate(Color c) {
        return numberOfColorPickerWith(c) > 1;
    }

    public int numberOfColorPickerWith(Color c) {
        int i = 0;
        for (Object o : contentColors.getItems())
            if (o instanceof ColorPick cp && cp.getValue().equals(c)) i++;
        return i;
    }

    /**
     * Ajoute la {@link Color} aux contentcolors.
     *
     * @param c la couleur
     */
    public void contentColorAddColor(Color c) {
        ColorPick colorPick = new ColorPick();
        colorPick.setValue(c);
        colorPick.setStyle("-fx-background-color: transparent;");
        contentColors.getItems().add(colorPick);
    }

    /**
     * Supprime la {@link Color} aux contentcolors.
     *
     * @param c la couleur
     */
    public void contentColorsRemoveColor(Color c) {
        ColorPicker rem = null;
        for (Object o : contentColors.getItems())
            if (o instanceof ColorPick cp && cp.getValue().equals(c))
                rem = cp;
        contentColors.getItems().remove(rem);
    }

    /**
     * Retourne l'étape ou se trouve la brique
     *
     * @param b la brique
     * @return listView<Brick> qui contient la brique ou rien sinon.
     */
    public ListView<Brick> getStepWhereIsBrick(Brick b) {
        for (Object o : steps.getItems())
            if (o instanceof ListView lv)
                for (Brick br : ((ListView<Brick>) lv).getItems()) if (br.equals(b)) return lv;
        return null;
    }

    public void reset() {
        ArrayList<Brick> removeB = new ArrayList<>();
        for (Object o : steps.getItems()) {
            for (Brick b : ((ListView<Brick>) o).getItems()) {
                removeB.add(b);
            }
        }
        for (Brick b : removeB) {
            Fast.log("remove");
            b.remove();
        }
        Step first = model.instruction.get(0);
        model.instruction.clear();
        model.instruction.add(first);
        model.instruction.get(0).setName("step 0", 45);
    }

    private double xOffsetNW = 0;
    private double yOffsetNW = 0;

    public void sendNotif(String message) {
        model.lastMessageNotif = message;
        notifWindow = new Stage();
        Parent pNotifWindow = null;
        try {
            pNotifWindow = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dsview.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifWindow.setScene(new Scene(pNotifWindow));
        pNotifWindow.setOnMousePressed(event -> {
            xOffsetNW = event.getSceneX();
            yOffsetNW = event.getSceneY();
        });
        pNotifWindow.setOnMouseDragged(event -> {
            notifWindow.setX(event.getScreenX() - xOffsetNW);
            notifWindow.setY(event.getScreenY() - yOffsetNW);
        });
        notifWindow.setTitle("Enregistrement échoué");
        notifWindow.setMinWidth(387);
        notifWindow.setMinHeight(127);
        notifWindow.setMaxWidth(387);
        notifWindow.setMaxHeight(127);
        notifWindow.initStyle(StageStyle.UNDECORATED);
        notifWindow.show();
    }
    
}