package fr.antoromeochrist.projetlego.utils;

/**
 * Cette classe permet de stocker un angle et une durée
 */
public record DurationAngle(float angle, float duration) {

    /**
     * Constructeur
     */
    public DurationAngle {
    }

    /**
     * Avoir l'angle
     *
     * @return angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Avoir la durée
     *
     * @return durée
     */
    public float getDuration() {
        return duration;
    }
}