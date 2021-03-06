package jaicore.search.graphgenerators.npuzzle.standard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A node for the normal n-Puzzleproblem.
 * Every node contains the current board configuration as an 2D-Array of integer.
 * The empty space is indicated by an Integer with value 0.
 * @author jkoepe
 */
public class NPuzzleNode {
	//board configuration and empty space
	protected int [][] board;
	protected int emptyX;
	protected int emptyY;
	protected int numberOfMoves;
	
	/**
	 * Constructor for a NPuzzleNode which creates a NPuzzleNode with complete 
	 * randomly distributed numbers.
	 * @param dim
	 * 		The dimension of the board.
	 */
	public NPuzzleNode(int dim) {
		this.numberOfMoves = 0;
		board = new int[dim][dim];
		List<Integer> numbers = new ArrayList<>(dim*dim);
		
		for(int i = 0; i < dim*dim; i++) 
			numbers.add(i);
		//creating of a random starting configuration for the NPuzzleProblem
		for(int i = 0; i <dim ; i++) {
			for(int j = 0; j < dim; j++) {
				int index = (int) (Math.random()* numbers.size());
				int number = numbers.remove(index);
				board[i][j] = number;
				if(number == 0) {
					emptyX = j;
					emptyY = i;
				}
			}
		}
	}
	
	/**
	 * Constructor for a NPuzzleNode which creates a NPuzzleNode.
	 * The board configuration starts with the targetconfiguration and shuffels the tiles afterwards.
	 * @param dim
	 * 			The dimension of the board.
	 * @param perm
	 * 			The number of moves which should be made before starting the search.
	 * 			This number is hardcoded to at least 1.
	 */
	public NPuzzleNode(int dim, int perm) {
		this.board = new int[dim][dim];
		this.numberOfMoves = 0;
		int x = 1;
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				board[i][j] = x;
				x++;
			}
		}
		emptyX = dim-1;
		emptyY = dim-1;
		if(perm <1)
			perm = 1;
		
		for(int i = 0; i < perm; i++) {
			String s = "";
			if(emptyX> 0)//move left
				s+= "l";
			if(emptyX< dim -1)//move right
				s+= "r";
			if(emptyY>0)//move up
				s+="u";
			if(emptyY< dim -1)//move down
				s+= "d";
			
			move(s.charAt((int)(Math.random()*s.length())));
		}
	}
	
	/**
	 * Constructor for a NPuzzleNode in which the board is already given.
	 * @param board
	 * 			The board configuration for this node
	 * @param emptyX
	 * 			The empty space on the x-axis.
	 * @param emptyY
	 * 			The empty space on the y-axis.
	 * 
	 * @param noMoves
	 * 			The number of already done moves.
	 */
	public NPuzzleNode(int [][] board, int emptyX, int emptyY, int numberOfMoves) {
		this.board = board;
		this.emptyX = emptyX;
		this.emptyY = emptyY;
		this.numberOfMoves = numberOfMoves;
	}

	
	public int[][] getBoard() {
		return board;
	}

	public int getEmptyX() {
		return emptyX;
	}

	public int getEmptyY() {
		return emptyY;
	}
	
	public int getNumberOfMoves() {
		return this.numberOfMoves;
	}
	
	
	/**
	 * Returns a graphical version of the board configuration. 
	 * Works best if there is no number with two or more digits.
	 */
	@Override
	public String toString() {
//		return "NPuzzleNode [board=" + Arrays.toString(board) + ", emptyX=" + emptyX + ", emptyY=" + emptyY + "]";
		String s = "";
		
		for(int j = 0; j < board.length; j++) {
			s+= "----";
		}
		s+= "\n";
		
		for(int i = 0; i< board.length; i++) {
			s += "| ";
			for(int j = 0; j < board.length; j++) {
				s += board[i][j] + " | ";
			}
			s += "\n";
			for(int j = 0; j < board.length; j++) {
				s+= "----";
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Moves the empty tile to another location.
	 * The possible parameters to move the empty tiles are:
	 * <code>l</code> for moving the empty space to the left.
	 * <code>right</code> for moving the empty space to the right.
	 * <code>u</code> for moving the empty space upwards.
	 * <code>d</down> for moving the empty space downwards.
	 * @param m
	 * 		The character which indicates the specific moves. Possible characters are given above.
	 * 		
	 */
	private void move(char m) {
		switch (m) {
			case 'l': 
				move(0,-1);
				break;
			case 'r': 
				move(0,1);
				break;
			case 'u': 
				move(-1,0);
				break;
			case 'd': 
				move(1,0);
				break;
		}
		
	}
	
	/**
	 * The actual move of the empty tile.
	 * @param y
	 * 		The movement on the y-axis. This value should be -1 if going upwards, 1 if going downwards.
	 * 		Otherwise it should be 0.
	 * @param x
	 * 		The movement on the y-axis. This value should be -1 if going left, 1 if going right.
	 * 		Otherwise it should be 0.
	 */
	private void move(int y, int x) {
		int eY = getEmptyY();		
		int eX = getEmptyX();
		board[eY][eX] = board[eY +y][eX+x];
		board[eY+y][eX+x] = 0;
		this.emptyY += y;
		this.emptyX += x;
	}
	
	
	/**
	 * Returns the number of wrongly placed tiles
	 * @return
	 * 		The number of wrongly placed tiles.
	 */
	public int getNumberOfWrongTiles() {
		int wrongTiles = 0;
		int x = 1;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(i == board.length -1 && j == board.length -1)
					x = 0;
				
				
				if(x != board[i][j])
					wrongTiles ++;
				
				x++;
			}
		}
		
		return wrongTiles;
	}

	/**
	 * Returns the steps which are minimal need to reach a goal state
	 * @return
	 * 	The number of steps.
	 */
	public double getDistance() {
		double d = 0.0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0;  j < board.length; j++) {
				int tile = board[i][j];
				double x = (double)tile / board.length;
				double y = tile % board.length-1;
				if(x%1==0)
					x--;
				x =Math.floor(x);
				if (y < 0)
					y = board.length-1;

				if(tile == 0) {
//					x = board.length-1;
//					y = board.length-1;
					continue;
				}
				double h1 = Math.abs(i-x);
				double h2 = Math.abs(j-y);
				double d1 = h1+h2;
				d+= d1;
			}
		}
		return d;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
//		result = prime * result + Arrays.hashCode(board);
		result = prime * result + emptyX;
		result = prime * result + emptyY;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NPuzzleNode other = (NPuzzleNode) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (emptyX != other.emptyX)
			return false;
		if (emptyY != other.emptyY)
			return false;
		return true;
	}
	
	
	
}
