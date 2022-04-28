package fr.antoromeochrist.projetlego.utils.print;

import java.lang.reflect.Method;

/**
 *
 * Cette classe permet de simplifier le débug du programme
 * Elle utilise la reflection.
 */

public class Fast {
    public static void log(String m){
        System.out.println(m);
    }

    /*
    * Cette permet de récupérer le nomde d'une variable
    * */


    public static void checkSize(int i,Object o) {
        Method methodToFind;
        String s = "";
        try {
            methodToFind = o.getClass().getMethod("size", (Class<?>[]) null);
            s = "Size: " + methodToFind.invoke(o, (Object[]) null);
        } catch(Exception ignored){

        }

        try {
            methodToFind = o.getClass().getMethod("length", (Class<?>[]) null);
            s = "Lenght: " + methodToFind.invoke(o, (Object[]) null);
        } catch (Exception ignored) {
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
