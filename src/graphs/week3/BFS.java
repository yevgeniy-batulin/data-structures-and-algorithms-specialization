package graphs.week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
    Given an undirected graph with 𝑛 vertices and 𝑚 edges and two vertices 𝑢 and 𝑣,
    compute the length of a shortest path between 𝑢 and 𝑣 (that is, the minimum number of edges in a path from 𝑢 to 𝑣).
 */
public class BFS {
    private static int distance(List<List<Integer>> adjacent, int from, int to) {
        int[] distance = new int[adjacent.size()];
        Arrays.fill(distance, -1);
        Queue<Integer> queue = new LinkedList<>();

        distance[from] = 0;
        queue.offer(from);

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (Integer neighbour : adjacent.get(poll)) {
                if (distance[neighbour] == -1) {
                    queue.offer(neighbour);
                    distance[neighbour] = distance[poll] + 1;
                }
            }
        }

        return distance[to];
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adjacent, x, y));
    }
}