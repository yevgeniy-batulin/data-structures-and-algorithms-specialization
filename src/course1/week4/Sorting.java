package course1.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

/*
    The goal in this problem is to redesign a given implementation of the randomized quick sort algorithm so that
    it works fast even on sequences containing many equal elements.
 */
public class Sorting {
    private static Random random = new Random();

    private static int[] partition3(int[] a, int l, int r) {
        int m1 = l;
        int m2 = l + 1;
        for (int i = l + 1; i <= r; i++) {
            if (a[m1] > a[i]) {
                swap(a, m1, i);
                swap(a, m2, i);
                m1++;
                m2++;
            } else if (a[m1] == a[i]) {
                swap(a, m2, i);
                m2++;
            }
        }

        return new int[]{m1, m2};
    }

    private static int partition2(int[] a, int l, int r) {
        int x = a[l];
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] <= x) {
                j++;
                swap(a, i, j);
            }
        }
        swap(a, l, j);
        return j;
    }

    private static void randomizedQuickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        swap(a, l, k);
        int[] m = partition3(a, l, r);
        randomizedQuickSort(a, l, m[0] - 1);
        randomizedQuickSort(a, m[1], r);
    }

    private static void randomizedQuickSortSlow(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int k = random.nextInt(r - l + 1) + l;
        swap(a, l, k);
        int m = partition2(a, l, r);
        randomizedQuickSortSlow(a, l, m - 1);
        randomizedQuickSortSlow(a, m + 1, r);
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        randomizedQuickSort(a, 0, n - 1);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    private static void swap(int[] a, int m1, int i) {
        int temp = a[m1];
        a[m1] = a[i];
        a[i] = temp;
    }
}