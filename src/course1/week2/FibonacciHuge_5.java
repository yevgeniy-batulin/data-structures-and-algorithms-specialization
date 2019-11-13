package course1.week2;

import java.util.Scanner;

// Given two integers 𝑛 and 𝑚, output 𝐹𝑛 mod 𝑚 (that is, the remainder of 𝐹𝑛 when divided by 𝑚).
public class FibonacciHuge_5 {
    private static long getFibonacciHugeNaive(long n, long m) {
        if (n <= 1)
            return n;

        int[] fibonacciRemainderArray = new int[(int) (m * m)];
        fibonacciRemainderArray[0] = 0;
        fibonacciRemainderArray[1] = 1;
        int periodLength = 2;
        for (; periodLength < (m * m); periodLength++) {
            fibonacciRemainderArray[periodLength] = (fibonacciRemainderArray[periodLength - 1] + fibonacciRemainderArray[periodLength - 2]) % (int) m;

            int previousRemainder = fibonacciRemainderArray[periodLength];
            int currentRemainder = fibonacciRemainderArray[periodLength - 1];
            if (previousRemainder == 0 && currentRemainder == 1) break;
        }

        int index = (int) (n % periodLength);

        return fibonacciRemainderArray[index];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHugeNaive(n, m));
    }
}