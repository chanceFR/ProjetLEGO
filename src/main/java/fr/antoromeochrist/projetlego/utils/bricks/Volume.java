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
    public static boolean volumeIntersection(Brick b, ArrayList<Brick> bricks){
        for(Brick brick : bricks){
            if(!brick.getID().equals(b.getID())) {
                if (volumeIntersection(brick.getVolume(), b.getVolume())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean volumeIntersection(Volume v, ArrayList<Brick> bricks){
        for(Brick brick : bricks){
                if (volumeIntersection(brick.getVolume(), v)) {
                    return true;
                }
        }
        return false;
    }

    public static boolean volumeIntersection(Volume a,Volume b){
        for(P3D p1 : a){
            for(P3D p2: b){
                if(p1.equals(p2)) return true;
            }
        }
        return false;
    }

    public static Volume createAllVolume(P3D point,Dim dim){
        Volume v = new Volume();
        for(int i = 0;i<dim.getWeight();i++){
            for(int j = 0;j<dim.getHeight();j++){
                for(int k = 0;k<dim.getDepth();k++){
                    P3D p = new P3D(point);
                    p=p.add(i,j,k);
                    v.add(p);
                }
            }
        }
        return v;
    }

}
