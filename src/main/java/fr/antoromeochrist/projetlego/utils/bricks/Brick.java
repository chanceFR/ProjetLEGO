package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import fr.antoromeochrist.projetlego.utils.images.ImagePath;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Brick extends ArrayList<Lego> {

    //Permet de recuperer l'objet group du controller et donc de relier les briques au group du controlleur.
    public static Group group;
    public static HashMap<Brick, Color> bricksSortByColors = new HashMap<>();
    public static ArrayList<Brick> environnement = new ArrayList<>();
    public static ListView<Color> contentColorsStatic;
    public static ListView<ListView<Brick>> stepsStatic;
    public static ListView<Brick> currentStepStatic;
    private Dim dim;
    private double x;
    private double y;
    private double z;
    private Volume volume;
    private Color color;
    private String id;
    private Rectangle rect;
    private boolean isHide;
    private ArrayList<Cylinder> border;
    private ImageView hidestatus;
    private ImageView trash;
    private boolean isDelete;

    public static ListView<Brick> getStepWhereIsBrick(Brick b) {
        System.out.println("steps size: " + stepsStatic.getItems().size());
        System.out.println("steps items: " + stepsStatic.getItems());
        for (ListView<Brick> lv : stepsStatic.getItems()) {
            for (Brick br : lv.getItems()) {
                System.out.println("brick: " + br);
                if (br.getID().equals(b.getID())) {
                    System.out.println("pareil");
                    return lv;
                }
            }
        }
        return null;
    }

    public Brick(Dim dim, double x, double z, double y, Color c) {
        this.isHide = false;
        this.isDelete = false;
        this.id = UUID.randomUUID().toString();
        this.dim = dim;
        this.x = x;
        this.y = y;
        this.z = z;
        border = new ArrayList<>();
        create();
        this.setColor(c);
        hidestatus = new ImageView();
        hidestatus.setFitHeight(12);
        hidestatus.setFitWidth(12);
        setViewstatusHide(false);
        hidestatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isHide()) {
                    hide(false);
                } else {
                    hide(true);
                }
            }
        });
        trash = new ImageView();
        try {
            trash.setImage(new ImagePath("trash.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        trash.setFitWidth(12);
        trash.setFitHeight(12);
        trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                remove();
            }
        });

        Brick b = this;
        for (Lego lego : this) {
            lego.setOnMousePressed(mouseEvent -> {
                if (Controller.brickClicked != null) {
                    if (Controller.brickClicked.equals(this)) {
                        setSelectMode(false);
                        Controller.brickClicked = null;
                    } else {
                        Controller.brickClicked.setSelectMode(false);
                        Controller.brickClicked = this;
                        //System.out.println("--> " + Controller.brickClicked);
                        this.setSelectMode(true);
                    }
                } else {
                    Controller.brickClicked = this;
                    //System.out.println("--> " + Controller.brickClicked);
                    this.setSelectMode(true);
                }
            });
            lego.cyl();
        }
        currentStepStatic.getItems().add(this);
    }

    private void create() {
        int i1 = 0;
        volume = Volume.createAllVolume(new P3D(this.x, this.y, this.z), this.dim);
        if (!environnement.isEmpty()) {
            while (Volume.volumeIntersection(this, environnement)) {
                i1++;
                volume = Volume.createAllVolume(new P3D(this.x, this.y - i1, this.z), this.dim);
            }
        }
        if (!environnement.contains(this)) environnement.add(this);
        for (int i = 0; i < volume.size(); i++) {
            Lego lego = new Lego();
            lego.setWidth(1);
            lego.setHeight(1);
            lego.setDepth(1);
            lego.setTranslateX(volume.get(i).getX());
            lego.setTranslateY(volume.get(i).getY());
            lego.setTranslateZ(volume.get(i).getZ());
            lego.cyl();
            this.add(lego);
        }
        for (Lego lego : this) {
            group.getChildren().add(lego);
            group.getChildren().add(lego.getCylinder());
        }
    }

    public void hide(boolean b) {
        isHide = b;
        setViewstatusHide(b);
        if (b) {
            for (Lego lego : this) {
                lego.setOpacity(0);
                lego.cyl();
            }
            setBorderColor(Color.web("#808080"));
        } else {
            for (Lego lego : this) {
                lego.setOpacity(100);
                lego.cyl();
            }
            removeBorder();
        }
    }

    public void remove() {
        if (!isDelete) { //evite des bugs si on clique deux fois sur le bonton corbeille
            isDelete = true;
            if(Controller.brickClicked != null) {
                if (Controller.brickClicked.getID().equals(this.getID())) {
                    Controller.brickClicked = null;
                }
            }
            group.getChildren().removeAll(this);
            setSelectMode(false);
            group.getChildren().removeAll(border);
            for (Lego lego : this) group.getChildren().remove(lego.getCylinder());
            bricksSortByColors.remove(this);
            environnement.remove(this);
            if (getBrickWithColor(this.color).isEmpty()) {
                contentColorsStatic.getItems().remove(this.color);
            }
            getStepWhereIsBrick(this).getItems().remove(this);
            this.remove();
        }
    }

    public static ArrayList<Brick> getBrickWithColor(Color c) {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (Map.Entry<Brick, Color> entry : bricksSortByColors.entrySet()) {
            if (entry.getValue().equals(c)) {
                bricks.add(entry.getKey());
            }
        }
        return bricks;
    }


    public void setColor(Color color) {
        for (Lego b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }
        this.color = color;
        if (!bricksSortByColors.containsKey(this)) bricksSortByColors.put(this, this.color);
        if (!bricksSortByColors.get(this).equals(this.color)) bricksSortByColors.replace(this, this.color);
        if (!contentColorsStatic.getItems().contains(this.color))contentColorsStatic.getItems().add(this.color);

        bricksSortByColors.replace(this, this.color);
    }

    public String getID() {
        return this.id;
    }

    public void translate(double x, double y, double z) {
        Volume temp = Volume.createAllVolume(new P3D(this.x + x, this.y + y, this.z + z), this.dim);
        if (!Volume.volumeIntersection(temp, environnement, this)) {
            this.x += x;
            this.y += y;
            this.z += z;
            volume = temp;
            for (int i = 0; i < volume.size(); i++) {
                get(i).setTranslateX(volume.get(i).getX());
                get(i).setTranslateY(volume.get(i).getY());
                get(i).setTranslateZ(volume.get(i).getZ());
                get(i).cyl();
            }
        }
    }


    public void up() {
        this.translate(0, 1, 0);
        setSelectMode(true);
    }

    public void down() {
        this.translate(0, -1, 0);
        setSelectMode(true);
    }

    public void leftX() {
        this.translate(-1, 0, 0);
        setSelectMode(true);
    }

    public void rightX() {
        this.translate(1, 0, 0);
        setSelectMode(true);
    }

    public void leftZ() {
        this.translate(0, 0, -1);
        setSelectMode(true);
    }

    public void rightZ() {
        this.translate(0, 0, 1);
        setSelectMode(true);
    }

    public Color getColor() {
        return color;
    }

    public Volume getVolume() {
        return volume;
    }

    public String toString() {
        return "[" + color + "|" + dim + "|" + volume + "]";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

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

    public Dim getDim() {
        return dim;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

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

    public void createBorder() {
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

    public void setBorderColor(Color c) {
        removeBorder();
        createBorder();
        for (Cylinder bord : border) {
            bord.setMaterial(new PhongMaterial(c));
        }
        if(!isDelete){
            Brick.group.getChildren().addAll(border);
        }
    }

    public void removeBorder() {
        if (!border.isEmpty()) {
            Brick.group.getChildren().removeAll(border);
            border.clear();
        }
    }

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

    public ImageView getTrash() {
        return trash;
    }

}