package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.pieces.Figurine;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static fr.antoromeochrist.projetlego.Controller.me;
import static fr.antoromeochrist.projetlego.Controller.grid;
import static fr.antoromeochrist.projetlego.Controller.model;

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
    protected boolean hide, delete, cylindrical, smooth,plate;

    /**
     * Etat de la brique
     */
    protected State state;

    /**
     * Permet de rendre cylindrique
     */
    private final Cylinder cylinder;

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
        this(dim, x, y, z, c, false);
    }

    /**
     * Constructeur pour construire une brique
     *
     * @see fr.antoromeochrist.projetlego.utils.ColorPick
     */
    public Brick(Dim dim, double x, double y, double z, Color color, boolean piece) {
        model.bricks.add(this);
        this.state = State.NONE;
        this.hide = false;
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
        for (int i = 0; i < volume.size(); i++) {
            MinBrick minBrick= new MinBrick(this);
            this.add(minBrick);
            me.group.getChildren().add(minBrick);
            me.group.getChildren().add(minBrick.getCylinder());
        }
        this.piece = piece;
        this.nodes = new ArrayList<>();
        cylinder = new Cylinder();
        cylinder.setRadius(Double.valueOf(this.dim.getWidth()) / 2);
        cylinder.setHeight(this.dim.getHeight());
        cylinder.setOpacity(0);
        this.setColor(color);
        Fast.log("cyl3");
        me.group.getChildren().add(cylinder);
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
        me.model.brickClicked=this;
        /*
         * On fait en sorte que si on clique sur ce bouton
         * Qu'on puisse supprimé la brique
         * */
        this.trash.setOnMouseClicked(mouseEvent -> remove());

        if (piece) for (MinBrick mb : this) mb.setOpacity(0);
        me.currentStep.getItems().add(this);
        this.move(getX(), getY(), getZ()); //on bouge la brique si collison
    }


    /**
     * Suppression de la brique
     */
    public void remove() {
        if (!delete) { //evite des bugs si on clique deux fois sur le bouton corbeille(à cause de lag)
            delete = true;
            /*suppression des briques*/
            me.group.getChildren().removeAll(this);
            //suppresion des bordures
            me.group.getChildren().removeAll(border);
            //suppression des cylindes
            for (MinBrick minBrick : this) me.group.getChildren().remove(minBrick.getCylinder());
            me.group.getChildren().remove(cylinder);
            me.group.getChildren().removeAll(nodes);
            //on conserve l'étape ou était la brique
            ListView<Brick> stepWhere = me.getStepWhereIsBrick(this);
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
                if (me.steps.getItems().size() > 1) {
                    //Fast.log("Recherche dans les autres étapes...");
                    //on prend la dernière étape qui n'est pas égale à stepWhere
                    int indexLS = me.steps.getItems().size() - 1;
                    ListView<Brick> lastStep = (ListView<Brick>) me.steps.getItems().get(indexLS);
                    while (lastStep.equals(stepWhere) && indexLS > 0) {
                        indexLS--;
                        lastStep = (ListView<Brick>) me.steps.getItems().get(indexLS);
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
            if (model.getBrickWithColor(beforeDel) == null) {
                //Fast.log("Suppresion du content colors car plus de brique de la couleur de la brique");
                me.contentColorsRemoveColor(beforeDel);
            }
            /*
             * On supprime l'étape d'ou vient la dernière brique.
             * L'étape n'a donc plus de bricque.
             * */
            if (stepWhere.getItems().size() == 0) {
                //on supprime que si c'est pas la première étape évidemment !
                if (me.steps.getItems().indexOf(stepWhere) > 0) {
                    me.steps.getItems().remove(stepWhere);
                }
                //pour que les briques ce remettre dans la dernière étape qui existe
                me.currentStep = (ListView) me.steps.getItems().get(me.steps.getItems().size() - 1);
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
        this.color=color;
        rect.setFill(color);
        for (MinBrick b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }
        if (me.notColorInContentColors(color)) me.contentColorAddColor(color);
        cylinder.setMaterial(new PhongMaterial(color));
    }

    /**
     * Translation ajout de x y z à la coordonnée actuelle de la brique
     *
     * @param x coordonnée
     * @param y coordonnée
     * @param z coordonnée
     */
    public void translate(double x, double y, double z) {
        move(getX() + x, getY() + y, getZ() + z);
    }

    /**
     * Placement de la brique.
     *
     * @param x coordonnée
     * @param y coordonnée
     * @param z coordonnée
     */
    public void move(double x, double y, double z) {
        volume = Volume.createAllVolume(new P3D(x, y, z), this.dim);
        double increment = 0.0;
        while (Volume.volumeIntersection(this, model.bricks)) {
            volume = Volume.createAllVolume(new P3D(x, y - increment, z), this.dim);
            increment+=0.5;
            System.out.println("Collision !");
        }
        for (int i = 0; i < volume.size(); i++) {
            get(i).setTranslateX(volume.get(i).getX());
            get(i).setTranslateZ(volume.get(i).getZ());
            get(i).setTranslateY(volume.get(i).getY());
            get(i).cyl();
        }
        switch (this.dim.getWidth()) {
            case 1 -> {
                cylinder.setTranslateX(getX());
                cylinder.setTranslateZ(getZ());
            }
            case 2 -> {
                cylinder.setTranslateX(getX() + 0.5);
                cylinder.setTranslateZ(getZ() + 0.5);
            }
            case 3 -> {
                cylinder.setTranslateX(getX() + 1);
                cylinder.setTranslateZ(getZ() + 1);
            }
            case 4 -> {
                cylinder.setTranslateX(getX() + 1.5);
                cylinder.setTranslateZ(getZ() + 1.5);
            }
        }
        cylinder.setTranslateY(-0.25+getY());

        this.updateDisplay();
    }

    public void moveWhereIsMouseIn(Grid grid) {
        this.move(grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2]);
    }


    public void createClone() {
        this.setState(State.NONE);
        if (!this.piece) { //la brique a cloné n'est pas une pièce
            model.brickClicked = new Brick(this.dim, this.getX(), this.getY(), this.getZ(), this.getColor());
            model.brickClicked.setState(State.SHOW_IS_SELECT);
            model.brickClicked.setCylindrical(this.cylindrical);
            model.brickClicked.setSmooth(this.smooth);
            model.brickClicked.updateDisplay();
        } else {
            if (pieceType.equals("Figurine")) model.brickClicked = new Figurine(this.getX(), this.getY(), this.getZ());
        }
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
        updateBorder();
        Fast.log(old + " --> " + this.state);
    }

    /**
     * Mettre à jour les bordures en fonction de l'état de la brique
     *
     * @param state état
     */
    public void setState(State state, int i) {
        if(this.state.equals(state)) return;
        State old = this.state;
        this.state = state;
        updateBorder();
        Fast.log(old + " --> " + this.state + "| debug" + i);
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


    protected Cylinder createFirstCylBorder(P3D p, double height) {
        Cylinder c = new Cylinder();
        c.setTranslateX(p.getX());
        c.setTranslateY(p.getY());
        c.setTranslateZ(p.getZ());
        c.setHeight(height);
        c.setRadius(0.01);
        border.add(c);
        return c;
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
            me.group.getChildren().addAll(border);
        }
    }

    public void updateDisplay() {
        //on met à jour l'image qui dit si la brique est caché
        setViewStatusHide(this.hide);

        //la brique est une pièce
        if (piece) {
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
            //on cache les cylindres si la brique est pas lisse
            if (!smooth) for (MinBrick mb : this) mb.getCylinder().setOpacity(0);

            //on cache le gros cylindre si la brique est cylindrique

            if (cylindrical) {
                cylinder.setOpacity(0);
                Fast.log("cyl2");
                //on cache les minBricks si la brique est pas cylindrique
            } else for (MinBrick mb : this) mb.setOpacity(0);
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
                //on cache les cylindres dont on a pas besoin si c'est cylindrique
                switch (this.dim.getWidth()) {
                    case 3 -> {
                        this.get(this.size()/2).getCylinder().setOpacity(0);
                        this.get(2+(this.size()/2)).getCylinder().setOpacity(0);
                        this.get(6+(this.size()/2)).getCylinder().setOpacity(0);
                        this.get(8+(this.size()/2)).getCylinder().setOpacity(0);
                    }
                    case 4 -> {
                        this.get(0+(this.size()/2)).getCylinder().setOpacity(0);
                        this.get(3+(this.size()/2)).getCylinder().setOpacity(0);
                        this.get(12+(this.size()/2)).getCylinder().setOpacity(0);
                        this.get(15+(this.size()/2)).getCylinder().setOpacity(0);
                    }
                }
                //on cache les minbricks
                for (MinBrick mb : this) mb.setOpacity(0);
                //on affiche le cylindre qui fait la taille du block
                cylinder.setOpacity(100);
                Fast.log("cyl4");
            }
            //on affiche les cylindres qui était caché car la brique est plus cylindrique mais cubique
            else {
                switch (this.dim.getWidth()) {
                    case 3 -> {
                        if (this.get(0).getCylinder() != null) this.get(0).getCylinder().setOpacity(100);
                        if (this.get(2).getCylinder() != null) this.get(2).getCylinder().setOpacity(100);
                        if (this.get(6).getCylinder() != null) this.get(6).getCylinder().setOpacity(100);
                        if (this.get(8).getCylinder() != null) this.get(8).getCylinder().setOpacity(100);
                    }
                    case 4 -> {
                        if (this.get(0).getCylinder() != null) this.get(0).getCylinder().setOpacity(100);
                        if (this.get(3).getCylinder() != null) this.get(3).getCylinder().setOpacity(100);
                        if (this.get(12).getCylinder() != null) this.get(12).getCylinder().setOpacity(100);
                        if (this.get(15).getCylinder() != null) this.get(15).getCylinder().setOpacity(100);
                    }
                }
                //on affiche les minbricks
                for (MinBrick mb : this) mb.setOpacity(100);
                //on cache le cylindre qui fait la taille du block
                cylinder.setOpacity(0);
            }
        }

        //on supprime tous les cylindres si la brique doit être lisse
        if (smooth) for (MinBrick mb : this) mb.getCylinder().setOpacity(0);

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
            me.group.getChildren().removeAll(border);
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
        if (dim.getDepth() == dim.getWidth()) return; //ex 1x1x2 or 2x2x3 or....
        Dim newDim = new Dim(this.dim.getWidth(), this.dim.getDepth(), this.dim.getHeight());
        newDim.rotate();
        if (newDim.getWidth() != this.dim.getWidth() || newDim.getDepth() != this.dim.getDepth()) {
            this.removeBorder();
            this.dim.rotate();
            this.volume = Volume.createAllVolume(new P3D(getX(), getY(), getZ()), this.dim);
            for (int i = 0; i < volume.size(); i++) {
                this.get(i).setTranslateX(volume.get(i).getX());
                this.get(i).setTranslateZ(volume.get(i).getZ());
                this.get(i).setTranslateY(volume.get(i).getY());
                this.get(i).cyl();
            }
        }
        this.updateDisplay();
    }

    /**
     * Fonction auxiliaire pour simplifier des conditions
     *
     * @param i        élément à compar
     * @param interval comparaison avec les autres
     * @return vrai si l'élément est dans l'intervalle, non sinon.
     */
    protected boolean isIn(double i, double[] interval) {
        for (double j : interval) if (j == i) return true;
        return false;
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
        this.hide = b;
        updateDisplay();
    }

    public void setCylindrical(boolean b) {
        if (piece) return;
        if (model.brickClicked.getDim().getWidth() != model.brickClicked.getDim().getDepth()) return;
        cylindrical = b;
        updateDisplay();
    }

    public void setSmooth(boolean b) {
        if (piece) return;
        smooth = b;
        updateDisplay();
    }

    public void setPlate(boolean b){
        this.plate = b;
        double d;
        if(plate) {
            d = (this.size()/2)+1;
            this.dim.setHeight(0.5);
            ArrayList<Integer> indexToRemove = new ArrayList<>();
            for(int i = this.size()-1;i > this.size()-d;i--){
                indexToRemove.add(i);
                me.group.getChildren().remove(get(i));
                me.group.getChildren().remove(get(i).getCylinder());
            }
            for(int i : indexToRemove){
                this.remove(i);
            }
            Fast.log("Plate New size: "+this.size());
        }else {
            d = (this.size());
            this.dim.setHeight(this.dim.getOldHeight());
            for(int i = 0;i <d;i++){
                MinBrick mb = new MinBrick(this);
                Fast.log("Color: "+this.getColor());
                mb.setMaterial(new PhongMaterial(this.getColor()));
                this.add(mb);
                me.group.getChildren().add(mb);
                me.group.getChildren().add(mb.getCylinder());
            }
            Fast.log("Note plate New size: "+this.size());
        }
        move(getX(),getY(),getZ());
        updateDisplay();
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

    protected Rotate addRotate(Point3D axis, double angle) {
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        rotate.setPivotZ(0);
        rotate.setAxis(axis);
        return rotate;
    }
}
