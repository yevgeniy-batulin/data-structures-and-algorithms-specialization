package course1.week6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// You and two of your friends have just returned back home after visiting various countries.
// Now you would like to evenly split all the souvenirs that all three of you bought.
public class Partition3 {
    private static int partition3(int[] A) {
        int sum = Arrays.stream(A).sum();
        if (sum % 3 != 0) return 0;

        int partitionSum = sum / 3;
        return internalPartition(A, 0, partitionSum, partitionSum, partitionSum, new HashMap<>()) ? 1 : 0;
    }

    private static boolean internalPartition(int[] array, int index, int firstSum, int secondSum, int thirdSum, Map<String, Boolean> answers) {
        if (firstSum == 0 && secondSum == 0 && thirdSum == 0) return true;

        if (index >= array.length) return false;

        String key = index + "|" + firstSum + "|" + secondSum + "|" + thirdSum;
        if (answers.containsKey(key)) return answers.get(key);

        boolean landedInFirst = false;
        if (array[index] <= firstSum) {
            landedInFirst = internalPartition(array, index + 1, firstSum - array[index], secondSum, thirdSum, answers);
        }

        boolean landedInSecond = false;
        if (!landedInFirst && array[index] <= secondSum) {
            landedInSecond = internalPartition(array, index + 1, firstSum, secondSum - array[index], thirdSum, answers);
        }

        boolean landedInThird = false;
        if (!landedInFirst && !landedInSecond && array[index] <= thirdSum) {
            landedInThird = internalPartition(array, index + 1, firstSum, secondSum, thirdSum - array[index], answers);
        }

        boolean result = landedInFirst || landedInSecond || landedInThird;
        answers.put(key, result);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        System.out.println(partition3(A));
    }
}

