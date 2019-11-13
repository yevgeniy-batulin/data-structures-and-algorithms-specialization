package course1.week5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
    You are given a primitive calculator that can perform the following three operations with the current number 𝑥:
    multiply 𝑥 by 2, multiply 𝑥 by 3, or add 1 to 𝑥.
    Your goal is given a positive integer 𝑛, find the minimum number of operations needed to obtain the number 𝑛 starting
    from the number 1.
 */
public class PrimitiveCalculator {
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> minNumberOfOperationList = buildMinNumbersOfOperationsList(n);

        List<Integer> result = new ArrayList<>();
        result.add(n);
        for (int i = n; i != 1; ) {
            int prev = minNumberOfOperationList.get(i) - 1;

            if (i % 3 == 0 && prev == minNumberOfOperationList.get(i / 3)) {
                i = i / 3;
                result.add(i);
            } else if (i % 2 == 0 && prev == minNumberOfOperationList.get(i / 2)) {
                i = i / 2;
                result.add(i);
            } else if (prev == minNumberOfOperationList.get(i - 1)) {
                i = i - 1;
                result.add(i);
            }
        }
        Collections.reverse(result);
        return result;
    }

    private static List<Integer> buildMinNumbersOfOperationsList(int n) {
        List<Integer> minNumberOfOperationList = new ArrayList<>();
        minNumberOfOperationList.add(0);
        minNumberOfOperationList.add(0);

        for (int i = 2; i <= n; i++) {
            int minNumberOfOperation = Integer.MAX_VALUE;
            if (i % 3 == 0) {
                int numberOfOperations = minNumberOfOperationList.get(i / 3) + 1;
                if (numberOfOperations < minNumberOfOperation) {
                    minNumberOfOperation = numberOfOperations;
                }
            }

            if (i % 2 == 0) {
                int numberOfOperations = minNumberOfOperationList.get(i / 2) + 1;
                if (numberOfOperations < minNumberOfOperation) {
                    minNumberOfOperation = numberOfOperations;
                }
            }

            int numberOfOperations = minNumberOfOperationList.get(i - 1) + 1;
            if (numberOfOperations < minNumberOfOperation) {
                minNumberOfOperation = numberOfOperations;
            }

            minNumberOfOperationList.add(minNumberOfOperation);
        }
        return minNumberOfOperationList;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}