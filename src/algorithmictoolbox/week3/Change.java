package algorithmictoolbox.week3;

import java.util.Scanner;

// The goal in this problem is to find the minimum number of coins needed to change the input value (an integer)
// into coins with denominations 1, 5, and 10.
public class Change {
    private static int getChange(int m) {
        int[] coins = {10, 5, 1};
        int i = 0;
        int result = 0;
        while (m > 0) {
            int coinCount = m / coins[i];
            result += coinCount;
            m %= coins[i];
            i++;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));
    }
}

