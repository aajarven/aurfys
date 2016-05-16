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
import java.util.Set;
import utils.TiedostoIO;

/**
 *
 * @author anni
 */
public class Quickhull {

    ArrayList<Piste> pisteet;
    Piste keskipiste;

    public Quickhull(ArrayList<Piste> pisteet) {
        this.pisteet = pisteet;
        keskipiste = new Piste(0, 0, 0);
    }

    public ArrayList<Kolmio> kolmioi() {
        ArrayList<Kolmio> palautettavatKolmiot = new ArrayList<>();
        Deque<QuickhullKolmio> tyostettavatKolmiot;
        tyostettavatKolmiot = luoEnsimmainenTetraedri(pisteet);
//        tyostettavatKolmiot = luoVakiotetra(pisteet);
        palautettavatKolmiot.addAll(tyostettavatKolmiot);
//        TiedostoIO.kirjoitaKolmiotTiedostoihin(palautettavatKolmiot, "ekatetra", ",");
//        for (Kolmio k: palautettavatKolmiot){
//            System.out.println(k.toString());
//        }
        
        
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
            System.out.println("löytyi " + valoisat.size() + " valoisaa");
            ArrayList<Sivu> horisontti = etsiHorisontti(valoisat, tyostettava);
            ArrayList<QuickhullKolmio> uudetKolmiot = new ArrayList<>();
            for (Sivu s : horisontti) {
                uudetKolmiot.add(new QuickhullKolmio(kaukaisin, s.getP1(), s.getP2()));
            }
            System.out.println("Luotiin " + uudetKolmiot.size() + " uutta kolmiota");

            palautettavatKolmiot.removeAll(valoisat);
            palautettavatKolmiot.remove(tyostettava);
            palautettavatKolmiot.addAll(uudetKolmiot);
            System.out.println("Palautettavia kolmioita nyt " + palautettavatKolmiot.size());
            System.out.println("");

            for (Piste p : tyostettava.getNakyvatPisteet()) {
                for (QuickhullKolmio k : uudetKolmiot) {
                    if (k.eriPuolilla(keskipiste, p)) {
                        k.lisaaNakyvaPiste(p);
                        break;
                    }
                }
            }
        }

        return palautettavatKolmiot;
    }

    private Deque<QuickhullKolmio> luoVakiotetra(ArrayList<Piste> jaettavatPisteet) {
        ArrayDeque<QuickhullKolmio> kolmiot = new ArrayDeque();
        Piste p1 = new Piste(0, 0, 0.2);
        Piste p2 = new Piste(Math.PI * 2 / 3, 0, 0.2);
        Piste p3 = new Piste(Math.PI * 2 / 3, Math.PI * 2 / 3, 0.2);
        Piste p4 = new Piste(Math.PI * 2 / 3, Math.PI * 4 / 3, 0.2);
        final QuickhullKolmio tahko1 = new QuickhullKolmio(p1, p2, p3);
        kolmiot.add(tahko1);
        final QuickhullKolmio tahko2 = new QuickhullKolmio(p1, p2, p4);
        kolmiot.add(tahko2);
        final QuickhullKolmio tahko3 = new QuickhullKolmio(p1, p3, p4);
        kolmiot.add(tahko3);
        final QuickhullKolmio tahko4 = new QuickhullKolmio(p2, p3, p4);
        kolmiot.add(tahko4);

        for (Piste p : jaettavatPisteet) {
            if (tahko1.eriPuolilla(keskipiste, p)) {
                tahko1.lisaaNakyvaPiste(p);
            } else if (tahko2.eriPuolilla(keskipiste, p)) {
                tahko2.lisaaNakyvaPiste(p);
            } else if (tahko3.eriPuolilla(keskipiste, p)) {
                tahko3.lisaaNakyvaPiste(p);
            } else if (tahko4.eriPuolilla(keskipiste, p)) {
                tahko4.lisaaNakyvaPiste(p);
            } else {
                throw new Error("Kappale ei ole konveksi");
            }
        }

        // asetetaan tahkot toistensa naapureiksi
        for (QuickhullKolmio k1 : kolmiot) {
            for (QuickhullKolmio k2 : kolmiot) {
                if (k1 != k2) {
                    k1.lisaaNaapuri(k2);
                }
            }
        }

        return kolmiot;
    }

    private Deque<QuickhullKolmio> luoEnsimmainenTetraedri(ArrayList<Piste> jaettavatPisteet) throws Error {

        ArrayDeque<QuickhullKolmio> ensimmaisetKasiteltavat = new ArrayDeque();

        // Generoidaan ensimmäisen tetraedrin tahkot
        ArrayList<Piste> kaukaisimmat = kaukaisimmatPisteet(jaettavatPisteet);
        poistaDuplikaatit(kaukaisimmat);

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

        System.out.println("Kaukaisin pari:");
        System.out.println(p1);
        System.out.println(p2);

        kaukaisimmat.remove(p1);
        kaukaisimmat.remove(p2);
        jaettavatPisteet.remove(p1);
        jaettavatPisteet.remove(p2);

        // etsitään piste, joka on kauimpana p1 ja p2 määräämästä suorasta
        p3 = kaukaisimmat.get(0);
        suurinEtaisyys = p3.etaisyysSuorasta(p1, p2);
        for (Piste tutkittava : kaukaisimmat) {
            System.out.println("pisteiden etäisyys: " + tutkittava.etaisyysSuorasta(p1, p2));
            if (tutkittava.etaisyysSuorasta(p1, p2) > suurinEtaisyys) {
                System.out.println("oli suurempi kuin edellinen");
                p3 = tutkittava;
                suurinEtaisyys = tutkittava.etaisyysSuorasta(p1, p2);
            }
        }
        System.out.println("Etäisin suorasta:");
        System.out.println(p3);
        kaukaisimmat.remove(p3);
        jaettavatPisteet.remove(p3);

        QuickhullKolmio tahko1 = new QuickhullKolmio(p1, p2, p3);

        try {
            p4 = tahko1.etsiKaukaisin(kaukaisimmat);
            jaettavatPisteet.remove(p4);
            keskipiste = etsiKeskipiste(p1, p2, p3, p4);
            System.out.println("Kauimpana tahkosta");
            System.out.println(p4);
            QuickhullKolmio tahko2 = new QuickhullKolmio(p1, p2, p4);
            QuickhullKolmio tahko3 = new QuickhullKolmio(p1, p3, p4);
            QuickhullKolmio tahko4 = new QuickhullKolmio(p2, p3, p4);

            ArrayList<Piste> kirjoitettava = new ArrayList();
            kirjoitettava.add(p1);
            kirjoitettava.add(p2);
            kirjoitettava.add(p3);
            kirjoitettava.add(p4);
            kirjoitaTiedostoon(kirjoitettava, "ensimmainen.txt");
            System.out.println("printd");

            // Jaetaan kukin piste jollekin tetraedrin tahkolle
            for (Piste p : jaettavatPisteet) {
                if (tahko1.eriPuolilla(keskipiste, p)) {
                    tahko1.lisaaNakyvaPiste(p);
                } else if (tahko2.eriPuolilla(keskipiste, p)) {
                    tahko2.lisaaNakyvaPiste(p);
                } else if (tahko3.eriPuolilla(keskipiste, p)) {
                    tahko3.lisaaNakyvaPiste(p);
                } else if (tahko4.eriPuolilla(keskipiste, p)) {
                    tahko4.lisaaNakyvaPiste(p);
                } else {
                    throw new Error("Kappale ei ole konveksi");
                }
            }

            kirjoitaTiedostoon(tahko1.getNakyvatPisteet(), "tahko1_nakyvat.txt");
            kirjoitaTiedostoon(tahko2.getNakyvatPisteet(), "tahko2_nakyvat.txt");
            kirjoitaTiedostoon(tahko3.getNakyvatPisteet(), "tahko3_nakyvat.txt");
            kirjoitaTiedostoon(tahko4.getNakyvatPisteet(), "tahko4_nakyvat.txt");

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

    private ArrayList<QuickhullKolmio> etsiValoisat(QuickhullKolmio tyostettava, Piste p) {
        ArrayList<QuickhullKolmio> valoisat = new ArrayList<>();
        for (QuickhullKolmio k : tyostettava.getNaapurit()) {
            if (k.eriPuolilla(keskipiste, p)) {
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

        ArrayList<QuickhullKolmio> varjoisat = new ArrayList<>(tyostettava.getNaapurit());
        varjoisat.removeAll(valoisat);
        for (QuickhullKolmio varjoisa : varjoisat) {
            for (QuickhullKolmio valoisa : valoisat) {
                Sivu yhteinen = valoisa.yhteinenSivu(varjoisa);
                if (yhteinen != null) {
                    horisontti.add(yhteinen);
                }
            }
            Sivu yhteinen = tyostettava.yhteinenSivu(varjoisa);
            if (yhteinen != null) {
                horisontti.add(yhteinen);
            }
        }
        System.out.println("horisontissa "+horisontti.size()+" sivua");
        return horisontti;
    }

    private void poistaDuplikaatit(ArrayList<Piste> lista) {
        Set<Piste> hashset = new HashSet<>();
        hashset.addAll(lista);
        lista.clear();
        lista.addAll(hashset);
    }

    private void kirjoitaTiedostoon(ArrayList<Piste> data, String tiedostonimi) {
        TiedostoIO.kirjoitaTiedostoon(Delaunay.valmistaPisteetTulostukseen(data, ","), tiedostonimi);
    }

    private Piste etsiKeskipiste(Piste p1, Piste p2, Piste p3, Piste p4) {
        double x = (p1.x() + p2.x() + p3.x() + p4.x()) / 4;
        double y = (p1.y() + p2.y() + p3.y() + p4.y()) / 4;
        double z = (p1.z() + p2.z() + p3.z() + p4.z()) / 4;
        double r = Math.sqrt(x * x + y * y + z * z);
        return new Piste(Math.acos(z / r), Math.atan(y / x), r);
    }
}
