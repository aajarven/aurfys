/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaunay;

import java.util.ArrayList;

/**
 *
 * @author anni
 */
public class Delaunay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Piste> pisteet = PisteGeneraattori.generoiLatitudeittain(10, 1.0, 1.2, 0.8);
        for (Piste p : pisteet){
            System.out.println(p.x() + ' ' + p.y() + ' ' + p.z());
        }
    }
    
}
