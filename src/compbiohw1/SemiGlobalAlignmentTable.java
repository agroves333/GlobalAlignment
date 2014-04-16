package compbiohw1;

import java.util.Map;

/**
 *
 * @author Adam Groves
 */
public class SemiGlobalAlignmentTable extends GlobalAlignmentTable{
    
    Map <String, Boolean> mode;
    
    public SemiGlobalAlignmentTable(String s1, String s2, Map<String, Boolean> mode) {
        
        super(s1, s2);
        this.s1 = "_"+s1;
        this.s2 = "_"+s2;
        this.table = new TableEntry[this.s1.length()][this.s2.length()];
        this.mode = mode; 
       
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = new TableEntry();
            }
        }
        
        populateTable();
        align();
        
    }
    
    
    @Override
    protected void populateTable(){
        for(int i = 0; i < this.s1.length(); i++){
            for(int j = 0; j < this.s2.length(); j++){
                if (i == 0 && j == 0) {
                    table[i][j].setScore(0);
                }
                else if(i == 0 && j > 0) {
                    if(mode.get("s1_begin").booleanValue()){
                        table[i][j].setScore(0);
                    }
                    else{
                        table[i][j].setScore(table[i][j-1].getScore() + INDEL);
                    }
                    table[i][j].setPath(TableEntry.Path.LEFT);
                }
                else if(i > 0 && j == 0) {
                    if(mode.get("s2_begin").booleanValue()){
                        table[i][j].setScore(0);
                    }
                    else{
                        table[i][j].setScore(table[i-1][j].getScore() + INDEL);
                    }
                    table[i][j].setPath(TableEntry.Path.UP);
                }
                else if(i > 0 && j > 0){
                    table[i][j].setScore(super.calculateScore(i,j));
                }
            }
        }
    }
    
    @Override
    protected void align(){
        String a1 = "";
        String a2 = "";
        int i = s1.length()-1;
        int j = s2.length()-1;
        
        // If not charging for spaces at end of S1, 
        // use maximum score of last row as alignment starting position
        if(mode.get("s1_end")){
            int max = Integer.MIN_VALUE;
            for (int k = 0; k < s2.length(); k++) {
                if(table[s1.length()-1][k].getScore() > max){
                    max = table[s1.length()-1][k].getScore();
                    j = k;
                    this.score = max;
                }
            }
            
        }
        
        // If not charging for spaces at end of S2,
        // use maximum score of last column as alignment starting position
        if(mode.get("s2_end")){
            int max = Integer.MIN_VALUE;
            for (int k = 0; k < s1.length(); k++) {
                if(table[k][s2.length()-1].getScore() > max){
                    max = table[k][s2.length()-1].getScore();
                    i = k;
                    this.score = max;
                }
            }
            
        }
        if(!mode.get("s1_end") && !mode.get("s2_end")){
            this.score = this.table[s1.length()-1][s2.length()-1].getScore();
        }
        
        
        
        // Calculate Alignment
        while (i != 0 | j != 0) {
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
}
