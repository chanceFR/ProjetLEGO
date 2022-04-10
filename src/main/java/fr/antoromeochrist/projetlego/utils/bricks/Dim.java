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

    public int getSizeOfPointToNeed(){
        int sum=0;
        for(int i =0;i<a;i++){
            for(int j =0;j<b;j++){
                for(int k =0;k<c;k++){
                    sum++;
                }
            }
        }
        return sum;
    }
    public int getWeight() {
        return a;
    }

    public int getHeight() {
        return b;
    }

    public int getDepth() {
        return c;
    }

    public String toString(){
        String s =""+a;
        s+=" X "+c;
        if(b != 1){
            s+=" X "+b;
        }
        return s;
    }
}

