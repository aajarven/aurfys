/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;
import java.util.HashSet;
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
        ArrayList<Piste> pisteet = PisteGeneraattori.generoiLatitudeittain(12, 1.7, 1.0, 0.8);
        //tulostaPisteetPerPi(pisteet);
        System.out.println("");
        tulostaPisteetKarteesinen(pisteet);
        System.out.println("\nPisteitä yhteensä: "+pisteet.size());
        TiedostoIO.kirjoitaTiedostoon(valmistaTulostukseen(pisteet, ","), "testi.txt");
    }
    
    private static String valmistaTulostukseen(Iterable<Piste> pisteet, String erotin){
        StringBuilder csv = new StringBuilder();
        for (Piste p : pisteet){
            csv.append(p.x());
            csv.append(erotin);
            csv.append(p.y());
            csv.append(erotin);
            csv.append(p.z());
            csv.append("\n");
        }
        return csv.toString();
    }
    
    private static void tulostaPisteetPerPi(Iterable<Piste> pisteet){
        System.out.println("theta\tfii\tr (per pi)");
        for(Piste p: pisteet){
            System.out.println(p.getTheta()/Math.PI + "\t" + p.getFii()/Math.PI + "\t" +p.getR());
        }
    }
    
    private static void tulostaPisteet(Iterable<Piste> pisteet){
        System.out.println("theta\tfii\tr");
        for(Piste p: pisteet){
            System.out.println(p.getTheta() + "\t" + p.getFii() + "\t" +p.getR());
        }
    }
    
    private static void tulostaPisteetKarteesinen(Iterable<Piste> pisteet){
        System.out.println("x\ty\tz");
        for(Piste p: pisteet){
            System.out.println(p.x()+"\t"+p.y()+"\t"+p.z());
        }
    }
}
