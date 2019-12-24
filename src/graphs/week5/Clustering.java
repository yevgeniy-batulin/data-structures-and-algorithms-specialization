package graphs.week5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
    Given 𝑛 points on a plane and an integer 𝑘, compute the largest possible value of 𝑑
    such that the given points can be partitioned into 𝑘 non-empty subsets in such a way
    that the distance between any two points from different subsets is at least 𝑑.
 */
public class Clustering {
    private static double clustering(int[] x, int[] y, int k) {
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

        for (Vertex vertex : vertices) {
            if (disjointSet.find(vertex.first.id) != disjointSet.find(vertex.second.id)) {
                if (k == disjointSet.numberOfComponents) return vertex.distance;

                disjointSet.union(vertex.first.id, vertex.second.id);
            }
        }

        return -1;
    }

    private static class DisjointSet {
        private int[] parent;
        private int[] rank;
        private int numberOfComponents;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            numberOfComponents = 0;
        }

        void makeSet(int index) {
            parent[index] = index;
            rank[index] = 0;
            numberOfComponents++;
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
            numberOfComponents--;
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
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
    }
}