package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.utils.P3D;

import java.util.ArrayList;
import java.util.Arrays;

public class Volume extends ArrayList<P3D> {

    public void addPoint(double x,double y,double z){
        this.add(new P3D(x,y,z));
    }
    public void addPoint(P3D... Points){
        for(P3D Point: Arrays.asList(Points)){
            this.add(Point);
        }
    }

    public void removePoint(double x,double y,double z){
        this.remove(new P3D(x,y,z));
    }
    public void removePoint(P3D... Points){
        for(P3D Point: Arrays.asList(Points)){
            this.remove(Point);
        }
    }

    public String toString(){
        String s = "<";
        for(P3D p: this){
            s+=p+" ";
        }
        s+=">";
        return s;
    }

}
