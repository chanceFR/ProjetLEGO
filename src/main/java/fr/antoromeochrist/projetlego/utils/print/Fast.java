package fr.antoromeochrist.projetlego.utils.print;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
/**
 *
 * Cette classe permet de simplifier le débug du programme
 * Elle utilise la reflection.
 */

public class Fast {
    public static void log(String m){
        System.out.println(m);
    }
    public static void log(int i) {
        log("Debug "+i);
    };

    /*
    * Cette permet de récupérer le nomde d'une variable
    * */

    public static void checkNull(String loc,Object... os){
        System.out.println("Location: "+loc);
        for(Object o: Arrays.asList(os)){


            if(o==null)System.out.println(o.getClass()+" is null"); else System.out.println(o.getClass()+" is not null");
        }
    }



    public static void checkSize(int i,Object o) {
        Method methodToFind = null;
        String s = "";
        try {
            methodToFind = o.getClass().getMethod("size", (Class<?>[]) null);
            s = "Size: " + methodToFind.invoke(o, (Object[]) null);
        } catch(Exception e){

        }

        try {
            methodToFind = o.getClass().getMethod("length", (Class<?>[]) null);
            s = "Lenght: " + methodToFind.invoke(o, (Object[]) null);
        } catch (Exception e) {
        }
        if(o==null){
            s="Size: object null";
            System.out.println(s);
            return;
        }
        String[] list = o.getClass().toString().substring(6).split("\\.");

        String type = list[list.length-1];

        if (!s.equals("")) {
            System.out.println("("+type+") Debug "+i+" "+s);
        }
    }


}
