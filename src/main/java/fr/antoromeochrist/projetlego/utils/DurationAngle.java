package fr.antoromeochrist.projetlego.utils;

public class DurationAngle {
    private float angle;
    private float duration;
    public DurationAngle( float angle, float duration){
        this.duration=duration;
        this.angle=angle;
    }

    public float getAngle() {
        return angle;
    }

    public float getDuration() {
        return duration;
    }
}
