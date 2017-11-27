import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwentyFour {
	protected static final int N = 4;
	protected static final int target = 24;
	protected int[] numbers = new int[N];
	protected List<Double>[] computation = new List[(int)Math.pow(2, N)];
	protected Map<Double, double[]>[] paths = new Map[computation.length];
	
	protected void initial(){
		numbers = new int[]{9,9,6,12};
		for(int i=0; i<computation.length; i++){
			computation[i] = new ArrayList<Double>();
			paths[i] = new HashMap<Double, double[]>();
		}
		for(int i=0; i<N; i++){
			computation[(int)Math.pow(2, i)].add((double)numbers[i]);
			paths[(int)Math.pow(2, i)].put((double)numbers[i], new double[]{i,numbers[i],0,0,5});// 1 +, 2 *, 3 -, 4 /, 5 no operation
		}
	}
	
	protected void solve(){
		int total = (int)Math.pow(2, N)-1;
		for(int i=1; i<total; i++)
			fork(i, total-i);
		boolean mark = true;
		for(int i=0; i<computation[total].size(); i++){
			if(Math.abs(computation[total].get(i)-target)<0.0000000001){
				System.out.println("yes");
				print(total, computation[total].get(i));
				System.out.println();
				mark = false;
			}
		}
		if(mark)
			System.out.println("no");
	}
	protected void print(int n, double value){
		if(paths[n].get(value)[4]==5){
			System.out.print((int)value);
		}else{
			double[] temp = paths[n].get(value);
			if(temp[4]==1 || temp[4]==3)
				System.out.print("(");
			
			print((int)temp[0], temp[1]);
			if(temp[4]==1)
				System.out.print("+");
			else if(temp[4]==2)
				System.out.print("*");
			else if(temp[4]==3)
				System.out.print("-");
			else
				System.out.print("/");
			print((int)temp[2], temp[3]);
			if(temp[4]==1 || temp[4]==3)
				System.out.print(")");
		}
	}
	
	protected void fork(int a, int b){
		if(computation[a].isEmpty()){
			for(int i=1; i<a; i++){
				if((a&i)==i){
					fork(i, a-i);
				}
			}
		}
		if(computation[b].isEmpty()){
			for(int i=1; i<b; i++){
				if((b&i)==i){
					fork(i, b-i);
				}
			}
		}
		int n = a+b;
		int aLength = computation[a].size();
		int bLength = computation[b].size();
		for(int i=0; i<aLength; i++){
			double aI = computation[a].get(i);
			for(int j=0; j<bLength; j++){
				double bJ = computation[b].get(j);
				double value = aI+bJ;
				if(!computation[n].contains(value)){
					computation[n].add(value);
					paths[n].put(value, new double[]{a,aI,b,bJ,1});
				}
				value = aI*bJ;
				if(!computation[n].contains(value)){
					computation[n].add(value);
					paths[n].put(value, new double[]{a,aI,b,bJ,2});
				}
				value = aI-bJ;
				if(!computation[n].contains(value)){
					computation[n].add(value);
					paths[n].put(value, new double[]{a,aI,b,bJ,3});
				}
				value = bJ-aI;
				if(!computation[n].contains(value)){
					computation[n].add(value);
					paths[n].put(value, new double[]{b,bJ,a,aI,3});
				}
				if(bJ!=0){
					value = aI/bJ;
					if(!computation[n].contains(value)){
						computation[n].add(value);
						paths[n].put(value, new double[]{a,aI,b,bJ,4});
					}
				}
				if(aI!=0){
					value = bJ/aI;
					if(!computation[n].contains(value)){
						computation[n].add(value);
						paths[n].put(value, new double[]{b,bJ,a,aI,4});
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TwentyFour tf = new TwentyFour();
		tf.initial();
		tf.solve();
	}

}
