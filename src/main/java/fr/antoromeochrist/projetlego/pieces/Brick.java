package fr.antoromeochrist.projetlego.pieces;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import fr.antoromeochrist.projetlego.utils.bricks.MinBrick;
import fr.antoromeochrist.projetlego.utils.bricks.State;
import fr.antoromeochrist.projetlego.utils.bricks.Volume;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
 * - On peut gérér son {@link State}, si elle peut bouger(en fonction de la souris,
 * ou alors qu'avec les flèches du clavier(et Z Q S D) ou bien non.
 * <p>
 * Ses bordures changent en fonction).
 * <p>
 * - On peut la cloner
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
    private final Dim dim;
    /**
     * {@link Volume} totale de la brique.
     * <p>
     * Il contient tous les {@link P3D} que va occuper le volume de la brique dans l'espace.
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Dim
     */
    private Volume volume;

    /**
     * Contient les bordures de la brique
     * <p>
     * La couleur des bordures change en fonction d'etat de la brique.
     */
    private final ArrayList<Cylinder> border;

    /**
     * Les {@link ImageView} qui seront utilisé et mis à jour dans steps
     *
     * @see fr.antoromeochrist.projetlego.Model
     */
    private final ImageView hidestatus;
    private final ImageView trash;

    /**
     * Le {@link Rectangle} qui sera utilisé
     * <p>
     * pour représenter la couleur et mis à jour dans steps
     *
     * @see fr.antoromeochrist.projetlego.Model
     */
    private final Rectangle rect;
    /**
     * informations sur la brique
     */
    private boolean hide, delete, plate;

    /**
     * Etat de la brique
     */
    private State state;

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
     * @param dim   {@link Dim} de la brique
     *              <p>
     * @param x     coordonnée
     *              <p>
     * @param z     coordonnée
     *              <p>
     * @param y     coordonnée
     *              <p>
     * @param c     {@link Color}
     * @param plate est t'elle plate ?
     * @see fr.antoromeochrist.projetlego.utils.ColorPick
     */
    public Brick(Dim dim, double x, double z, double y, Color c, boolean plate) {
        this.state = State.NONE;
        this.hide = false;
        this.plate = plate;
        this.dim = dim;
        this.rect = new Rectangle(0, 0, 10, 10);
        this.rect.setStroke(Color.BLACK);
        this.border = new ArrayList<>();//contiendra les bordures
        this.hidestatus = new ImageView(); //image oeil/oeil barré
        this.hidestatus.setFitHeight(12);
        this.hidestatus.setFitWidth(12);
        this.setViewStatusHide(false); //mettre l'image oeil (non barré)
        this.volume = Volume.createAllVolume(new P3D(x, y, z), this.dim);
        this.createFromNothing(true);
        this.setColor(c);
        this.move(getX(), getY(), getZ()); //on bouge la brique si collison
        /*
         * On fait en sorte que si on clique sur ce bouton
         * Qu'on puisse caché la brique
         * */
        this.hidestatus.setOnMouseClicked(mouseEvent -> hide(isNotHide()));
        this.trash = new ImageView();
        try {
            this.trash.setImage(new ImagePath("trash.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.trash.setFitWidth(12);
        this.trash.setFitHeight(12);
        /*
         * On fait en sorte que si on clique sur ce bouton
         * Qu'on puisse supprimé la brique
         * */
        this.trash.setOnMouseClicked(mouseEvent -> remove());

        /*
         *
         * Les composantes de la briques si elles sont cliqué on peut mettre la bordure.
         * Permet de mettre à jour dans grids les coords de la souris pour la superposition des
         * bricks.
         *
         * C'est expliqué dans le controller, lors de la déclaration de la variable grid.
         *
         *
         * */
        for (MinBrick minBrick : this) {
            /*
             * Permet la SuperPosition des briques
             *
             **/
            minBrick.setOnMouseEntered(mouseEvent -> {
                if (!Controller.model.brickClicked.equals(this)) {
                    if (isPlate())
                        if (Controller.model.brickClicked.isPlate()) {// Les deux sont plates
                            Fast.log("cas 1");
                            Controller.grid.setCoors(minBrick.getTranslateX(), minBrick.getTranslateY()-0.5, minBrick.getTranslateZ());
                        } else { //brickClicked est pas plate et l'autre est plate
                            Fast.log("cas 2");
                            Controller.grid.setCoors(minBrick.getTranslateX(), minBrick.getTranslateY(), minBrick.getTranslateZ()); //brickclicked est pas plate et le bloc touché est plate
                        }
                    else if (Controller.model.brickClicked.isPlate()) { //brickClicked est plate mais pas l'autre
                        Fast.log("cas 3");
                        Controller.grid.setCoors(minBrick.getTranslateX(), minBrick.getTranslateY() - 0.75, minBrick.getTranslateZ());
                    } else {
                        Fast.log("cas 4");
                        Controller.grid.setCoors(minBrick); //les 2 sont pas plates
                    }
                }
            });
            /*
             * On vient de cliquer sur la brique
             * Permet de d'afficher les bordures
             *
             **/
            minBrick.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) { //click gauche
                    if (Controller.model.brickClicked.equals(this)) {
                        if (!getState().equals(State.SHOW_IS_SELECT))
                            this.setState(State.SHOW_IS_SELECT, 134);
                        else
                            this.setState(State.FOLLOW_THE_MOUSE);
                    } else {
                        Brick old = Controller.model.brickClicked;
                        old.setState(State.NONE, 555);
                        if (old.hide)
                            /*Si on clique sur une autre brique et quel'ancienne brique selectionné est invisible
                              elle doit garder la bordure #808080
                            */
                            old.setBorderColor(Color.web("#808080"));
                        Controller.model.brickClicked = this;
                        this.setState(State.SHOW_IS_SELECT, 4);
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY))
                    this.rotate();
            });
            minBrick.getCylinder().setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) { //click gauche
                    if (Controller.model.brickClicked.equals(this)) {
                        if (!getState().equals(State.SHOW_IS_SELECT))
                            this.setState(State.SHOW_IS_SELECT, 134);
                        else
                            this.setState(State.FOLLOW_THE_MOUSE);
                    } else {
                        Brick old = Controller.model.brickClicked;
                        old.setState(State.NONE, 555);
                        if (old.hide)
                            /*Si on clique sur une autre brique et quel'ancienne brique selectionné est invisible
                              elle doit garder la bordure #808080
                            */
                            old.setBorderColor(Color.web("#808080"));
                        Controller.model.brickClicked = this;
                        this.setState(State.SHOW_IS_SELECT, 4);
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY))
                    this.rotate();
            });

            minBrick.cyl();
        }
        Controller.me.currentStep.getItems().add(this);
    }

    /**
     * Permet de recreer graphiquement une brique
     *
     * @param fromNothing si on l'avait déjà créé ou non
     */
    private void createFromNothing(boolean fromNothing) {
        delete = false;
        for (int i = 0; i < volume.size(); i++) {
            MinBrick minBrick;
            if (fromNothing) {
                minBrick = new MinBrick();
                this.add(minBrick);
                Controller.me.group.getChildren().add(minBrick);
                Controller.me.group.getChildren().add(minBrick.getCylinder());
            } else {
                minBrick = this.get(i);
            }
            minBrick.setWidth(1);
            minBrick.setDepth(1);
            minBrick.setTranslateX(volume.get(i).getX());
            minBrick.setTranslateZ(volume.get(i).getZ());
            minBrick.setTranslateY(volume.get(i).getY());
            if (plate) minBrick.setHeight(0.5);
            else{
                minBrick.setHeight(1);
            }

            minBrick.setTranslateY(volume.get(i).getY());
            minBrick.cyl();


        }
    }

    /**
     * Faut il caché la brique ?
     *
     * @param b booléen
     */
    public void hide(boolean b) {
        this.hide = b;
        setViewStatusHide(b);
        if (b) {
            for (MinBrick minBrick : this) {
                minBrick.setOpacity(0);
                minBrick.cyl();
            }
            setBorderColor(Color.web("#808080"));
        } else {
            for (MinBrick minBrick : this) {
                minBrick.setOpacity(100);
                minBrick.cyl();
            }
            removeBorder();
            setState(this.state, 5); //avoir à nouveau les bordures correspondant à son état
        }
    }

    /**
     * Suppression de la brique
     */
    public void remove() {
        if (!delete) { //evite des bugs si on clique deux fois sur le bouton corbeille(à cause de lag)
            delete = true;
            /*suppression des briques*/
            Controller.me.group.getChildren().removeAll(this);
            //suppresion des bordures
            Controller.me.group.getChildren().removeAll(border);
            //suppression des cylindes
            for (MinBrick minBrick : this) Controller.me.group.getChildren().remove(minBrick.getCylinder());
            //on conserve l'étape ou était la brique
            ListView<Brick> stepWhere = Controller.me.getStepWhereIsBrick(this);
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
                Controller.model.brickClicked = stepWhere.getItems().get(stepWhere.getItems().size() - 1);
                //Fast.log("---");
                Controller.model.brickClicked.setState(State.SHOW_IS_SELECT, 6);
            } else { //Cas: il n'y a plus de brique dans l'étape actuelle après la suppresion
                        /*
                        Il faut trouver une autre brique d'une autre étape si il y en a
                         */
                //Fast.log("Il n'y a plus de brique dans l'étape actuelle après la suppresion");
                if (Controller.me.steps.getItems().size() > 1) {
                    //Fast.log("Recherche dans les autres étapes...");
                    //on prend la dernière étape qui n'est pas égale à stepWhere
                    int indexLS = Controller.me.steps.getItems().size() - 1;
                    ListView<Brick> lastStep = (ListView<Brick>) Controller.me.steps.getItems().get(indexLS);
                    while (lastStep.equals(stepWhere) && indexLS > 0) {
                        indexLS--;
                        lastStep = (ListView<Brick>) Controller.me.steps.getItems().get(indexLS);
                    }

                    //Cas limite:
                    //Si l'étape restante est l'étape 1, il faut vérfier qu'elle n'est pas vide
                    //rappel la seul étape qui a le droit d'être vide c'est l'étape 1
                    if (indexLS == 0) {
                        //Fast.log("Il reste que l'étape 1, regardons si elle est vide");
                        if (lastStep.getItems().size() > 0) {
                            //on prend le dernier élément.
                            Controller.model.brickClicked = lastStep.getItems().get(lastStep.getItems().size() - 1);
                            Controller.model.brickClicked.setState(State.SHOW_IS_SELECT, 7);
                        }
                    } else { //on sait que forcement l'étape n'est pas vide, sinon elle existerait plus
                        //on prend le dernier élément.
                        //Fast.log("On a trouvé une étape");
                        if (lastStep.getItems().size() > 0) {
                            Controller.model.brickClicked = lastStep.getItems().get(lastStep.getItems().size() - 1);
                            Controller.model.brickClicked.setState(State.SHOW_IS_SELECT, 8);
                        } else
                            Controller.model.brickClicked = null;//il y a plus de brique sur le plateau

                    }
                } else  //on est dans l'étape 1 et il reste aucune brique dedans
                    Controller.model.brickClicked = null;
            }
            //suppresion dans le content color si il reste plus de brick de sa couleur
            Color beforeDel = getColor();
            Controller.model.bricks.remove(this);
            if (Controller.model.getBrickWithColor(beforeDel) == null) {
                //Fast.log("Suppresion du content colors car plus de brique de la couleur de la brique");
                Controller.me.contentColorsRemoveColor(beforeDel);
            }
            /*
             * On supprime l'étape d'ou vient la dernière brique.
             * L'étape n'a donc plus de bricque.
             * */
            if (stepWhere.getItems().size() == 0) {
                //on supprime que si c'est pas la première étape évidemment !
                if (Controller.me.steps.getItems().indexOf(stepWhere) > 0) {
                    Controller.me.steps.getItems().remove(stepWhere);
                }
                //pour que les briques ce remettre dans la dernière étape qui existe
                Controller.me.currentStep = (ListView) Controller.me.steps.getItems().get(Controller.me.steps.getItems().size() - 1);
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
        brickHaveColorInDictionnary(color);
        rect.setFill(color);
        Fast.log("setColor param is null ? " + (color == null));
        for (MinBrick b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }
        if (Controller.me.notColorInContentColors(color)) Controller.me.contentColorAddColor(color);
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
        if (plate)
            volume = Volume.createAllVolume(new P3D(x, y, z), new Dim(this.dim.getWidth(), this.dim.getDepth(), 1));
        else volume = Volume.createAllVolume(new P3D(x, y, z), this.dim);
        int increment = 0;
        while (Volume.volumeIntersection(this, Controller.model.bricks.keySet())) {
            volume = Volume.createAllVolume(new P3D(x, y - increment, z), this.dim);
            increment++;
        }
        for (int i = 0; i < volume.size(); i++) {
            get(i).setTranslateX(volume.get(i).getX());
            get(i).setTranslateZ(volume.get(i).getZ());
            if (plate) {
                get(i).setHeight(0.5);
                get(i).setTranslateY(volume.get(i).getY());
            } else {
                get(i).setHeight(1);
                get(i).setTranslateY(volume.get(i).getY());
            }
            get(i).cyl();
        }
        this.updateBorder();
    }

    public void createClone() {
        this.setState(State.NONE);
        Controller.model.brickClicked = new Brick(this.dim, getX(), getZ(), getY() - 1, getColor());
        Controller.model.brickClicked.setState(State.SHOW_IS_SELECT);
    }

    /**
     * Translation de -1 sur l'axe y
     */
    public void up() {
        if (state.equals(State.FOLLOW_KEYPRESS)) this.translate(0, -1, 0);
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
        return Controller.model.bricks.get(this);
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
        switch (state) {
            case SHOW_IS_SELECT -> setBorderColor(Color.web("#7CFC00"));
            case FOLLOW_THE_MOUSE -> setBorderColor(Color.web("#42C0FB"));
            case FOLLOW_KEYPRESS -> setBorderColor(Color.web("#DA70D6"));
            default -> removeBorder();
        }
        State old = this.state;
        this.state = state;
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

    /**
     * La brique est t'elle pas caché ?
     */
    public boolean isNotHide() {
        return !hide;
    }

    private Cylinder createFirstCylBorder(P3D p, double height) {
        Cylinder c = new Cylinder();
        c.setTranslateX(p.getX());
        c.setTranslateY(p.getY());
        c.setTranslateZ(p.getZ());
        if (plate) c.setHeight(height * 0.5);
        else c.setHeight(height);
        c.setRadius(0.01);
        border.add(c);
        return c;
    }

    private Cylinder createCylBorder(P3D p, double height) {
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
    private void createBorder() {
        Rotate rotateX = new Rotate();
        rotateX.setAngle(90);
        rotateX.setPivotX(0);
        rotateX.setPivotY(0);
        rotateX.setAxis(Rotate.X_AXIS);

        Rotate rotateZ = new Rotate();
        rotateZ.setAngle(90);
        rotateZ.setPivotX(0);
        rotateZ.setPivotY(0);
        rotateZ.setAxis(Rotate.Z_AXIS);
        if (volume.size() > 1 && dim.getHeight() == 1) {
            createFirstCylBorder(volume.get(0).add(-0.5, 0, -0.5), this.dim.getHeight());
            createFirstCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), 0, -0.5), this.dim.getHeight());
            createFirstCylBorder(volume.get(0).add(-0.5, 0, -0.5 + this.getDim().getDepth()), this.dim.getHeight());
            createFirstCylBorder(volume.get(volume.size() - 1).add(0.5, 0, 0.5), this.dim.getHeight());
        } else if (dim.getWidth() == 1 && dim.getDepth() == 1 && dim.getHeight() == 4) {
            createFirstCylBorder(volume.get(0).add(-0.5, 1.5, -0.5), this.dim.getHeight());
            createFirstCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), 1.5, -0.5), this.dim.getHeight());
            createFirstCylBorder(volume.get(0).add(-0.5, 1.5, -0.5 + this.getDim().getDepth()), this.dim.getHeight());
            createFirstCylBorder(volume.get(volume.size() - 1).add(0.5, -1.5, 0.5), this.dim.getHeight());
        } else if (dim.getWidth() == 1 && dim.getDepth() == 1 && dim.getHeight() == 2) {
            createFirstCylBorder(volume.get(0).add(-0.5, 0.5, -0.5), this.dim.getHeight());
            createFirstCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getHeight());
            createFirstCylBorder(volume.get(0).add(-0.5, 0.5, -0.5 + this.getDim().getDepth()), this.dim.getHeight());
            createFirstCylBorder(volume.get(volume.size() - 1).add(0.5, -0.5, 0.5), this.dim.getHeight());
        } else {
            createFirstCylBorder(volume.get(0).add(-0.5, 0, -0.5), 1);
            createFirstCylBorder(volume.get(0).add(-0.5 + 1, 0, -0.5), 1);
            createFirstCylBorder(volume.get(0).add(-0.5, 0, -0.5 + 1), 1);
            createFirstCylBorder(volume.get(0).add(0.5, 0, 0.5), 1);
        }


        if (dim.getDepth() == 2 && dim.getWidth() == 2 && dim.getHeight() == 1) {
            createCylBorder(volume.get(0).add(0.5, -0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            createCylBorder(volume.get(0).add(0.5, 0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(-0.5, -0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);

        } else if (dim.getHeight() == 1) {
            if (dim.getWidth() == 1) {
                createCylBorder(volume.get(0).add(0, -0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-1 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(0, -0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(0, 0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);

            } else if (dim.getWidth() == 2) {
                createCylBorder(volume.get(0).add(0.5, -0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-1.5 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, -0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            } else if (dim.getWidth() == 4) {
                createCylBorder(volume.get(0).add(1.5, -0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-2.5 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1.5, -0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            } else {
                createCylBorder(volume.get(0).add(1, -0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-2 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1, -0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1, 0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            }
        } else {
            createCylBorder(volume.get(0).add(0, -0.5, -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            createCylBorder(volume.get(0).add(-1 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), -0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(0, 0.5 - this.dim.getHeight(), 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(0, 0.5, 0.5), this.dim.getWidth()).getTransforms().add(rotateZ);
        }


        if (dim.getDepth() == 2 && dim.getWidth() == 2 && dim.getHeight() == 1) { //2x2
            createCylBorder(volume.get(0).add(-0.5, -0.5, 0.5), this.dim.getDepth()).getTransforms().add(rotateX);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0.5), this.dim.getDepth()).getTransforms().add(rotateX);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -0.5), this.dim.getDepth()).getTransforms().add(rotateX);
            createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -0.5), this.dim.getDepth()).getTransforms().add(rotateX);
        } else if (dim.getHeight() == 1) {
            if (dim.getWidth() == 1) {
                if (dim.getDepth() == 2) { //1x2
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 0.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -0.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -0.5), this.dim.getDepth()).getTransforms().add(rotateX);

                } else if (dim.getDepth() == 3) { //1x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -1), this.dim.getDepth()).getTransforms().add(rotateX);
                } else if (dim.getDepth() == 4) { //1x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                } else { //1x1
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 0), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), 0), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0), this.dim.getDepth()).getTransforms().add(rotateX);
                }
            } else if (dim.getWidth() == 2) {
                if (dim.getDepth() == 3) { //2x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -1), this.dim.getDepth()).getTransforms().add(rotateX);
                } else { //2x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                }
            } else if (dim.getWidth() == 3) {
                if (dim.getDepth() == 3) {  //3x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-2.5, 0.5, -1), this.dim.getDepth()).getTransforms().add(rotateX);
                } else { //3x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-2.5, 0.5, -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                }
            } else { //4x4
                createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(-3.5, 0.5, -1.5), this.dim.getDepth()).getTransforms().add(rotateX);
            }
        } else {
            if (dim.getWidth() == 1 && dim.getDepth() == 1) { //1x1x?
                createCylBorder(volume.get(0).add(-0.5, -0.5, 0), this.dim.getDepth()).getTransforms().add(rotateX);
                createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0), this.dim.getDepth()).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), 0), this.dim.getDepth()).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0), this.dim.getDepth()).getTransforms().add(rotateX);
            }
        }
        if (isIn(dim.getWidth(), new double[]{2, 3, 4}) && dim.getDepth() == 1) {
            border.get(8).setTranslateZ(border.get(8).getTranslateZ() - 1.5);
            border.get(9).setTranslateZ(border.get(9).getTranslateZ() - 1.5);
            border.get(10).setTranslateZ(border.get(10).getTranslateZ() + 1.5);
            border.get(11).setTranslateZ(border.get(11).getTranslateZ() + 1.5);
        }
        if (isIn(dim.getWidth(), new double[]{3, 4}) && dim.getDepth() == 2) {
            border.get(8).setTranslateZ(border.get(8).getTranslateZ() - 1);
            border.get(9).setTranslateZ(border.get(9).getTranslateZ() - 1);
            border.get(10).setTranslateZ(border.get(10).getTranslateZ() + 1);
            border.get(11).setTranslateZ(border.get(11).getTranslateZ() + 1);
        }
        if (dim.getWidth() == 4 && dim.getDepth() == 3) {
            border.get(8).setTranslateZ(border.get(8).getTranslateZ() - 0.5);
            border.get(9).setTranslateZ(border.get(9).getTranslateZ() - 0.5);
            border.get(10).setTranslateZ(border.get(10).getTranslateZ() + 0.5);
            border.get(11).setTranslateZ(border.get(11).getTranslateZ() + 0.5);
        }
        Brick b = this;
        for (Cylinder c : border) {
            c.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) { //click gauche
                    if (Controller.model.brickClicked.equals(b)) {
                        if (!getState().equals(State.SHOW_IS_SELECT))
                            b.setState(State.SHOW_IS_SELECT, 134);
                        else
                            b.setState(State.FOLLOW_THE_MOUSE);
                    } else {
                        Brick old = Controller.model.brickClicked;
                        old.setState(State.NONE, 555);
                        if (old.hide)
                        /*Si on clique sur une autre brique et quel'ancienne brique selectionné est invisible
                          elle doit garder la bordure #808080
                        */
                            old.setBorderColor(Color.web("#808080"));
                        Controller.model.brickClicked = b;
                        b.setState(State.SHOW_IS_SELECT, 4);
                    }
                } else if (e.getButton().equals(MouseButton.SECONDARY))
                    b.rotate();
            });
        }
        if (isPlate()) {
            //for(int i = 0; i <4;i++){
               //  Cylinder c =Controller.model.brickClicked.border.get(i);
                //c.setTranslateY(Controller.model.brickClicked.border.get(i).getTranslateY());//-0.5
            //}
            Cylinder c4 =Controller.model.brickClicked.border.get(4);
            c4.setTranslateY(Controller.model.brickClicked.border.get(4).getTranslateY()+0.25);//-0.25
            Cylinder c5 =Controller.model.brickClicked.border.get(5);
            c5.setTranslateY(Controller.model.brickClicked.border.get(5).getTranslateY()-0.25); //-0.75
            Cylinder c6 =Controller.model.brickClicked.border.get(6);
            c6.setTranslateY(Controller.model.brickClicked.border.get(6).getTranslateY()+0.25);//-0.25
            Cylinder c7 =Controller.model.brickClicked.border.get(7);
            c7.setTranslateY(Controller.model.brickClicked.border.get(7).getTranslateY()-0.25);//-0.75
            Cylinder c8 =Controller.model.brickClicked.border.get(8);
            c8.setTranslateY(Controller.model.brickClicked.border.get(8).getTranslateY()+0.25); //-0.25
            Cylinder c9 =Controller.model.brickClicked.border.get(9);
            c9.setTranslateY(Controller.model.brickClicked.border.get(9).getTranslateY()-0.75);//-1.25
            Cylinder c10 =Controller.model.brickClicked.border.get(10);
            c10.setTranslateY(Controller.model.brickClicked.border.get(10).getTranslateY()+0.75);//0.25
            Cylinder c11 =Controller.model.brickClicked.border.get(11);
            c11.setTranslateY(Controller.model.brickClicked.border.get(11).getTranslateY()-0.25);//-0.75
        }
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
            Controller.me.group.getChildren().addAll(border);
        }
    }

    public void updateBorder() {
        switch (state) {
            case SHOW_IS_SELECT -> setBorderColor(Color.web("#7CFC00"));
            case FOLLOW_THE_MOUSE -> setBorderColor(Color.web("#42C0FB"));
            case FOLLOW_KEYPRESS -> setBorderColor(Color.web("#DA70D6"));
            default -> removeBorder();
        }
    }

    /**
     * Supprimer la bordure
     */
    public void removeBorder() {
        if (!border.isEmpty()) {
            Controller.me.group.getChildren().removeAll(border);
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
    private void setViewStatusHide(boolean b) {
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
     * Fonction pour que la brique soit toujours dans le dictionnaire !
     *
     * @param color couleur
     */
    private void brickHaveColorInDictionnary(Color color) {
        if (Controller.model.bricks.containsKey(this) && !Controller.model.bricks.get(this).equals(color))
            Controller.model.bricks.replace(this, color);
        else
            Controller.model.bricks.put(this, color);
    }

    /**
     * Permet d'appliquer une rotation de 90 degrée sur une brique
     */
    public void rotate() {
        if (dim.getDepth() == dim.getWidth()) { //ex 1x1x2 or 2x2x3 or....
            return;
        }
        Dim newDim = new Dim(this.dim.getWidth(), this.dim.getDepth(), this.dim.getHeight());
        newDim.rotate();
        if (newDim.getWidth() != this.dim.getWidth() || newDim.getDepth() != this.dim.getDepth()) {
            this.removeBorder();
            this.dim.rotate();
            this.volume = Volume.createAllVolume(new P3D(getX(), getY(), getZ()), this.dim);
            this.createFromNothing(false);
        }
        this.updateBorder();
    }

    /**
     * Fonction auxiliaire pour simplifier des conditions
     *
     * @param i        élément à compar
     * @param interval comparaison avec les autres
     * @return vrai si l'élément est dans l'intervalle, non sinon.
     */
    private boolean isIn(double i, double[] interval) {
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
     * Rentre plate ou rétablir la hauteur d'une birque
     *
     * @param plate votre décision
     */
    public void setPlate(boolean plate) {
        this.plate = plate;
        if (plate)
            this.volume = Volume.createAllVolume(new P3D(getX(), getY(), getZ()), new Dim(this.dim.getWidth(), dim.getDepth(), 1));
        else
            this.volume = Volume.createAllVolume(new P3D(getX(), getY(), getZ()), this.dim);
        createFromNothing(false);
        updateBorder();
    }

    /**
     * @return est-ce que la brique est plate ?
     */
    public boolean isPlate() {
        return plate;
    }
}