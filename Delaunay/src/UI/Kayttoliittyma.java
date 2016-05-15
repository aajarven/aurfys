/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author anni
 */
public class Kayttoliittyma implements Runnable{
    
    private JFrame frame;

    @Override
    public void run() {
        frame = new JFrame("Delaunay");
        frame.setPreferredSize(new Dimension(800,800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }
    
    private void luoKomponentit(Container container){
        //container.add();
    }

    public JFrame getFrame() {
        return frame;
    }
    
    
    
}
