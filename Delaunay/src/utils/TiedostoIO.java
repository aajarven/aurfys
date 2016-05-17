/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import delaunay.Kolmio;
import delaunay.Piste;
import delaunay.QuickhullKolmio;
import delaunay.Sivu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public static void kirjoitaPisteetTiedostoon(ArrayList<Piste> pisteet, String tiedostonimi, String erotin) {
        if (pisteet.isEmpty()) {
            return;
        }
        try {
            FileWriter kirjoittaja = new FileWriter(tiedostonimi);
            for (Piste p : pisteet) {
                StringBuilder rakentaja = new StringBuilder();
                rakentaja.append(p.x());
                rakentaja.append(erotin);
                rakentaja.append(p.y());
                rakentaja.append(erotin);
                rakentaja.append(p.z());
                kirjoittaja.write(rakentaja.toString());
            }
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void kirjoitaPisteTiedostoon(Piste p, String tiedostonimi, String erotin) {
        try {
            FileWriter kirjoittaja = new FileWriter(tiedostonimi);
            StringBuilder rakentaja = new StringBuilder();
            rakentaja.append(p.x());
            rakentaja.append(erotin);
            rakentaja.append(p.y());
            rakentaja.append(erotin);
            rakentaja.append(p.z());
            kirjoittaja.write(rakentaja.toString());
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
     * @param basename
     * @param erotin
     */
    public static void kirjoitaKolmiotTiedostoihin(List<Kolmio> kolmiot, String basename, String erotin) {
        if (kolmiot.isEmpty()) {
            return;
        }

        StringBuilder xBuilder = new StringBuilder();
        for (int j = 0; j < kolmiot.size(); j++) {
            Kolmio k = kolmiot.get(j);
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                xBuilder.append(p.x());
                if (i < pisteet.size() - 1) {
                    xBuilder.append(erotin);
                }
            }
            if (j < kolmiot.size() - 1) {
                xBuilder.append("\n");
            }
        }

        StringBuilder yBuilder = new StringBuilder();
        for (int j = 0; j < kolmiot.size(); j++) {
            Kolmio k = kolmiot.get(j);
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                yBuilder.append(p.y());
                if (i < pisteet.size() - 1) {
                    yBuilder.append(erotin);
                }
            }
            if (j < kolmiot.size() - 1) {
                yBuilder.append("\n");
            }
        }

        StringBuilder zBuilder = new StringBuilder();
        for (int j = 0; j < kolmiot.size(); j++) {
            Kolmio k = kolmiot.get(j);
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                zBuilder.append(p.z());
                if (i < pisteet.size() - 1) {
                    zBuilder.append(erotin);
                }
            }
            if (j < kolmiot.size() - 1) {
                zBuilder.append("\n");
            }

        }

        try {
            FileWriter kirjoittaja = new FileWriter(basename + "-x.txt");
            kirjoittaja.write(xBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-y.txt");
            kirjoittaja.write(yBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-z.txt");
            kirjoittaja.write(zBuilder.toString());
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void kirjoitaKolmiotTiedostoihin(ArrayList<QuickhullKolmio> kolmiot, String basename, String erotin) {
        if (kolmiot.isEmpty()) {
            return;
        }

        StringBuilder xBuilder = new StringBuilder();
        for (int j = 0; j < kolmiot.size(); j++) {
            Kolmio k = kolmiot.get(j);
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                xBuilder.append(p.x());
                if (i < pisteet.size() - 1) {
                    xBuilder.append(erotin);
                }
            }
            if (j < kolmiot.size() - 1) {
                xBuilder.append("\n");
            }
        }

        StringBuilder yBuilder = new StringBuilder();
        for (int j = 0; j < kolmiot.size(); j++) {
            Kolmio k = kolmiot.get(j);
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                yBuilder.append(p.y());
                if (i < pisteet.size() - 1) {
                    yBuilder.append(erotin);
                }
            }
            if (j < kolmiot.size() - 1) {
                yBuilder.append("\n");
            }
        }

        StringBuilder zBuilder = new StringBuilder();
        for (int j = 0; j < kolmiot.size(); j++) {
            Kolmio k = kolmiot.get(j);
            ArrayList<Piste> pisteet = k.getPisteet();
            for (int i = 0; i < pisteet.size(); i++) {
                Piste p = pisteet.get(i);
                zBuilder.append(p.z());
                if (i < pisteet.size() - 1) {
                    zBuilder.append(erotin);
                }
            }
            if (j < kolmiot.size() - 1) {
                zBuilder.append("\n");
            }

        }

        try {
            FileWriter kirjoittaja = new FileWriter(basename + "-x.txt");
            kirjoittaja.write(xBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-y.txt");
            kirjoittaja.write(yBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-z.txt");
            kirjoittaja.write(zBuilder.toString());
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void kirjoitaKolmioTiedostoihin(QuickhullKolmio k, String basename, String erotin) {
        StringBuilder xBuilder = new StringBuilder();
        ArrayList<Piste> pisteet = k.getPisteet();
        for (int i = 0; i < pisteet.size(); i++) {
            Piste p = pisteet.get(i);
            xBuilder.append(p.x());
            if (i < pisteet.size() - 1) {
                xBuilder.append(erotin);
            }
        }

        StringBuilder yBuilder = new StringBuilder();
        for (int i = 0; i < pisteet.size(); i++) {
            Piste p = pisteet.get(i);
            yBuilder.append(p.y());
            if (i < pisteet.size() - 1) {
                yBuilder.append(erotin);
            }
        }

        StringBuilder zBuilder = new StringBuilder();
        for (int i = 0; i < pisteet.size(); i++) {
            Piste p = pisteet.get(i);
            zBuilder.append(p.z());
            if (i < pisteet.size() - 1) {
                zBuilder.append(erotin);
            }
        }

        try {
            FileWriter kirjoittaja = new FileWriter(basename + "-x.txt");
            kirjoittaja.write(xBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-y.txt");
            kirjoittaja.write(yBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-z.txt");
            kirjoittaja.write(zBuilder.toString());
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void kirjoitaSivutTiedostoihin(Iterable<Sivu> sivut, String basename, String erotin) {
        StringBuilder xBuilder = new StringBuilder();
        StringBuilder yBuilder = new StringBuilder();
        StringBuilder zBuilder = new StringBuilder();

        for (Sivu s : sivut) {
            xBuilder.append(s.getP1().x());
            xBuilder.append(erotin);
            xBuilder.append(s.getP2().x());
            xBuilder.append("\n");

            yBuilder.append(s.getP1().y());
            yBuilder.append(erotin);
            yBuilder.append(s.getP2().y());
            yBuilder.append("\n");

            zBuilder.append(s.getP1().z());
            zBuilder.append(erotin);
            zBuilder.append(s.getP2().z());
            zBuilder.append("\n");
        }

        try {
            FileWriter kirjoittaja = new FileWriter(basename + "-x.txt");
            kirjoittaja.write(xBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-y.txt");
            kirjoittaja.write(yBuilder.toString());
            kirjoittaja.close();

            kirjoittaja = new FileWriter(basename + "-z.txt");
            kirjoittaja.write(zBuilder.toString());
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void tyhjennaKansio(String kansio) {
        File dir = new File(kansio);
        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

}
