package algorithmictoolbox.week2;

import java.util.Scanner;

// The goal in this problem is to find the last digit of a sum of the first 𝑛 Fibonacci numbers.
public class FibonacciSumLastDigit_6 {
    private static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        int periodLength = 60;
        long indexOfSum = n + 2;
        long index = indexOfSum % periodLength;

        int previousRemainder = 0;
        int currentRemainder = 1;
        for (int i = 2; i <= index; i++) {
            int oldCurrentRemainder = currentRemainder;
            currentRemainder = (currentRemainder + previousRemainder) % 10;
            previousRemainder = oldCurrentRemainder;
        }

        if (currentRemainder == 0) currentRemainder = 10;
        return currentRemainder - 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long s = getFibonacciSumNaive(n);
        System.out.println(s);
    }
}

