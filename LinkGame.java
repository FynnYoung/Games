import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import machine.MatrixUtil;

public class LinkGame {
	protected int[][] board; // 0 means empty element
	protected static int[][] directions = {
			{1, 0},
			{-1, 0},
			{0, 1},
			{0, -1}
	};
	protected static int limitedTurns = 2;
	protected static int[][] matchedPair = new int[2][2];
	protected static Stack<int[]> path = new Stack<int[]>();
	
	protected void initial(){
		board = new int[][]{
			{2, 1, 1, 2},
			{4, 3, 5, 8},
			{6, 4, 7, 5},
			{3, 7, 8, 6}
		};
	}
	
	protected void solve(){
		if(board==null || board.length<=0 || board[0].length<=0) return;
		if(!solvable()) return;
		
		while(!solved()){
			if(isDeadLock()){
				System.out.println("no possible matched pair!");
				break;
			}else{
				MatrixUtil.print(matchedPair);
				board[matchedPair[0][0]][matchedPair[0][1]] = 0;
				board[matchedPair[1][0]][matchedPair[1][1]] = 0;
				while(!path.isEmpty()){
					int[] p = path.pop();
					System.out.println(p[0]+" "+p[1]);
				}
			}
			System.out.println();
		}
		
		MatrixUtil.print(board);
	}
	
	protected boolean solvable(){
		Set<Integer> elements = new HashSet<Integer>();
		for(int[] i : board){
			for(int j : i){
				if(j!=0){
					if(!elements.remove(j))
						elements.add(j);
				}
			}
		}
		
		return elements.isEmpty();
	}
	
	protected boolean solved(){
		boolean result = true;
		Mark:
		for(int[] i : board){
			for(int j : i){
				if(j!=0){
					result = false;
					break Mark;
				}
			}
		}
		return result;
	}
	
	protected boolean isDeadLock(){
		if(board==null || board.length<=0 || board[0].length<=0) return false;
		
		boolean result = true;
		Mark:
			for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j]!=0 && findMatch(i,j)){
						result = false;
						break Mark;
					}
				}
			}
		
		return result;
	}
	
	protected boolean findMatch(int x, int y){ // x, y: the coordinates of considered element
		int[][] turns = new int[board.length][board[0].length];
		for(int i=0; i<turns.length; i++){
			for(int j=0; j<turns[i].length; j++){
				turns[i][j] = Integer.MAX_VALUE;
			}
		}
		turns[x][y] = -1;
		
		Queue<int[]> q = new LinkedList<int[]>();
		q.offer(new int[]{x, y});
		
		while(!q.isEmpty()){
			int[] currentPos = q.poll();
			for(int i=0; i<4; i++){
				int nextX = currentPos[0]+directions[i][0];
				int nextY = currentPos[1]+directions[i][1];
				for(; nextX>=0 && nextX<board.length && nextY>=0 && nextY<board[0].length && (nextX!=x || nextY!=y);
						nextX+=directions[i][0], nextY+=directions[i][1]){
					if(board[nextX][nextY]==0){
						int addOneTurn = turns[currentPos[0]][currentPos[1]]+1;
						if(addOneTurn<turns[nextX][nextY] && addOneTurn<=limitedTurns){
							turns[nextX][nextY] = addOneTurn;
							q.offer(new int[]{nextX, nextY});
						}
					}else if(board[nextX][nextY]!=board[x][y]){
						break;
					}else{ // board[nextX][nextY]==board[x][y]
						int addOneTurn = turns[currentPos[0]][currentPos[1]]+1;
						if(addOneTurn<=limitedTurns){
							turns[nextX][nextY] = addOneTurn;
							matchedPair[0][0] = x;
							matchedPair[0][1] = y;
							matchedPair[1][0] = nextX;
							matchedPair[1][1] = nextY;
							q.clear();
							MatrixUtil.print(turns);
							
							path.clear();
							path.add(new int[]{nextX, nextY});
							Mark:
							while(nextX!=x || nextY!=y){
								int xx, yy, equalX = 0, equalY = 0; // the position that has same number of turns
								for(int j=0; j<4; j++){
									xx = nextX+directions[j][0];
									yy = nextY+directions[j][1];
									if(xx>=0 && xx<board.length && yy>=0 && yy<board[0].length){
										if(turns[xx][yy]<turns[nextX][nextY]){
											path.push(new int[]{xx,yy});
											nextX = xx;
											nextY = yy;
											continue Mark;
										}else if(turns[xx][yy]==turns[nextX][nextY]){
											equalX = xx;
											equalY = yy;
										}
									}
								}
								path.push(new int[]{equalX,equalY});
								nextX = equalX;
								nextY = equalY;
							}
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkGame lg = new LinkGame();
		lg.initial();
		lg.solve();

	}

}
