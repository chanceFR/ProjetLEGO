package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

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
 * La classe Brick permet de créér des briques en fonction de leur dimension.
 * <p>
 * Elle permet de changer leur couleur dans le dictionnaire du modèle.
 * <p>
 * De déplacer la brique avec la gestion des collisions.
 * <p>
 * De la supprimer.
 * <p>
 * De la cacher.
 *
 * @see fr.antoromeochrist.projetlego.utils.bricks.MinBrick
 */
public class Brick extends ArrayList<MinBrick> {

    /*
     *
     * Les attributs d'une brique
     *
     * */

    /**
     * La dimension d'une brique.
     * <p>
     * Notation: largeur x profondeur x hauteur
     * <p>
     * Règle: quand hauteur = 1 (on le note pas)
     * <p>
     * Exemples:
     * <p>
     * - 1X2
     * <p>
     * Exemples 2:
     * <p>
     * - 2x1x2
     */
    private Dim dim;
    /**
     * Coordonnées de la première {@link MinBrick}
     */
    private double x, y, z;
    /**
     * {@link Volume} totale de la brique.
     * <p>
     * Il contient tous les {@link P3D} qu'occupe la brique dans l'espace.
     *
     * @see fr.antoromeochrist.projetlego.utils.bricks.Dim
     */
    private Volume volume;
    /**
     * Important pour identifier deux briques de même dimension et de même couleur
     */
    private String id;
    /**
     * Contient les bordures de la briques si elle est selectionné
     */
    private ArrayList<Cylinder> border;

    /**
     * Les {@link ImageView} qui seront utilisé et mis à jour dans steps
     *
     * @see fr.antoromeochrist.projetlego.Model
     */
    private ImageView hidestatus, trash;

    /**
     * Le {@link Rectangle} qui sera utilisé
     * <p>
     * pour représenter la couleur et mis à jour dans steps
     *
     * @see fr.antoromeochrist.projetlego.Model
     */
    private Rectangle rect;
    private boolean isHide, isDelete;

    /**
     * Etat de la brique
     */
    private BrickState brickState;


    /**
     * Constructeur pour construire une brique
     *
     * @param dim {@link Dim} de la brique
     *            <p>
     * @param x   coordonnée
     *            <p>
     * @param z   coordonnée
     *            <p>
     * @param y   coordonnée
     *            <p>
     * @param c   {@link Color}
     * @see fr.antoromeochrist.projetlego.utils.ColorPick
     */
    public Brick(Dim dim, double x, double z, double y, Color c) {
        this.brickState = BrickState.NONE;
        this.isHide = false;
        this.id = UUID.randomUUID().toString();
        this.dim = dim;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rect = new Rectangle(0, 0, 10, 10);
        this.rect.setStroke(Color.BLACK);
        this.border = new ArrayList<>();
        this.create(false);
        this.setColor(c);
        this.hidestatus = new ImageView();
        this.hidestatus.setFitHeight(12);
        this.hidestatus.setFitWidth(12);
        this.setViewstatusHide(false);
        /*
         * On fait en sorte que si on clique sur ce bouton
         * Qu'on puisse caché la brique
         * */
        this.hidestatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isHide()) {
                    hide(false);
                } else {
                    hide(true);
                }
            }
        });
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
        this.trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                remove();
            }
        });

        Brick instance = this;
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
            minBrick.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Controller.grid.setCoors(minBrick);
                }
            });
            /*
             * On vient de cliquer sur la brique
             * Permet de d'afficher les bordures
             *
             **/
            minBrick.setOnMouseClicked(mouseEvent -> {
                if (Controller.model.brickClicked != null) {
                    //si c'est déjà la brique sélectionne
                    if (Controller.model.brickClicked.equals(this)) {
                        if (getState().equals(BrickState.SELECT)) {
                            setState(BrickState.SELECTCANMOVE);
                        } else {
                            setState(BrickState.SELECT);
                        }
                    } else {
                        Controller.model.brickClicked.setState(BrickState.NONE);
                        Controller.model.brickClicked = this;
                        this.setState(BrickState.SELECTCANMOVE, 13);
                    }
                }
            });
            minBrick.cyl();
        }
        Controller.me.currentStep.getItems().add(this);
    }

    /**
     * Permet de recreer graphiquement une brique
     *
     * @param recreate si on l'avait déjà créé et qu'on l'a recréé (en appliquand une rotation)
     */
    private void create(boolean recreate) {
        isDelete = false;
        int i1 = 0;
        volume = Volume.createAllVolume(new P3D(this.x, this.y, this.z), this.dim);
        while (Volume.volumeIntersection(this, Controller.model.bricks.keySet())) {
            i1++;
            volume = Volume.createAllVolume(new P3D(this.x, this.y - i1, this.z), this.dim);
        }
        for (int i = 0; i < volume.size(); i++) {
            if (recreate) {
                MinBrick minBrick = this.get(i);
                minBrick.setWidth(1);
                minBrick.setHeight(1);
                minBrick.setDepth(1);
                minBrick.setTranslateX(volume.get(i).getX());
                minBrick.setTranslateY(volume.get(i).getY());
                minBrick.setTranslateZ(volume.get(i).getZ());
                minBrick.cyl();
                this.add(minBrick);
            } else {
                MinBrick minBrick = new MinBrick();
                minBrick.setWidth(1);
                minBrick.setHeight(1);
                minBrick.setDepth(1);
                minBrick.setTranslateX(volume.get(i).getX());
                minBrick.setTranslateY(volume.get(i).getY());
                minBrick.setTranslateZ(volume.get(i).getZ());
                minBrick.cyl();
                this.add(minBrick);
            }
        }
        Controller.model.brickClicked = this;
        if (!recreate) {
            for (MinBrick minBrick : this) {
                Controller.me.group.getChildren().add(minBrick);
                Controller.me.group.getChildren().add(minBrick.getCylinder());
            }
        }
    }

    /**
     * Faut il caché la brique ?
     *
     * @param b booléen
     */
    public void hide(boolean b) {
        this.isHide = b;
        setViewstatusHide(b);
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
            setState(this.brickState);
        }
    }

    /**
     * Suppression de la brique
     */
    public void remove() {
        if (!isDelete) { //evite des bugs si on clique deux fois sur le bouton corbeille(à cause de lag)
            isDelete = true;
            /*suppression des briques*/
            Controller.me.group.getChildren().removeAll(this);
            //suppresion des bordures
            Controller.me.group.getChildren().removeAll(border);
            //suppression des cylindes
            for (MinBrick minBrick : this) Controller.me.group.getChildren().remove(minBrick.getCylinder());
            //suppresion dans le content color si il reste plus de brick de sa couleur
            Color beforeDel = getColor();
            if (Controller.model.getBrickWithColor(beforeDel) == null) {
                //Fast.log("Suppresion du content colors car plus de brique de la couleur de la brique");
                Controller.me.contentColorsRemoveColor(beforeDel);
            }
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
                Controller.model.brickClicked.setState(BrickState.SELECT, 9);
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
                            Controller.model.brickClicked.setState(BrickState.SELECT, 3);
                        }
                    } else { //on sait que forcement l'étape n'est pas vide, sinon elle existerait plus
                        //on prend le dernier élément.
                        //Fast.log("On a trouvé une étape");
                        if (lastStep.getItems().size() > 0) {
                            Controller.model.brickClicked = lastStep.getItems().get(lastStep.getItems().size() - 1);
                            Controller.model.brickClicked.setState(BrickState.SELECT, 4);
                        } else {
                            Controller.model.brickClicked = null;//il y a plus de brique sur le plateau
                        }
                    }
                } else { //on est dans l'étape 1 et il reste aucune brique dedans
                    Controller.model.brickClicked = null;
                }
            }


            Controller.model.bricks.remove(this);
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
            } else {
                //On ajuste la taille
                stepWhere.setPrefHeight(stepWhere.getItems().size() * 50 + 5);
            }
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
        for (MinBrick b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }
        if (!Controller.me.colorInContentColors(color)) Controller.me.contentColorAddColor(color);
    }

    /**
     * {@link String} Identifiant de la brique
     *
     * @return id de la brique
     */
    public String getID() {
        return this.id;
    }

    /**
     * Translation ajout de x y z à la coordonnée actuelle de la brique
     *
     * @param x coordonnée
     * @param y coordonnée
     * @param z coordonnée
     */
    public void translate(double x, double y, double z) {
        move(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Placement de la brique.
     *
     * @param x coordonnée
     * @param y coordonnée
     * @param z coordonnée
     */
    public void move(double x, double y, double z) {
        Volume temp = Volume.createAllVolume(new P3D(x, y, z), this.dim);
        if (!Controller.model.dropInProgress) {
            int increment = 1;
            while (Volume.volumeIntersection(temp, Controller.model.bricks.keySet(), this)) {
                temp = Volume.createAllVolume(new P3D(x, y - increment, z), this.dim);
                increment++;
            }
        }
        if (!Volume.volumeIntersection(temp, Controller.model.bricks.keySet(), this)) {
            this.x = x;
            this.y = y;
            this.z = z;
            volume = temp;
            for (int i = 0; i < volume.size(); i++) {
                get(i).setTranslateX(volume.get(i).getX());
                get(i).setTranslateY(volume.get(i).getY());
                get(i).setTranslateZ(volume.get(i).getZ());
                get(i).cyl();
            }
        }

    }

    /**
     * Translation de -1 sur l'axe y
     */
    public void up() {
        this.translate(0, -1, 0);
        setState(BrickState.SELECTCANMOVE);
    }

    /**
     * Translation de 1 sur l'axe y
     */
    public void down() {
        this.translate(0, 1, 0);
        setState(BrickState.SELECTCANMOVE);
    }

    /**
     * Translation de -1 sur l'axe x
     */
    public void leftX() {
        this.translate(-1, 0, 0);
        setState(BrickState.SELECTCANMOVE);
    }

    /**
     * Translation de 1 sur l'axe x
     */
    public void rightX() {
        this.translate(1, 0, 0);
        setState(BrickState.SELECTCANMOVE);
    }

    /**
     * Translation de -1 sur l'axe z
     */
    public void leftZ() {
        this.translate(0, 0, -1);
        setState(BrickState.SELECTCANMOVE);
    }

    /**
     * Translation de 1 sur l'axe z
     */
    public void rightZ() {
        this.translate(0, 0, 1);
        setState(BrickState.SELECTCANMOVE);
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
     *
     * @return
     */
    public String toString() {
        return "[" + getColor() + "|" + dim + "|" + volume + "]";
    }

    /**
     * Coordonnées en x
     *
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Coordonnées en y
     *
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Coordonnées en z
     *
     * @return z
     */
    public double getZ() {
        return z;
    }


    /**
     * Mettre à jour les bordures en fonction de l'état de la brique
     *
     * @param brickState état
     */
    public void setState(BrickState brickState) {
        switch (brickState) {
            case SELECT:
                setBorderColor(Color.web("#7CFC00"));
                break;
            case SELECTCANMOVE:
                setBorderColor(Color.web("#42C0FB"));
                break;
            default:
                removeBorder();
                break;
        }
        BrickState old = this.brickState;
        this.brickState = brickState;
        //Fast.log(old+" --> "+this.brickState);
    }

    /**
     * Mettre à jour les bordures en fonction de l'état de la brique
     *
     * @param brickState état
     */
    public void setState(BrickState brickState, int i) {
        switch (brickState) {
            case SELECT:
                setBorderColor(Color.web("#7CFC00"));
                break;
            case SELECTCANMOVE:
                setBorderColor(Color.web("#42C0FB"));
                break;
            default:
                removeBorder();
                break;
        }
        BrickState old = this.brickState;
        this.brickState = brickState;
        //Fast.log(old+" --> "+this.brickState +"| debug"+i);
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
     * La brique est t'elle caché ?
     *
     * @return booléen
     */
    public boolean isHide() {
        return isHide;
    }

    private Cylinder createCylBorder(P3D p, double height, double radius) {
        Cylinder c = new Cylinder();
        c.setTranslateX(p.getX());
        c.setTranslateY(p.getY());
        c.setTranslateZ(p.getZ());
        c.setHeight(height);
        c.setRadius(radius);
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
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), 0, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5 + this.getDim().getDepth()), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, 0, 0.5), this.dim.getHeight(), 0.01);
        } else if (dim.getWidth() == 1 && dim.getDepth() == 1 && dim.getHeight() == 4) {
            createCylBorder(volume.get(0).add(-0.5, 1.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), 1.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5, 1.5, -0.5 + this.getDim().getDepth()), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, -1.5, 0.5), this.dim.getHeight(), 0.01);
        } else if (dim.getWidth() == 1 && dim.getDepth() == 1 && dim.getHeight() == 2) {
            createCylBorder(volume.get(0).add(-0.5, 0.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5, 0.5, -0.5 + this.getDim().getDepth()), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, -0.5, 0.5), this.dim.getHeight(), 0.01);
        } else {
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5), 1, 0.01);
            createCylBorder(volume.get(0).add(-0.5 + 1, 0, -0.5), 1, 0.01);
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5 + 1), 1, 0.01);
            createCylBorder(volume.get(0).add(0.5, 0, 0.5), 1, 0.01);
        }


        if (dim.getDepth() == 2 && dim.getWidth() == 2 && dim.getHeight() == 1) {
            createCylBorder(volume.get(0).add(0.5, -0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(0).add(0.5, 0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(-0.5, -0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);

        } else if (dim.getHeight() == 1) {
            if (dim.getWidth() == 1) {
                createCylBorder(volume.get(0).add(0, -0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-1 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(0, -0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(0, 0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);

            } else if (dim.getWidth() == 2) {
                createCylBorder(volume.get(0).add(0.5, -0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-1.5 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, -0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            } else if (dim.getWidth() == 4) {
                createCylBorder(volume.get(0).add(1.5, -0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-2.5 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1.5, -0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            } else {
                createCylBorder(volume.get(0).add(1, -0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-2 + this.getDim().getWidth(), 0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1, -0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1, 0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            }
        } else {
            createCylBorder(volume.get(0).add(0, -0.5, -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(0).add(-1 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), -0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(0, 0.5 - this.dim.getHeight(), 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(0, 0.5, 0.5), this.dim.getWidth(), 0.01).getTransforms().add(rotateZ);
        }


        if (dim.getDepth() == 2 && dim.getWidth() == 2 && dim.getHeight() == 1) { //2x2
            createCylBorder(volume.get(0).add(-0.5, -0.5, 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
        } else if (dim.getHeight() == 1) {
            if (dim.getWidth() == 1) {
                if (dim.getDepth() == 2) { //1x2
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);

                } else if (dim.getDepth() == 3) { //1x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else if (dim.getDepth() == 4) { //1x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else { //1x1
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                }
            } else if (dim.getWidth() == 2) {
                if (dim.getDepth() == 3) { //2x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else { //2x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                }
            } else if (dim.getWidth() == 3) {
                if (dim.getDepth() == 3) {  //3x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-2.5, 0.5, -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else { //3x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-2.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                }
            } else { //4x4
                createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(-3.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            }
        } else {
            if (dim.getWidth() == 1 && dim.getDepth() == 1) { //1x1x?
                createCylBorder(volume.get(0).add(-0.5, -0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWidth(), -0.5 + this.getDim().getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            }
        }
        if (isIn(dim.getWidth(), new int[]{2, 3, 4}) && dim.getDepth() == 1) {
            border.get(8).setTranslateZ(border.get(8).getTranslateZ() - 1.5);
            border.get(9).setTranslateZ(border.get(9).getTranslateZ() - 1.5);
            border.get(10).setTranslateZ(border.get(10).getTranslateZ() + 1.5);
            border.get(11).setTranslateZ(border.get(11).getTranslateZ() + 1.5);
        }
        if (isIn(dim.getWidth(), new int[]{3, 4}) && dim.getDepth() == 2) {
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
        if (!isDelete) {
            Controller.me.group.getChildren().addAll(border);
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
     * @return
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
    private void setViewstatusHide(boolean b) {
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
     * @return
     * @see fr.antoromeochrist.projetlego.Controller
     */
    public ImageView getTrash() {
        return trash;
    }


    private void brickHaveColorInDictionnary(Color color) {
        if (Controller.model.bricks.containsKey(this)) {
            Controller.model.bricks.replace(this, color);
        } else {
            Controller.model.bricks.put(this, color);
        }
    }

    /**
     * Permet d'appliquer une rotation de 90 degrée sur une brique
     */
    public void rotate() {
        if (dim.getDepth() == 1 && dim.getWidth() == 1) { //ex 1x1x2
            return;
        }
        Dim newDim = new Dim(this.dim.getWidth(), this.dim.getDepth(), this.dim.getHeight());
        newDim.rotate();
        if (newDim.getWidth() != this.dim.getWidth() || newDim.getDepth() != this.dim.getWidth()) {
            this.removeBorder();
            this.dim.rotate();
            this.create(true);
            this.setState(BrickState.SELECTCANMOVE, 1);
        }
    }

    private boolean isIn(int i, int[] interval) {
        for (int p = 0; p < interval.length; p++) {
            if (interval[p] == i) {
                return true;
            }
        }
        return false;
    }

    public BrickState getState() {
        return brickState;
    }
}