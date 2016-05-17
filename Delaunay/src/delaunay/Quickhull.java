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
        Deque<QuickhullKolmio> tyostettavatKolmiot = luoEnsimmainenTetraedri(pisteet);
        TiedostoIO.kirjoitaKolmiotTiedostoihin(new ArrayList<Kolmio>(tyostettavatKolmiot), "ensimmaiset", ",");
        palautettavatKolmiot.addAll(tyostettavatKolmiot);

        while (!tyostettavatKolmiot.isEmpty()) {
            tulostaNakyvienMaara(tyostettavatKolmiot);
            QuickhullKolmio tyostettava = tyostettavatKolmiot.pop();
            Piste kaukaisin;
            try {
                kaukaisin = tyostettava.etsiKaukaisin();
                tyostettava.poistaNakyvaPiste(kaukaisin);
            } catch (Exception ex) {
                System.out.println("Ei löytynyt kaukaisinta\n");
                continue;
            }

            ArrayList<QuickhullKolmio> valoisat = etsiValoisat(tyostettava, kaukaisin);
            System.out.println("löytyi " + valoisat.size() + " valoisaa naapuria");
            TiedostoIO.kirjoitaKolmiotTiedostoihin(valoisat, "valoisat", ",");
            ArrayList<Sivu> horisontti = etsiHorisontti(valoisat, tyostettava);
            ArrayList<QuickhullKolmio> uudetKolmiot = new ArrayList<>();
            for (Sivu s : horisontti) {
                uudetKolmiot.add(new QuickhullKolmio(kaukaisin, s.getP1(), s.getP2()));
            }
            System.out.println("Luotiin " + uudetKolmiot.size() + " uutta kolmiota");

            palautettavatKolmiot.removeAll(valoisat);
            palautettavatKolmiot.remove(tyostettava);
            palautettavatKolmiot.addAll(uudetKolmiot);
            tyostettavatKolmiot.addAll(uudetKolmiot);
            tyostettavatKolmiot.removeAll(valoisat);
            System.out.println("Palautettavia kolmioita nyt " + palautettavatKolmiot.size());
            System.out.println("");

            jaaNakyvatPisteet(tyostettava.getNakyvatPisteet(), uudetKolmiot);

//            for (Piste p : tyostettava.getNakyvatPisteet()) {
//                for (QuickhullKolmio k : uudetKolmiot) {
//                    if (k.eriPuolilla(keskipiste, p)) {
//                        k.lisaaNakyvaPiste(p);
//                        break;
//                    }
//                }
//            }
            paivitaNaapurit(tyostettava, valoisat, uudetKolmiot);
        }

        return palautettavatKolmiot;
    }

    private void tulostaNakyvienMaara(Deque<QuickhullKolmio> tyostettavatKolmiot) {
        int pistemaara = 0;
        for (QuickhullKolmio k : tyostettavatKolmiot) {
            pistemaara += k.getNakyvatPisteet().size();
        }
        System.out.println("näkyviä pisteitä " + pistemaara);
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

        kaukaisimmat.remove(p1);
        kaukaisimmat.remove(p2);
        jaettavatPisteet.remove(p1);
        jaettavatPisteet.remove(p2);

        // etsitään piste, joka on kauimpana p1 ja p2 määräämästä suorasta
        p3 = kaukaisimmat.get(0);
        suurinEtaisyys = p3.etaisyysSuorasta(p1, p2);
        for (Piste tutkittava : kaukaisimmat) {
//            System.out.println("pisteiden etäisyys: " + tutkittava.etaisyysSuorasta(p1, p2));
            if (tutkittava.etaisyysSuorasta(p1, p2) > suurinEtaisyys) {
//                System.out.println("oli suurempi kuin edellinen");
                p3 = tutkittava;
                suurinEtaisyys = tutkittava.etaisyysSuorasta(p1, p2);
            }
        }
        kaukaisimmat.remove(p3);
        jaettavatPisteet.remove(p3);

        QuickhullKolmio tahko1 = new QuickhullKolmio(p1, p2, p3);

        try {
        p4 = tahko1.etsiKaukaisin(jaettavatPisteet);
        jaettavatPisteet.remove(p4);
        keskipiste = etsiKeskipiste(p1, p2, p3, p4);
//            System.out.println("Kauimpana tahkosta");
//            System.out.println(p4);
        QuickhullKolmio tahko2 = new QuickhullKolmio(p1, p2, p4);
        QuickhullKolmio tahko3 = new QuickhullKolmio(p1, p3, p4);
        QuickhullKolmio tahko4 = new QuickhullKolmio(p2, p3, p4);

        ArrayList<Piste> kirjoitettava = new ArrayList();
        kirjoitettava.add(p1);
        kirjoitettava.add(p2);
        kirjoitettava.add(p3);
        kirjoitettava.add(p4);
//            kirjoitaTiedostoon(kirjoitettava, "ensimmainen.txt");
//            System.out.println("printd");

        ensimmaisetKasiteltavat.add(tahko1);
        ensimmaisetKasiteltavat.add(tahko2);
        ensimmaisetKasiteltavat.add(tahko3);
        ensimmaisetKasiteltavat.add(tahko4);

        jaaNakyvatPisteet(jaettavatPisteet, new ArrayList<QuickhullKolmio>(ensimmaisetKasiteltavat));

        kirjoitaTiedostoon(tahko1.getNakyvatPisteet(), "tahko1_nakyvat.txt");
        kirjoitaTiedostoon(tahko2.getNakyvatPisteet(), "tahko2_nakyvat.txt");
        kirjoitaTiedostoon(tahko3.getNakyvatPisteet(), "tahko3_nakyvat.txt");
        kirjoitaTiedostoon(tahko4.getNakyvatPisteet(), "tahko4_nakyvat.txt");

        // asetetaan tahkot toistensa naapureiksi
        for (QuickhullKolmio k1 : ensimmaisetKasiteltavat) {
            for (QuickhullKolmio k2 : ensimmaisetKasiteltavat) {
                if (k1 != k2) {
                    k1.lisaaNaapuri(k2);
                }
            }
        }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Ensimmäisen tetraedrin viimeisen kärjen etsiminen epäonnistui");
            System.exit(0);
        }

        return ensimmaisetKasiteltavat;
    }

    private void jaaNakyvatPisteet(ArrayList<Piste> jaettavatPisteet, Iterable<QuickhullKolmio> kolmiot) throws Error {
        for (Piste p : jaettavatPisteet) {
            boolean loydettyKolmio = false;
            for (QuickhullKolmio k : kolmiot) {
                if (k.eriPuolilla(keskipiste, p)) {
                    k.lisaaNakyvaPiste(p);
                    loydettyKolmio = true;
                    break;
                }
            }
            if (!loydettyKolmio) {
                System.out.println("Pisteelle " + p.toString() + " ei löytynyt kolmiota");
                throw new Error();
            }
        }
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
        System.out.println("horisontissa " + horisontti.size() + " sivua");
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

    private void paivitaNaapurit(QuickhullKolmio tyostettava, ArrayList<QuickhullKolmio> valoisat, ArrayList<QuickhullKolmio> uudetKolmiot) {
        int naapurit = 0;

        // poistetaan työstettävä kaikkien naapuriensa naapurustosta
        ArrayList<QuickhullKolmio> tyostettavanNaapurit = new ArrayList<>(tyostettava.getNaapurit());
        for (QuickhullKolmio tyostettavanNaapuri : tyostettavanNaapurit) {
            tyostettavanNaapuri.poistaNaapuri(tyostettava);
            tyostettava.poistaNaapuri(tyostettavanNaapuri);
        }

        // poistetaan jokainen valoisa kolmio jokaisen naapurinsa naapurustosta
        HashSet<QuickhullKolmio> valoistenNaapurit = new HashSet<>();
        for (QuickhullKolmio valoisa : valoisat) {
            valoistenNaapurit.addAll(valoisa.getNaapurit());
        }
        for (QuickhullKolmio valoisa : valoisat) {
            for (QuickhullKolmio naapuri : valoistenNaapurit) {
                naapuri.poistaNaapuri(valoisa);
            }
        }

        // kerätään yhteen listaan kaikki uusien kolmioiden mahdolliset naapurit
        HashSet<QuickhullKolmio> mahdollisetNaapurit = new HashSet(tyostettava.getNaapurit());
        for (QuickhullKolmio k : tyostettava.getNaapurit()) {
            mahdollisetNaapurit.addAll(k.getNaapurit());
        }
        mahdollisetNaapurit.addAll(uudetKolmiot);

        // etsitään uusien kolmioiden naapurit
        for (QuickhullKolmio uusi : uudetKolmiot) {
            for (QuickhullKolmio mahdollinen : mahdollisetNaapurit) {
                if (uusi.erillisetPisteet(mahdollinen).size() != 3) {
                    uusi.lisaaNaapuri(mahdollinen);
                    mahdollinen.lisaaNaapuri(uusi);
                    naapurit++;
                }
            }
        }

        System.out.println("löydettiin " + naapurit + " naapuria");

    }
}
