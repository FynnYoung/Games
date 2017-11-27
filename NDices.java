
public class NDices {
	protected int max = 6;
	
	protected void printProb(int n){
		if(n<1) return;
		int[][] probabilities = new int[2][max*n+1];
		int flag = 0;
		for(int i=1; i<=max; i++) probabilities[0][i] = 1;
		for(int i=2; i<=n; i++){
			for(int j=0; j<i; j++) probabilities[1-flag][j] = 0;
			for(int j=i; j<=i*max; j++){
				probabilities[1-flag][j] = 0;
				for(int k=1; k<=max && k<=j; k++){
					probabilities[1-flag][j] += probabilities[flag][j-k];
				}
			}
			flag = 1-flag;
		}
		
		double total = Math.pow(max, n);
		for(int i=n; i<=max*n; i++) System.out.println(i+"\t"+probabilities[flag][i]/total);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NDices nd = new NDices();
		nd.printProb(2);

	}

}
