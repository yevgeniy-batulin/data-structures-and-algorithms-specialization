package course1.week3;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
    Given two sequences 𝑎1,𝑎2,...,𝑎𝑛 (𝑎𝑖 is the profit per click of the 𝑖-th ad) and 𝑏1,𝑏2,...,𝑏𝑛 (𝑏𝑖 is the average number of clicks per day of the 𝑖-th slot),
    we need to partition them into 𝑛 pairs (𝑎𝑖,𝑏𝑗) such that the sum of their products is maximized.
 */
public class DotProduct {
    private static long maxDotProduct(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        long result = 0;
        for (int i = 0; i < a.length; i++) {
            result += (long) a[i] * b[i];
        }
        return result;
    }

    private static BigInteger maxDotProductSlow(List<BigInteger> a, List<BigInteger> b) {
        BigInteger result = BigInteger.ZERO;
        while (!a.isEmpty()) {
            BigInteger maxA = BigInteger.valueOf(Long.MIN_VALUE);
            for (BigInteger integer : a) {
                if (integer.compareTo(maxA) > 0) {
                    maxA = integer;
                }
            }

            BigInteger maxB = BigInteger.valueOf(Long.MIN_VALUE);
            for (BigInteger integer : b) {
                if (integer.compareTo(maxB) > 0) {
                    maxB = integer;
                }
            }

            result = result.add(maxA.multiply(maxB));
            a.remove(maxA);
            b.remove(maxB);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextInt();
        }
        System.out.println(maxDotProduct(a, b));
    }
}

