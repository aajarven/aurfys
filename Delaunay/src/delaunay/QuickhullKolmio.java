/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;

/**
 *
 * @author anni
 */
public class QuickhullKolmio extends Kolmio{
    
    private ArrayList<Piste> nakyvatPisteet;
    
    public QuickhullKolmio(Piste p1, Piste p2, Piste p3) {
        super(p1, p2, p3);
        this.nakyvatPisteet = new ArrayList<>();
    }
    
    public void lisaaNakyvaPiste(Piste p){
        nakyvatPisteet.add(p);
    }
    
    /**
     * Etsii näkyvistä pisteistä kauimpana tasosta sijaitsevan
     * @return kaukaisin piste
     */
    public Piste etsiKaukaisin() throws Exception{
        if (nakyvatPisteet.isEmpty()){
            throw new Exception("QuickhullKolmiolla ei ole näkyviä pisteitä");
        }
        
        Piste kaukaisin = nakyvatPisteet.get(0);
        double kaukaisimmanEtaisyys = 0;
        for (Piste p: nakyvatPisteet){
            double kasiteltavanEtaisyys = etaisyysTasosta(p);
            if (kasiteltavanEtaisyys < kaukaisimmanEtaisyys){
                kaukaisin = p;
                kaukaisimmanEtaisyys = kasiteltavanEtaisyys;
            }
        }
        return kaukaisin;
    }

    public ArrayList<Piste> getNakyvatPisteet() {
        return nakyvatPisteet;
    }
    
    public Kolmio toKolmio(){
        return new Kolmio(super.getP1(), super.getP2(), super.getP3());
    }
    
        /**
     * Kertoo, onko piste samalla puolella kolmion virittämää tasoa kuin origo
     * @param p tutkittava piste
     * @return true jos piste on kauempana origosta kuin taso, muuten false
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
        Vektori3D v1 = super.getP1().vektoriPisteeseen(super.getP2());
        Vektori3D v2 = super.getP1().vektoriPisteeseen(super.getP3());
        Vektori3D normaali = v1.ristitulo(v2);
        
        double a = normaali.getI();
        double b = normaali.getJ();
        double c = normaali.getK();
        double d = normaali.getI()*v1.getI() + normaali.getJ()*v1.getJ()+normaali.getK()*v1.getK();
        
        return new double[]{a, b, c, d};
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
    
}
