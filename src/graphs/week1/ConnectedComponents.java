package graphs.week1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
    Given an undirected graph with 𝑛 vertices and 𝑚 edges, compute the number of connected components in it.
 */
public class ConnectedComponents {
    private static int numberOfComponents(List<List<Integer>> adj) {
        int counter = 0;
        boolean[] visited = new boolean[adj.size()];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < adj.size(); i++) {
            if (!visited[i]) {
                visited[i] = true;
                counter++;
                queue.addAll(adj.get(i));
                while (!queue.isEmpty()) {
                    Integer poll = queue.poll();
                    if (!visited[poll]) {
                        visited[poll] = true;
                        queue.addAll(adj.get(poll));
                    }
                }
            }
        }
        return counter;
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
            adj.get(y - 1).add(x - 1);
        }
        System.out.println(numberOfComponents(adj));
    }
}

