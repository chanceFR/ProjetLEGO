package fr.antoromeochrist.projetlego.pieces;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import fr.antoromeochrist.projetlego.utils.bricks.Grid;
import fr.antoromeochrist.projetlego.utils.bricks.MinBrick;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public abstract class Piece extends Brick {
    //composant de la pièce
    protected ArrayList<Node> nodes;
    protected boolean hidePiece;

    public Piece(Dim dim, double x, double y, double z, Color c) {
        this(dim, x, y, z, c, false);
    }

    public Piece(Dim dim, double x, double y, double z, Color c, boolean b) {
        super(dim, x, y, z, c, b);
        nodes = new ArrayList<>();
        for (MinBrick mb : this) {
            mb.setOpacity(0);
            mb.getCylinder().setOpacity(0);
        }
    }

    public void moveThePiece(double x, double y, double z) {
        this.move(x, y, z);
        updateNodesLocation();
    }

    public void moveThePiece(Grid grid) {
        moveThePiece(grid.getMouseCoors()[0], grid.getMouseCoors()[1], grid.getMouseCoors()[2]);
    }

    public void upThePiece() {
        this.up();
        updateNodesLocation();
    }


    public void downThePiece() {
        this.down();
        updateNodesLocation();
    }


    public void rightXThePiece() {
        this.rightX();
        updateNodesLocation();
    }


    public void leftXThePiece() {
        this.leftX();
        updateNodesLocation();
    }


    public void rightZThePiece() {
        this.rightZ();
        updateNodesLocation();
    }


    public void leftZThePiece() {
        this.leftZ();
        updateNodesLocation();
    }

    public Rotate addRotate(Point3D axis, double angle) {
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(0);
        rotate.setPivotY(0);
        rotate.setPivotZ(0);
        rotate.setAxis(axis);
        return rotate;
    }

    public void hideThePiece(boolean b) {
        hidePiece = b;
        if (hidePiece) {
            for (Node node : nodes) node.setOpacity(0);
            this.setBorderColor(Color.web("#808080"));
        } else {
            for (Node node : nodes) node.setOpacity(100);
            updateBorder();
        }
        setViewStatusHide(b);
    }

    public void removeThePiece() {
        if (!isDelete()) {
            Controller.me.group.getChildren().removeAll(nodes);
        }
        this.remove();
    }

    public boolean thePieceIsNotHide() {
        return !hidePiece;
    }

    public abstract void cloneThePiece();

    public abstract void updateNodesLocation();
}
