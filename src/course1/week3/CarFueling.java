package course1.week3;

import java.util.Scanner;

/*
    You are going to travel to another city that is located 𝑑 miles away from your home city.
    Your can can travel at most 𝑚 miles on a full tank and you start with a full tank.
    Along your way, there are gas stations at distances stop1, stop2, . . . , stop𝑛 from your home city.
    What is the minimum number of refills needed?
 */
public class CarFueling {
    private static int computeMinRefills(int dist, int tank, int[] stops) {
        int numRefills = 0;
        int currentRefill = 0;
        int[] newStops = new int[stops.length + 2];
        newStops[0] = 0;
        System.arraycopy(stops, 0, newStops, 1, stops.length);
        newStops[newStops.length - 1] = dist;
        while (currentRefill < newStops.length - 1) {
            int lastRefill = currentRefill;
            while (currentRefill < newStops.length - 1 && newStops[currentRefill + 1] - newStops[lastRefill] <= tank) {
                currentRefill++;
            }
            if (currentRefill == lastRefill) return -1;
            if (currentRefill < newStops.length - 1) numRefills++;
        }
        return numRefills;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int[] stops = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));
    }
}
