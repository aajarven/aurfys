/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
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
//        ArrayList<Piste> pisteet = PisteGeneraattori.generoiLatitudeittain(100, 1.7, 1.0, 0.8);
//        tulostaPisteetPerPi(pisteet);
        ArrayList<Piste> pisteet = PisteGeneraattori.generoiSatunnaisesti(15, 1, 1.0, 1, 1);
        System.out.println("");
        tulostaPisteetKarteesinen(pisteet);
        System.out.println("\nPisteitä yhteensä: " + pisteet.size());
        TiedostoIO.kirjoitaTiedostoon(valmistaTulostukseen(pisteet, ","), "testi.txt");

        Quickhull kolmioija = new Quickhull(pisteet);
        ArrayList<Kolmio> kolmiot = kolmioija.kolmioi();
        System.out.println(kolmiot.size());

//        Piste p1 = new Piste(Math.PI/2-0.1, 0, 1);
//        Piste p2 = new Piste(Math.PI/2-0.1, Math.PI, 1);
//        Piste p3 = new Piste(Math.PI/2-0.1, Math.PI/2, 1);
//        QuickhullKolmio k = new QuickhullKolmio(p1, p2, p3);
//        System.out.println(p1.x()+"\t"+p1.y()+"\t"+p1.z());
//        System.out.println(p2.x()+"\t"+p2.y()+"\t"+p2.z());
//        System.out.println(p3.x()+"\t"+p3.y()+"\t"+p3.z());
//        
//        for(double d : k.tasonYhtalo()){
//            System.out.print(d+"\t");
//        }
//        System.out.println("");
//        
//        Piste p4 = new Piste(Math.PI/2-0.2, 0, 1);
//        System.out.println(k.onKauempanaOrigosta(p4));
//        System.out.println(p4.x()+"\t"+p4.y()+"\t"+p4.z());
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
