package course1.week6;

import java.util.Scanner;

/*
    In this problem, your goal is to add parentheses to a given arithmetic expression to maximize its value.
 */
public class PlacingParentheses {
    private static long getMaximValue(String exp) {
        long[] digits = new long[exp.length() / 2 + 1];
        char[] operations = new char[exp.length() / 2];
        int numberOfDigits = 0;
        int numberOfOperations = 0;
        for (int i = 0; i < exp.length(); i++) {
            char character = exp.charAt(i);
            if (i % 2 == 0) digits[numberOfDigits++] = Character.getNumericValue(character);
            else operations[numberOfOperations++] = character;
        }

        long[][] minMatrix = new long[digits.length][digits.length];
        long[][] maxMatrix = new long[digits.length][digits.length];
        for (int i = 0; i < digits.length; i++) {
            minMatrix[i][i] = digits[i];
            maxMatrix[i][i] = digits[i];
        }

        for (int s = 1; s < digits.length; s++) {
            for (int i = 0; i < digits.length - s; i++) {
                int j = i + s;
                long[] minAndMax = minAndMax(minMatrix, maxMatrix, operations, i, j);
                minMatrix[i][j] = minAndMax[0];
                maxMatrix[i][j] = minAndMax[1];
            }
        }

        return maxMatrix[0][digits.length - 1];
    }

    private static long[] minAndMax(long[][] minMatrix, long[][] maxMatrix, char[] operations, int start, int end) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int i = start; i < end; i++) {
            long a = eval(maxMatrix[start][i], maxMatrix[i + 1][end], operations[i]);
            long b = eval(maxMatrix[start][i], minMatrix[i + 1][end], operations[i]);
            long c = eval(minMatrix[start][i], maxMatrix[i + 1][end], operations[i]);
            long d = eval(minMatrix[start][i], minMatrix[i + 1][end], operations[i]);
            min = Math.min(min, Math.min(a, Math.min(b, Math.min(c, d))));
            max = Math.max(max, Math.max(a, Math.max(b, Math.max(c, d))));
        }
        return new long[]{min, max};
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}