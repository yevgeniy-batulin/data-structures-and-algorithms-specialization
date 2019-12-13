package algorithmictoolbox.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    In this problem, you will implement the binary search algorithm that allows searching very efficiently (even huge) lists,
    provided that the list is sorted.
 */
public class BinarySearch {

    private static int binarySearch(int[] a, int x) {
        int left = 0, right = a.length;
        return internalSearch(a, left, right, x);
    }

    private static int internalSearch(int[] array, int left, int right, int key) {
        if (right <= left) return -1;

        int mid = (int) Math.floor((left + right) / 2d);
        if (key == array[mid]) return mid;

        else if (key < array[mid])
            return internalSearch(array, left, mid, key);
        else
            return internalSearch(array, mid + 1, right, key);
    }

    static int linearSearch(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
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

        for (int i = 0; i < m; i++) {
            System.out.print(binarySearch(a, b[i]) + " ");
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
}
