package algorithmictoolbox.week2;

import java.util.Scanner;

// Given an integer 𝑛, find the 𝑛th Fibonacci number 𝐹𝑛.
public class Fibonacci_1 {
    private static long calculateFibonacci(int n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;

        for (int i = 0; i < n - 1; i++) {
            long oldCurrent = current;
            current = previous + current;
            previous = oldCurrent;
        }

        return current;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        System.out.println(calculateFibonacci(n));
    }
}