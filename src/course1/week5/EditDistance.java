package course1.week5;

import java.util.Scanner;

// The goal of this problem is to implement the algorithm for computing the edit distance between two strings.
class EditDistance {
    private static int editDistance(String s, String t) {
        int[][] editDistanceMatrix = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            editDistanceMatrix[i][0] = i;
        }
        for (int j = 0; j <= t.length(); j++) {
            editDistanceMatrix[0][j] = j;
        }

        for (int j = 1; j <= t.length(); j++) {
            for (int i = 1; i <= s.length(); i++) {
                int insertion = editDistanceMatrix[i][j - 1] + 1;
                int deletion = editDistanceMatrix[i - 1][j] + 1;
                int match = editDistanceMatrix[i - 1][j - 1];
                int mismatch = editDistanceMatrix[i - 1][j - 1] + 1;

                if (s.charAt(i - 1) == t.charAt(j - 1))
                    editDistanceMatrix[i][j] = Math.min(insertion, Math.min(deletion, match));
                else
                    editDistanceMatrix[i][j] = Math.min(insertion, Math.min(deletion, mismatch));
            }
        }
        return editDistanceMatrix[s.length()][t.length()];
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(editDistance(s, t));
    }

}
