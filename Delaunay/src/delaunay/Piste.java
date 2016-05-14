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
    /**
     * Maksimietäisyys pisteille, joiden katsotaan olevan sama piste
     */
    private final static double KYNNYSARVO = 0.00001;
    
    /**
     * Latitudi
     */
    private double theta; 
    
    /**
     * Longitudi
     */
    private double fii;
    
    /**
     * Etäisyys origosta
     */
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
    
    @Override
    public boolean equals(Object o){
        if ( !(o instanceof Piste) ){
            return false;
        }
        Piste p = (Piste) o;
        if (Math.abs(p.theta - this.theta) < KYNNYSARVO && Math.abs(p.fii - this.fii) < KYNNYSARVO && Math.abs(p.r - this.r) < KYNNYSARVO){
            return true;
        } else {
            return false;
        }
    }
    
    public String toString(){
        return this.x()+"\t"+this.y()+"\t"+this.z();
    }
    
    public double etaisyys(Piste p){
        return Math.sqrt(Math.pow(this.x()-p.x(), 2)+Math.pow(this.y()-p.y(), 2) + Math.pow(this.z()-p.z(), 2));
    }
    
}
