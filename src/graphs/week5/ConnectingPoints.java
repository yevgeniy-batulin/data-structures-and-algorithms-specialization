package graphs.week5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
    Given 𝑛 points on a plane, connect them with segments of minimum total length such that there is a path between any two points.
    Recall that the length of a segment with endpoints (𝑥1,𝑦1) and (𝑥2,𝑦2)︀ is equal to √(𝑥1 −𝑥2)^2 +(𝑦1 −𝑦2)^2 .
 */
public class ConnectingPoints {
    private static double minimumDistance(int[] x, int[] y) {
        DisjointSet disjointSet = new DisjointSet(x.length);
        for (int i = 0; i < x.length; i++) {
            disjointSet.makeSet(i);
        }
        List<Vertex> vertices = new ArrayList<>(x.length);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (i != j) vertices.add(new Vertex(new Point(i, x[i], y[i]), new Point(j, x[j], y[j])));
            }
        }
        Collections.sort(vertices);

        double result = 0;
        for (Vertex vertex : vertices) {
            if (disjointSet.find(vertex.first.id) != disjointSet.find(vertex.second.id)) {
                result += vertex.distance;
                disjointSet.union(vertex.first.id, vertex.second.id);
            }
        }
        return result;
    }

    private static class DisjointSet {
        private int[] parent;
        private int[] rank;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
        }

        void makeSet(int index) {
            parent[index] = index;
            rank[index] = 0;
        }

        int find(int index) {
            while (index != parent[index])
                index = parent[index];
            return index;
        }

        void union(int i, int j) {
            int iId = find(i);
            int jId = find(j);
            if (iId == jId) return;
            if (rank[iId] > rank[jId]) parent[jId] = iId;
            else {
                parent[iId] = jId;
                if (rank[iId] == rank[jId]) rank[jId] = rank[jId] + 1;
            }
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        private Point first;
        private Point second;
        private double distance;

        Vertex(Point first, Point second) {
            this.first = first;
            this.second = second;
            this.distance = Math.hypot(first.x - second.x, first.y - second.y);
        }

        @Override
        public int compareTo(Vertex other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    private static class Point {
        private int id;
        private int x;
        private int y;

        Point(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }
}