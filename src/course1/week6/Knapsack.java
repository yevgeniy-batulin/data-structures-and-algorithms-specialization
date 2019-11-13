package course1.week6;

import java.util.Scanner;

/*
    You are given a set of bars of gold and your goal is to take as much gold as possible into your bag.
    There is just one copy of each bar and for each bar you can either take it or not (hence you cannot take a fraction of a bar).
 */
public class Knapsack {
    private static int optimalWeight(int W, int[] weights) {
        int[][] solutionMatrix = new int[weights.length + 1][W + 1];
        for (int i = 1; i <= weights.length; i++) {
            for (int w = 1; w <= W; w++) {
                solutionMatrix[i][w] = solutionMatrix[i - 1][w];
                if (weights[i - 1] <= w) {
                    int value = solutionMatrix[i - 1][w - weights[i - 1]] + weights[i - 1];
                    if (solutionMatrix[i][w] < value) {
                        solutionMatrix[i][w] = value;
                    }
                }
            }
        }
        return solutionMatrix[weights.length][W];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}