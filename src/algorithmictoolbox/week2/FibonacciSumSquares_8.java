package algorithmictoolbox.week2;

import java.util.Scanner;

// Compute the last digit of 𝐹02 +𝐹12 +···+𝐹𝑛2.
public class FibonacciSumSquares_8 {
    private static long getFibonacciSumSquaresNaive(long n) {
        if (n <= 1)
            return n;

        int periodLength = 60;
        long index = n % periodLength;

        if (index == 0) return 0;

        int previousRemainder = 0;
        int currentRemainder = 1;
        for (int i = 2; i <= index; i++) {
            int oldCurrentRemainder = currentRemainder;
            currentRemainder = (currentRemainder + previousRemainder) % 10;
            previousRemainder = oldCurrentRemainder;
        }

        return ((previousRemainder + currentRemainder) * currentRemainder) % 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long s = getFibonacciSumSquaresNaive(n);
        System.out.println(s);
    }
}