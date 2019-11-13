package course1.week3;

import java.util.Scanner;

// A thief finds much more loot than his bag can fit.
// Help him to find the most valuable combination of items assuming that any fraction of a loot item can be put into his bag.
public class FractionalKnapsack {
    private static double getOptimalValue(double capacity, int[] values, int[] weights) {
        double value = 0;
        for (int i = 0; i < values.length; i++) {
            if (capacity == 0) return value;
            int maxCoefficientIndex = 0;
            double maxCoefficient = 0;
            for (int j = 0; j < values.length; j++) {
                if (weights[j] != 0) {
                    double coefficient = ((double) values[j]) / weights[j];
                    if (coefficient > maxCoefficient) {
                        maxCoefficientIndex = j;
                        maxCoefficient = coefficient;
                    }
                }
            }
            double amount = weights[maxCoefficientIndex] < capacity ? weights[maxCoefficientIndex] : capacity;
            value += amount * maxCoefficient;
            capacity -= amount;
            values[maxCoefficientIndex] = 0;
            weights[maxCoefficientIndex] = 0;
        }
        return value;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.printf("%.4f%n", getOptimalValue(capacity, values, weights));
    }
} 
