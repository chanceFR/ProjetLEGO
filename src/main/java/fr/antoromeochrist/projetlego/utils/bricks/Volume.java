package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.utils.P3D;

import java.util.ArrayList;
import java.util.Set;

/**
 * Un Volume c'est un ensemble de point.
 * <p>
 * Cette classe hérite de ArrayList<P3D>
 * P3D est un point 3D.
 * <p>
 * La classe permet de générer des volumes à partir :
 * <p>
 * -la dimension de la brique
 * <p>
 * -un lieu de départ
 */
public class Volume extends ArrayList<P3D> {
    /**
     * La brique rentre t'-elle en collision avec des briques ?
     *
     * @param b      la brique
     * @param bricks ensemble
     */
    public static boolean volumeIntersection(Brick b, Set<Brick> bricks) {
        for (Brick brick : bricks) {
            if (!brick.equals(b)) {
                if (volumeIntersection(brick.getVolume(), b.getVolume())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourne s'il y a collision ou non entre deux volumes
     */
    public static boolean volumeIntersection(Volume a, Volume b) {
        for (P3D p1 : a) {
            for (P3D p2 : b) {
                if (p1.equals(p2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Générer le volume à partir du point de départ et de la dimension
     */
    public static Volume createAllVolume(P3D point, Dim dim) {
        Volume v = new Volume();
        for (int i = 0; i < dim.getWidth(); i++) {
            for (int j = 0; j < dim.getHeight(); j++) {
                for (int k = 0; k < dim.getDepth(); k++) {
                    P3D p = new P3D(point);
                    p = p.add(i, j, k);
                    v.add(p);
                }
            }
        }
        return v;
    }

    /**
     * Affichage du volume
     *
     * @return string
     */
    public String toString() {
        StringBuilder s = new StringBuilder("<");
        for (P3D p : this) {
            s.append(p).append(" ");
        }
        s.append(">");
        return s.toString();
    }
}