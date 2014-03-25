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
	
	
}
