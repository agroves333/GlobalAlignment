package compbiohw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Driver {

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("test2.dat"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
        }
        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();
        
        Scanner in = new Scanner(System.in);
        System.out.println("Select Gap Mode:");
        System.out.println("1: Gaps allowed at beginning of S1, but not S2");
        System.out.println("2: Gaps allowed at beginning of S2, but not S1");
        System.out.println("3: Gaps allowed at end of S1, but not S2");
        System.out.println("4: Gaps allowed at end of S2, but not S1");
        System.out.println("5: Gaps allowed at beginning and end of S1");
        System.out.println("6: Gaps allowed at beginning and end of S2");
        System.out.println("7: Gaps allowed at beginning of S1 and end of S2");
        System.out.println("8: Gaps allowed at beginning of S2 and end of S1");
        
        int selection = in.nextInt();
        
        Map<String, Boolean> mode  = new HashMap<>();
        mode.put("s1_begin", selection == 1 || selection == 5 || selection == 7);
        mode.put("s1_end",   selection == 3 || selection == 5 || selection == 8);
        mode.put("s2_begin", selection == 2 || selection == 6 || selection == 8);
        mode.put("s2_end",   selection == 4 || selection == 6 || selection == 7);
        
        SemiGlobalAlignmentTable dpt = new SemiGlobalAlignmentTable(s1, s2, mode);
//        GlobalAlignmentTable dpt = new GlobalAlignmentTable(s1, s2);
        
        dpt.printScores();
        dpt.printPaths();
        dpt.printAlignment();
        System.out.println("Optimal Score: " + dpt.getScore());
    }
}
