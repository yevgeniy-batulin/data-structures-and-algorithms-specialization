package course2.week3;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
    In this problem you will convert an array of integers into a heap.
    This is the crucial step of the sorting algorithm called HeapSort.
    It has guaranteed worst-case running time of 𝑂(𝑛 log 𝑛) as opposed to QuickSort’s average running time of 𝑂(𝑛 log 𝑛).
 */
public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
            data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
        swaps = new ArrayList<>();
        buildHeap();
    }

    private void buildHeap() {
        for (int i = data.length / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftDown(int index) {
        int minIndex = index;
        int leftChildIndex = leftChildIndex(index);
        if (leftChildIndex < data.length && data[leftChildIndex] < data[minIndex]) minIndex = leftChildIndex;
        int rightChildIndex = rightChildIndex(index);
        if (rightChildIndex < data.length && data[rightChildIndex] < data[minIndex]) minIndex = rightChildIndex;
        if (index != minIndex) {
            swap(index, minIndex);
            swaps.add(new Swap(index, minIndex));
            siftDown(minIndex);
        }
    }

    private int leftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int rightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void swap(int firstIndex, int secondIndex) {
        int temp = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = temp;
    }

    private void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}