/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        //ArrayList<Piste> pisteet = PisteGeneraattori.generoiLatitudeittain(100, 1.7, 1.0, 0.8);
        //tulostaPisteetPerPi(pisteet);
        ArrayList<Piste> pisteet = PisteGeneraattori.generoiSatunnaisesti(5, 1.7, 1.0, 0.5, 1.3);
        System.out.println("");
        tulostaPisteetKarteesinen(pisteet);
        System.out.println("\nPisteitä yhteensä: " + pisteet.size());
        TiedostoIO.kirjoitaTiedostoon(valmistaTulostukseen(pisteet, ","), "testi.txt");

        ArrayList<Kolmio> delaunay = modifiedQuickhull(pisteet);
        System.out.println(delaunay.size());
    }

    private static ArrayList<Kolmio> modifiedQuickhull(ArrayList<Piste> pisteet) {
        ArrayList<Kolmio> palautettavatKolmiot = new ArrayList<>();
        Deque<QuickhullKolmio> tyostettavatKolmiot = new ArrayDeque<>();

        tyostettavatKolmiot = luoEnsimmainenTetraedri(new ArrayDeque<>(pisteet));

        while (!tyostettavatKolmiot.isEmpty()) {
            System.out.println("Työstettävien kolmioiden määrä: " + tyostettavatKolmiot.size());
            QuickhullKolmio tyostettava = tyostettavatKolmiot.pop();
            Piste kaukaisin;
            try {
                kaukaisin = tyostettava.etsiKaukaisin();
            } catch (Exception ex) {
                palautettavatKolmiot.add(tyostettava.toKolmio());
                continue;
            }

            QuickhullKolmio t1 = new QuickhullKolmio(tyostettava.getP1(), tyostettava.getP2(), kaukaisin);
            QuickhullKolmio t2 = new QuickhullKolmio(tyostettava.getP1(), tyostettava.getP3(), kaukaisin);
            QuickhullKolmio t3 = new QuickhullKolmio(tyostettava.getP2(), tyostettava.getP3(), kaukaisin);

            for (Piste p : tyostettava.getNakyvatPisteet()) {
                if (t1.onKauempanaOrigosta(p)) {
                    t1.lisaaNakyvaPiste(p);
                } else if (t2.onKauempanaOrigosta(p)) {
                    t2.lisaaNakyvaPiste(p);
                } else if (t3.onKauempanaOrigosta(p)) {
                    t3.onKauempanaOrigosta(p);
                } else if ( !(p.equals(tyostettava.getP1()) || p.equals(tyostettava.getP2()) || p.equals(tyostettava.getP3())) ){
                    throw new Error("Kappale ei ole konveksi");
                }
            }

            if (t1.getNakyvatPisteet().isEmpty()) {
                palautettavatKolmiot.add(t1.toKolmio());
            } else {
                tyostettavatKolmiot.add(t1);
            }
            if (t2.getNakyvatPisteet().isEmpty()) {
                palautettavatKolmiot.add(t2.toKolmio());
            } else {
                tyostettavatKolmiot.add(t2);
            }
            if (t3.getNakyvatPisteet().isEmpty()) {
                palautettavatKolmiot.add(t3.toKolmio());
            } else {
                tyostettavatKolmiot.add(t3);
            }
        }

        return palautettavatKolmiot;
    }

    private static Deque<QuickhullKolmio> luoEnsimmainenTetraedri(Deque<Piste> jaettavatPisteet) throws Error {

        ArrayDeque ensimmaisetKasiteltavat = new ArrayDeque();

        // Generoidaan ensimmäinen tetraedri
        Piste p1 = jaettavatPisteet.pop();
        Piste p2 = jaettavatPisteet.pop();
        Piste p3 = jaettavatPisteet.pop();
        Piste p4 = jaettavatPisteet.pop();
        QuickhullKolmio tahko1 = new QuickhullKolmio(p1, p2, p3);
        QuickhullKolmio tahko2 = new QuickhullKolmio(p1, p2, p4);
        QuickhullKolmio tahko3 = new QuickhullKolmio(p1, p3, p4);
        QuickhullKolmio tahko4 = new QuickhullKolmio(p2, p3, p4);
        // Jaetaan kukin piste jollekin tetraedrin tahkolle
        for (Piste p : jaettavatPisteet) {
            if (tahko1.onKauempanaOrigosta(p)) {
                tahko1.lisaaNakyvaPiste(p);
            } else if (tahko2.onKauempanaOrigosta(p)) {
                tahko2.lisaaNakyvaPiste(p);
            } else if (tahko3.onKauempanaOrigosta(p)) {
                tahko3.lisaaNakyvaPiste(p);
            } else if (tahko4.onKauempanaOrigosta(p)) {
                tahko4.lisaaNakyvaPiste(p);
            } else {
                throw new Error("Kappale ei ole konveksi");
            }
        }

        ensimmaisetKasiteltavat.add(tahko1);
        ensimmaisetKasiteltavat.add(tahko2);
        ensimmaisetKasiteltavat.add(tahko3);
        ensimmaisetKasiteltavat.add(tahko4);

        return ensimmaisetKasiteltavat;
    }

    /**
     * Palauttaa maksimi- ja minimipisteet x-, y- ja z-suunnissa
     *
     * @param pisteet tutkittavat pisteet
     * @return [minX, maxX, minY, maxY, minZ, maxZ]
     */
    private Piste[] kaukaisimmatPisteet(ArrayList<Piste> pisteet) {
        Piste[] kaukaisimmat = new Piste[6];
        Piste minX = pisteet.get(0);
        Piste maxX = pisteet.get(0);
        Piste minY = pisteet.get(0);
        Piste maxY = pisteet.get(0);
        Piste minZ = pisteet.get(0);
        Piste maxZ = pisteet.get(0);

        for (Piste p : pisteet) {
            if (p.x() < minX.x()) {
                minX = p;
            }
            if (p.x() > minX.x()) {
                maxX = p;
            }
            if (p.y() < minY.y()) {
                minY = p;
            }
            if (p.y() > minY.y()) {
                maxY = p;
            }
            if (p.z() < minZ.z()) {
                minZ = p;
            }
            if (p.z() > minZ.z()) {
                maxZ = p;
            }
        }

        return new Piste[]{minX, maxX, minY, maxY, minZ, maxZ};
    }

    private static String valmistaTulostukseen(Iterable<Piste> pisteet, String erotin) {
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
