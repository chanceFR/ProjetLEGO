package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.pieces.Figurine;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static fr.antoromeochrist.projetlego.Controller.*;

/*
                                 ,...,      .....
                               ...              ,.,
                            ......               .....
                        .....  .. .............. ..  ,...
             .......,.......   ...              ,..    ......,,,.....
           ...              ..   ,....,     ....,   ...              ..
          ...               ..                       ..              ..
      ... ,.. ....     .....,.                       . ....      ...........
  ....     ..               ..      ..........,      .               ..     ....
  ...... .   ....         ...   ..             ...    ....        ...   ..... ..
  ..,     ....     ....         .                ..         ....     ....     ..
  ..,         ....,             ....          .....             .....         ..
  ..,             .....         .    .......     ..        ,....              ..
  ..,                 ,....      ..            ..      ....                   ..
  ..,                     .....     ..........     ....,                      ..
  ..,                          .....          .....*                          ..
  ..,                              .....  .....                               ..
  ..,                                   ...                                   ..
  ..,                                   ..                                    ..
  ..,                                   ..                                    ..
  ..,                                   ..                                    ..
    ....,                               ..                               ....
        ....                            ..                            ...
            ....                        ..                        ....
               ....                     ..                    ....,
                    ....                ..                .....
                        ...,            ..            ....
                           ....         ..         ....
                               ....     ..      ...,
                                   .... .. .....
                                       ....
*/

/**
 * La classe Brick permet de créer des briques en fonction de leur {@link Dim}.
 * <p>
 * Une brique est une liste de {@link MinBrick} généré par la méthode createFromNothing,
 * <p>
 * à partir du {@link Volume} qui lui-même est généré en fonction de la dimension.
 * <p>
 * Remarque: une brique de dimension 1x1 revient à faire une minbrick.
 * <p>
 * Action possible sur la brique:
 * <p>
 * - Changement de couleur dans le dictionnaire du {@link fr.antoromeochrist.projetlego.Model}.
 * <p>
 * - Déplacement la brique avec la gestion des collisions.
 * <p>
 * - On peut cacher la brique et la rétablir.
 * <p>
 * - On peut la rendre plate (brique de 0.5 en hauteur, les briques dans ce cas précis,
 * <p>
 * ressemble à des plaques). Et lui rétablir sa hauteur de base sinon.
 * <p>
 * - On peut gérér son {@link State}, si elle peut bouger(elle suit la position de la souris sur la grille (BORDURE BLEU),
 * ou alors qu'avec les flèches du clavier(et Z Q S D) (BORDURE VIOLETTTE)
 * <p></p>
 * ou bien non (BORDURE VERTE: pour montrer qu'elle reste sélectionné).
 * <p>
 * Ses bordures changent en fonction).
 * <p>
 * - On peut la cloner
 * <p>
 * - On peut la rendre cylindrique (si la brique est carré avec width=depth)
 * <p>
 * - On peut la rendre lisse
 * <p>
 * - On peut la supprimer
 *
 * @see fr.antoromeochrist.projetlego.utils.bricks.Grid
 */
public class Brick extends ArrayList<MinBrick> {

    /**
     * {@link Dim}.
     * <p>
     * Elle permet de régir la dimension de la brique et donc,
     * <p>
     * de générer le {@link Volume} dont elle aura besoin.
     * <p>
     * Notation : largeur x profondeur x hauteur
     * <p>
     * Règle : quand hauteur = 1 (On ne le note pas)
     * <p>
     * Exemples :
     * <p>
     * - 1X2
     * <p>
     * Exemples 2 :
     * <p>
     * - 2x1x2
     */
    protected final Dim dim;
    /**
     * {@link Volume} totale de la brique.
     * <p>
     * Il contient tous les {@link P3D} que va occuper le volume de la brique dans l'espace.
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Dim
     */
    protected Volume volume;

    /**
     * Contient les bordures de la brique
     * <p>
     * La couleur des bordures change en fonction d'etat de la brique.
     */
    protected final ArrayList<Cylinder> border;

    /**
     * Les {@link ImageView} qui seront utilisé et mis à jour dans steps
     *
     * @see fr.antoromeochrist.projetlego.Model
     */
    protected final ImageView hidestatus;
    protected final ImageView trash;

    /**
     * Le {@link Rectangle} qui sera utilisé
     * <p>
     * pour représenter la couleur et mis à jour dans steps
     *
     * @see fr.antoromeochrist.projetlego.Model
     */
    protected final Rectangle rect;
    /**
     * informations sur la brique
     */
    protected boolean hide, delete, cylindrical, smooth, plate;

    /**
     * Etat de la brique
     */
    protected State state;

    /**
     * Permet de rendre cylindrique
     */
    private final ArrayList<Cylinder> cylinders;

    /**
     * Permet de savoir si la brique est utilisée pour pouvoir bouger un modele
     */
    protected boolean piece;

    /**
     * Permet de savoir précisément quel est le type de la pièce
     */
    protected String pieceType;


    protected ArrayList<Node> nodes;

    protected Color color;

    /**
     * Surcharge du constructeur
     *
     * @param dim dimension
     * @param x   coordonnée
     * @param y   coordonnée
     * @param z   coordonnée
     * @param c   couleur
     */
    public Brick(Dim dim, double x, double y, double z, Color c) {
        this(dim, x, y, z, c, false, false);
    }

    /**
     * Constructeur pour construire une brique
     *
     * @see fr.antoromeochrist.projetlego.utils.ColorPick
     */
    public Brick(Dim dim, double x, double y, double z, Color color, boolean plate, boolean piece) {
        //Fast.log("Constructor");
        model.bricks.add(this);
        this.pieceType = "brick";
        this.state = State.NONE;
        this.hide = false;
        this.plate = plate;
        this.cylindrical = false;
        this.smooth = false;
        this.dim = dim;
        this.rect = new Rectangle(0, 0, 10, 10);
        this.rect.setStroke(Color.BLACK);
        this.border = new ArrayList<>();//contiendra les bordures
        this.hidestatus = new ImageView(); //image oeil/oeil barré
        this.hidestatus.setFitHeight(12);
        this.hidestatus.setFitWidth(12);
        this.setViewStatusHide(false); //mettre l'image oeil (non barré)
        this.volume = Volume.createAllVolume(new P3D(x, y, z), this.dim);
        this.delete = false;
        this.cylinders = new ArrayList<>();
        for (int i = 0; i < volume.size(); i++) {
            //Fast.log("> Spawn minbrick");
            MinBrick minBrick = new MinBrick(this);
            this.add(minBrick);
            controller.group.getChildren().add(minBrick);
            controller.group.getChildren().add(minBrick.getCylinder());
        }
        double dc = this.dim.getHeight() * 2;
        //Fast.log("number of cylinders to add :" + dc);
        for (double i = 0.0; i < dc; i += 1) {
            //Fast.log("> Spawn cylinder");
            Cylinder cylinder = new Cylinder();
            cylinder.setRadius((double) this.dim.getWidth() / 2);
            cylinder.setHeight(0.5);
            //Fast.log("cyl opacity 0 #1");
            cylinder.setOpacity(0);
            cylinder.setMaterial(new PhongMaterial(this.color));
            cylinders.add(cylinder);
        }

        controller.group.getChildren().addAll(cylinders);

        this.piece = piece;
        this.nodes = new ArrayList<>();
        this.setColor(color);

        /*
         * On fait en sorte que si on clique sur ce bouton
         * Qu'on puisse caché la brique
         * */
        this.hidestatus.setOnMouseClicked(mouseEvent -> hide(!hide));
        this.trash = new ImageView();
        try {
            this.trash.setImage(new ImagePath("trash.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.trash.setFitWidth(12);
        this.trash.setFitHeight(12);
        model.brickClicked = this;
        /*
         * On fait en sorte que si on clique sur ce bouton
         * Qu'on puisse supprimé la brique
         * */
        this.trash.setOnMouseClicked(mouseEvent -> remove());
        if (this.piece) {
            for (MinBrick mb : this) {
                mb.setOpacity(0);
                mb.getCylinder().setOpacity(0);
            }
        }
        controller.currentStep.getItems().add(this);
        this.move(getX(), getY(), getZ(), 1234); //on bouge la brique si collison
    }

    public void manageState(int click) {
        if (model.brickClicked != null && !model.brickClicked.equals(this)) {
            model.brickClicked.setState(State.NONE);
            this.setState(State.SHOW_IS_SELECT);
        } else {
            if (click >= 2)
                this.setState(State.NONE);
            else
                this.switchState();

        }


    }


    /**
     * Suppression de la brique
     */
    public void remove() {
        if (!delete) { //evite des bugs si on clique deux fois sur le bouton corbeille(à cause de lag)
            delete = true;
            model.numberOfBrickWithColor.replace(this.color,model.numberOfBrickWithColor.get(this.color)-1);
            /*suppression des briques*/
            controller.group.getChildren().removeAll(this);
            //suppresion des bordures
            controller.group.getChildren().removeAll(border);
            //suppression des cylindes
            for (MinBrick minBrick : this) controller.group.getChildren().remove(minBrick.getCylinder());
            controller.group.getChildren().removeAll(cylinders);
            controller.group.getChildren().removeAll(nodes);
            //on conserve l'étape ou était la brique
            ListView<Brick> stepWhere = controller.getStepWhereIsBrick(this);
            //suppresion de la brique dans l'étape
            stepWhere.getItems().remove(this);
            /*
             * On doit attribuer la brickClicked si jamais il existe
             *
             * */

           /*
           Objectif: Quand on supprime la brick sélectionné, on veut attribuer la valeur
                      brickClicked à l'une des briques de la même étape

           Cas: il reste au moins 1 brick dans la même étape après la suppresion
            */
            if (stepWhere.getItems().size() > 0) {
                //Fast.log("Il reste au moins 1 brick dans la même étape après la suppresion");
                //on prend le dernier de l'étape
                model.brickClicked = stepWhere.getItems().get(stepWhere.getItems().size() - 1);
                //Fast.log("---");
                model.brickClicked.setState(State.SHOW_IS_SELECT, 6);
            } else { //Cas: il n'y a plus de brique dans l'étape actuelle après la suppresion
                        /*
                        Il faut trouver une autre brique d'une autre étape si il y en a
                         */
                //Fast.log("Il n'y a plus de brique dans l'étape actuelle après la suppresion");
                if (controller.steps.getItems().size() > 1) {
                    //Fast.log("Recherche dans les autres étapes...");
                    //on prend la dernière étape qui n'est pas égale à stepWhere
                    int indexLS = controller.steps.getItems().size() - 1;
                    ListView<Brick> lastStep = (ListView<Brick>) controller.steps.getItems().get(indexLS);
                    while (lastStep.equals(stepWhere) && indexLS > 0) {
                        indexLS--;
                        lastStep = (ListView<Brick>) controller.steps.getItems().get(indexLS);
                    }

                    //Cas limite:
                    //Si l'étape restante est l'étape 1, il faut vérfier qu'elle n'est pas vide
                    //rappel la seul étape qui a le droit d'être vide c'est l'étape 1
                    if (indexLS == 0) {
                        //Fast.log("Il reste que l'étape 1, regardons si elle est vide");
                        if (lastStep.getItems().size() > 0) {
                            //on prend le dernier élément.
                            model.brickClicked = lastStep.getItems().get(lastStep.getItems().size() - 1);
                            model.brickClicked.setState(State.SHOW_IS_SELECT, 7);
                        }
                    } else { //on sait que forcement l'étape n'est pas vide, sinon elle existerait plus
                        //on prend le dernier élément.
                        //Fast.log("On a trouvé une étape");
                        if (lastStep.getItems().size() > 0) {
                            model.brickClicked = lastStep.getItems().get(lastStep.getItems().size() - 1);
                            model.brickClicked.setState(State.SHOW_IS_SELECT, 8);
                        } else
                            model.brickClicked = null;//il y a plus de brique sur le plateau

                    }
                } else  //on est dans l'étape 1 et il reste aucune brique dedans
                    model.brickClicked = null;
            }
            //suppresion dans le content color si il reste plus de brick de sa couleur
            Color beforeDel = getColor();
            model.bricks.remove(this);
            if (model.numberOfBrickWithColor.get(beforeDel) == 0) {
                //Fast.log("Suppresion du content colors car plus de brique de la couleur de la brique");
                controller.contentColorsRemoveColor(beforeDel);
            }
            /*
             * On supprime l'étape d'ou vient la dernière brique.
             * L'étape n'a donc plus de bricque.
             * */
            if (stepWhere.getItems().size() == 0) {
                //on supprime que si c'est pas la première étape évidemment !
                if (controller.steps.getItems().indexOf(stepWhere) > 0) {
                    controller.steps.getItems().remove(stepWhere);
                }
                //pour que les briques ce remettre dans la dernière étape qui existe
                controller.currentStep = (ListView) controller.steps.getItems().get(controller.steps.getItems().size() - 1);
            } else
                //On ajuste la taille
                stepWhere.setPrefHeight(stepWhere.getItems().size() * 50 + 5);
            this.clear();
        }
    }

    /**
     * Mettre la {@link Color} à la brique
     *
     * @param color la couleur
     */
    public void setColor(Color color) {
        if(this.color != null && this.color.equals(color)) return;

        if(this.color != null){
            model.numberOfBrickWithColor.replace(this.color,model.numberOfBrickWithColor.get(this.color)-1);
        }
        this.color = color;
        if(model.numberOfBrickWithColor.containsKey(this.color)){
            model.numberOfBrickWithColor.replace(this.color,model.numberOfBrickWithColor.get(this.color)+1);
        }else{
            model.numberOfBrickWithColor.put(this.color,1);
        }

        rect.setFill(color);
        for (MinBrick b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }



        if (controller.notColorInContentColors(color)) controller.contentColorAddColor(color);
        for (Cylinder c : cylinders) c.setMaterial(new PhongMaterial(color));
    }

    /**
     * Translation ajout de x y z à la coordonnée actuelle de la brique
     *
     * @param x coordonnée
     * @param y coordonnée
     * @param z coordonnée
     */
    public void translate(double x, double y, double z) {
        move(getX() + x, getY() + y, getZ() + z, 2345);
        this.setState(State.FOLLOW_KEYPRESS);
    }

    /**
     * Placement de la brique.
     *
     * @param x coordonnée
     * @param y coordonnée
     * @param z coordonnée
     */
    public void move(double x, double y, double z, int debug) {
        //Fast.log("move #" + debug);
        //Fast.log("> dimension: " + this.dim);
        volume = Volume.createAllVolume(new P3D(x, y, z), this.dim);
        double increment = 0.0;
        while (Volume.volumeIntersection(this, model.bricks)) {
            volume = Volume.createAllVolume(new P3D(x, y - increment, z), this.dim);
            increment += 0.5;
        }
        //Fast.log("> brick size: " + this.size() + " volume size: " + this.volume.size());
        for (int i = 0; i < volume.size(); i++) {
            get(i).setTranslateX(volume.get(i).getX());
            get(i).setTranslateZ(volume.get(i).getZ());
            get(i).setTranslateY(volume.get(i).getY());
            get(i).cyl();
        }
        double i = 0;
        for (Cylinder c : cylinders) {
            switch (this.dim.getWidth()) {
                case 1 -> {
                    c.setTranslateX(getX());
                    c.setTranslateZ(getZ());
                }
                case 2 -> {
                    c.setTranslateX(getX() + 0.5);
                    c.setTranslateZ(getZ() + 0.5);
                }
                case 3 -> {
                    c.setTranslateX(getX() + 1);
                    c.setTranslateZ(getZ() + 1);
                }
                case 4 -> {
                    c.setTranslateX(getX() + 1.5);
                    c.setTranslateZ(getZ() + 1.5);
                }
            }
            c.setTranslateY(getY() - i);
            i += 0.5;
        }
        if (!piece) {
            this.updateDisplay();
        } else {
            if (this.pieceType != null) {
                if (this.pieceType.equals("Figurine")) {
                    ((Figurine) this).updateNodesLocation();
                }
            }
            updateBorder();
        }
    }

    public void moveWhereIsMouseIn(Grid grid) {
        this.move(grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2], 890);
    }

    public void createCloneOver() {
        this.setState(State.NONE);
        boolean piece = this.piece;
        if (piece) {
            model.brickClicked = new Figurine(getX(), getY(), getZ());
        } else {
            model.brickClicked = new Brick(dim, getX(), getY(), getZ(), this.color, (dim.getHeight() == 0.5), false);
            model.brickClicked.setCylindrical(this.cylindrical, 36788);
            model.brickClicked.setSmooth(this.smooth, 36789);
        }
        model.brickClicked.setState(State.SHOW_IS_SELECT);
        model.brickClicked.hide(this.hide);
    }

    public void createClone() {
        this.setState(State.NONE);
        boolean piece = this.piece;
        if (piece) {
            model.brickClicked = new Figurine(grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2]);
        } else {
            model.brickClicked = new Brick(dim, grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2], this.color, (dim.getHeight() == 0.5), false);
            model.brickClicked.setCylindrical(this.cylindrical, 36788);
            model.brickClicked.setSmooth(this.smooth, 36789);
        }
        model.brickClicked.setState(State.FOLLOW_THE_MOUSE);
        model.brickClicked.hide(this.hide);
    }

    /**
     * Translation de -1 sur l'axe y
     */
    public void up() {
        this.translate(0, -1, 0);
    }

    /**
     * Translation de 1 sur l'axe y
     */
    public void down() {
        this.translate(0, 1, 0);
    }

    /**
     * Translation de -1 sur l'axe x
     */
    public void leftX() {
        this.translate(-1, 0, 0);
    }

    /**
     * Translation de 1 sur l'axe x
     */
    public void rightX() {
        this.translate(1, 0, 0);
    }

    /**
     * Translation de -1 sur l'axe z
     */
    public void leftZ() {
        this.translate(0, 0, -1);
    }

    /**
     * Translation de 1 sur l'axe z
     */
    public void rightZ() {
        this.translate(0, 0, 1);
    }

    /**
     * Obtenir la couleur de la brique à partir du dictionnaire "bricks" du controlleur.
     *
     * @return la couleur
     * @see fr.antoromeochrist.projetlego.Controller
     */
    public Color getColor() {
        return color;
    }

    /**
     * Volume de la brique
     *
     * @return volume
     * @see fr.antoromeochrist.projetlego.utils.bricks.Volume
     */
    public Volume getVolume() {
        return volume;
    }

    /**
     * Affichage de la brique depuis la console
     */
    public String toString() {
        return "[" + getColor() + "|" + dim + "|" + volume + "]";
    }

    /**
     * Coordonnées en x de la première brique du volume
     *
     * @return x
     */
    public double getX() {
        return volume.get(0).getX();
    }

    /**
     * Coordonnées en y de la première brique du volume
     *
     * @return y
     */
    public double getY() {
        return volume.get(0).getY();
    }

    /**
     * Coordonnées en z de la première brique du volume
     *
     * @return z
     */
    public double getZ() {
        return volume.get(0).getZ();
    }


    /**
     * Mettre à jour son état et par la même occasion les bordures en fonction.
     *
     * @param state état
     */
    public void setState(State state) {
        if (this.state.equals(state)) return;
        State old = this.state;
        this.state = state;
        if (this.state.equals(State.NONE)) model.brickClicked = null;
        else model.brickClicked = this;
        updateBorder();
    }

    /**
     * Mettre à jour les bordures en fonction de l'état de la brique
     *
     * @param state état
     */
    public void setState(State state, int i) {
        if (this.state.equals(state)) return;
        State old = this.state;
        this.state = state;
        updateBorder();
    }

    public void switchState() {
        switch (state) {
            case FOLLOW_THE_MOUSE, FOLLOW_KEYPRESS, NONE -> setState(State.SHOW_IS_SELECT);
            case SHOW_IS_SELECT -> setState(State.FOLLOW_THE_MOUSE);
        }
    }


    /**
     * Obtenir la dimension de la brique
     *
     * @return dim
     */
    public Dim getDim() {
        return dim;
    }

    /**
     * Obtenir le rectangle qui représente la couleur de brique dans "steps"
     *
     * @return dim
     * @see fr.antoromeochrist.projetlego.Model
     */
    public Rectangle getRect() {
        return rect;
    }

    protected Cylinder createCylBorder(P3D p, double height) {
        Cylinder c = new Cylinder();
        c.setTranslateX(p.getX());
        c.setTranslateY(p.getY());
        c.setTranslateZ(p.getZ());
        c.setHeight(height);
        c.setRadius(0.01);
        border.add(c);
        return c;
    }

    /**
     * Création de bordure
     */
    protected void createBorder() {
        double mx = (double) this.dim.getWidth() / 2.0;
        double my = this.dim.getHeight() / 2.0;
        double mz = (double) this.dim.getDepth() / 2.0;
        P3D p = new P3D(-0.5 + this.getX(), this.getY() - my + 0.25, this.getZ() - 0.5);
        P3D p2 = new P3D(-0.5 + this.getX() + this.dim.getWidth(), this.getY() - my + 0.25, this.getZ() - 0.5);
        P3D p3 = new P3D(-0.5 + this.getX(), this.getY() - my + 0.25, this.getZ() - 0.5 + this.dim.getDepth());
        P3D p4 = new P3D(-0.5 + this.getX() + this.dim.getWidth(), this.getY() - my + 0.25, this.getZ() - 0.5 + this.dim.getDepth());
        P3D p5 = new P3D(-0.5 + this.getX() + mx, this.getY() + 0.25, this.getZ() - 0.5);
        P3D p6 = new P3D(-0.5 + this.getX() + mx, this.getY() + 0.25, this.getZ() - 0.5 + this.dim.getDepth());
        P3D p7 = new P3D(-0.5 + this.getX() + mx, this.getY() + 0.25 - this.dim.getHeight(), this.getZ() - 0.5);
        P3D p8 = new P3D(-0.5 + this.getX() + mx, this.getY() + 0.25 - this.dim.getHeight(), this.getZ() - 0.5 + this.dim.getDepth());
        createCylBorder(p, this.dim.getHeight());
        createCylBorder(p2, this.dim.getHeight());
        createCylBorder(p3, this.dim.getHeight());
        createCylBorder(p4, this.dim.getHeight());
        Rotate z_90 = addRotate(Rotate.Z_AXIS, 90);
        createCylBorder(p5, this.dim.getWidth()).getTransforms().add(z_90);
        createCylBorder(p6, this.dim.getWidth()).getTransforms().add(z_90);
        createCylBorder(p7, this.dim.getWidth()).getTransforms().add(z_90);
        createCylBorder(p8, this.dim.getWidth()).getTransforms().add(z_90);
        Rotate x_90 = addRotate(Rotate.X_AXIS, 90);
        P3D p9 = new P3D(-0.5 + this.getX(), this.getY() + 0.25, this.getZ() - 0.5 + mz);
        P3D p10 = new P3D(-0.5 + this.getX() + this.dim.getWidth(), this.getY() + 0.25, this.getZ() - 0.5 + mz);
        P3D p11 = new P3D(-0.5 + this.getX(), this.getY() + 0.25 - this.dim.getHeight(), this.getZ() - 0.5 + mz);
        P3D p12 = new P3D(-0.5 + this.getX() + this.dim.getWidth(), this.getY() + 0.25 - this.dim.getHeight(), this.getZ() - 0.5 + mz);
        createCylBorder(p9, this.dim.getDepth()).getTransforms().add(x_90);
        createCylBorder(p10, this.dim.getDepth()).getTransforms().add(x_90);
        createCylBorder(p11, this.dim.getDepth()).getTransforms().add(x_90);
        createCylBorder(p12, this.dim.getDepth()).getTransforms().add(x_90);
    }


    /**
     * Mettre la bordure de la brique avec la couleur
     *
     * @param c couleur
     */
    public void setBorderColor(Color c) {
        removeBorder();
        createBorder();
        for (Cylinder bord : border) {
            bord.setMaterial(new PhongMaterial(c));
        }
        if (!delete) {
            controller.group.getChildren().addAll(border);
        }
    }

    public void updateDisplay() {
        //on met à jour l'image qui dit si la brique est caché
        setViewStatusHide(this.hide);

        //la brique est une pièce
        if (this.piece) {
            if (this.hide)
                for (Node n : nodes) n.setOpacity(0);
            else
                for (Node n : nodes) n.setOpacity(100);
            updateBorder();
            return;
        }

        //on est pas dans un pièce :


        //tous doit être caché
        if (hide) {
            /*
            on cache les cylindres si la brique est pas lisse car si elle est lisse ça veut dire que
            c'est déjà fait pour nous
             */
            if (!smooth) for (MinBrick mb : this) mb.getCylinder().setOpacity(0);

            //on cache le gros cylindre si la brique est cylindrique

            if (cylindrical) {
                for (Cylinder c : cylinders) {
                    c.setOpacity(0);
                }
                //on cache les minBricks si la brique est pas cylindrique
            } else for (MinBrick mb : this) mb.setOpacity(0);
            //taille des bordures
            updateBorder();
            return; //on lit pas plus de code
        }

        //on affiche tous les minbricks qui composent la brique
        for (MinBrick mb : this) mb.setOpacity(100);
        //on affiche tous les cylindres (qui forme les points sur la brique
        for (MinBrick mb : this) mb.getCylinder().setOpacity(100);


        //Si la brique est carré != rectangle
        if (this.dim.getWidth() == this.dim.getDepth()) {
            //brique cylindrique
            if (cylindrical) {
                //on cache les minbricks
                for (MinBrick mb : this) mb.setOpacity(0);
                //on affiche tous les cylindres
                for (Cylinder c : cylinders) {
                    c.setOpacity(100);
                }
                //si c'est plat
                if (plate) cylinders.get(cylinders.size() - 1).setOpacity(0);

                cylinders.get(0).setOpacity(100);

                //on cache les cylindres dont on a pas besoin si c'est cylindrique
                switch (this.dim.getWidth()) {

                    case 2 -> {
                        if (!plate) {
                            try {
                                this.get(0).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }
                            try {
                                this.get(1).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }
                            try {
                                this.get(4).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }
                            try {
                                this.get(5).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }
                        }
                    }

                    case 3 -> {
                        try {
                            this.get(0).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(2).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(12).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(14).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        if (!plate) {
                            try {
                                this.get(3).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(5).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(15).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(17).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                        } else {
                            try {
                                this.get(6).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(8).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                        }
                    }
                    case 4 -> {
                        try {
                            this.get(0).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }
                        try {
                            this.get(3).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(24).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(27).getCylinder().setOpacity(0);
                        } catch (Exception ignored) {
                        }

                        if (!plate) {
                            try {
                                this.get(4).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(7).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(28).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(31).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                        } else {
                            try {
                                this.get(12).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }

                            try {
                                this.get(15).getCylinder().setOpacity(0);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
            }
            //on affiche les cylindres qui était caché car la brique est plus cylindrique mais cubique
            else {
                switch (this.dim.getWidth()) {
                    case 3 -> {
                        try {
                            this.get(0).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }
                        try {
                            this.get(2).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }

                        try {
                            this.get(12).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }

                        try {
                            this.get(14).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }

                        try {
                            this.get(3).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }
                        try {
                            this.get(5).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }
                        try {
                            this.get(15).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }
                        try {
                            this.get(17).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {

                        }

                    }
                    case 4 -> {
                        try {
                            this.get(0).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(3).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(24).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(27).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(4).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(7).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(28).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                        try {
                            this.get(31).getCylinder().setOpacity(100);
                        } catch (Exception ignored) {
                        }

                    }
                }
                //on affiche les minbricks
                for (MinBrick mb : this) mb.setOpacity(100);
                //on cache le cylindre qui fait la taille du block
                for (Cylinder c : cylinders) {
                    c.setOpacity(0);
                }
            }
        }
        //on supprime tous les cylindres si la brique lisse et plate
        if (smooth && plate) for (MinBrick mb : this) mb.getCylinder().setOpacity(0);
        //on met à jour les bordures
        updateBorder();
    }


    public void updateBorder() {
        switch (state) {
            case SHOW_IS_SELECT -> setBorderColor(Color.web("#7CFC00"));
            case FOLLOW_THE_MOUSE -> setBorderColor(Color.web("#42C0FB"));
            case FOLLOW_KEYPRESS -> setBorderColor(Color.web("#DA70D6"));
            default -> {
                if (hide)
                    setBorderColor(Color.web("#808080"));
                else
                    removeBorder();
            }
        }
    }

    /**
     * Supprimer la bordure
     */
    public void removeBorder() {
        if (!border.isEmpty()) {
            controller.group.getChildren().removeAll(border);
            border.clear();
        }
    }

    /**
     * Obtenir l'image qui est utilisé pour montrer si la brique est caché ou non depuis "steps"
     *
     * @see fr.antoromeochrist.projetlego.Controller
     */
    public ImageView getHidestatus() {
        return hidestatus;
    }

    /**
     * Mettre à jour l'image qui permet de voir depuis "steps"
     * <p>
     * si l'image est caché
     *
     * @param b booléen
     */
    protected void setViewStatusHide(boolean b) {
        try {
            if (b) this.hidestatus.setImage(new ImagePath("noview.png"));
            else this.hidestatus.setImage(new ImagePath("view.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtenir l'image qui est utilisé pour pouvoir supprimer la brique dans "steps"
     *
     * @see fr.antoromeochrist.projetlego.Controller
     */
    public ImageView getTrash() {
        return trash;
    }

    /**
     * Permet d'appliquer une rotation de 90 degrée sur une brique
     */
    public void rotate() {
        if (this.piece) return;
        if (this.dim.getDepth() == this.dim.getWidth()) return; //ex 1x1x2 or 2x2x3 or....
        this.removeBorder();
        this.dim.rotate();
        this.volume = Volume.createAllVolume(new P3D(getX(), getY(), getZ()), this.dim);
        for (int i = 0; i < volume.size(); i++) {
            this.get(i).setTranslateX(volume.get(i).getX());
            this.get(i).setTranslateZ(volume.get(i).getZ());
            this.get(i).setTranslateY(volume.get(i).getY());
            this.get(i).cyl();
        }
        controller.steps.refresh();//on refresh les dim affiché dans les labels du menu
        this.updateDisplay();
    }

    /**
     * Obtenir l'état de la brique
     */
    public State getState() {
        return state;
    }


    /**
     * Faut il caché la brique ?
     *
     * @param b booléen
     */
    public void hide(boolean b) {
        if (this.hide == b) return;
        this.hide = b;
        updateDisplay();
    }

    public void setCylindrical(boolean b, int debug) {
        if (this.piece) return;
        if (this.cylindrical == b) return;
        //Fast.log("setCylindrical #" + debug);
        if (this.dim.getWidth() != this.dim.getDepth()) return;
        cylindrical = b;
        updateDisplay();
    }

    public void setSmooth(boolean b, int debug) {
        if (this.piece) return;
        if (this.smooth == b) return;
        if (this.dim.getHeight() != 0.5 && b) return;
        //Fast.log("setPlate #" + debug);
        smooth = b;
        updateDisplay();
    }

    public void setPlate(boolean b, int debug) {
        if (this.piece) return;
        if (this.getDim().getHeight() > 1) return;
        if (this.plate == b) return;
        //Fast.log("setPlate #" + debug);
        //Fast.log("> minBrick size before plate operation :" + this.size());
        //Fast.log("> cylinder size before plate operation :" + cylinders.size());
        this.plate = b;
        double d;
        if (plate) {
            d = (this.size() / 2.0) + 1;
            this.dim.setHeight(0.5);
            ArrayList<Integer> indexToRemove = new ArrayList<>();
            for (int i = this.size() - 1; i > this.size() - d; i--) {
                indexToRemove.add(i);
                controller.group.getChildren().remove(get(i));
                controller.group.getChildren().remove(get(i).getCylinder());
            }
            for (int i : indexToRemove) {
                //Fast.log("> remove minBrick");
                this.remove(i);
            }
            //suprimer les cylindres sauf le premier
            //Fast.log("> save the first cylinders");
            ArrayList<Integer> indexCylToRemove = new ArrayList<>();
            for (int i = 1; i < this.cylinders.size(); i++) {
                controller.group.getChildren().remove(cylinders.get(i));
                indexCylToRemove.add(i);
            }
            for (int i = indexCylToRemove.size() - 1; i > 0; i--) {
                //Fast.log("> remove cylinder");
                cylinders.remove(i);
            }
        } else {
            d = (this.size());
            this.dim.setHeight(1);
            for (int i = 0; i < d; i++) {
                MinBrick mb = new MinBrick(this);
                //Fast.log("> Spawn minbrick #7459");
                mb.setMaterial(new PhongMaterial(this.getColor()));
                if (hide) {
                    mb.getCylinder().setOpacity(0);
                    mb.setOpacity(0);
                }
                this.add(mb);
                controller.group.getChildren().add(mb);
                controller.group.getChildren().add(mb.getCylinder());
            }
            //mettre à jour les cylindres

            //supprime tous les cylindres
            for (Cylinder c : cylinders) {
                //Fast.log("> remove cylinder");
                controller.group.getChildren().remove(c);
            }
            cylinders.clear();
            //ajouter tous les cylindres dont on a
            double dc = this.dim.getHeight() * 2;
            //Fast.log("number of cylinders to add :" + dc);
            for (double i = 0.0; i < dc; i += 1) {
                //Fast.log("> Spawn cylinder");
                Cylinder cylinder = new Cylinder();
                cylinder.setRadius((double) this.dim.getWidth() / 2);
                cylinder.setHeight(0.5);
                cylinder.setOpacity(0);
                //Fast.log("cyl opacity 0 #2");
                cylinder.setMaterial(new PhongMaterial(this.color));
                cylinders.add(cylinder);
            }
            controller.group.getChildren().addAll(cylinders);
        }
        //Fast.log("> size after plate operation :" + this.size());
        //Fast.log("> cylinder size before plate operation :" + cylinders.size());
        move(getX(), getY(), getZ(), 666);
        controller.steps.refresh();//on refrest la dim dans les labels du menu.
    }

    public boolean isHide() {
        return hide;
    }

    public boolean isCylindrical() {
        return cylindrical;
    }

    public boolean isSmooth() {
        return smooth;
    }


    public boolean isPiece() {
        return piece;
    }

    public boolean isPlate() {
        return plate;
    }

    protected void setPieceType(String type) {
        this.pieceType = type;
    }

    public String getPieceType() {
        return pieceType;
    }

    public static Rotate addRotate(Point3D axis, double angle) {
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        rotate.setPivotZ(0);
        rotate.setAxis(axis);
        return rotate;
    }
}
