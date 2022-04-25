package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import javafx.event.EventHandler;
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
        this.isHide = false;
        this.isDelete = false;
        this.id = UUID.randomUUID().toString();
        this.dim = dim;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rect = new Rectangle(0, 0, 10, 10);
        this.rect.setStroke(Color.BLACK);
        this.border = new ArrayList<>();
        this.create();
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

        Brick b = this;


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
             *
             * Permet de d'afficher les bordures
             *
             **/
            minBrick.setOnMousePressed(mouseEvent -> {
                if (Controller.model.actionWithDropDone) {
                    if (Controller.model.brickClicked != null) {
                        if (Controller.model.brickClicked.equals(this)) {
                            setSelectMode(false);
                            Controller.model.brickClicked = null;
                        } else {
                            Controller.model.brickClicked.setSelectMode(false);
                            Controller.model.brickClicked = this;
                            this.setSelectMode(true);
                        }
                    } else {
                        Controller.model.brickClicked = this;
                        this.setSelectMode(true);
                    }
                }
            });
            minBrick.cyl();
        }
        Controller.me.currentStep.getItems().add(this);
    }

    private void create() {
        int i1 = 0;
        volume = Volume.createAllVolume(new P3D(this.x, this.y, this.z), this.dim);
        while (Volume.volumeIntersection(this, Controller.model.bricks.keySet())) {
            i1++;
            volume = Volume.createAllVolume(new P3D(this.x, this.y - i1, this.z), this.dim);
        }
        Controller.model.bricks.put(this, Color.WHITE);
        for (int i = 0; i < volume.size(); i++) {
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
        for (MinBrick minBrick : this) {
            Controller.me.group.getChildren().add(minBrick);
            Controller.me.group.getChildren().add(minBrick.getCylinder());
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
        }
    }

    /**
     * Suppression de la brique
     */
    public void remove() {
        if (!isDelete) { //evite des bugs si on clique deux fois sur le bonton corbeille
            isDelete = true;
            if (Controller.model.brickClicked != null) {
                if (Controller.model.brickClicked.getID().equals(this.getID())) {
                    Controller.model.brickClicked = null;
                }
            }
            Controller.me.group.getChildren().removeAll(this);
            setSelectMode(false);
            Controller.me.group.getChildren().removeAll(border);
            for (MinBrick minBrick : this) Controller.me.group.getChildren().remove(minBrick.getCylinder());
            Color beforeDel = getColor();
            Controller.model.bricks.remove(this);
            if (Controller.model.getBrickWithColor(beforeDel) == null) {
                System.out.println("Suppression de la couleur inutile ");
                Controller.me.contentColorsRemoveColor(beforeDel);
            }
            Controller.me.getStepWhereIsBrick(this).getItems().remove(this);
            this.remove();
        }
    }

    /**
     * Mettre la {@link Color} à la brique
     *
     * @param color la couleur
     */
    public void setColor(Color color) {
        rect.setFill(color);
        for (MinBrick b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }
        if (!Controller.model.bricks.containsKey(this)) Controller.model.bricks.put(this, color);
        if (!getColor().equals(color)) Controller.model.bricks.replace(this, color);
        if (!Controller.me.colorInContentColors(color)) Controller.me.contentColorAddColor(color);
        Controller.model.bricks.replace(this, color);
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
        if (!Controller.model.actionWithDropDone) {
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
        setSelectMode(true);
    }

    /**
     * Translation de 1 sur l'axe y
     */
    public void down() {
        this.translate(0, 1, 0);
        setSelectMode(true);
    }

    /**
     * Translation de -1 sur l'axe x
     */
    public void leftX() {
        this.translate(-1, 0, 0);
        setSelectMode(true);
    }

    /**
     * Translation de 1 sur l'axe x
     */
    public void rightX() {
        this.translate(1, 0, 0);
        setSelectMode(true);
    }

    /**
     * Translation de -1 sur l'axe z
     */
    public void leftZ() {
        this.translate(0, 0, -1);
        setSelectMode(true);
    }

    /**
     * Translation de 1 sur l'axe z
     */
    public void rightZ() {
        this.translate(0, 0, 1);
        setSelectMode(true);
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
     * Mettre les bordures de la brique en bleu pour
     * <p>
     * montrer qu'elle est sélectionné ou les retirer.
     *
     * @param b booléen
     */
    public void setSelectMode(boolean b) {
        if (b) {
            setBorderColor(Color.web("#42C0FB"));
        } else {
            if (isHide) {
                setBorderColor(Color.web("#808080"));
            } else {
                removeBorder();
            }
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
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), 0, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5 + this.getDim().getDepth()), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, 0, 0.5), this.dim.getHeight(), 0.01);
        } else if (dim.getWeight() == 1 && dim.getDepth() == 1 && dim.getHeight() == 4) {
            createCylBorder(volume.get(0).add(-0.5, 1.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), 1.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5, 1.5, -0.5 + this.getDim().getDepth()), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, -1.5, 0.5), this.dim.getHeight(), 0.01);
        } else if (dim.getWeight() == 1 && dim.getDepth() == 1 && dim.getHeight() == 2) {
            createCylBorder(volume.get(0).add(-0.5, 0.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), 0.5, -0.5), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(0).add(-0.5, 0.5, -0.5 + this.getDim().getDepth()), this.dim.getHeight(), 0.01);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, -0.5, 0.5), this.dim.getHeight(), 0.01);
        } else {
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5), 1, 0.01);
            createCylBorder(volume.get(0).add(-0.5 + 1, 0, -0.5), 1, 0.01);
            createCylBorder(volume.get(0).add(-0.5, 0, -0.5 + 1), 1, 0.01);
            createCylBorder(volume.get(0).add(0.5, 0, 0.5), 1, 0.01);
        }


        if (dim.getDepth() == 2 && dim.getWeight() == 2 && dim.getHeight() == 1) {
            createCylBorder(volume.get(0).add(0.5, -0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(0).add(0.5, 0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(-0.5, -0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);

        } else if (dim.getHeight() == 1) {
            if (dim.getWeight() == 1) {
                createCylBorder(volume.get(0).add(0, -0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-1 + this.getDim().getWeight(), 0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(0, -0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(0, 0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);

            } else if (dim.getWeight() == 2) {
                createCylBorder(volume.get(0).add(0.5, -0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-1.5 + this.getDim().getWeight(), 0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, -0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            } else if (dim.getWeight() == 4) {
                createCylBorder(volume.get(0).add(1.5, -0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-2.5 + this.getDim().getWeight(), 0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1.5, -0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            } else {
                createCylBorder(volume.get(0).add(1, -0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(0).add(-2 + this.getDim().getWeight(), 0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1, -0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
                createCylBorder(volume.get(volume.size() - 1).add(-1, 0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            }
        } else {
            createCylBorder(volume.get(0).add(0, -0.5, -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(0).add(-1 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), -0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(0, 0.5 - this.dim.getHeight(), 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
            createCylBorder(volume.get(volume.size() - 1).add(0, 0.5, 0.5), this.dim.getWeight(), 0.01).getTransforms().add(rotateZ);
        }


        if (dim.getDepth() == 2 && dim.getWeight() == 2 && dim.getHeight() == 1) { //2x2
            createCylBorder(volume.get(0).add(-0.5, -0.5, 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
        } else if (dim.getHeight() == 1) {
            if (dim.getWeight() == 1) {
                if (dim.getDepth() == 2) { //1x2
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -0.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);

                } else if (dim.getDepth() == 3) { //1x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else if (dim.getDepth() == 4) { //1x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else { //1x1
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                }
            } else if (dim.getWeight() == 2) {
                if (dim.getDepth() == 3) { //2x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else { //2x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-1.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                }
            } else if (dim.getWeight() == 3) {
                if (dim.getDepth() == 3) {  //3x3
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-2.5, 0.5, -1), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                } else { //3x4
                    createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                    createCylBorder(volume.get(volume.size() - 1).add(-2.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                }
            } else { //4x4
                createCylBorder(volume.get(0).add(-0.5, -0.5, 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(-3.5, 0.5, -1.5), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            }
        } else {
            if (dim.getWeight() == 1 && dim.getDepth() == 1) { //1x1x?
                createCylBorder(volume.get(0).add(-0.5, -0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(0).add(-0.5 + this.getDim().getWeight(), -0.5 + this.getDim().getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(0.5, 0.5 - this.dim.getHeight(), 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
                createCylBorder(volume.get(volume.size() - 1).add(-0.5, 0.5, 0), this.dim.getDepth(), 0.01).getTransforms().add(rotateX);
            }
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
}