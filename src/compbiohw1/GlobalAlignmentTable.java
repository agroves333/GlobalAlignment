package compbiohw1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Adam
 */
public class GlobalAlignmentTable{
    
    String s1;
    String s2;
    String[] alignment;
    TableEntry table[][];
    int MATCH = 2, MISMATCH = -1, INDEL = -1;
    
    int score;

    public GlobalAlignmentTable(String s1, String s2) {
        
        this.s1 = "_"+s1;
        this.s2 = "_"+s2;
        this.table = new TableEntry[this.s1.length()][this.s2.length()];
        
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new TableEntry();
            }
        }
        
//        populateTable();
//        align();
        
        this.score = this.table[this.s1.length()-1][this.s2.length()-1].getScore();
    }
    
    
    public int getScore(){
        return this.score;
    }
    
   
    protected void populateTable(){
        for(int i = 0; i < this.s1.length(); i++){
            for(int j = 0; j < this.s2.length(); j++){
                if (i == 0 && j == 0) {
                    table[i][j].setScore(0);
                }
                else if(i == 0 && j > 0) {
                    table[i][j].setScore(table[i][j-1].getScore() + INDEL);
                    table[i][j].setPath(TableEntry.Path.LEFT);
                }
                else if(i > 0 && j == 0) {
                    table[i][j].setScore(table[i-1][j].getScore() + INDEL);
                    table[i][j].setPath(TableEntry.Path.UP);
                }
                else if(i > 0 && j > 0){
                    table[i][j].setScore(calculateScore(i,j));
                }
            }
        }
    }
    
    
    protected int calculateScore(int i , int j){
      
        boolean isMatch = s1.charAt(i) == s2.charAt(j) ? true : false;
        
        Map<String, Integer> cells = new HashMap<>();
        cells.put("up", table[i-1][j].getScore() + INDEL);
        cells.put("diag", table[i-1][j-1].getScore() + (isMatch ? MATCH : MISMATCH));
        cells.put("left", table[i][j-1].getScore() + INDEL);
        
        int max = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> cell : cells.entrySet()) {
            if (cell.getValue() > max) {
                max = cell.getValue();
                switch (cell.getKey()) {
                    case "up":
                        table[i][j].setPath(TableEntry.Path.UP);
                        break;
                    case "diag":
                        table[i][j].setPath(TableEntry.Path.DIAG);
                        break;
                    case "left":
                        table[i][j].setPath(TableEntry.Path.LEFT);
                        break;
                }
            }
        }
        
        return max;
    }
    
    protected void align(){
        String a1 = "";
        String a2 = "";
        
        int i = s1.length()-1;
        int j = s2.length()-1;
        while (i != 0 && j != 0) {
            if(table[i][j].getPath() == TableEntry.Path.UP){
                a1 += s1.charAt(i);
                a2 += "_";
                i--;
            }
            else if(table[i][j].getPath() == TableEntry.Path.DIAG){
                a1 += s1.charAt(i);
                a2 += s2.charAt(j);
                i--;
                j--;
            }
            else if(table[i][j].getPath() == TableEntry.Path.LEFT){
                a1 += "_";
                a2 += s2.charAt(j);
                j--;
            }
        }
        
        alignment =  new String[]{new StringBuilder(a1).reverse().toString(), new StringBuilder(a2).reverse().toString()};
    }
    
    
    public void printPaths(){
        
        System.out.println("--------------------------------------------");
        System.out.format("%5s", " ");
        for (int i = 0; i < s2.length(); i++) {
            System.out.format("%5s", s2.charAt(i));
        }
        System.out.println();
        for (int i = 0; i < this.table.length; i++) {
            System.out.format("%5s", s1.charAt(i));
            for (int j = 0; j < this.table[i].length; j++) {
                  System.out.format("%5s", this.table[i][j].getPath());
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");
    }
    
    public void printScores(){
        
        System.out.println("--------------------------------------------");
        System.out.format("%3s", " ");
        for (int i = 0; i < s2.length(); i++) {
            System.out.format("%3s", s2.charAt(i));
        }
        System.out.println();
        for (int i = 0; i < this.table.length; i++) {
            System.out.format("%3s", s1.charAt(i));
            for (int j = 0; j < this.table[i].length; j++) {
                  System.out.format("%3s", this.table[i][j].getScore());
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");
    }
    
    public void printAlignment(){
        for (int i = 0; i < alignment.length; i++) {
            System.out.println(alignment[i]);
        }
    }
}
