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

}
