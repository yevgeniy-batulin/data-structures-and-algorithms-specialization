package graphs.week4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
    Given an directed graph with possibly negative edge weights and with 𝑛 vertices and 𝑚 edges as well as its vertex 𝑠,
    compute the length of shortest paths from 𝑠 to all other vertices of the graph.
 */
public class ShortestPaths {
    private static void shortestPaths(List<List<Integer>> adjacent, List<List<Integer>> cost, int start, long[] distance, int[] reachable, int[] shortest) {
        checkReachability(adjacent, start, reachable);
        distance[start] = 0;
        checkNegativeCycles(adjacent, cost, distance, reachable, shortest);
    }

    private static void checkReachability(List<List<Integer>> adjacent, int start, int[] reachable) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        long[] distance = distance(adjacent, queue);
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] != -1) {
                reachable[i] = 1;
            }
        }
    }

    private static void checkNegativeCycles(List<List<Integer>> adjacent, List<List<Integer>> cost, long[] distance, int[] reachable, int[] shortest) {
        for (int i = 0; i < adjacent.size() - 1; i++) {
            relaxAllEdges(adjacent, cost, distance, reachable);
        }
        long[] oldDistance = Arrays.copyOf(distance, distance.length);
        relaxAllEdges(adjacent, cost, distance, reachable);

        Queue<Integer> relaxedOnLastCall = new LinkedList<>();
        for (int i = 0; i < oldDistance.length; i++)
            if (oldDistance[i] != distance[i] && reachable[i] == 1)
                relaxedOnLastCall.add(i);

        if (!relaxedOnLastCall.isEmpty()) {
            long[] distanceFromTheCycle = distance(adjacent, relaxedOnLastCall);
            for (int i = 0; i < distanceFromTheCycle.length; i++) {
                if (distanceFromTheCycle[i] != -1) {
                    shortest[i] = 0;
                }
            }
        }
    }

    private static long[] distance(List<List<Integer>> adjacent, Queue<Integer> queue) {
        long[] distance = new long[adjacent.size()];
        Arrays.fill(distance, -1);
        distance[queue.peek()] = 0;

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (Integer neighbour : adjacent.get(poll)) {
                if (distance[neighbour] == -1) {
                    queue.offer(neighbour);
                    distance[neighbour] = distance[poll] + 1;
                }
            }
        }

        return distance;
    }

    private static void relaxAllEdges(List<List<Integer>> adjacent, List<List<Integer>> cost, long[] distance, int[] reachable) {
        for (int i = 0; i < adjacent.size(); i++) {
            if (reachable[i] == 1) {
                List<Integer> neighbours = adjacent.get(i);
                for (int j = 0; j < adjacent.get(i).size(); j++) {
                    int neighbour = neighbours.get(j);
                    long newDistance = distance[i] + cost.get(i).get(j);
                    if (distance[neighbour] > newDistance) {
                        distance[neighbour] = newDistance;
                    }
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
        int s = scanner.nextInt() - 1;
        long[] distance = new long[n];
        int[] reachable = new int[n];
        int[] shortest = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = 9999999999L;
            reachable[i] = 0;
            shortest[i] = 1;
        }
        shortestPaths(adjacent, cost, s, distance, reachable, shortest);
        for (int i = 0; i < n; i++) {
            if (reachable[i] == 0) {
                System.out.println('*');
            } else if (shortest[i] == 0) {
                System.out.println('-');
            } else {
                System.out.println(distance[i]);
            }
        }
    }
}