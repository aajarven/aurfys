/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import delaunay.Kolmio;
import delaunay.Piste;
import delaunay.QuickhullKolmio;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anni Järvenpää
 */
public class TiedostoIO {

    public static void kirjoitaTiedostoon(String kirjoitettava, String tiedostonimi) {
        try {
            FileWriter kirjoittaja = new FileWriter(tiedostonimi);
            kirjoittaja.write(kirjoitettava);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Tallentaa annetut kolmiot tiedostoihin tiedostonimi-x.txt,
     * tiedostonimi-y.txt ja tiedostonimi-z.txt siten, että kukin tiedosto
     * sisältää jokaisen kolmion jokaisen kulman yhden koordinaatin, yksi rivi
     * per kolmio. Siis esimerkiksi kolmiot-x.txt sisältäisi kahden kolmion ja
     * erotusmerkin , tapauksessa rivit "x1-1,x1-2,x1-2\nx2-1,x2-2,x2-3"
     *
     * @param kolmiot
     * @param tiedostonimi
     */
    public static void kirjoitaKolmiotTiedostoihin(Iterable<Kolmio> kolmiot, String tiedostonimi, String erotin) {

        StringBuilder xBuilder = new StringBuilder();
        for (Kolmio k : kolmiot) {
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                xBuilder.append(p.x());
                if (i < pisteet.size() - 1) {
                    xBuilder.append(erotin);
                }
            }
            xBuilder.append("\n");
        }

        StringBuilder yBuilder = new StringBuilder();
        for (Kolmio k : kolmiot) {
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                yBuilder.append(p.y());
                if (i < pisteet.size() - 1) {
                    yBuilder.append(erotin);
                }
            }
            yBuilder.append("\n");
        }

        StringBuilder zBuilder = new StringBuilder();
        for (Kolmio k : kolmiot) {
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                zBuilder.append(p.z());
                if (i < pisteet.size() - 1) {
                    zBuilder.append(erotin);
                }
            }
            zBuilder.append("\n");
        }

        try {
            FileWriter kirjoittaja = new FileWriter(tiedostonimi + "-x.txt");
            kirjoittaja.write(xBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(tiedostonimi + "-y.txt");
            kirjoittaja.write(yBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(tiedostonimi + "-z.txt");
            kirjoittaja.write(zBuilder.toString());
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void kirjoitaKolmiotTiedostoihin(ArrayList<QuickhullKolmio> kolmiot, String tiedostonimi, String erotin) {
        StringBuilder xBuilder = new StringBuilder();
        for (Kolmio k : kolmiot) {
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                xBuilder.append(p.x());
                if (i < pisteet.size() - 1) {
                    xBuilder.append(erotin);
                }
            }
            xBuilder.append("\n");
        }

        StringBuilder yBuilder = new StringBuilder();
        for (Kolmio k : kolmiot) {
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                yBuilder.append(p.y());
                if (i < pisteet.size() - 1) {
                    yBuilder.append(erotin);
                }
            }
            yBuilder.append("\n");
        }

        StringBuilder zBuilder = new StringBuilder();
        for (Kolmio k : kolmiot) {
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                zBuilder.append(p.z());
                if (i < pisteet.size() - 1) {
                    zBuilder.append(erotin);
                }
            }
            zBuilder.append("\n");
        }

        try {
            FileWriter kirjoittaja = new FileWriter(tiedostonimi + "-x.txt");
            kirjoittaja.write(xBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(tiedostonimi + "-y.txt");
            kirjoittaja.write(yBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(tiedostonimi + "-z.txt");
            kirjoittaja.write(zBuilder.toString());
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
