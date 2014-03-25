/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameBoard;

/**
 *
 * @author lili
 */
public class Chip implements Comparable {
   protected int row;
   protected int column;
   protected int color;
   
   public Chip(){
       row=-1;
       column = -1;
       color = -1;
   }
   
   public Chip(int row, int column, int color){
       this.row = row;
       this.column = column;
       this.color = color;
   }
   
   public boolean isEqual(Chip chip){
       if(this.row == chip.row && this.column== chip.column && this.color == chip.color)
           return true;
       return false;
   }
   
   public int compareTo(Object object){
       if(this.isEqual((Chip) object))
           return 0;
       return 1;

   }
   
   public String toString(){
       
       return "("+ this.color + "," + this.row + "," + this.column + ")"; 
       
   }
    
}
