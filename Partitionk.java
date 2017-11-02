/*************************************************************************
 *  Integer_Partitions
 *
 *  Author: Tomer Alon
 *  Collaborators: none
 *  References: 
 *  https://codegolf.stackexchange.com/questions/812/partition-of-a-positive-integer-n (Tabulation method for Integer Partitions- used to print the value of p(n))
 *  https://introcs.cs.princeton.edu/java/23recursion/Partition.java.html (prints all integer partitions)
 *  
 * 
 *  Problem: Integer Partition P(n,k) 
 *  Input: Any positive integer for n & k 
 *  Output: P(n,k) 	
 *
 *
 *  Visible methods:
 * 	public static void main(String[] args)
 * 	public static void resetall()
 * 
 * 
 * 	note:
 * 	Printing every integer partition to the console for n > 29 will cut off some parts of the output.
 *
 *************************************************************************/
import java.math.*;
import java.util.*;

public class Partitionk {

	static Scanner scan = new Scanner(System.in);
	@SuppressWarnings("unused")
	private static long count1, count2;
	private static BigInteger[][] T;
	private static int n = scan.nextInt();
	private static int k = scan.nextInt();

	// Naive recursive method for calculating p(n,k)
	private static BigInteger p(int n, int k) {
		count1++;
		if (k < 1 || k > n)
			return BigInteger.ZERO;
		else if (k == 1 || k == n)
			return BigInteger.ONE;
		else
			return (p(n - 1, k - 1).add(p(n - k, k)));

	}

	// Memoization method for calculating p(n,k) 
	private static BigInteger p2(int n, int k) {
		count2++;
		if (k < 1 || k > n)
			return BigInteger.ZERO;
		else if (k == 1 || k == n) {
			T[n][k] = BigInteger.ONE;
			return BigInteger.ONE;
		} else {
			if (T[n][k] == BigInteger.ZERO)
				T[n][k] = (p2(n - 1, k - 1).add(p2(n - k, k)));
			return T[n][k];
		}
	}

	
	// Tabulation method for calculating just p(n) --- https://codegolf.stackexchange.com/questions/812/partition-of-a-positive-integer-n 
	private static int p3(int n) {
		int[][] a = new int[n + 1][n + 1];
		int i, j, k;
		for (i = 0; i < a.length; i++) {
			a[i][0] = 0;
			a[0][i] = 1;
		}
		for (i = 1; i < a.length; i++) {
			a[i][1] = 1;
			for (j = 2; j < a[0].length; j++) {
				k = i - j;
				if (k < 0)
					a[i][j] = a[i][j - 1];
				else
					a[i][j] = a[i][j - 1] + a[k][j];
			}
		}
		i--;
		int answer = a[i][i - 1];
		return (answer + 1);
	}

	// Method that prints out all of the integer partitions --- https://introcs.cs.princeton.edu/java/23recursion/Partition.java.html
	@SuppressWarnings("unused")
	private static void print_partition(int n) {
		partition(n, n, "");
	}

	private static void partition(int n, int max, String prefix) {
		if (n == 0) {
			System.out.println(prefix);
			return;
		}

		for (int i = Math.min(max, n); i >= 1; i--) {
			partition(n - i, i, prefix + " " + i);
		}
	}

	public static void resetall() {
		count1 = 0;
		count2 = 0;
		for (int i = 0; i <= n; i++)
			for (int j = 0; j <= k; j++)
				T[i][j] = BigInteger.ZERO;
	}

	public static void main(String[] args) {
		T = new BigInteger[n + 1][k + 1];

		resetall();
		System.out.println("p(" + n + "," + k + ") partitions: " + p(n, k));
		System.out.println("p(" + n + "," + k + ") partitions (dynamic): " + p2(n, k));

		System.out.print("Number of total partitions: ");

		System.out.println("p(" + n + ") = " + p3(n));
		System.out.println();
		
		/*
		System.out.println("Total partitions:");
		print_partition(n); // prints every integer partition (cuts off for n > 29)
		*/
		
		// Comparing running times for the three different methods
		/*	
	  	float startTime = System.nanoTime();
		p(n, k);
		System.out.println("The time taken to sort with p1 is " + (System.nanoTime() - startTime) + " nanoseconds.\n");
		
		float startTime2 = System.nanoTime();
		p2(n, k);
		System.out.println("The time taken to sort with p2 is " + (System.nanoTime() - startTime2) + " nanoseconds.\n");
		 
		float startTime3 = System.nanoTime();
		p3(n);
		System.out.println("The time taken to sort with p3 is " + (System.nanoTime() - startTime3) + " nanoseconds.\n"); 
		*/

	}

}