package fr.antoromeochrist.projetlego.utils.bricks;

public class Dim {

    private int a; //x axis
    private int b; //y axix
    private int c; //z axis

    public Dim(int width,int depth){
        this.a = width*10;
        this.b = 10;
        this.c = depth*10;
    }

    public Dim(int width,int depth,int height){
        this.a = width*10;
        this.c = depth*10;
        this.b = height*10;
    }

    public int getW() {
        return a;
    }

    public int getH() {
        return b;
    }

    public int getD() {
        return c;
    }
}
