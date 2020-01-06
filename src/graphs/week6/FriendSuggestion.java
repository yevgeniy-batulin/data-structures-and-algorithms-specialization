package graphs.week6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
    Implement the Bidirectional Dijkstra algorithm from the lectures.
 */
public class FriendSuggestion {
    private static class Impl {
        // Number of nodes
        int n;
        // adj[0] and cost[0] store the initial graph, adj[1] and cost[1] store the reversed graph.
        // Each graph is stored as array of adjacency lists for each node. adj stores the edges,
        // and cost stores their costs.
        List<List<List<Integer>>> adj;
        List<List<List<Integer>>> cost;
        // distance[0] and distance[1] correspond to distance estimates in the forward and backward searches.
        long[][] distance;
        // Two priority queues, one for forward and one for backward search.
        ArrayList<PriorityQueue<Entry>> queue;
        // visited[side][v] == true iff v was visited either by forward or backward search.
        boolean[][] visited;
        // List of all the nodes which were visited either by forward or backward search.
        List<Integer> workset;
        final long INFINITY = Long.MAX_VALUE / 4;

        Impl(int n) {
            this.n = n;
            visited = new boolean[2][n];
            workset = new ArrayList<>();
            distance = new long[][]{new long[n], new long[n]};
            Arrays.fill(distance[0], INFINITY);
            Arrays.fill(distance[1], INFINITY);
            queue = new ArrayList<>();
            queue.add(new PriorityQueue<>(n));
            queue.add(new PriorityQueue<>(n));
        }

        // Reinitialize the data structures before new query after the previous query
        void clear() {
            for (int vertex : workset) {
                distance[0][vertex] = INFINITY;
                distance[1][vertex] = INFINITY;
            }
            Arrays.fill(visited[0], false);
            Arrays.fill(visited[1], false);
            workset.clear();
            queue.get(0).clear();
            queue.get(1).clear();
        }

        // Try to relax the distance from direction side to node v using value dist.
        void visit(int side, int v, Long dist) {
            // Implement this method yourself
            if (distance[side][v] > dist) {
                distance[side][v] = dist;
                queue.get(side).add(new Entry(distance[side][v], v));
                workset.add(v);
            }
        }

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(0, s, 0L);
            visit(1, t, 0L);
            // Implement the rest of the algorithm yourself

            do {
                Entry forwardEntry = queue.get(0).poll();
                process(0, forwardEntry.node);
                visited[0][forwardEntry.node] = true;
                if (visited[1][forwardEntry.node]) {
                    return shortestPath();
                }

                Entry reverseEntry = queue.get(1).poll();
                process(1, reverseEntry.node);
                visited[1][reverseEntry.node] = true;
                if (visited[0][reverseEntry.node]) {
                    return shortestPath();
                }
            } while (!queue.get(0).isEmpty() && !queue.get(1).isEmpty());

            return -1L;
        }

        void process(int side, int node) {
            List<List<Integer>> sideList = adj.get(side);
            List<Integer> neighbours = sideList.get(node);
            for (int i = 0; i < neighbours.size(); i++) {
                visit(side, neighbours.get(i), distance[side][node] + cost.get(side).get(node).get(i));
            }
        }

        long shortestPath() {
            long result = INFINITY;
            for (Integer get : workset) {
                if (distance[0][get] + distance[1][get] < result) {
                    result = distance[0][get] + distance[1][get];
                }
            }
            if (result == INFINITY) return -1;
            else return result;
        }

        static class Entry implements Comparable<Entry> {
            Long cost;
            int node;

            Entry(Long cost, int node) {
                this.cost = cost;
                this.node = node;
            }

            public int compareTo(Entry other) {
                return cost.compareTo(other.cost);
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Impl impl = new Impl(n);
        impl.adj = new ArrayList<>(2);
        impl.cost = new ArrayList<>(2);
        for (int side = 0; side < 2; ++side) {
            impl.adj.add(new ArrayList<>(n));
            impl.cost.add(new ArrayList<>(n));
            for (int i = 0; i < n; i++) {
                impl.adj.get(side).add(new ArrayList<>());
                impl.cost.get(side).add(new ArrayList<>());
            }
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            impl.adj.get(0).get(x - 1).add(y - 1);
            impl.cost.get(0).get(x - 1).add(c);
            impl.adj.get(1).get(y - 1).add(x - 1);
            impl.cost.get(1).get(y - 1).add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(impl.query(u - 1, v - 1));
        }
    }
}