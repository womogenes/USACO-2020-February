
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class triangles {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		// Input routine!
		BufferedReader fin = new BufferedReader(new FileReader("triangles.in"));
		
		int n = Integer.parseInt(fin.readLine());
		long[][] posts = new long[n][2];
		
		for (int i = 0; i < n; i++) {
			String[] temp1 = fin.readLine().split(" ");
			posts[i] = new long[] { Integer.parseInt(temp1[0]), 
								   Integer.parseInt(temp1[1]) };
		}
		
		fin.close();
		
		// Input routine over, start algorithming!
		long mod = 1000000007;
		long totalArea = 0;

		HashMap<Long, ArrayList<long[]>> cols = new HashMap<>();
		HashMap<Long, ArrayList<long[]>> rows = new HashMap<>();
		
		HashMap<long[], Long> heights = new HashMap<>();
		
		for (long[] p : posts) {
			if (!cols.keySet().contains(p[0])) {
				cols.put(p[0], new ArrayList<>());
			}
			cols.get(p[0]).add(p);
			
			if (!rows.keySet().contains(p[1])) {
				rows.put(p[1], new ArrayList<>());
			}
			rows.get(p[1]).add(p);
		}
		
		// Deal with columns!
		for (long c : cols.keySet()) {
			ArrayList<long[]> column = cols.get(c);
			column.sort((a, b) -> Long.compare(a[1], b[1]));
			
			long initialSum = 0;
			
			for (int i = 0; i < column.size(); i++) {
				initialSum += column.get(i)[1] - column.get(0)[1];
			}
			
			for (int i = 0; i < column.size() - 1; i++) {
				heights.put(column.get(i), initialSum);
				
				long dist = column.get(i + 1)[1] - column.get(i)[1];
				initialSum += (i - (column.size() - 2 - i)) * dist;
			}
			heights.put(column.get(column.size() - 1), initialSum);
		}
		
		// Deal with rows!
		for (long r : rows.keySet()) {
			
			ArrayList<long[]> row = rows.get(r);
			row.sort((a, b) -> Long.compare(a[0], b[0]));
			
			long initialSum = 0;
			
			for (int i = 0; i < row.size(); i++) {
				initialSum += row.get(i)[0] - row.get(0)[0];
			}
			
			//System.out.println(initialSum);
			
			for (int i = 0; i < row.size() - 1; i++) {
				
				//System.out.println(initialSum + ", " + Arrays.toString(row.get(i)) + ", " + heights.get(row.get(i)));
				
				totalArea += (initialSum * heights.get(row.get(i))) % mod;
				
				long dist = row.get(i + 1)[0] - row.get(i)[0];
				initialSum += (i - (row.size() - 2 - i)) * dist;
			}
			
			totalArea += (initialSum * heights.get(row.get(row.size() - 1)));
		}
		
		PrintWriter fout = new PrintWriter(new BufferedWriter(new FileWriter("triangles.out")));
		fout.println(totalArea % mod);
		fout.close();
	}
}
