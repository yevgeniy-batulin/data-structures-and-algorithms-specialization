package course1.week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/*
    Given a set of 𝑛 segments {[𝑎0,𝑏0],[𝑎1,𝑏1],...,[𝑎𝑛−1,𝑏𝑛−1]} with integer coordinates on a line,
    find the minimum number 𝑚 of points such that each segment contains at least one point.
    That is, find a set of integers 𝑋 of the minimum size such that for any segment [𝑎𝑖,𝑏𝑖] there is a point 𝑥 ∈ 𝑋 such that𝑎𝑖 ≤𝑥≤𝑏𝑖.
 */
public class CoveringSegments {

    private static int[] optimalPoints(Segment[] segments) {
        Arrays.sort(segments, Comparator.comparingInt(o -> o.end));

        List<Integer> points = new ArrayList<>();
        while (segments.length > 0) {
            Segment theMostLeftSegment = segments[0];
            List<Integer> indexes = new ArrayList<>();
            for (int i = 1; i < segments.length; i++) {
                if (theMostLeftSegment.end >= segments[i].start) {
                    indexes.add(i);
                }
            }
            points.add(theMostLeftSegment.end);
            segments[0] = null;
            for (Integer index : indexes) {
                segments[index] = null;
            }
            segments = Arrays.stream(segments).filter(Objects::nonNull).toArray(Segment[]::new);
        }
        return points.stream().mapToInt(i -> i).toArray();
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
 
