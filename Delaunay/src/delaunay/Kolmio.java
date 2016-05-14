/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

/**
 *
 * @author anni
 */
public class Kolmio {
    private Piste p1;
    private Piste p2;
    private Piste p3;

    public Kolmio(Piste p1, Piste p2, Piste p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    
    /**
     * Kertoo pisteen p etäisyyden kolmion virittämästä tasosta
     * @param p tutkittava piste
     * @return pisteen etäisyys tasosta
     */
    public double etaisyysTasosta(Piste p){
        double[] kertoimet = tasonYhtalo();
        double a = kertoimet[0];
        double b = kertoimet[1];
        double c = kertoimet[2];
        double d = kertoimet[3];
        
        return Math.abs(a*p.x()+b*p.y()+c*p.z()+d)/Math.sqrt(a*a+b*b+c*c);
    }
    
    /**
     * Kertoo, onko piste samalla puolella kolmion virittämää tasoa kuin origo
     * @param p tutkittava piste
     * @return true jos piste on lähempänä origoa kuin taso, muuten false
     */
    public boolean onKauempanaOrigosta(Piste p){
        double[] kertoimet = tasonYhtalo();
        double a = kertoimet[0];
        double b = kertoimet[1];
        double c = kertoimet[2];
        double d = kertoimet[3];
        
        return (p.x()*a+p.y()*b+p.z()*c-d)*(-d)>0 ; // tarkasta tämä
    }
    
    /**
     * Palauttaa kertoimet tason yhtälöön muodossa ax+by+cz=d
     * @return [a, b, c, d]
     */
    private double[] tasonYhtalo(){
        Vektori3D v1 = p1.vektoriPisteeseen(p2);
        Vektori3D v2 = p1.vektoriPisteeseen(p3);
        Vektori3D normaali = v1.ristitulo(v2);
        
        double a = normaali.getI();
        double b = normaali.getJ();
        double c = normaali.getK();
        double d = normaali.getI()*v1.getI() + normaali.getJ()*v1.getJ()+normaali.getK()*v1.getK();
        
        return new double[]{a, b, c, d};
    }
    
}
