/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;
import kirkkaus.LommelSeeliger;
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
        
        ArrayList<Piste> pisteet = PisteGeneraattori.generoiLatitudeittain(22, 1, 1, 1);
        TiedostoIO.kirjoitaPisteetTiedostoon(pisteet, "saannollinen.txt", ",");
        for(Piste p: pisteet){
            System.out.println(p);
        }

//        TiedostoIO.tyhjennaKansio("debug");

        //ArrayList<Piste> pisteet = PisteGeneraattori.generoiSatunnaisesti(100, 1, 1, 1, 1);
//        System.out.println("generoitu");
//        TiedostoIO.kirjoitaTiedostoon(valmistaPisteetTulostukseen(pisteet, ","), "debug/pisteet.txt");
//
//        Quickhull kolmioija = new Quickhull(pisteet);
//        ArrayList<QuickhullKolmio> kolmiot = kolmioija.kolmioi();
//        System.out.println(kolmiot.size());
//
//        TiedostoIO.kirjoitaKolmiotTiedostoihin(kolmiot, "kolmiot", ",");
//
//        Vektori3D havaitsija = new Vektori3D(0.0001, 0.0001, 0.0001);
//        
//        ArrayList<Double> kirkkaudet = new ArrayList<>();
//        ArrayList<Double> kulmat = new ArrayList<>();
//        for (double theta = 0.001; theta < Math.PI / 2; theta += 0.01) {
//            Vektori3D lahde = new Vektori3D(-Math.cos(theta), -Math.sin(theta), 0.00);
//            double kokonaiskirkkaus = 0;
//            for (QuickhullKolmio k : kolmiot) {
//                double kirkkaus = LommelSeeliger.kirkkaus(k, havaitsija, lahde, 1);
//                //kirkkaudet.add(kirkkaus);
//                kokonaiskirkkaus += kirkkaus;
//            }
//            kirkkaudet.add(kokonaiskirkkaus);
//            kulmat.add(theta);
//        }
//        TiedostoIO.kirjoitaLuvut(kirkkaudet, "kirkkaudet.txt");
//        TiedostoIO.kirjoitaLuvut(kulmat, "kulmat.txt");
        //System.out.println(kokonaiskirkkaus);
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
     *
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
