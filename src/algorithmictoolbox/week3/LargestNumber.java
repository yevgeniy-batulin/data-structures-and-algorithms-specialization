package algorithmictoolbox.week3;

import java.util.Arrays;
import java.util.Scanner;

// Compose the largest number out of a set of integers.
public class LargestNumber {
    private static String largestNumber(String[] a) {
        Arrays.sort(a, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));
        return String.join("", a);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}