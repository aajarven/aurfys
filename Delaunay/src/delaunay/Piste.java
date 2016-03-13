/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

/**
 *
 * @author anni
 */
public class Piste {
    private double theta;
    private double fii;
    private double r;

    public Piste(double theta, double fii, double r) {
        this.theta = theta;
        this.fii = fii;
        this.r = r;
    }

    public double getFii() {
        return fii;
    }

    public double getR() {
        return r;
    }

    public double getTheta() {
        return theta;
    }
    
    public double x(){
        return r*Math.sin(theta)*Math.cos(fii);
    }
    
    public double y(){
        return r*Math.sin(theta)*Math.sin(fii);
    }
    
    public double z(){
        return r*Math.cos(theta);
    }
    
    public boolean equals(Object o){
        if ( !(o instanceof Piste) ){
            return false;
        }
        Piste p = (Piste) o;
        if (p.theta == this.theta && p.fii == this.fii && p.r == this.r){
            return true;
        } else {
            return false;
        }
    }
    
}
