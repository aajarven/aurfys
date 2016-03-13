/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author anni
 */
public class PisteGeneraattori {
    
    /**
     * Generoi ellipsoidin x^2/a^2 + y^2/b^2 + z^2/c^2 = 1 pinnalle pistejoukon siten, että pisteiden välille tehty Delaunayn kolmiointi tuottaa latitudeittain eteneviä kolmiorivejä
     * @param kerroksia kuinka moneen kerrokseen pallo jaetaan
     * @param a ellipsoidin yhtälön a
     * @param b ellipsoidin yhtälön b
     * @param c ellipsoidin yhtälön c
     * @return 
     */
    static ArrayList<Piste> generoiLatitudeittain(int kerroksia, double a, double b, double c){
        if(kerroksia%2 != 0){
            System.out.println("Kerroksia on oltava kahdella jaollinen määrä");
            System.exit(0);
        }
        ArrayList<Piste> palautus = new ArrayList<Piste>();
        for(int kerros=1; kerros<=kerroksia/2; kerros++){
            for(int i=0; i<kerros*4; i++){
                double theta = i*2*Math.PI/(kerros*4);
                double fii = Math.PI/2-kerros*Math.PI/kerroksia; // mieti tämä
                double r = r(a, b, c, theta, fii);
                Piste lisattava = new Piste(theta, fii, r);
                if (!palautus.contains(lisattava)){
                    palautus.add(lisattava);
                }
                lisattava = new Piste(theta, -fii, r);
                if (!palautus.contains(lisattava)){
                    palautus.add(lisattava);
                }
            }
        }
        return palautus;
    }
    
    private static double r(double a, double b, double c, double theta, double fii){
        return a*b*c/Math.sqrt(b*b*c*c*Math.pow(Math.sin(theta), 2)*Math.pow(Math.cos(fii), 2)+a*a*c*c*Math.pow(Math.sin(theta), 2)*Math.pow(Math.sin(fii), 2)+a*a*b*b*Math.pow(Math.cos(theta), 2));
    }
}
