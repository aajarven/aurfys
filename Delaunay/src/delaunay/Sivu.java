/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.Objects;

/**
 *
 * @author anni
 */
public class Sivu {

    private Piste p1;
    private Piste p2;

    public Sivu(Piste p1, Piste p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Keroo, onko tällä sivulla yhteistä pistettä annetun kolmion kanssa
     *
     * @param k tutkittava kolmio
     * @return true jos yhteisiä pisteitä on vähintään yksi
     */
    public boolean onYhteinenPiste(Kolmio k) {
        for (Piste p : k.getPisteet()) {
            if (p.equals(p1) || p.equals(p2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kertoo, onko tällä sivulla yhteistä pistettä annetun sivun kanssa
     *
     * @param s tutkittava sivu
     * @return true, jos sivuilla on vähintään yksi yhteinen piste
     */
    public boolean onYhteinenPiste(Sivu s) {
        if (s.p1.equals(this.p1) || s.p1.equals(this.p2)) {
            return true;
        } else if (s.p2.equals(this.p1) || s.p2.equals(this.p2)) {
            return true;
        }
        return false;
    }

    /**
     * Kertoo, onko annettu piste jompikupmi sivun päätepisteistä
     * @param p tutkittava piste
     * @return true, jos annettu piste on sivun päätepiste
     */
    public boolean sisaltaaPisteen(Piste p) {
        if (p.equals(p1) || p.equals(p2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Sivu)) {
            return false;
        } else {
            Sivu s = (Sivu) o;
            if ((s.p1.equals(this.p1) || s.p1.equals(this.p2)) && (s.p2.equals(this.p1) || s.p2.equals(this.p2))) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.p1);
        hash = 29 * hash + Objects.hashCode(this.p2);
        return hash;
    }

    /**
     * Palauttaa sivun ensimmäisen päätepisteen
     * @return päätepiste
     */
    public Piste getP1() {
        return p1;
    }

    /**
     * Palauttaa sivun toisen päätepisteen
     * @return päätepiste
     */
    public Piste getP2() {
        return p2;
    }
    
}
