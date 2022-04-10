package fr.antoromeochrist.projetlego.utils.bricks;

public class PointInt {

    private int x;
    private int y;
    private int z;

    public PointInt(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public PointInt setX(int x) {
        this.x = x;
        return this;
    }
    public PointInt setY(int y) {
        this.y = y;
        return this;
    }

    public PointInt setZ(int z) {
        this.z = z;
        return this;
    }

    public PointInt set(int x,int y,int z){
        setX(x);
        setY(y);
        setZ(z);
        return this;
    }

    public String toString(){
        return "("+x+";"+y+";"+z+")";
    }

    public boolean same(PointInt pointInt){
        return this.x==pointInt.getX() && this.y==pointInt.getY() && this.z==pointInt.getZ();
    }
}
