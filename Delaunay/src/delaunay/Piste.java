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
    
    /**
     * Palauttaa pisteen etäisyyden annetusta pisteestä
     * @param p toinen piste
     * @return pisteiden välinen etäisyys
     */
    public double etaisyys(Piste p){
        return Math.sqrt(Math.pow(this.x()-p.x(), 2)+Math.pow(this.y()-p.y(), 2) + Math.pow(this.z()-p.z(), 2));
    }
    
    /**
     * Palauttaa vektorin tästä pisteestä annettuun pisteeseen
     * @param p kohdepiste
     * @return vektori tästä pisteestä kohdepisteeseen
     */
    public Vektori3D vektoriPisteeseen(Piste p){
        return new Vektori3D(p.x()-this.x(), p.y()-this.y(), p.z()-this.z());
    }
    
    /**
     * Palauttaa pisteen kohtisuoran etäisyyden parametrina annettujen pisteiden
     * virittämästä suorasta.
     * @param p1 ensimmäinen piste suoralla
     * @param p2 toinen piste suoralla
     * @return pisteen etäisyys kahden muun pisteen määräämästä suorasta
     */
    public double etaisyysSuorasta(Piste p1, Piste p2){
        Vektori3D suuntavektori = p1.vektoriPisteeseen(p2);
        Vektori3D v = this.vektoriPisteeseen(p1);
        Vektori3D ristitulo = suuntavektori.ristitulo(v);
        return ristitulo.pituus()/suuntavektori.pituus();
    }
    
}
