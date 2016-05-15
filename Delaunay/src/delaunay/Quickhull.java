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

/**
 *
 * @author anni
 */
public class Quickhull {
    
    ArrayList<Piste> pisteet;

    public Quickhull(ArrayList<Piste> pisteet) {
        this.pisteet = pisteet;
    }

    
    public ArrayList<Kolmio> kolmioi() {
        ArrayList<Kolmio> palautettavatKolmiot = new ArrayList<>();
        Deque<QuickhullKolmio> tyostettavatKolmiot;

        tyostettavatKolmiot = luoEnsimmainenTetraedri(pisteet);
        palautettavatKolmiot.addAll(tyostettavatKolmiot);

        while (!tyostettavatKolmiot.isEmpty()) {
            QuickhullKolmio tyostettava = tyostettavatKolmiot.pop();
            Piste kaukaisin;
            try {
                kaukaisin = tyostettava.etsiKaukaisin();
            } catch (Exception ex) {
                System.out.println("Ei löytynyt kaukaisinta");
                continue;
            }

            ArrayList<QuickhullKolmio> valoisat = etsiValoisat(tyostettava, kaukaisin);
            System.out.println("löytyi "+valoisat.size()+" valoisaa");
            ArrayList<Sivu> horisontti = etsiHorisontti(valoisat, tyostettava);
            ArrayList<QuickhullKolmio> uudetKolmiot = new ArrayList<>();
            for (Sivu s: horisontti){
                uudetKolmiot.add(new QuickhullKolmio(kaukaisin, s.getP1(), s.getP2()));
            }
            System.out.println("Luotiin "+uudetKolmiot.size()+" uutta kolmiota");
            
            palautettavatKolmiot.removeAll(valoisat);
            palautettavatKolmiot.addAll(uudetKolmiot);
            System.out.println("Palautettavia kolmioita nyt "+palautettavatKolmiot.size());
            System.out.println("");
            
            for(Piste p: tyostettava.getNakyvatPisteet()){
                for (QuickhullKolmio k: uudetKolmiot){
                    if (k.onKauempanaOrigosta(p)){
                        k.lisaaNakyvaPiste(p);
                        break;
                    }
                }
            }
        }
        
        return palautettavatKolmiot;
    }
    
    private Deque<QuickhullKolmio> luoEnsimmainenTetraedri(ArrayList<Piste> jaettavatPisteet) throws Error {

        ArrayDeque<QuickhullKolmio> ensimmaisetKasiteltavat = new ArrayDeque();

        // Generoidaan ensimmäisen tetraedrin tahkot
        ArrayList<Piste> kaukaisimmat = kaukaisimmatPisteet(jaettavatPisteet);

        Piste p1 = kaukaisimmat.get(0);
        Piste p2 = kaukaisimmat.get(1);
        Piste p3;
        Piste p4;

        // etsitään kaukaisin pistepari
        double suurinEtaisyys = p1.etaisyys(p2);
        for (Piste tutkittava1 : kaukaisimmat) {
            for (Piste tutkittava2 : kaukaisimmat) {
                if (tutkittava1.etaisyys(tutkittava2) > suurinEtaisyys) {
                    p1 = tutkittava1;
                    p2 = tutkittava2;
                    suurinEtaisyys = p1.etaisyys(p2);
                }
            }
        }
        kaukaisimmat.remove(p1);
        kaukaisimmat.remove(p2);
        jaettavatPisteet.remove(p1);
        jaettavatPisteet.remove(p2);

        // etsitään piste, joka on kauimpana p1 ja p2 määräämästä suorasta
        p3 = kaukaisimmat.get(0);
        suurinEtaisyys = p3.etaisyysSuorasta(p1, p2);
        for (Piste tutkittava : kaukaisimmat) {
            if (tutkittava.etaisyysSuorasta(p1, p2) > suurinEtaisyys) {
                p3 = tutkittava;
                suurinEtaisyys = tutkittava.etaisyysSuorasta(p1, p2);
            }
        }
        kaukaisimmat.remove(p3);
        jaettavatPisteet.remove(p3);

        QuickhullKolmio tahko1 = new QuickhullKolmio(p1, p2, p3);
        for (Piste p : kaukaisimmat) {
            tahko1.lisaaNakyvaPiste(p);
        }

        try {
            p4 = tahko1.etsiKaukaisin();
            jaettavatPisteet.remove(p4);
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

            // asetetaan tahkot toistensa naapureiksi
            for (QuickhullKolmio k1 : ensimmaisetKasiteltavat) {
                for (QuickhullKolmio k2 : ensimmaisetKasiteltavat) {
                    if (k1 != k2) {
                        k1.lisaaNaapuri(k2);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        return ensimmaisetKasiteltavat;
    }
    
    
    /**
     * Palauttaa maksimi- ja minimipisteet x-, y- ja z-suunnissa
     *
     * @param pisteet tutkittavat pisteet
     * @return [minX, maxX, minY, maxY, minZ, maxZ]
     */
    private ArrayList<Piste> kaukaisimmatPisteet(ArrayList<Piste> pisteet) {
        ArrayList<Piste> kaukaisimmat = new ArrayList<>();
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

        kaukaisimmat.add(minX);
        kaukaisimmat.add(maxX);
        kaukaisimmat.add(minY);
        kaukaisimmat.add(maxY);
        kaukaisimmat.add(minZ);
        kaukaisimmat.add(maxZ);

        return kaukaisimmat;
    }
    
        private ArrayList<QuickhullKolmio> etsiValoisat(QuickhullKolmio tyostettava, Piste kaukaisin) {
        ArrayList<QuickhullKolmio> valoisat = new ArrayList<>();
        for (QuickhullKolmio k : tyostettava.getNaapurit()) {
            if (k.onKauempanaOrigosta(kaukaisin)) {
                valoisat.add(k);
            }
        }
        return valoisat;
    }
        
        private ArrayList<Sivu> etsiHorisontti(ArrayList<QuickhullKolmio> valoisat, QuickhullKolmio tyostettava) {
        ArrayList<Sivu> horisontti = new ArrayList<>();
        for (QuickhullKolmio k : valoisat) {
            for (Sivu s : k.getSivut()) {
                if (!s.onYhteinenPiste(tyostettava)) {
                    horisontti.add(s);
                }
            }
        }

        //ArrayList<Piste> paatepisteet = etsiPaatepisteet(horisontti);
        ArrayList<QuickhullKolmio> varjoisat = new ArrayList<>(tyostettava.getNaapurit());
        varjoisat.removeAll(valoisat);
        //for(Piste p: paatepisteet){
        for (QuickhullKolmio varjoisa : varjoisat) {
            for (QuickhullKolmio valoisa : valoisat) {
                Sivu yhteinen = valoisa.yhteinenSivu(varjoisa);
                if (yhteinen != null) {
                    horisontti.add(yhteinen);
                }
            }
        }
        //}
        return horisontti;
    }
}
