package algorithmictoolbox.week5;

import java.util.Scanner;

/*
    As we already know, a natural greedy strategy for the change problem does not work correctly for any set of denominations.
    For example, if the available denominations are 1, 3, and 4, the greedy algorithm will change 6 cents using three coins
    (4 + 1 + 1) while it can be changed using just two coins (3 + 3).
    Your goal now is to apply dynamic programming for solving the Money Change Problem for denominations 1, 3, and 4.
 */
public class ChangeDP {
    private static int[] COINS = {1, 3, 4};

    private static int getChange(int money) {
        int[] minimumNumberOfCoins = new int[money + 1];
        for (int m = 1; m <= money; m++) {
            minimumNumberOfCoins[m] = Integer.MAX_VALUE;
            for (int coin : COINS) {
                if (m >= coin) {
                    int numberOfCoins = minimumNumberOfCoins[m - coin] + 1;
                    if (numberOfCoins < minimumNumberOfCoins[m])
                        minimumNumberOfCoins[m] = numberOfCoins;
                }
            }
        }
        return minimumNumberOfCoins[minimumNumberOfCoins.length - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}

