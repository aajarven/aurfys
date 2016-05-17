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
public class QuickhullKolmio extends Kolmio {

    private ArrayList<Piste> nakyvatPisteet;
    private ArrayList<QuickhullKolmio> naapurit;

    public QuickhullKolmio(Piste p1, Piste p2, Piste p3) {
        super(p1, p2, p3);
        this.nakyvatPisteet = new ArrayList<>();
        this.naapurit = new ArrayList<>();
    }

    public void lisaaNakyvaPiste(Piste p) {
        nakyvatPisteet.add(p);
    }
    
    public void poistaNakyvaPiste(Piste p){
        nakyvatPisteet.remove(p);
    }

    public void lisaaNaapuri(QuickhullKolmio k) {
        if (!naapurit.contains(k)) {
            naapurit.add(k);
        }
    }
    
    public void poistaNaapuri(QuickhullKolmio k){
        naapurit.remove(k);
    }

    /**
     * Etsii näkyvistä pisteistä kauimpana tasosta sijaitsevan
     *
     * @return kaukaisin piste
     */
    public Piste etsiKaukaisin() throws Exception {
        if (nakyvatPisteet.isEmpty()) {
            throw new Exception("QuickhullKolmiolla ei ole näkyviä pisteitä");
        }

        Piste kaukaisin = nakyvatPisteet.get(0);
        double kaukaisimmanEtaisyys = 0;
        for (Piste p : nakyvatPisteet) {
            double kasiteltavanEtaisyys = etaisyysTasosta(p);
            if (kasiteltavanEtaisyys < kaukaisimmanEtaisyys) {
                kaukaisin = p;
                kaukaisimmanEtaisyys = kasiteltavanEtaisyys;
            }
        }
        return kaukaisin;
    }

    public Piste etsiKaukaisin(ArrayList<Piste> pisteet) {
        Piste kaukaisin = pisteet.get(0);
        double kaukaisimmanEtaisyys = 0;
        for (Piste p : pisteet) {
            double kasiteltavanEtaisyys = etaisyysTasosta(p);
            if (kasiteltavanEtaisyys < kaukaisimmanEtaisyys) {
                kaukaisin = p;
                kaukaisimmanEtaisyys = kasiteltavanEtaisyys;
            }
        }
        return kaukaisin;
    }

    public ArrayList<Piste> getNakyvatPisteet() {
        return nakyvatPisteet;
    }

    public Kolmio toKolmio() {
        return new Kolmio(super.getP1(), super.getP2(), super.getP3());
    }

    /**
     * Kertoo, onko piste samalla puolella kolmion virittämää tasoa kuin origo
     *
     * @param p tutkittava piste
     * @return true jos taso on kauempana origosta kuin piste, muuten false
     */
    public boolean onKauempanaOrigosta(Piste p) {
        double[] kertoimet = tasonYhtalo();
        double a = kertoimet[0];
        double b = kertoimet[1];
        double c = kertoimet[2];
        double d = kertoimet[3];

        return (p.x() * a + p.y() * b + p.z() * c + d) * d > 0;
    }

    /**
     * Palauttaa kertoimet tason yhtälöön muodossa ax+by+cz+d=0
     *
     * @return [a, b, c, d]
     */
    public double[] tasonYhtalo() {
        Vektori3D v1 = super.getP1().vektoriPisteeseen(super.getP2());
        Vektori3D v2 = super.getP1().vektoriPisteeseen(super.getP3());
        Vektori3D normaali = v1.ristitulo(v2);
        
        double a = normaali.getI();
        double b = normaali.getJ();
        double c = normaali.getK();
        double d = normaali.getI() * super.getP1().x() + normaali.getJ() * super.getP1().y() + normaali.getK() * super.getP1().z();

        return new double[]{a, b, c, -d};
    }

    /**
     * Kertoo pisteen p etäisyyden kolmion virittämästä tasosta
     *
     * @param p tutkittava piste
     * @return pisteen etäisyys tasosta
     */
    public double etaisyysTasosta(Piste p) {
        double[] kertoimet = tasonYhtalo();
        double a = kertoimet[0];
        double b = kertoimet[1];
        double c = kertoimet[2];
        double d = kertoimet[3];

        return Math.abs(a * p.x() + b * p.y() + c * p.z() + d) / Math.sqrt(a * a + b * b + c * c);
    }

    /**
     * Palauttaa annetun kolmion pisteet, jotka eivät ole osa tätä kolmiota
     *
     * @param k kolmio
     * @return ArrayList jossa 0-3 pistettä
     */
    public ArrayList<Piste> erillisetPisteet(QuickhullKolmio k) {
        ArrayList<Piste> yhteiset = new ArrayList<>();
        ArrayList<Piste> omat = super.getPisteet();
        for (Piste p : k.getPisteet()) {
            if (!omat.contains(p)) {
                yhteiset.add(p);
            }
        }
        return yhteiset;
    }

    public ArrayList<QuickhullKolmio> getNaapurit() {
        return naapurit;
    }

    public ArrayList<Sivu> getSivut() {
        ArrayList<Sivu> palautus = new ArrayList();
        palautus.add(new Sivu(super.getP1(), super.getP2()));
        palautus.add(new Sivu(super.getP1(), super.getP3()));
        palautus.add(new Sivu(super.getP2(), super.getP3()));
        return palautus;
    }

    public boolean sisaltaaPisteen(Piste p) {
        return super.getPisteet().contains(p);
    }

    /**
     * Määrittää tämän ja tutkittavan kolmion yhteisen sivun
     *
     * @param k toinen kolmio
     * @return kolmioiden yhteinen sivu tai null jos sellaista ei ole
     */
    public Sivu yhteinenSivu(QuickhullKolmio k) {
        ArrayList<Piste> yhteisetPisteet = new ArrayList(this.getPisteet());
        yhteisetPisteet.removeAll(k.erillisetPisteet(this));
        if (yhteisetPisteet.size() != 2) {
            return null;
        } else {
            return new Sivu(yhteisetPisteet.get(0), yhteisetPisteet.get(1));
        }
    }

    boolean eriPuolilla(Piste keskipiste, Piste p) {
        double[] kertoimet = tasonYhtalo();
        double a = kertoimet[0];
        double b = kertoimet[1];
        double c = kertoimet[2];
        double d = kertoimet[3];

        return (p.x() * a + p.y() * b + p.z() * c + d) * (keskipiste.x() * a + keskipiste.y() * b + keskipiste.z() * c + d) < 0;
    }

}
