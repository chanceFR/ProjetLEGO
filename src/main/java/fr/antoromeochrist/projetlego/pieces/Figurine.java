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

    private final Cylinder leftHand1;
    private final Cylinder leftHand2;
    private final Cylinder leftHand3;
    private final Cylinder leftHand4;
    private final Cylinder leftHand5;


    private final Cylinder rightHand1;
    private final Cylinder rightHand2;
    private final Cylinder rightHand3;
    private final Cylinder rightHand4;
    private final Cylinder rightHand5;


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

        Rotate rotateZ = addRotate(Rotate.Z_AXIS, 90);

        Rotate rotateZ7 = addRotate(Rotate.Z_AXIS, 7);

        Rotate rotateZ_7 = addRotate(Rotate.Z_AXIS, -7);

        Rotate rotateZ12 = addRotate(Rotate.Z_AXIS, 13);

        Rotate rotateZ_12 = addRotate(Rotate.Z_AXIS, -13);

        Rotate rotateX90 = addRotate(Rotate.X_AXIS, -75);

        leftHand1 = new Cylinder();
        leftHand1.setHeight(0.4);
        leftHand1.setRadius(0.2);
        leftHand1.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand2 = new Cylinder();
        leftHand2.setHeight(0.4);
        leftHand2.setRadius(0.2);
        leftHand2.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand3 = new Cylinder();
        leftHand3.setHeight(0.4);
        leftHand3.setRadius(0.2);
        leftHand3.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand4 = new Cylinder();
        leftHand4.setHeight(0.4);
        leftHand4.setRadius(0.2);
        leftHand4.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand5 = new Cylinder();
        leftHand5.setHeight(0.4);
        leftHand5.setRadius(0.2);
        leftHand5.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand1 = new Cylinder();
        rightHand1.setHeight(0.10);
        rightHand1.setRadius(0.15);
        rightHand1.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand2 = new Cylinder();
        rightHand2.setHeight(0.10);
        rightHand2.setRadius(0.15);
        rightHand2.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand3 = new Cylinder();
        rightHand3.setHeight(0.10);
        rightHand3.setRadius(0.15);
        rightHand3.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand4 = new Cylinder();
        rightHand4.setHeight(0.10);
        rightHand4.setRadius(0.15);
        rightHand4.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand5 = new Cylinder();
        rightHand5.setHeight(0.10);
        rightHand5.setRadius(0.15);
        rightHand5.setMaterial(new PhongMaterial(Color.YELLOW));

        leftShoulder = new Sphere();
        leftShoulder.setRadius(0.33);
        leftShoulder.setMaterial(new PhongMaterial(Color.RED));

        rightShoulder = new Sphere();
        rightShoulder.setRadius(0.33);
        rightShoulder.setMaterial(new PhongMaterial(Color.GREEN));


        leftArmTop = new Cylinder();
        leftArmTop.setHeight(0.8);
        leftArmTop.setRadius(0.24);
        leftArmTop.setMaterial(new PhongMaterial(Color.RED));
        leftArmTop.getTransforms().add(rotateZ12);

        rightArmTop = new Cylinder();
        rightArmTop.setHeight(0.8);
        rightArmTop.setRadius(0.24);
        rightArmTop.setMaterial(new PhongMaterial(Color.GREEN));
        rightArmTop.getTransforms().add(rotateZ_12);

        leftArmDown = new Cylinder();
        leftArmDown.setHeight(0.8);
        leftArmDown.setRadius(0.24);
        leftArmDown.setMaterial(new PhongMaterial(Color.RED));
        leftArmDown.getTransforms().add(rotateZ12);
        leftArmDown.getTransforms().add(rotateX90);

        rightArmDown = new Cylinder();
        rightArmDown.setHeight(0.8);
        rightArmDown.setRadius(0.24);
        rightArmDown.setMaterial(new PhongMaterial(Color.GREEN));
        rightArmDown.getTransforms().add(rotateZ_12);
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

        rightKnee = new Cylinder();
        rightKnee.setRadius(0.25);
        rightKnee.setHeight(0.8);
        rightKnee.setMaterial(new PhongMaterial(Color.RED));
        rightKnee.getTransforms().add(rotateZ);

        leftButLock = new Box();
        leftButLock.setWidth(0.9);
        leftButLock.setDepth(0.75);
        leftButLock.setHeight(0.5);
        leftButLock.setMaterial(new PhongMaterial(Color.RED));

        rightButLock = new Box();
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

        nodes.add(leftHand1);
        nodes.add(leftHand2);
        nodes.add(leftHand3);
        nodes.add(leftHand4);
        nodes.add(leftHand5);

        /*
        nodes.add(rightHand1);
        nodes.add(rightHand2);
        nodes.add(rightHand3);
        nodes.add(rightHand4);
        nodes.add(rightHand5);*/


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

        leftShoulder.setTranslateX(volume.get(volume.size() - 1).getX() - 2.40);
        leftShoulder.setTranslateY(volume.get(volume.size() - 1).getY() - 3);
        leftShoulder.setTranslateZ(volume.get(volume.size() - 1).getZ());

        rightShoulder.setTranslateX(volume.get(volume.size() - 1).getX() - 0.65);
        rightShoulder.setTranslateY(volume.get(volume.size() - 1).getY() - 3);
        rightShoulder.setTranslateZ(volume.get(volume.size() - 1).getZ());

        leftArmTop.setTranslateX(volume.get(volume.size() - 1).getX() - 2.55);
        leftArmTop.setTranslateY(volume.get(volume.size() - 1).getY() - 2.75);
        leftArmTop.setTranslateZ(volume.get(volume.size() - 1).getZ());

        rightArmTop.setTranslateX(volume.get(volume.size() - 1).getX() - 0.5);
        rightArmTop.setTranslateY(volume.get(volume.size() - 1).getY() - 2.75);
        rightArmTop.setTranslateZ(volume.get(volume.size() - 1).getZ());

        leftArmDown.setTranslateX(volume.get(volume.size() - 1).getX() - 2.65);
        leftArmDown.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        leftArmDown.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.12);

        rightArmDown.setTranslateX(volume.get(volume.size() - 1).getX() - 0.35);
        rightArmDown.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        rightArmDown.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.12);


        leftHand1.setTranslateX(volume.get(volume.size() - 1).getX() - 2.65);
        leftHand1.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        leftHand1.setTranslateZ(volume.get(volume.size() - 1).getZ()  + 5);

        leftHand2.setTranslateX(volume.get(volume.size() - 1).getX() - 2.50);
        leftHand2.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        leftHand2.setTranslateZ(volume.get(volume.size() - 1).getZ() + 5 );

        leftHand3.setTranslateX(volume.get(volume.size() - 1).getX() - 2.45);
        leftHand3.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        leftHand3.setTranslateZ(volume.get(volume.size() - 1).getZ()  -3);

        leftHand4.setTranslateX(volume.get(volume.size() - 1).getX() - 2.40);
        leftHand4.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        leftHand4.setTranslateZ(volume.get(volume.size() - 1).getZ() + 5 );

        leftHand5.setTranslateX(volume.get(volume.size() - 1).getX() - 2.65);
        leftHand5.setTranslateY(volume.get(volume.size() - 1).getY() - 2.25);
        leftHand5.setTranslateZ(volume.get(volume.size() - 1).getZ()  -3);






        bodyMiddle.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        bodyMiddle.setTranslateY(volume.get(volume.size() - 1).getY() - 2.5);
        bodyMiddle.setTranslateZ(volume.get(volume.size() - 1).getZ());


        bodyLeft.setTranslateX(volume.get(volume.size() - 1).getX() - 2.25);
        bodyLeft.setTranslateY(volume.get(volume.size() - 1).getY() - 2.5);
        bodyLeft.setTranslateZ(volume.get(volume.size() - 1).getZ());

        bodyRight.setTranslateX(volume.get(volume.size() - 1).getX() - 0.75);
        bodyRight.setTranslateY(volume.get(volume.size() - 1).getY() - 2.5);
        bodyRight.setTranslateZ(volume.get(volume.size() - 1).getZ());


        pants.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        pants.setTranslateY(volume.get(volume.size() - 1).getY() - 1.40);
        pants.setTranslateZ(volume.get(volume.size() - 1).getZ());

        leftKnee.setTranslateX(volume.get(volume.size() - 1).getX() - 2.05);
        leftKnee.setTranslateY(volume.get(volume.size() - 1).getY() - 1.05);
        leftKnee.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.15);

        middlePants.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        middlePants.setTranslateY(volume.get(volume.size() - 1).getY() - 1.05);
        middlePants.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.15);

        rightKnee.setTranslateX(volume.get(volume.size() - 1).getX() - 0.95);
        rightKnee.setTranslateY(volume.get(volume.size() - 1).getY() - 1.05);
        rightKnee.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.15);


        leftButLock.setTranslateX(volume.get(volume.size() - 1).getX() - 2);
        leftButLock.setTranslateY(volume.get(volume.size() - 1).getY() - 1.05);
        leftButLock.setTranslateZ(volume.get(volume.size() - 1).getZ() + 0.12);

        rightButLock.setTranslateX(volume.get(volume.size() - 1).getX() - 1);
        rightButLock.setTranslateY(volume.get(volume.size() - 1).getY() - 1.05);
        rightButLock.setTranslateZ(volume.get(volume.size() - 1).getZ() + 0.12);


        leftLeg.setTranslateX(volume.get(volume.size() - 1).getX() - 2);
        leftLeg.setTranslateY(volume.get(volume.size() - 1).getY() - 0.30);
        leftLeg.setTranslateZ(volume.get(volume.size() - 1).getZ() + 0.12);

        rightLeg.setTranslateX(volume.get(volume.size() - 1).getX() - 1);
        rightLeg.setTranslateY(volume.get(volume.size() - 1).getY() - 0.30);
        rightLeg.setTranslateZ(volume.get(volume.size() - 1).getZ() + 0.12);

        leftFoot.setTranslateX(volume.get(volume.size() - 1).getX() - 2);
        leftFoot.setTranslateY(volume.get(volume.size() - 1).getY() + 0.35);
        leftFoot.setTranslateZ(volume.get(volume.size() - 1).getZ());

        rightFoot.setTranslateX(volume.get(volume.size() - 1).getX() - 1);
        rightFoot.setTranslateY(volume.get(volume.size() - 1).getY() + 0.35);
        rightFoot.setTranslateZ(volume.get(volume.size() - 1).getZ());

    }

    @Override
    public void cloneThePiece() {
        new Figurine(getX(), getY(), getZ());
    }
}