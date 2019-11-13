package course1.week4;

import java.util.Scanner;

/*
    An inversion of a sequence 𝑎0,𝑎1,...,𝑎𝑛−1 is a pair of indices 0 ≤ 𝑖 < 𝑗 < 𝑛 such that 𝑎𝑖 > 𝑎𝑗.
    The number of inversions of a sequence in some sense measures how close the sequence is to being sorted.
    For example, a sorted (in non-descending order) sequence contains no inversions at all,
    while in a sequence sorted in descending order any two elements constitute an inversion (for a total of 𝑛(𝑛 − 1)/2 inversions).
 */
public class Inversions {

    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int mid = (left + right) / 2;

        numberOfInversions += getNumberOfInversions(a, b, left, mid);
        numberOfInversions += getNumberOfInversions(a, b, mid, right);

        int i = left;
        int j = mid;
        int k = left;
        while (i < mid && j < right) {
            if (a[i] <= a[j]) {
                b[k++] = a[i++];
            } else {
                b[k++] = a[j++];
                numberOfInversions += mid - i;
            }
        }

        while (i < mid) b[k++] = a[i++];
        while (j < right) b[k++] = a[j++];

        System.arraycopy(b, left, a, left, right - left);

        return numberOfInversions;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int[] b = new int[n];
        System.out.println(getNumberOfInversions(a, b, 0, a.length));
    }
}