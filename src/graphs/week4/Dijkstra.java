package graphs.week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
    Given an directed graph with positive edge weights and with 𝑛 vertices and 𝑚 edges as well as two vertices 𝑢 and 𝑣,
    compute the weight of a shortest path between 𝑢 and 𝑣 (that is, the minimum total weight of a path from 𝑢 to 𝑣).
 */
public class Dijkstra {
    private static int distance(List<List<Integer>> adjacent, List<List<Integer>> cost, int from, int to) {
        int defaultDistance = 9999999;
        int[] distance = new int[adjacent.size()];
        Arrays.fill(distance, defaultDistance);
        distance[from] = 0;
        boolean[] usedVertices = new boolean[adjacent.size()];

        while (anyVertexNotUsed(usedVertices)) {
            int minDistanceVertex = extractMin(distance, usedVertices);
            List<Integer> neighbours = adjacent.get(minDistanceVertex);
            for (int i = 0; i < neighbours.size(); i++) {
                int neighbour = neighbours.get(i);
                int newDistance = distance[minDistanceVertex] + cost.get(minDistanceVertex).get(i);
                if (distance[neighbour] > newDistance) {
                    distance[neighbour] = newDistance;
                }
            }
        }

        return distance[to] == defaultDistance ? -1 : distance[to];
    }

    private static boolean anyVertexNotUsed(boolean[] usedVertices) {
        for (boolean b : usedVertices) if (!b) return true;
        return false;
    }

    private static int extractMin(int[] distance, boolean[] used) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                if (distance[i] < min) {
                    index = i;
                    min = distance[i];
                }
            }
        }
        used[index] = true;
        return index;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adjacent = new ArrayList<>(n);
        List<List<Integer>> cost = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjacent.add(new ArrayList<>());
            cost.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adjacent.get(x - 1).add(y - 1);
            cost.get(x - 1).add(w);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adjacent, cost, x, y));
    }
}