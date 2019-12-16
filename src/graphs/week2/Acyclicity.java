package graphs.week2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Check whether a given directed graph with 𝑛 vertices and 𝑚 edges contains a cycle.
 */
public class Acyclicity {
    private static boolean foundCycle(List<List<Integer>> adjacent, int current, boolean[] visited, boolean[] visitedDuringRecursion) {
        if (visitedDuringRecursion[current]) return true;

        visited[current] = true;
        visitedDuringRecursion[current] = true;

        for (Integer neighbour : adjacent.get(current)) {
            if (foundCycle(adjacent, neighbour, visited, visitedDuringRecursion)) return true;
        }

        visitedDuringRecursion[current] = false;
        return false;
    }

    private static int acyclic(List<List<Integer>> adj) {
        boolean[] visited = new boolean[adj.size()];
        boolean[] visitedDuringRecursion = new boolean[adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            if (!visited[i] && foundCycle(adj, i, visited, visitedDuringRecursion)) return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj.get(x - 1).add(y - 1);
        }
        System.out.println(acyclic(adj));
    }
}