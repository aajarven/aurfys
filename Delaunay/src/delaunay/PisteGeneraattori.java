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
     * Generoi ellipsoidin x^2/a^2 + y^2/b^2 + z^2/c^2 = 1 pinnalle pistejoukon
     * siten, että pisteiden välille tehty Delaunayn kolmiointi tuottaa
     * latitudeittain eteneviä kolmiorivejä
     *
     * @param kerroksia kuinka moneen kerrokseen pallo jaetaan
     * @param a ellipsoidin yhtälön a
     * @param b ellipsoidin yhtälön b
     * @param c ellipsoidin yhtälön c
     * @return
     */
    static ArrayList<Piste> generoiLatitudeittain(int kerroksia, double a, double b, double c) {
        if (kerroksia % 2 != 0 || kerroksia < 2) {
            System.out.println("Kerroksia on oltava kahdella jaollinen määrä ja vähintään kaksi");
            System.exit(0);
        }
        
        ArrayList<Piste> palautus = new ArrayList<Piste>();
        palautus.add(new Piste(0, 0, r(a, b, c, 0, Math.PI)));
        palautus.add(new Piste(Math.PI, 0, r(a, b, c, 0, Math.PI)));
        
        double theta = 0;
        double dTheta = Math.PI / kerroksia;
        for (int kerros = 1; kerros <= kerroksia / 2; kerros++) {
            theta += dTheta;
            double dFii = Math.PI / (kerros * 2.0);
            for (double fii = 0; fii < 2 * Math.PI; fii += dFii) {
                double r = r(a, b, c, theta, fii);
                Piste lisattava = new Piste(theta, fii, r);
                if (!palautus.contains(lisattava)) {
                    palautus.add(lisattava);
                }
                if (Math.abs(theta - Math.PI / 2) > 0.0001) { // ekvaattorin pisteitä ei lisätä kahdesti
                    lisattava = new Piste(Math.PI - theta, fii, r);
                    //System.out.println(lisattava);
                    if (!palautus.contains(lisattava)) {
                        palautus.add(lisattava);
                    }
                }
            }
        }
        return palautus;
    }

    private static double r(double a, double b, double c, double theta, double fii) {
        return a * b * c / Math.sqrt(b * b * c * c * Math.pow(Math.sin(theta), 2) * Math.pow(Math.cos(fii), 2) + a * a * c * c * Math.pow(Math.sin(theta), 2) * Math.pow(Math.sin(fii), 2) + a * a * b * b * Math.pow(Math.cos(theta), 2));
    }
}
