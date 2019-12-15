package graphs.week1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
    Given an undirected graph and two distinct vertices 𝑢 and 𝑣, check if there is a path between 𝑢 and 𝑣.
 */
public class Reachability {
    private static int reach(List<List<Integer>> adj, int x, int y) {
        if (x == y) return 1;
        boolean[] visited = new boolean[adj.size()];
        visited[x] = true;
        Queue<Integer> queue = new LinkedList<>(adj.get(x));

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            if (poll == y) return 1;

            if (!visited[poll]) {
                visited[poll] = true;
                queue.addAll(adj.get(poll));
            }
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
            adj.get(y - 1).add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
}

