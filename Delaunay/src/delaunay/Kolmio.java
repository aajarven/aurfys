/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author anni
 */
public class Kolmio {
    private Piste p1;
    private Piste p2;
    private Piste p3;

    public Kolmio(Piste p1, Piste p2, Piste p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
   
    public Piste getP1() {
        return p1;
    }

    public Piste getP2() {
        return p2;
    }

    public Piste getP3() {
        return p3;
    }
    
    public ArrayList<Piste> getPisteet(){
        ArrayList<Piste> pisteet = new ArrayList<>();
        pisteet.add(p1);
        pisteet.add(p2);
        pisteet.add(p3);
        return pisteet;
    }

    @Override
    public String toString() {
        String palautus = "";
        for (Piste p: getPisteet()){
            palautus = palautus+p.toString();
            palautus = palautus+"\n";
        }
        
        return palautus;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.p1);
        hash = 73 * hash + Objects.hashCode(this.p2);
        hash = 73 * hash + Objects.hashCode(this.p3);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Kolmio k = (Kolmio) o;
        if(!k.getPisteet().contains(this.p1)){
            return false;
        } else if (!k.getPisteet().contains(this.p2)){
            return false;
        } else if (!k.getPisteet().contains(this.p3)){
            return false;
        }
        return true;
    }
    
    
    
}
