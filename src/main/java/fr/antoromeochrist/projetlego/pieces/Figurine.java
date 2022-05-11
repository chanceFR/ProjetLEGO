package fr.antoromeochrist.projetlego.pieces;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Figurine extends Brick {


    private final Cylinder mouth;
    private final Cylinder mouth2;
    private final Cylinder mouth3;
    private final Sphere leftEyesReflect;
    private final Sphere rightEyesReflect;
    private final Sphere leftEyes;
    private final Sphere rightEyes;

    private final Cylinder headTop;
    private final Cylinder head;
    private final Cylinder neck;

    private final Cylinder leftHand1;

    private final Cylinder leftHand2;
    private final Cylinder leftHand3;

    private final Cylinder leftHand4;
    private final Cylinder leftHand5;

    private final Cylinder leftHand6;
    private final Cylinder leftHand7;

    private final Cylinder leftHand8;
    private final Cylinder leftHand9;

    private final Cylinder leftHand10;
    private final Cylinder leftHand11;

    private final Cylinder leftHand12;
    private final Cylinder leftHand13;

    private final Cylinder leftHand14;
    private final Cylinder leftHand15;

    private final Cylinder leftHand16;
    private final Cylinder leftHand17;

    private final Cylinder leftHand18;
    private final Cylinder leftHand19;

    private final Cylinder leftHand20;
    private final Cylinder leftHand21;

    private final Cylinder rightHand1;

    private final Cylinder rightHand2;
    private final Cylinder rightHand3;

    private final Cylinder rightHand4;
    private final Cylinder rightHand5;

    private final Cylinder rightHand6;
    private final Cylinder rightHand7;

    private final Cylinder rightHand8;
    private final Cylinder rightHand9;

    private final Cylinder rightHand10;
    private final Cylinder rightHand11;

    private final Cylinder rightHand12;
    private final Cylinder rightHand13;

    private final Cylinder rightHand14;
    private final Cylinder rightHand15;

    private final Cylinder rightHand16;
    private final Cylinder rightHand17;

    private final Cylinder rightHand18;
    private final Cylinder rightHand19;

    private final Cylinder rightHand20;
    private final Cylinder rightHand21;

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
        super(new Dim(4, 1, 5), x, y, z, Color.GRAY,true);
        this.setPieceType("Figurine");
        Rotate rotateZ = addRotate(Rotate.Z_AXIS, 90);

        Rotate rotateZ7 = addRotate(Rotate.Z_AXIS, 7);

        Rotate rotateZ_7 = addRotate(Rotate.Z_AXIS, -7);

        Rotate rotateZ12 = addRotate(Rotate.Z_AXIS, 13);

        Rotate rotateZ_12 = addRotate(Rotate.Z_AXIS, -13);

        Rotate rotateX90 = addRotate(Rotate.X_AXIS, -75);

        mouth = new Cylinder();
        mouth.setHeight(0.18);
        mouth.setRadius(0.012);
        mouth.setMaterial(new PhongMaterial(Color.BLACK));
        mouth.getTransforms().add(rotateZ);

        mouth2 = new Cylinder();
        mouth2.setHeight(0.07);
        mouth2.setRadius(0.012);
        mouth2.setMaterial(new PhongMaterial(Color.BLACK));
        mouth2.getTransforms().add(rotateZ);

        mouth3 = new Cylinder();
        mouth3.setHeight(0.07);
        mouth3.setRadius(0.012);
        mouth3.setMaterial(new PhongMaterial(Color.BLACK));
        mouth3.getTransforms().add(rotateZ);


        leftEyesReflect = new Sphere();
        leftEyesReflect.setRadius(0.025);
        leftEyesReflect.setMaterial(new PhongMaterial(Color.WHITE));

        rightEyesReflect = new Sphere();
        rightEyesReflect.setRadius(0.025);
        rightEyesReflect.setMaterial(new PhongMaterial(Color.WHITE));


        leftEyes = new Sphere();
        leftEyes.setRadius(0.06);
        leftEyes.setMaterial(new PhongMaterial(Color.BLACK));

        rightEyes = new Sphere();
        rightEyes.setRadius(0.06);
        rightEyes.setMaterial(new PhongMaterial(Color.BLACK));

        headTop = new Cylinder();
        headTop.setRadius(0.2);
        headTop.setMaterial(new PhongMaterial(Color.YELLOW));
        headTop.setHeight(0.5);

        head = new Cylinder();
        head.setRadius(0.5);
        head.setHeight(0.70);
        head.setMaterial(new PhongMaterial(Color.YELLOW));

        neck = new Cylinder();
        neck.setHeight(0.5);
        neck.setRadius(0.15);
        neck.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand1 = new Cylinder();
        leftHand1.setHeight(0.4);
        leftHand1.setRadius(0.15);
        leftHand1.setMaterial(new PhongMaterial(Color.YELLOW));
        leftHand1.getTransforms().add(rotateX90);

        leftHand2 = new Cylinder();
        leftHand2.setHeight(0.2);
        leftHand2.setRadius(0.02);
        leftHand2.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand3 = new Cylinder();
        leftHand3.setHeight(0.2);
        leftHand3.setRadius(0.02);
        leftHand3.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand4 = new Cylinder();
        leftHand4.setHeight(0.2);
        leftHand4.setRadius(0.02);
        leftHand4.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand5 = new Cylinder();
        leftHand5.setHeight(0.2);
        leftHand5.setRadius(0.02);
        leftHand5.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand6 = new Cylinder();
        leftHand6.setHeight(0.2);
        leftHand6.setRadius(0.02);
        leftHand6.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand7 = new Cylinder();
        leftHand7.setHeight(0.2);
        leftHand7.setRadius(0.02);
        leftHand7.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand8 = new Cylinder();
        leftHand8.setHeight(0.2);
        leftHand8.setRadius(0.02);
        leftHand8.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand9 = new Cylinder();
        leftHand9.setHeight(0.2);
        leftHand9.setRadius(0.02);
        leftHand9.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand10 = new Cylinder();
        leftHand10.setHeight(0.2);
        leftHand10.setRadius(0.02);
        leftHand10.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand11 = new Cylinder();
        leftHand11.setHeight(0.2);
        leftHand11.setRadius(0.02);
        leftHand11.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand12 = new Cylinder();
        leftHand12.setHeight(0.2);
        leftHand12.setRadius(0.02);
        leftHand12.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand13 = new Cylinder();
        leftHand13.setHeight(0.2);
        leftHand13.setRadius(0.02);
        leftHand13.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand14 = new Cylinder();
        leftHand14.setHeight(0.2);
        leftHand14.setRadius(0.02);
        leftHand14.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand15 = new Cylinder();
        leftHand15.setHeight(0.2);
        leftHand15.setRadius(0.02);
        leftHand15.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand16 = new Cylinder();
        leftHand16.setHeight(0.2);
        leftHand16.setRadius(0.02);
        leftHand16.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand17 = new Cylinder();
        leftHand17.setHeight(0.2);
        leftHand17.setRadius(0.02);
        leftHand17.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand18 = new Cylinder();
        leftHand18.setHeight(0.2);
        leftHand18.setRadius(0.02);
        leftHand18.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand19 = new Cylinder();
        leftHand19.setHeight(0.2);
        leftHand19.setRadius(0.02);
        leftHand19.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand20 = new Cylinder();
        leftHand20.setHeight(0.2);
        leftHand20.setRadius(0.02);
        leftHand20.setMaterial(new PhongMaterial(Color.YELLOW));

        leftHand21 = new Cylinder();
        leftHand21.setHeight(0.2);
        leftHand21.setRadius(0.02);
        leftHand21.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand1 = new Cylinder();
        rightHand1.setHeight(0.4);
        rightHand1.setRadius(0.15);
        rightHand1.setMaterial(new PhongMaterial(Color.YELLOW));
        rightHand1.getTransforms().add(rotateX90);

        rightHand2 = new Cylinder();
        rightHand2.setHeight(0.2);
        rightHand2.setRadius(0.02);
        rightHand2.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand3 = new Cylinder();
        rightHand3.setHeight(0.2);
        rightHand3.setRadius(0.02);
        rightHand3.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand4 = new Cylinder();
        rightHand4.setHeight(0.2);
        rightHand4.setRadius(0.02);
        rightHand4.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand5 = new Cylinder();
        rightHand5.setHeight(0.2);
        rightHand5.setRadius(0.02);
        rightHand5.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand6 = new Cylinder();
        rightHand6.setHeight(0.2);
        rightHand6.setRadius(0.02);
        rightHand6.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand7 = new Cylinder();
        rightHand7.setHeight(0.2);
        rightHand7.setRadius(0.02);
        rightHand7.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand8 = new Cylinder();
        rightHand8.setHeight(0.2);
        rightHand8.setRadius(0.02);
        rightHand8.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand9 = new Cylinder();
        rightHand9.setHeight(0.2);
        rightHand9.setRadius(0.02);
        rightHand9.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand10 = new Cylinder();
        rightHand10.setHeight(0.2);
        rightHand10.setRadius(0.02);
        rightHand10.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand11 = new Cylinder();
        rightHand11.setHeight(0.2);
        rightHand11.setRadius(0.02);
        rightHand11.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand12 = new Cylinder();
        rightHand12.setHeight(0.2);
        rightHand12.setRadius(0.02);
        rightHand12.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand13 = new Cylinder();
        rightHand13.setHeight(0.2);
        rightHand13.setRadius(0.02);
        rightHand13.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand14 = new Cylinder();
        rightHand14.setHeight(0.2);
        rightHand14.setRadius(0.02);
        rightHand14.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand15 = new Cylinder();
        rightHand15.setHeight(0.2);
        rightHand15.setRadius(0.02);
        rightHand15.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand16 = new Cylinder();
        rightHand16.setHeight(0.2);
        rightHand16.setRadius(0.02);
        rightHand16.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand17 = new Cylinder();
        rightHand17.setHeight(0.2);
        rightHand17.setRadius(0.02);
        rightHand17.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand18 = new Cylinder();
        rightHand18.setHeight(0.2);
        rightHand18.setRadius(0.02);
        rightHand18.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand19 = new Cylinder();
        rightHand19.setHeight(0.2);
        rightHand19.setRadius(0.02);
        rightHand19.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand20 = new Cylinder();
        rightHand20.setHeight(0.2);
        rightHand20.setRadius(0.02);
        rightHand20.setMaterial(new PhongMaterial(Color.YELLOW));

        rightHand21 = new Cylinder();
        rightHand21.setHeight(0.2);
        rightHand21.setRadius(0.02);
        rightHand21.setMaterial(new PhongMaterial(Color.YELLOW));

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
        leftArmDown.setHeight(0.6);
        leftArmDown.setRadius(0.24);
        leftArmDown.setMaterial(new PhongMaterial(Color.RED));
        leftArmDown.getTransforms().add(rotateZ12);
        leftArmDown.getTransforms().add(rotateX90);

        rightArmDown = new Cylinder();
        rightArmDown.setHeight(0.6);
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

        nodes.add(mouth);
        nodes.add(mouth2);
        nodes.add(mouth3);
        nodes.add(leftEyesReflect);
        nodes.add(rightEyesReflect);
        nodes.add(leftEyes);
        nodes.add(rightEyes);
        nodes.add(headTop);
        nodes.add(head);
        nodes.add(neck);
        nodes.add(leftHand1);
        nodes.add(leftHand2);
        nodes.add(leftHand3);
        nodes.add(leftHand4);
        nodes.add(leftHand5);
        nodes.add(leftHand6);
        nodes.add(leftHand7);
        nodes.add(leftHand8);
        nodes.add(leftHand9);
        nodes.add(leftHand10);
        nodes.add(leftHand11);
        nodes.add(leftHand12);
        nodes.add(leftHand13);
        nodes.add(leftHand14);
        nodes.add(leftHand15);
        nodes.add(leftHand16);
        nodes.add(leftHand17);
        nodes.add(leftHand18);
        nodes.add(leftHand19);
        nodes.add(leftHand20);
        nodes.add(leftHand21);

        nodes.add(rightHand1);
        nodes.add(rightHand2);
        nodes.add(rightHand3);
        nodes.add(rightHand4);
        nodes.add(rightHand5);
        nodes.add(rightHand6);
        nodes.add(rightHand7);
        nodes.add(rightHand8);
        nodes.add(rightHand9);
        nodes.add(rightHand10);
        nodes.add(rightHand11);
        nodes.add(rightHand12);
        nodes.add(rightHand13);
        nodes.add(rightHand14);
        nodes.add(rightHand15);
        nodes.add(rightHand16);
        nodes.add(rightHand17);
        nodes.add(rightHand18);
        nodes.add(rightHand19);
        nodes.add(rightHand20);
        nodes.add(rightHand21);

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

    public void updateNodesLocation() {

        double ecart = 2.35;

        mouth2.setTranslateX(volume.get(volume.size() - 1).getX() - 1.59);
        mouth2.setTranslateY(volume.get(volume.size() - 1).getY() - 3.8);
        mouth2.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.48);

        mouth3.setTranslateX(volume.get(volume.size() - 1).getX() - 1.39);
        mouth3.setTranslateY(volume.get(volume.size() - 1).getY() - 3.8);
        mouth3.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.48);

        mouth.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        mouth.setTranslateY(volume.get(volume.size() - 1).getY() - 3.78);
        mouth.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.495);


        leftEyesReflect.setTranslateX(volume.get(volume.size() - 1).getX() - 1.65);
        leftEyesReflect.setTranslateY(volume.get(volume.size() - 1).getY() - 4.2);
        leftEyesReflect.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.485);

        rightEyesReflect.setTranslateX(volume.get(volume.size() - 1).getX() - 1.35);
        rightEyesReflect.setTranslateY(volume.get(volume.size() - 1).getY() - 4.2);
        rightEyesReflect.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.485);

        leftEyes.setTranslateX(volume.get(volume.size() - 1).getX() - 1.65);
        leftEyes.setTranslateY(volume.get(volume.size() - 1).getY() - 4.2);
        leftEyes.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.44);

        rightEyes.setTranslateX(volume.get(volume.size() - 1).getX() - 1.35);
        rightEyes.setTranslateY(volume.get(volume.size() - 1).getY() - 4.2);
        rightEyes.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.44);


        headTop.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        headTop.setTranslateY(volume.get(volume.size() - 1).getY() - 4.2);
        headTop.setTranslateZ(volume.get(volume.size() - 1).getZ());


        head.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        head.setTranslateY(volume.get(volume.size() - 1).getY() - 4);
        head.setTranslateZ(volume.get(volume.size() - 1).getZ());

        neck.setTranslateX(volume.get(volume.size() - 1).getX() - 1.5);
        neck.setTranslateY(volume.get(volume.size() - 1).getY() - 3.8);
        neck.setTranslateZ(volume.get(volume.size() - 1).getZ());


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

        leftHand1.setTranslateX(volume.get(volume.size() - 1).getX() - 2.68);
        leftHand1.setTranslateY(volume.get(volume.size() - 1).getY() - 2.1);
        leftHand1.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.5);


        leftHand2.setTranslateX(volume.get(volume.size() - 1).getX() - 2.80);
        leftHand2.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand2.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.7);
        leftHand3.setTranslateX(volume.get(volume.size() - 1).getX() - 2.55);
        leftHand3.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand3.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.7);

        leftHand4.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82);
        leftHand4.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand4.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.72);
        leftHand5.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53);
        leftHand5.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand5.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.72);

        leftHand6.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82);
        leftHand6.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand6.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.74);
        leftHand7.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53);
        leftHand7.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand7.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.74);

        leftHand8.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82);
        leftHand8.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand8.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.76);
        leftHand9.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53);
        leftHand9.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand9.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.76);

        leftHand10.setTranslateX(volume.get(volume.size() - 1).getX() - 2.83);
        leftHand10.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand10.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.78);
        leftHand11.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52);
        leftHand11.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand11.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.78);

        leftHand12.setTranslateX(volume.get(volume.size() - 1).getX() - 2.83);
        leftHand12.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand12.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.8);
        leftHand13.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52);
        leftHand13.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand13.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.8);

        leftHand14.setTranslateX(volume.get(volume.size() - 1).getX() - 2.83);
        leftHand14.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand14.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.82);
        leftHand15.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52);
        leftHand15.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand15.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.82);

        leftHand16.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82);
        leftHand16.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand16.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.84);
        leftHand17.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53);
        leftHand17.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand17.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.84);

        leftHand18.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82);
        leftHand18.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand18.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.86);
        leftHand19.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52);
        leftHand19.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand19.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.86);

        leftHand20.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82);
        leftHand20.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand20.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.88);
        leftHand21.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52);
        leftHand21.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        leftHand21.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.88);

        rightHand1.setTranslateX(volume.get(volume.size() - 1).getX() - 2.68 + ecart);
        rightHand1.setTranslateY(volume.get(volume.size() - 1).getY() - 2.1);
        rightHand1.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.5);

        rightHand2.setTranslateX(volume.get(volume.size() - 1).getX() - 2.80 + ecart);
        rightHand2.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand2.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.7);
        rightHand3.setTranslateX(volume.get(volume.size() - 1).getX() - 2.55 + ecart);
        rightHand3.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand3.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.7);

        rightHand4.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82 + ecart);
        rightHand4.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand4.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.72);
        rightHand5.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53 + ecart);
        rightHand5.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand5.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.72);

        rightHand6.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82 + ecart);
        rightHand6.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand6.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.74);
        rightHand7.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53 + ecart);
        rightHand7.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand7.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.74);

        rightHand8.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82 + ecart);
        rightHand8.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand8.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.76);
        rightHand9.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53 + ecart);
        rightHand9.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand9.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.76);

        rightHand10.setTranslateX(volume.get(volume.size() - 1).getX() - 2.83 + ecart);
        rightHand10.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand10.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.78);
        rightHand11.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52 + ecart);
        rightHand11.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand11.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.78);

        rightHand12.setTranslateX(volume.get(volume.size() - 1).getX() - 2.83 + ecart);
        rightHand12.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand12.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.8);
        rightHand13.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52 + ecart);
        rightHand13.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand13.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.8);

        rightHand14.setTranslateX(volume.get(volume.size() - 1).getX() - 2.83 + ecart);
        rightHand14.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand14.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.82);
        rightHand15.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52 + ecart);
        rightHand15.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand15.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.82);

        rightHand16.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82 + ecart);
        rightHand16.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand16.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.84);
        rightHand17.setTranslateX(volume.get(volume.size() - 1).getX() - 2.53 + ecart);
        rightHand17.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand17.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.84);

        rightHand18.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82 + ecart);
        rightHand18.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand18.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.86);
        rightHand19.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52 + ecart);
        rightHand19.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand19.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.86);

        rightHand20.setTranslateX(volume.get(volume.size() - 1).getX() - 2.82 + ecart);
        rightHand20.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand20.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.88);
        rightHand21.setTranslateX(volume.get(volume.size() - 1).getX() - 2.52 + ecart);
        rightHand21.setTranslateY(volume.get(volume.size() - 1).getY() - 2);
        rightHand21.setTranslateZ(volume.get(volume.size() - 1).getZ() - 0.88);


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
}