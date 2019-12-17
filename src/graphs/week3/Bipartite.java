package graphs.week3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
    Given an undirected graph with 𝑛 vertices and 𝑚 edges, check whether it is bipartite.

    An undirected graph is called bipartite if its vertices can be split into two parts such that each edge of the graph joins to vertices from different parts.
 */
public class Bipartite {
    private static int bipartite(List<List<Integer>> adjacent) {
        boolean[] part = new boolean[adjacent.size()];
        boolean[] visited = new boolean[adjacent.size()];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < adjacent.size(); i++) {
            if (!visited[i]) {
                part[i] = true;
                visited[i] = true;
                queue.offer(i);

                while (!queue.isEmpty()) {
                    Integer poll = queue.poll();
                    for (Integer neighbour : adjacent.get(poll)) {
                        if (!visited[neighbour]) {
                            queue.offer(neighbour);
                            visited[neighbour] = true;
                            part[neighbour] = !part[poll];
                        } else if (part[neighbour] == part[poll]) {
                            return 0;
                        }
                    }
                }
            }
        }

        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> adjacent = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjacent.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adjacent.get(x - 1).add(y - 1);
            adjacent.get(y - 1).add(x - 1);
        }
        System.out.println(bipartite(adjacent));
    }
}

