package fr.antoromeochrist.projetlego.pieces;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Figurine extends Piece {



    private final Sphere leftShoulder;
    private final Sphere rightShoulder;
    private final Cylinder leftArmTop;
    private final Cylinder rightArmTop;
    private final Cylinder leftArmDown;
    private final Cylinder rightArmDown;

    private final Box bodyMiddle;
    private final Box bodyLeft;
    private final Box bodyRight;
    private final Box pants;
    private final Cylinder middlePants;
    private final Cylinder leftKnee;
    private final Cylinder rightKnee;
    private final Box leftButLock;
    private final Box rightButLock;
    private final Box leftLeg;
    private final Box rightLeg;
    private final Box leftFoot;
    private final Box rightFoot;

    public Figurine(double x, double y, double z) {
        this(x, y, z, false);
    }

    public Figurine(double x, double y, double z, boolean b) {
        super(new Dim(4, 1, 5), x, y, z, Color.GRAY, b);

        Rotate rotateZ  = addRotate(Rotate.Z_AXIS,90);

        Rotate rotateZ7 = addRotate(Rotate.Z_AXIS,12);

        Rotate rotateZ_7 = addRotate(Rotate.Z_AXIS,-12);

        Rotate rotateX90 = addRotate(Rotate.X_AXIS,-75);

        leftShoulder = new Sphere();
        leftShoulder.setRadius(0.2825);
        leftShoulder.setMaterial(new PhongMaterial(Color.RED));

        rightShoulder = new Sphere();
        rightShoulder.setRadius(0.2825);
        rightShoulder.setMaterial(new PhongMaterial(Color.GREEN));


        leftArmTop = new Cylinder();
        leftArmTop.setHeight(0.8);
        leftArmTop.setRadius(0.24);
        leftArmTop.setMaterial(new PhongMaterial(Color.RED));
        leftArmTop.getTransforms().add(rotateZ7);

        rightArmTop = new Cylinder();
        rightArmTop.setHeight(0.8);
        rightArmTop.setRadius(0.24);
        rightArmTop.setMaterial(new PhongMaterial(Color.GREEN));
        rightArmTop.getTransforms().add(rotateZ_7);

        leftArmDown = new Cylinder();
        leftArmDown.setHeight(0.6);
        leftArmDown.setRadius(0.24);
        leftArmDown.setMaterial(new PhongMaterial(Color.RED));
        leftArmDown.getTransforms().add(rotateZ7);
        leftArmDown.getTransforms().add(rotateX90);

        rightArmDown = new Cylinder();
        rightArmDown.setHeight(0.6);
        rightArmDown.setRadius(0.24);
        rightArmDown.setMaterial(new PhongMaterial(Color.GREEN));
        rightArmDown.getTransforms().add(rotateZ_7);
        rightArmDown.getTransforms().add(rotateX90);


        bodyMiddle = new Box();
        bodyMiddle.setHeight(2);
        bodyMiddle.setWidth(1.52);
        bodyMiddle.setDepth(1);
        bodyMiddle.setMaterial(new PhongMaterial(Color.BLUE));

        bodyLeft = new Box();
        bodyLeft.setHeight(2);
        bodyLeft.setWidth(0.25);
        bodyLeft.setDepth(1);
        bodyLeft.setMaterial(new PhongMaterial(Color.BLUE));
        bodyLeft.getTransforms().add(rotateZ7);

        bodyRight = new Box();
        bodyRight.setHeight(2);
        bodyRight.setWidth(0.25);
        bodyRight.setDepth(1);
        bodyRight.setMaterial(new PhongMaterial(Color.BLUE));
        bodyRight.getTransforms().add(rotateZ_7);

        pants = new Box();
        pants.setHeight(0.25);
        pants.setWidth(2);
        pants.setDepth(1);
        pants.setMaterial(new PhongMaterial(Color.BLACK));

        middlePants = new Cylinder();
        middlePants.setRadius(0.25);
        middlePants.setHeight(0.25);
        middlePants.setMaterial(new PhongMaterial(Color.BLACK));
        middlePants.getTransforms().add(rotateZ);

        leftKnee = new Cylinder();
        leftKnee.setRadius(0.25);
        leftKnee.setHeight(0.8);
        leftKnee.setMaterial(new PhongMaterial(Color.RED));
        leftKnee.getTransforms().add(rotateZ);

        rightKnee= new Cylinder();
        rightKnee.setRadius(0.25);
        rightKnee.setHeight(0.8);
        rightKnee.setMaterial(new PhongMaterial(Color.RED));
        rightKnee.getTransforms().add(rotateZ);

        leftButLock = new Box();
        leftButLock.setWidth(0.9);
        leftButLock.setDepth(0.75);
        leftButLock.setHeight(0.5);
        leftButLock.setMaterial(new PhongMaterial(Color.RED));

        rightButLock= new Box();
        rightButLock.setWidth(0.9);
        rightButLock.setDepth(0.75);
        rightButLock.setHeight(0.5);
        rightButLock.setMaterial(new PhongMaterial(Color.RED));

        leftLeg = new Box();
        leftLeg.setWidth(0.9);
        leftLeg.setDepth(0.75);
        leftLeg.setHeight(1);
        leftLeg.setMaterial(new PhongMaterial(Color.RED));

        rightLeg = new Box();
        rightLeg.setWidth(0.9);
        rightLeg.setDepth(0.75);
        rightLeg.setHeight(1);
        rightLeg.setMaterial(new PhongMaterial(Color.RED));

        leftFoot = new Box();
        leftFoot.setWidth(0.9);
        leftFoot.setHeight(0.30);
        leftFoot.setDepth(1);
        leftFoot.setMaterial(new PhongMaterial(Color.RED));

        rightFoot = new Box();
        rightFoot.setWidth(0.9);
        rightFoot.setHeight(0.30);
        rightFoot.setDepth(1);
        rightFoot.setMaterial(new PhongMaterial(Color.RED));

        nodes.add(leftShoulder);
        nodes.add(rightShoulder);
        nodes.add(leftArmTop);
        nodes.add(rightArmTop);
        nodes.add(leftArmDown);
        nodes.add(rightArmDown);
        nodes.add(bodyMiddle);
        nodes.add(bodyLeft);
        nodes.add(bodyRight);
        nodes.add(pants);
        nodes.add(middlePants);
        nodes.add(leftKnee);
        nodes.add(rightKnee);
        nodes.add(leftButLock);
        nodes.add(rightButLock);
        nodes.add(leftLeg);
        nodes.add(rightLeg);
        nodes.add(leftFoot);
        nodes.add(rightFoot);
        updateNodesLocation();
        Controller.me.group.getChildren().addAll(nodes);
    }
    @Override
    public void updateNodesLocation() {

        leftShoulder.setTranslateX(volume.get(volume.size() - 1).getX()-2.40);
        leftShoulder.setTranslateY(volume.get(volume.size() - 1).getY()-3);
        leftShoulder.setTranslateZ(volume.get(volume.size() - 1).getZ());

        rightShoulder.setTranslateX(volume.get(volume.size() - 1).getX()-0.60);
        rightShoulder.setTranslateY(volume.get(volume.size() - 1).getY()-3);
        rightShoulder.setTranslateZ(volume.get(volume.size() - 1).getZ());

        leftArmTop.setTranslateX(volume.get(volume.size() - 1).getX()-2.60);
        leftArmTop.setTranslateY(volume.get(volume.size() - 1).getY()-2.75);
        leftArmTop.setTranslateZ(volume.get(volume.size() - 1).getZ());

        rightArmTop.setTranslateX(volume.get(volume.size() - 1).getX()-0.35);
        rightArmTop.setTranslateY(volume.get(volume.size() - 1).getY()-2.75);
        rightArmTop.setTranslateZ(volume.get(volume.size() - 1).getZ());

        leftArmDown.setTranslateX(volume.get(volume.size() - 1).getX()-2.70);
        leftArmDown.setTranslateY(volume.get(volume.size() - 1).getY()-2.25);
        leftArmDown.setTranslateZ(volume.get(volume.size() - 1).getZ()-0.12);

        rightArmDown.setTranslateX(volume.get(volume.size() - 1).getX()-0.15);
        rightArmDown.setTranslateY(volume.get(volume.size() - 1).getY()-2.25);
        rightArmDown.setTranslateZ(volume.get(volume.size() - 1).getZ()-0.12);










        bodyMiddle.setTranslateX(volume.get(volume.size() - 1).getX()-1.5);
        bodyMiddle.setTranslateY(volume.get(volume.size() - 1).getY()-2.5);
        bodyMiddle.setTranslateZ(volume.get(volume.size() - 1).getZ());


        bodyLeft.setTranslateX(volume.get(volume.size() - 1).getX()-2.25);
        bodyLeft.setTranslateY(volume.get(volume.size() - 1).getY()-2.5);
        bodyLeft.setTranslateZ(volume.get(volume.size() - 1).getZ());

        bodyRight.setTranslateX(volume.get(volume.size() - 1).getX()-0.75);
        bodyRight.setTranslateY(volume.get(volume.size() - 1).getY()-2.5);
        bodyRight.setTranslateZ(volume.get(volume.size() - 1).getZ());



        pants.setTranslateX(volume.get(volume.size() - 1).getX()-1.5);
        pants.setTranslateY(volume.get(volume.size() - 1).getY()-1.40);
        pants.setTranslateZ(volume.get(volume.size() - 1).getZ());

        leftKnee.setTranslateX(volume.get(volume.size() - 1).getX()- 2.05);
        leftKnee.setTranslateY(volume.get(volume.size() - 1).getY()-1.05);
        leftKnee.setTranslateZ(volume.get(volume.size() - 1).getZ()-0.15);

        middlePants.setTranslateX(volume.get(volume.size() - 1).getX()-1.5);
        middlePants.setTranslateY(volume.get(volume.size() - 1).getY()-1.05);
        middlePants.setTranslateZ(volume.get(volume.size() - 1).getZ()-0.15);

        rightKnee.setTranslateX(volume.get(volume.size() - 1).getX()-0.95);
        rightKnee.setTranslateY(volume.get(volume.size() - 1).getY()-1.05);
        rightKnee.setTranslateZ(volume.get(volume.size() - 1).getZ()-0.15);


        leftButLock.setTranslateX(volume.get(volume.size() - 1).getX()- 2);
        leftButLock.setTranslateY(volume.get(volume.size() - 1).getY()-1.05);
        leftButLock.setTranslateZ(volume.get(volume.size() - 1).getZ()+0.12);

        rightButLock.setTranslateX(volume.get(volume.size() - 1).getX()-1);
        rightButLock.setTranslateY(volume.get(volume.size() - 1).getY()-1.05);
        rightButLock.setTranslateZ(volume.get(volume.size() - 1).getZ()+0.12);


        leftLeg.setTranslateX(volume.get(volume.size() - 1).getX()- 2);
        leftLeg.setTranslateY(volume.get(volume.size() - 1).getY()-0.30);
        leftLeg.setTranslateZ(volume.get(volume.size() - 1).getZ()+0.12);

        rightLeg.setTranslateX(volume.get(volume.size() - 1).getX()-1);
        rightLeg.setTranslateY(volume.get(volume.size() - 1).getY()-0.30);
        rightLeg.setTranslateZ(volume.get(volume.size() - 1).getZ()+0.12);

        leftFoot.setTranslateX(volume.get(volume.size() - 1).getX() -2);
        leftFoot.setTranslateY(volume.get(volume.size() - 1).getY() + 0.35);
        leftFoot.setTranslateZ(volume.get(volume.size() - 1).getZ());

        rightFoot.setTranslateX(volume.get(volume.size() - 1).getX()-1);
        rightFoot.setTranslateY(volume.get(volume.size() - 1).getY() + 0.35);
        rightFoot.setTranslateZ(volume.get(volume.size() - 1).getZ());

    }

    @Override
    public void cloneThePiece() {
        new Figurine(getX(),getY(),getZ());
    }
}