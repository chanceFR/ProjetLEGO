package fr.antoromeochrist.projetlego.utils;

import javafx.geometry.Point3D;

public class P3D extends Point3D {
    public P3D(double v, double v1, double v2) {
        super(v, v1, v2);
    }
    public String toString(){
        String s = "";
        if(this.getX() % 1 == 0){
            s+="("+(int)this.getX()+";";
        }else{
            s+="("+this.getX()+";";
        }
        if(this.getY() % 1 == 0){
            s+=(int)this.getY()+";";
        }else{
            s+=this.getY()+";";
        }
        if(this.getZ() % 1 == 0){
            s+=(int)this.getZ()+")";
        }else{
            s+=this.getZ()+")";
        }
        return s;
    }
}
