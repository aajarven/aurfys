/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;
import utils.TiedostoIO;

/**
 *
 * @author anni
 */
public class Delaunay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        ArrayList<Piste> pisteet = PisteGeneraattori.generoiLatitudeittain(100, 1.7, 1.0, 0.8);
//        tulostaPisteetPerPi(pisteet);

        TiedostoIO.tyhjennaKansio("debug");

        ArrayList<Piste> pisteet = PisteGeneraattori.generoiSatunnaisesti(250, 1, 1, 1, 1);
        System.out.println("generoitu");
//        tulostaPisteetKarteesinen(pisteet);
        TiedostoIO.kirjoitaTiedostoon(valmistaPisteetTulostukseen(pisteet, ","), "debug/pisteet.txt");

        Quickhull kolmioija = new Quickhull(pisteet);
        ArrayList<Kolmio> kolmiot = kolmioija.kolmioi();
        System.out.println(kolmiot.size());

        TiedostoIO.kirjoitaKolmiotTiedostoihin(kolmiot, "kolmiot", ",");
    }

    private static ArrayList<Piste> etsiPaatepisteet(ArrayList<Sivu> horisontti) {
        ArrayList<Piste> reunat = new ArrayList();
        for (Sivu s1 : horisontti) {
            boolean p1onViereinen = false;
            boolean p2onViereinen = false;
            for (Sivu s2 : horisontti) {
                if (s2.sisaltaaPisteen(s1.getP1())) {
                    p1onViereinen = true;
                }
                if (s2.sisaltaaPisteen(s1.getP2())) {
                    p2onViereinen = true;
                }
            }
            if (!p1onViereinen) {
                reunat.add(s1.getP1());
            }
            if (!p2onViereinen) {
                reunat.add(s1.getP2());
            }
        }
        return reunat;
    }

    public static String valmistaPisteetTulostukseen(Iterable<Piste> pisteet, String erotin) {
        StringBuilder csv = new StringBuilder();
        for (Piste p : pisteet) {
            csv.append(p.x());
            csv.append(erotin);
            csv.append(p.y());
            csv.append(erotin);
            csv.append(p.z());
            csv.append("\n");
        }
        return csv.toString();
    }

    /**
     * Tuottaa kolmioista tulostuksen siten, että rivillä on peräkkäin kolmion
     * kolmen kulman koordinaatit erottimella erotettuina, esim kun erottimena
     * pilkku, saadaan "x1,y1,z1,x2,y2,z2,x3,y3,z3"
     * @param kolmiot
     * @param erotin
     * @return 
     */
    public static String valmistaKolmiotTulostukseen(Iterable<Kolmio> kolmiot, String erotin) {
        StringBuilder csv = new StringBuilder();
        for (Kolmio k : kolmiot) {
            for (Piste p : k.getPisteet()) {
                csv.append(p.x());
                csv.append(erotin);
                csv.append(p.y());
                csv.append(erotin);
                csv.append(p.z());
            }
            csv.append("\n");
        }
        return csv.toString();
    }

    private static void tulostaPisteetPerPi(Iterable<Piste> pisteet) {
        System.out.println("theta\tfii\tr (per pi)");
        for (Piste p : pisteet) {
            System.out.println(p.getTheta() / Math.PI + "\t" + p.getFii() / Math.PI + "\t" + p.getR());
        }
    }

    private static void tulostaPisteet(Iterable<Piste> pisteet) {
        System.out.println("theta\tfii\tr");
        for (Piste p : pisteet) {
            System.out.println(p.getTheta() + "\t" + p.getFii() + "\t" + p.getR());
        }
    }

    private static void tulostaPisteetKarteesinen(Iterable<Piste> pisteet) {
        System.out.println("x\ty\tz");
        for (Piste p : pisteet) {
            System.out.println(p.x() + "\t" + p.y() + "\t" + p.z());
        }
    }
}
