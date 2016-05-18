/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kirkkaus;

import delaunay.Piste;
import delaunay.QuickhullKolmio;
import delaunay.Vektori3D;

/**
 *
 * @author anni
 */
public class LommelSeeliger {
    public static double kirkkaus(QuickhullKolmio kolmio, Vektori3D havaitsijanSuunta, Vektori3D valonlahteenSuunta, double albedo){
        Vektori3D normaali = normaaliUlospain(kolmio);
        double i = vektorienValinenKulma(valonlahteenSuunta,normaali);
        double e = vektorienValinenKulma(normaali, havaitsijanSuunta);
        if (i<-Math.PI/2 || i>Math.PI/2){
            return 0;
        } else if (e<-Math.PI/2 || e>Math.PI/2){
            return 0;
        }
        return 0.25*albedo/(Math.cos(i)+Math.cos(e));
    }
    
    /**
     * Palauttaa annetun kolmion sen normaalin, joka osoittaa poispäin origosta
     * @param k kolmio
     * @return kolmion normaalivektori
     */
    private static Vektori3D normaaliUlospain(QuickhullKolmio k){
        Piste origo = new Piste(0, 0, 0);
        Piste p1 = k.getP1();
        Piste p2 = k.getP2();
        Piste p3 = k.getP3();
        Vektori3D v1 = p1.vektoriPisteeseen(p2);
        Vektori3D v2 = p1.vektoriPisteeseen(p3);
        Vektori3D v3 = p3.vektoriPisteeseen(p1);
        Vektori3D verrokki = origo.vektoriPisteeseen(p1);
        Vektori3D normaali1 = v1.ristitulo(v2);
        if (verrokki.pistetulo(normaali1)>=0){
            return normaali1;
        } else {
            return v1.ristitulo(v3);
        }
    }
    
    private static double vektorienValinenKulma(Vektori3D v1, Vektori3D v2){
        return Math.acos(v1.pistetulo(v2)/(v1.pituus()*v2.pituus()));
    }
}
