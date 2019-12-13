package algorithmictoolbox.week2;

import java.util.Scanner;

// Given two integers 𝑎 and 𝑏, find their least common multiple.
public class LCM_4 {
    private static long lcm_naive(int a, int b) {
        for (long l = 1; l <= (long) a * b; ++l) {
            if (l % a == 0 && l % b == 0)
                return l;
        }

        return (long) a * b;
    }

    private static long lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;

        long max, min;
        if (a > b) {
            max = a;
            min = b;
        } else {
            max = b;
            min = a;
        }

        if (max % min == 0) return max;

        for (long i = max; i <= max * min; i += max) {
            if (i % min == 0)
                return i;
        }

        return max * min;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(lcm(a, b));
    }
}
