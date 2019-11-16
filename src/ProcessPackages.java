import java.util.ArrayList;
import java.util.Scanner;

/*
    You are given a series of incoming network packets, and your task is to simulate their processing.
    Packets arrive in some order. For each packet number 𝑖, you know the time when it arrived 𝐴𝑖 and the time it takes
    the processor to process it 𝑃𝑖 (both in milliseconds). There is only one processor,
    and it processes the incoming packets in the order of their arrival.
    If the processor started to process some packet, it doesn’t interrupt or stop until it finishes the processing of this packet,
    and the processing of packet 𝑖 takes exactly 𝑃𝑖 milliseconds.

    The computer processing the packets has a network buffer of fixed size 𝑆.
    When packets arrive, they are stored in the buffer before being processed.
    However, if the buffer is full when a packet arrives (there are 𝑆 packets which have arrived before this packet,
    and the computer hasn’t finished processing any of them), it is dropped and won’t be processed at all.
    If several packets arrive at the same time, they are first all stored in the buffer
    (some of them may be dropped because of that — those which are described later in the input).
    The computer processes the packets in the order of their arrival,
    and it starts processing the next available packet from the buffer as soon as it finishes processing the previous one.
    If at some point the computer is not busy, and there are no packets in the buffer,
    the computer just waits for the next packet to arrive.
    Note that a packet leaves the buffer and frees the space in the buffer as soon as the computer finishes processing it.
 */
class Request {
    Request(int arrivalTime, int processTime) {
        this.arrivalTime = arrivalTime;
        this.processTime = processTime;
    }

    int arrivalTime;
    int processTime;
}

class Response {
    Response(boolean dropped, int startTime) {
        this.dropped = dropped;
        this.startTime = startTime;
    }

    boolean dropped;
    int startTime;
}

class Buffer {
    private int size;
    private ArrayList<Integer> finishTimeList;

    Buffer(int size) {
        this.size = size;
        this.finishTimeList = new ArrayList<>(this.size);
    }

    Response process(Request request) {
        for (int i = 0; i < finishTimeList.size(); i++) {
            if (finishTimeList.get(i) <= request.arrivalTime) finishTimeList.remove(i);
            else break;
        }

        if (finishTimeList.isEmpty()) {
            finishTimeList.add(request.processTime + request.arrivalTime);
            return new Response(false, request.arrivalTime);
        } else {
            if (finishTimeList.size() == size) return new Response(true, -1);

            int startTime = finishTimeList.get(finishTimeList.size() - 1);
            finishTimeList.add(startTime + request.processTime);
            return new Response(false, startTime);
        }
    }
}

class ProcessPackages {
    private static ArrayList<Request> readQueries(Scanner scanner) {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<>();
        for (int i = 0; i < requests_count; ++i) {
            int arrivalTime = scanner.nextInt();
            int processTime = scanner.nextInt();
            requests.add(new Request(arrivalTime, processTime));
        }
        return requests;
    }

    private static ArrayList<Response> processRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<>();
        for (Request request : requests) {
            responses.add(buffer.process(request));
        }
        return responses;
    }

    private static void printResponses(ArrayList<Response> responses) {
        for (Response response : responses) {
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.startTime);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int bufferMaxSize = scanner.nextInt();
        Buffer buffer = new Buffer(bufferMaxSize);

        ArrayList<Request> requests = readQueries(scanner);
        ArrayList<Response> responses = processRequests(requests, buffer);
        printResponses(responses);
    }
}