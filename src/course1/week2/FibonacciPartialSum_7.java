package course1.week2;

import java.util.Scanner;

// Given two non-negative integers 𝑚 and 𝑛, where 𝑚 ≤ 𝑛, find the last digit of the sum 𝐹𝑚 + 𝐹𝑚+1 + ···+𝐹𝑛.
public class FibonacciPartialSum_7 {
    private static long getFibonacciPartialSumNaive(long from, long to) {
        if (to <= 1)
            return to;

        int periodLength = 60;
        long indexOfSum = to + 2;
        long indexTo = indexOfSum % periodLength;
        long indexFrom = from % periodLength;

        if (indexFrom > indexTo) indexTo += 60;

        int previousRemainder = 0;
        int currentRemainder = 1;
        int sum = 0;
        for (int i = 1; i < indexTo - 1; i++) {
            if (i >= indexFrom) {
                sum += currentRemainder;
                sum %= 10;
            }
            int oldCurrentRemainder = currentRemainder;
            currentRemainder = (currentRemainder + previousRemainder) % 10;
            previousRemainder = oldCurrentRemainder;
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSumNaive(from, to));
    }
}