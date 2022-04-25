package fr.antoromeochrist.projetlego.utils;

/**
 * Cette classe permet de stocker un angle et une durée
 */
public class DurationAngle {

    /**
     * attribtus
     */
    private float angle, duration;

    /**
     * Constructeur
     *
     * @param angle
     * @param duration
     */
    public DurationAngle(float angle, float duration) {
        this.duration = duration;
        this.angle = angle;
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