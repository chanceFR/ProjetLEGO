package fr.antoromeochrist.projetlego.utils;

import javafx.geometry.Point3D;

/**
 * Cette classe est un Point3d
 * <p>
 * Cette classe sert juste à modifier la méthode toString
 */
public class P3D extends Point3D {
    public P3D(double v, double v1, double v2) {
        super(v, v1, v2);
    }

    public P3D(P3D P3D) {
        super(P3D.getX(), P3D.getY(), P3D.getZ());
    }

    public String toString() {
        String s = "";
        if (this.getX() % 1 == 0) {
            s += "(" + (int) this.getX() + ";";
        } else {
            s += "(" + this.getX() + ";";
        }
        if (this.getY() % 1 == 0) {
            s += (int) this.getY() + ";";
        } else {
            s += this.getY() + ";";
        }
        if (this.getZ() % 1 == 0) {
            s += (int) this.getZ() + ")";
        } else {
            s += this.getZ() + ")";
        }
        return s;
    }

    /**
     * Ajouter un point avec des coordonnées
     *
     */
    public P3D add(double a, double b, double c) {
        return new P3D(this.getX() + a, this.getY() + b, this.getZ() + c);
    }
}
