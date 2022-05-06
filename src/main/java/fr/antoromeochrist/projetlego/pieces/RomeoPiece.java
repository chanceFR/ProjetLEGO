package fr.antoromeochrist.projetlego.pieces;

import fr.antoromeochrist.projetlego.utils.bricks.Dim;
import javafx.scene.paint.Color;

public class RomeoPiece extends Piece{

    /*
    *
    * Attributs
    *
    * */


    public RomeoPiece(Dim dim, double x, double y, double z, Color c) {
        super(dim, x, y, z, c);

        /*
        * Création des attributs (à faire qu'une fois)
        * et appliquer rotation si besoin
        *
        * /!\ pas de coordoorné pour les placer
        * */
        //updateNodesLocation(); prémier spawn
        //Controller.me.group.getChildren().addAll(nodes);
    }

    @Override
    public void cloneThePiece() {

    }

    @Override
    public void updateNodesLocation() {

        /*objet qui bouge, les objets qui le compose se remettent bien*/

    }
}
