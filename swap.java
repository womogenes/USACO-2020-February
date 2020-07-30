
public class swap {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader fin = new BufferedReader(new FileReader("swap.in"));
		
		String[] temp1 = fin.readLine().split(" ");
		int n = Integer.parseInt(temp1[0]);
		int m = Integer.parseInt(temp1[1]);
		int k = Integer.parseInt(temp1[2]);
		
		int[][] swaps = new int[m][2];
		
		for (int i = 0; i < m; i++) {
			String[] temp2 = fin.readLine().split(" ");
			int[] temp3 = new int[] { Integer.parseInt(temp2[0]) - 1, 
									  Integer.parseInt(temp2[1]) - 1 };
			swaps[i] = temp3;
		}
		
		fin.close();
		
		// Input routine over, start algorithm.
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = i;
		}
		int[] cows = result.clone();
		
		for (int[] s : swaps) {
			result = swapcows(result, s);
		}
		
		ArrayList<int[]> funcs = new ArrayList<>();
		funcs.add(result);
		
		int bitLength = 0;
		int temp4 = k;
		while (temp4 > 0) {
			temp4 >>= 1;
			bitLength++;
		}
		System.out.println(bitLength + ", " + k);
		
		for (int i = 1; i < bitLength + 1; i++) {
			result = funcs.get(funcs.size() - 1);
			result = permute(result, result);
			funcs.add(result);
		}
		
		for (int i = 0; i <= bitLength; i++) {
			if (((k >> i) & 1) == 1) {
				cows = permute(cows, funcs.get(i));
			}
			System.out.println((k >> i) & 1);
		}
		
		System.out.println("done");
		
		PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter("swap.out")));

		for (int i = 0; i < cows.length; i++) {
			fout.println((cows[i] + 1));
		}
		fout.close();
	}
	
	public static int[] swapcows(int[] cows, int[] interval) {		
		int start = interval[0];
		int end = interval[1];
		
		int[] newCows = new int[cows.length];
		
		for (int i = 0; i < start; i++) {
			newCows[i] = cows[i];
		}
		
		for (int i = start; i <= end; i++) {
			newCows[i] = cows[start + end - i];
		}
		
		for (int i = end + 1; i < cows.length; i++) {
			newCows[i] = cows[i];
		}
		
		return newCows;
	}

	public static int[] permute(int[] cows, int[] result) {
		
		int[] newcows = new int[cows.length];
		
		for (int j = 0; j < cows.length; j++) {
			newcows[j] = cows[result[j]];
		}
		
		return newcows;
	}

}