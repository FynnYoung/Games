import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import machine.MatrixUtil;

public class Sudoku {
	protected static Random random = new Random(System.currentTimeMillis());
	
	protected int[][] board = new int[9][9];
	protected List<Integer>[][] possible = new ArrayList[9][9];
	
	protected void initial(){
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				possible[i][j] = new ArrayList<Integer>(9);
		
		for(int i=1; i<10; i++)
			possible[0][0].add(i);
	}
	
	protected void mask(double p){
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++){
				if(random.nextDouble()<p)
					board[i][j] = 0;
			}
	}
	
	protected void generateComplete(){
		initial();
		
		int x = 0, y = 0;
		while(true){
			if(possible[x][y].isEmpty()){
				if(y>0){
					y--;
				}else{ // y==0
					x--;
					y = 8;
				}
			}else{
				board[x][y] = possible[x][y].remove(random.nextInt(possible[x][y].size()));
				if(x==8 && y==8){
					break;
				}else if(y<8){
					y++;
					getPossible(x, y);
				}else{ // y==8, x<8
					x++;
					y = 0;
					getPossible(x, y);
				}
			}
		}
		
	}
	
	protected void getPossible(int x, int y){
		for(int i=1; i<=9; i++)
			if(checkRow(x,y,i) && checkColumn(x,y,i) && checkSquare(x,y,i))
				possible[x][y].add(i);
	}
	
	protected boolean checkRow(int x, int y, int value){
		for(int i=0; i<y; i++){
			if(board[x][i]==value)
				return false;
		}
		
		return true;
	}
	protected boolean checkColumn(int x, int y, int value){
		for(int i=0; i<x; i++){
			if(board[i][y]==value)
				return false;
		}
		
		return true;
	}
	protected boolean checkSquare(int x, int y, int value){
		int rowBegin = x-x%3;
		int colBegin = y-y%3;
		int colEnd   = colBegin+2;
		
		for(int i=rowBegin; i<x; i++){
			for(int j=colBegin; j<=colEnd; j++){
				if(board[i][j]==value)
					return false;
			}
		}
		for(int j=colBegin; j<y; j++){
			if(board[x][j]==value)
				return false;
		}
		
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sudoku s = new Sudoku();
		s.generateComplete();
		MatrixUtil.print(s.board);
		System.out.println("\n\n");
		s.mask(0.5);
		MatrixUtil.print(s.board);

	}

}
