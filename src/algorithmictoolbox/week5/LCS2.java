package algorithmictoolbox.week5;

import java.util.Scanner;

// Compute the length of a longest common subsequence of two sequences.
public class LCS2 {

    private static int lcs2(int[] a, int[] b) {
        int[][] alignmentScoreMatrix = new int[a.length + 1][b.length + 1];
        for (int j = 1; j <= b.length; j++) {
            for (int i = 1; i <= a.length; i++) {
                int insertion = alignmentScoreMatrix[i][j - 1];
                int deletion = alignmentScoreMatrix[i - 1][j];
                int match = alignmentScoreMatrix[i - 1][j - 1] + 1;
                int mismatch = alignmentScoreMatrix[i - 1][j - 1];

                if (a[i - 1] == b[j - 1])
                    alignmentScoreMatrix[i][j] = Math.max(insertion, Math.max(deletion, match));
                else
                    alignmentScoreMatrix[i][j] = Math.max(insertion, Math.max(deletion, mismatch));
            }
        }
        return alignmentScoreMatrix[a.length][b.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = scanner.nextInt();
        }

        System.out.println(lcs2(a, b));
    }
}

