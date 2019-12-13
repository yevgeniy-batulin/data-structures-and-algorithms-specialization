package algorithmictoolbox.week4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// You are given a set of points on a line and a set of segments on a line.
// The goal is to compute, for each point, the number of segments that contain this point.
public class PointsAndSegments {
    private static int[] fastCountSegments(int[] starts, int[] ends, int[] points) {
        Stream<String> startsStream = Arrays.stream(starts).mapToObj(it -> it + "_l");
        Stream<String> endsStream = Arrays.stream(ends).mapToObj(it -> it + "_r");
        Stream<String> pointsStream = IntStream.range(0, points.length).mapToObj(it -> points[it] + "_p_" + it);

        String[] strings = Stream.concat(Stream.concat(startsStream, pointsStream), endsStream)
                .sorted((val1, val2) -> {
                    String[] s1 = val1.split("_");
                    String[] s2 = val2.split("_");

                    int compareNumbers = Integer.compare(Integer.parseInt(s1[0]), Integer.parseInt(s2[0]));
                    return compareNumbers == 0 ? s1[1].compareTo(s2[1]) : compareNumbers;
                })
                .toArray(String[]::new);

        int currentNumberOfSegments = 0;
        int[] result = new int[points.length];
        for (String entry : strings) {
            String[] s1 = entry.split("_");
            if (s1[1].equals("l")) {
                currentNumberOfSegments++;
            }
            if (s1[1].equals("p")) {
                result[Integer.parseInt(s1[2])] = currentNumberOfSegments;
            }
            if (s1[1].equals("r")) {
                currentNumberOfSegments--;
            }
        }

        return result;
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }

        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
    }
}