package graphs.week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
    Given an directed graph with possibly negative edge weights and with 𝑛 vertices and 𝑚 edges,
    check whether it contains a cycle of negative weight.
 */
public class NegativeCycle {
    private static int negativeCycle(List<List<Integer>> adjacent, List<List<Integer>> cost) {
        int size = adjacent.size();
        int[] distance = new int[size];
        Arrays.fill(distance, 99999);
        distance[0] = 0;

        for (int i = 0; i < size - 1; i++) {
            relaxAllEdges(adjacent, cost, distance);
        }

        int[] oldDistance = Arrays.copyOf(distance, distance.length);
        relaxAllEdges(adjacent, cost, distance);

        return Arrays.equals(oldDistance, distance) ? 0 : 1;
    }

    private static void relaxAllEdges(List<List<Integer>> adjacent, List<List<Integer>> cost, int[] distance) {
        for (int j = 0; j < adjacent.size(); j++) {
            List<Integer> neighbours = adjacent.get(j);
            for (int k = 0; k < adjacent.get(j).size(); k++) {
                int neighbour = neighbours.get(k);
                int newDistance = distance[j] + cost.get(j).get(k);
                if (distance[neighbour] > newDistance) {
                    distance[neighbour] = newDistance;
                }
            }
        }
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
        System.out.println(negativeCycle(adjacent, cost));
    }
}