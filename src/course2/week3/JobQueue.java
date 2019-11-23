package course2.week3;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
    You have a program which is parallelized and uses 𝑛 independent threads to process the given list of 𝑚 jobs.
    Threads take jobs in the order they are given in the input.
    If there is a free thread, it immediately takes the next job from the list.
    If a thread has started processing a job, it doesn’t interrupt or stop until it finishes processing the job.
    If several threads try to take jobs from the list simultaneously, the thread with smaller index takes the job.
    For each job you know exactly how long will it take any thread to process this job,
    and this time is the same for all the threads.
    You need to determine for each job which thread will process it and when will it start processing.
 */
public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        PriorityQueue<Thread> queue = new PriorityQueue<>();
        for (int i = 0; i < numWorkers; i++) {
            queue.add(new Thread(i, 0));
        }
        int counter = 0;
        for (int job : jobs) {
            Thread poll = queue.poll();
            queue.offer(new Thread(poll.id, poll.finishTime + job));
            assignedWorker[counter] = poll.id;
            startTime[counter++] = poll.finishTime;
        }
    }

    private static class Thread implements Comparable<Thread> {
        private int id;
        private long finishTime;

        Thread(int id, long finishTime) {
            this.id = id;
            this.finishTime = finishTime;
        }

        @Override
        public int compareTo(Thread o) {
            if (this.finishTime != o.finishTime)
                return Long.compare(this.finishTime, o.finishTime);
            return Integer.compare(this.id, o.id);
        }
    }

    private void assignJobsSlow() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    private void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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