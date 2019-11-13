package course1.week4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/*
    Given 𝑛 points on a plane, find the smallest distance between a pair of two (different) points.
    Recall that the distance between points (𝑥1, 𝑦1) and (𝑥2, 𝑦2) is equal to √(𝑥1 − 𝑥2)^2 + (𝑦1 − 𝑦2)^2.
 */
public class Closest {

    private static double minimalDistance(long[] x, long[] y) {
        if (x.length == 0) return 0;
        Point[] pointsSortedByX = new Point[x.length];
        Point[] pointsSortedByY = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            pointsSortedByX[i] = new Point(x[i], y[i]);
            pointsSortedByY[i] = new Point(x[i], y[i]);
        }

        Arrays.sort(pointsSortedByX, Comparator.comparingLong(v -> v.x));
        Arrays.sort(pointsSortedByY, Comparator.comparingLong(v -> v.y));

        return minimalDistanceInternal(pointsSortedByX, pointsSortedByY);
    }

    private static double minimalDistanceInternal(Point[] pointsSortedByX, Point[] pointsSortedByY) {
        if (pointsSortedByX.length <= 3) {
            double answer = Double.POSITIVE_INFINITY;
            for (int i = 0; i < pointsSortedByX.length; i++) {
                for (int j = i + 1; j < pointsSortedByX.length; j++) {
                    double distance = distance(pointsSortedByX[i], pointsSortedByX[j]);
                    if (distance < answer) answer = distance;
                }
            }
            return answer;
        }

        int mid = pointsSortedByX.length / 2;

        Point[] leftPartSortedByX = Arrays.copyOf(pointsSortedByX, mid);
        Point[] rightPartSortedByX = Arrays.copyOfRange(pointsSortedByX, mid, pointsSortedByX.length);

        Point[] leftPartSortedByY = Arrays.copyOf(leftPartSortedByX, leftPartSortedByX.length);
        Point[] rightPartSortedByY = Arrays.copyOf(rightPartSortedByX, rightPartSortedByX.length);

        Arrays.sort(leftPartSortedByY, Comparator.comparingLong(v -> v.y));
        Arrays.sort(rightPartSortedByY, Comparator.comparingLong(v -> v.y));

        double minimalDistanceLeft = minimalDistanceInternal(leftPartSortedByX, leftPartSortedByY);
        double minimalDistanceRight = minimalDistanceInternal(rightPartSortedByX, rightPartSortedByY);

        double minimalDistance = Math.min(minimalDistanceLeft, minimalDistanceRight);

        List<Point> strippedPoints = new ArrayList<>();
        for (Point point : pointsSortedByY) {
            if (Math.abs(pointsSortedByX[mid].x - point.x) < minimalDistance) {
                strippedPoints.add(point);
            }
        }

        for (int i = 0; i < strippedPoints.size() - 1; i++) {
            for (int j = i + 1; j < strippedPoints.size() && strippedPoints.get(j).y - strippedPoints.get(i).y < minimalDistance; j++) {
                double distance = distance(strippedPoints.get(i), strippedPoints.get(j));
                if (distance < minimalDistance) minimalDistance = distance;
            }
        }

        return minimalDistance;
    }

    static double minimalDistanceSlow(long[] x, long[] y) {
        if (x.length == 0) return 0;
        Point[] points = new Point[x.length];
        for (int i = 0; i < x.length; i++) {
            points[i] = new Point(x[i], y[i]);
        }

        double answer = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = distance(points[i], points[j]);
                if (distance < answer) answer = distance;
            }
        }

        return answer;
    }

    private static double distance(Point first, Point second) {
        return Math.hypot(first.x - second.x, first.y - second.y);
    }

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        long[] x = new long[n];
        long[] y = new long[n];
        for (int i = 0; i < n; i++) {
            x[i] = nextInt();
            y[i] = nextInt();
        }
        System.out.printf("%.4f%n", minimalDistance(x, y));
        writer.close();
    }

    private static BufferedReader reader;
    private static PrintWriter writer;
    private static StringTokenizer tok = new StringTokenizer("");


    private static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    private static int nextInt() {
        return Integer.parseInt(next());
    }

    static class Point {
        long x, y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}