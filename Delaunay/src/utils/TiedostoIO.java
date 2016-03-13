/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anni Järvenpää
 */
public class TiedostoIO {
    public static void kirjoitaTiedostoon(String kirjoitettava, String tiedostonimi){
        try {
            FileWriter kirjoittaja = new FileWriter(tiedostonimi);
            kirjoittaja.write(kirjoitettava);
            kirjoittaja.close();
        } catch (IOException ex) {
            Logger.getLogger(TiedostoIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
