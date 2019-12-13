package algorithmictoolbox.week5;

import java.util.Scanner;

// Compute the length of a longest common subsequence of three sequences.
public class LCS3 {
    private static int lcs3(int[] a, int[] b, int[] c) {
        int[][][] alignmentScoreMatrix = new int[a.length + 1][b.length + 1][c.length + 1];
        for (int k = 1; k <= c.length; k++) {
            for (int j = 1; j <= b.length; j++) {
                for (int i = 1; i <= a.length; i++) {
                    int insertion = alignmentScoreMatrix[i][j - 1][k];
                    int deletionsSecondSequence = alignmentScoreMatrix[i - 1][j][k];
                    int deletionsThirdSequence = alignmentScoreMatrix[i][j][k - 1];
                    int match = alignmentScoreMatrix[i - 1][j - 1][k - 1] + 1;
                    int mismatch = alignmentScoreMatrix[i - 1][j - 1][k - 1];

                    if (a[i - 1] == b[j - 1] && a[i - 1] == c[k - 1])
                        alignmentScoreMatrix[i][j][k] = Math.max(insertion,
                                Math.max(deletionsSecondSequence,
                                        Math.max(deletionsThirdSequence, match)));
                    else
                        alignmentScoreMatrix[i][j][k] = Math.max(insertion,
                                Math.max(deletionsSecondSequence,
                                        Math.max(deletionsThirdSequence, mismatch)));
                }
            }
        }
        return alignmentScoreMatrix[a.length][b.length][c.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
    }
}

