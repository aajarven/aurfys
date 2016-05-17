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
public class Vektori3D {
    private double i;
    private double j;
    private double k;

    public Vektori3D(double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }
    
    public Vektori3D ristitulo(Vektori3D v){
        return new Vektori3D(this.j*v.k-this.k*v.j, this.k*v.i-this.i*v.k, this.i*v.j-this.j*v.i);
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public double getK() {
        return k;
    }
    
    public double pituus(){
        return Math.sqrt(i*i+j*j+k*k);
    }

    @Override
    public String toString() {
        return "<"+i+", "+j+", "+k+">";
    }
    
    
    
}
