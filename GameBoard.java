/* GameBoard.java */

package player;

/** A class to home all the fields of the game board.
 * 
 *
 */

public class GameBoard {
	
	public final static int EMPTY = -1;
	public final static int BLACK = 0;
	public final static int WHITE = 1;
	private int board[][] = new int [8][8];
	
	public GameBoard() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				board[x][y] = EMPTY;
			}
		}
	}
	
	/** records the move as a move and updates the internal gameboard and returns true 
	 * if the move is valid. if not returns false without modifying the gameboard.
	 * @param m 
	 * @param color
	 * @return true or false
	 */
	public boolean update(Move m, int color){
		if (m.moveKind == Move.ADD){
	    		board[m.x1][m.y1] = color;
	    		return true;	
		}
    		if (m.moveKind == Move.STEP){
    			board[m.x1][m.y1] = board[m.x2][m.y2];
    			board[m.x2][m.y2] = EMPTY;
    			return true;	
    		}
		return false;
	}
	
	
	private Chip parser(Chip chip, int xidx, int yidx){
       int x = chip.row + xidx;
       int y = chip.column+yidx;
       Chip connectedChip = new Chip();
       while(x<8 && x>-1 && y<8 && y>-1){
           
           if(board[x][y]==chip.color){
               connectedChip.row = x;
               connectedChip.column = y;
               connectedChip.color = chip.color;
               break;
           }
           else if(board[x][y]!= chip.color && board[x][y] != -1)
               break;
           
           x +=xidx;
           y +=yidx;
       }
       return connectedChip;
   }
   
   
   public boolean hasValidNetwork(int side) {
       
       if(side == 1){
       for(int i= 1; i<6; i++){
          if(this.board[0][i] == side){       
              if (wns(new Chip(0,i,side),new DList(),-1,6))
                  return true;
          } 
      }
      
       return false;
       }
       else{
           for(int i= 1; i<6; i++){
          if(this.board[i][0] == side){       
              if (bns(new Chip(i,0,side),new DList(),-1,6))
                  return true;
          } 
       }
           return false;
   }
   }
   
    public boolean wns(Chip chip, DList network, int angle, int length){
      network.insertBack(chip);
      System.out.println(chip.toString());
      DListNode node = (DListNode) connectionIdentifier(chip).front();
      if (chip.row == 7) {
          if ((length - 1) <= 0) {
              return true;
          } else {
              return false;
          }
      } else {
          try {


              for (int i = 1; i <= 8; i++) {
                  if (chip.row == 0 && (i == 4 || i == 5)) {
                      node = (DListNode) (node.next());
                  } else if (i == angle || network.isInList((Chip) (node.item())) || ((Chip) (node.item())).color == -1 || ((Chip) (node.item())).row ==0 ) {
                      node = (DListNode) (node.next());
                  } else {
                      boolean recurse = wns(((Chip) (node.item())), network, i, length - 1);
                      node = (DListNode) (node.next());
                      if(recurse)
                          return true;
                      else
                          network.removeBack();                      
                  }
              }


          } catch (InvalidNodeException e) {
              System.out.println(e.toString());
          }
          return false;
      }

   } 
   
   public boolean bns(Chip chip, DList network, int angle, int length){
      network.insertBack(chip);
      System.out.println(chip.toString());
      DListNode node = (DListNode) connectionIdentifier(chip).front();
      if (chip.column == 7) {
          if ((length - 1) <= 0) {
              return true;
          } else {
              return false;
          }
      } else {
          try {


              for (int i = 1; i <= 8; i++) {
                  if (chip.column == 0 && (i == 4 || i == 5)) {
                      node = (DListNode) (node.next());
                  } else if (i == angle || network.isInList((Chip) (node.item())) || ((Chip) (node.item())).color == -1 || ((Chip) (node.item())).column ==0 ) {
                      node = (DListNode) (node.next());
                  } else {
                      boolean recurse = bns(((Chip) (node.item())), network, i, length - 1);
                      node = (DListNode) (node.next());
                      if(recurse)
                          return true;
                      else
                          network.removeBack();                      
                  }
              }


          } catch (InvalidNodeException e) {
              System.out.println(e.toString());
          }
          return false;
      
  }}
  
  public DList connectionIdentifier(Chip chip){
        DList chipList = new DList();
        if(chip.color == 1){
        chipList.insertBack(parser(chip,-1,-1));
        chipList.insertBack(parser(chip,-1,0));
        chipList.insertBack(parser(chip,-1,1));
        chipList.insertBack(parser(chip,0,-1));
        chipList.insertBack(parser(chip,0,1));
        chipList.insertBack(parser(chip,1,-1));         
        chipList.insertBack(parser(chip,1,0));
        chipList.insertBack(parser(chip,1,1));
        }
        else{
           chipList.insertBack(parser(chip,1,-1)); 
           chipList.insertBack(parser(chip,0,-1));
           chipList.insertBack(parser(chip,-1,1));
           chipList.insertBack(parser(chip,1,0));
           chipList.insertBack(parser(chip,-1,0));
           chipList.insertBack(parser(chip,1,1));
           chipList.insertBack(parser(chip,0,1));
           chipList.insertBack(parser(chip,-1,1));
        }
        
        System.out.println(chipList.toString());
        
        return chipList;
    }
    
    
     public double evaluate(int side) {
        if (hasValidNetwork(side)) {
            return 1;
        } else if (hasValidNetwork(1 - side)) {
            return -1;
        } else {
            int myConnections = partialEvaluation(side);
            int opponentConnections = partialEvaluation(1 - side);
            return ((double)(myConnections - opponentConnections) / (double)(myConnections + opponentConnections));
        }
    }
    
    public int partialEvaluation(int side){
        int totalConnections = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board[i][j] == side) {
                    DList connections = connectionIdentifier(new Chip(i, j, side));
                    DListNode node = (DListNode) connections.front();
                    try {
                        for (int k = 0; k < 8; k++) {
                            if (((Chip) node.item()).color != -1) {
                                totalConnections++;
                            }
                            node = (DListNode) node.next();
                        }
                    } catch (InvalidNodeException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }
        return totalConnections;
        
    }
	
}

public DList listofValidMoves(int color){
  DList listofmoves = new DList();
  for (int x = 0, x<7, x++){
    for (int y = 0, y<7, y++){
      if (isValidMove(x, y, color)){
        int [] list = new int[2];
        list[0] = x;
        list[1] = y;
        listofmoves.insertback(list);
      }
    }
  }
  return listofmoves;
}

public boolean isValidMove(int x, int y, int color){
  int [][] neighbors=neighbor(x,y);
  if (x==0 && y==0 || x==7 && y==0 || x==0 && y==7 || x==7 && y==7){
    return false;
  }else if (getColor(x,y) !== -1){
    return false;
  }else if (color == 1 && (x==0 || x==7)){
    return false;
  }else if (color == 0 && (y==0 || y==7)){
    return false;
  }else
    return checkNeighbors(x,y,color);
}

public boolean checkNeighbors (int x, int y, int color){
  DList neighbors  = neighbor(x,y)
  int numberofsamecolors = 0;
  while (neighbors.node.next !== null){
    int [] vals = node.item;
    if (vals[0] == color){
      numberofsamecolors++;
      if (numberofsamecolors>1){
        return false;
      }
      DList otherneighbors = neighbors(vals[1], vals[2]);
      if (otherneighbors.length() > 1){
        return false;
      }
    }
    neighbors.node = neighbors.node.next;
  }
  return true;
}

private boolean inBound(int x, int y){
    return (x>=0 && x<=7 && y>=0 && y<=7);
  }

public int getColor(int x, int y) {
      return board[x][y];
    }
//CHECK FOR X AND Y TO MAKE SURE THEY ARE THE SAME

public DList neighbor(int x, int y){
  DList neighbors = new DList()
  for (int offsetX = -1; offsetX<= 1, offsetX){
    for (int offsetY = -1, offsetY <= 1, offsetY++){
      if (!(offsetX == 0 && offsetY == 0)){
        if (inbounds(x+ offsetX, y + offsetY)){
          int [] vals = new int[3];
          vals[0] = getColor(x+offsetX, y+offsetY);
          vals[1] = x+offsetX;
          vals[2] = y+offsetY;
          neighbors.insertback(vals);
        }
      }
    }
  }
  return neighbors;
}
