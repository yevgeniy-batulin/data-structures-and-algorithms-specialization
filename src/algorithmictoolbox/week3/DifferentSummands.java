package algorithmictoolbox.week3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    The goal of this problem is to represent a given positive integer 𝑛 as a sum of as many pairwise distinct positive integers as possible.
    That is, to find the maximum 𝑘 such that 𝑛 can be written as 𝑎1+𝑎2+···+𝑎𝑘 where𝑎1,...,𝑎𝑘 are positive integers and𝑎𝑖 ̸=𝑎𝑗 for all1 ≤𝑖<𝑗≤𝑘.
 */
public class DifferentSummands {
    private static List<Integer> optimalSummands(int n) {
        List<Integer> summands = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n - i <= i) {
                summands.add(n);
                break;
            } else {
                summands.add(i);
                n -= i;
            }
        }
        return summands;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> summands = optimalSummands(n);
        System.out.println(summands.size());
        for (Integer summand : summands) {
            System.out.print(summand + " ");
        }
    }
}

