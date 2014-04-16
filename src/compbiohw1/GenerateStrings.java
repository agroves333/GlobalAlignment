/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compbiohw1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class GenerateStrings {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter("test.dat", "UTF-8")) {
            
            Random r = new Random(); // Keep this stored as a field
            char[] nucleotides = new char[]{'A', 'C', 'G', 'T'};
            
            for (int i = 0; i < 1000; i++) {
                writer.append(nucleotides[r.nextInt(nucleotides.length)]);
            }
            
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GenerateStrings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
