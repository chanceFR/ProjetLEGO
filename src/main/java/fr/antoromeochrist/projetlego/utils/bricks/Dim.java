package fr.antoromeochrist.projetlego.utils.bricks;

public class Dim {

    private int a; //x axis
    private int b; //y axix
    private int c; //z axis
    //              x         z
    public Dim(int width,int depth){
        this.a = width;
        this.b = 1;
        this.c = depth;
    }
    //              x         z           y
    public Dim(int width,int depth,int height){
        this.a = width;
        this.c = depth;
        this.b = height;
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
