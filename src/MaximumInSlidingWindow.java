import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/*
    Given a sequence 𝑎1, . . . , 𝑎𝑛 of integers and an integer 𝑚 ≤ 𝑛,
    find the maximum among {𝑎𝑖, . . . , 𝑎𝑖+𝑚−1} for every 1 ≤ 𝑖 ≤ 𝑛 − 𝑚 + 1.
    A naive 𝑂(𝑛𝑚) algorithm for solving this problem scans each window separately.
    Your goal is to design an 𝑂(𝑛) algorithm.
 */
public class MaximumInSlidingWindow {
    static class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static class Queue {
        private Stack<Entry<Integer>> firstStack = new Stack<>();
        private Stack<Entry<Integer>> secondStack = new Stack<>();

        static class Entry<T> {
            private T element;
            private T max;

            Entry(T element, T max) {
                this.element = element;
                this.max = max;
            }
        }

        void enqueue(Integer entry) {
            int max;
            if (firstStack.isEmpty()) max = entry;
            else max = Math.max(firstStack.peek().max, entry);

            firstStack.push(new Entry<>(entry, max));
        }

        int dequeue() {
            if (secondStack.isEmpty()) {
                int max = Integer.MIN_VALUE;
                while (!firstStack.empty()) {
                    Entry<Integer> pop = firstStack.pop();
                    if (pop.element > max) max = pop.element;

                    secondStack.push(new Entry<>(pop.element, max));
                }
            }
            return secondStack.pop().element;
        }

        int max() {
            if (firstStack.empty() && secondStack.isEmpty()) return 0;

            if (secondStack.isEmpty())
                return firstStack.peek().max;

            if (firstStack.isEmpty())
                return secondStack.peek().max;

            return Math.max(firstStack.peek().max, secondStack.peek().max);
        }

    }

    public static class SlidingWindow {
        int[] sequence;
        int windowSize;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            int n = in.nextInt();
            sequence = new int[n];
            for (int i = 0; i < n; i++) {
                sequence[i] = in.nextInt();
            }
            windowSize = in.nextInt();
        }

        int[] getMaxSequence() {
            Queue queue = new Queue();
            for (int i = 0; i < windowSize; i++) {
                queue.enqueue(sequence[i]);
            }

            int[] result = new int[sequence.length - windowSize + 1];
            result[0] = queue.max();
            int counter = 1;
            for (int i = windowSize; i < sequence.length; i++) {
                queue.enqueue(sequence[i]);
                queue.dequeue();
                result[counter++] = queue.max();
            }
            return result;
        }
    }

    public static void main(String[] args) throws IOException {
        SlidingWindow slidingWindow = new SlidingWindow();
        slidingWindow.read();
        int[] result = slidingWindow.getMaxSequence();
        for (int value : result) {
            System.out.print(value + " ");
        }
    }
}