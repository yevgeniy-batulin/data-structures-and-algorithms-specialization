package course2.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    In this problem you will implement a simple phone book manager.
    Use the direct addressing scheme.
 */
public class PhoneBook {
    private static final String NOT_FOUND = "not found";
    private FastScanner in = new FastScanner();
    private String[] contacts = new String[10000000];

    public static void main(String[] args) {
        new PhoneBook().processQueries();
    }

    private Query readQuery() {
        String type = in.next();
        int number = in.nextInt();
        if (type.equals("add")) {
            String name = in.next();
            return new Query(type, name, number);
        } else {
            return new Query(type, number);
        }
    }

    private void writeResponse(String response) {
        System.out.println(response);
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                contacts[query.number] = query.name;
                break;
            case "del":
                contacts[query.number] = NOT_FOUND;
                break;
            default:
                String contact = contacts[query.number];
                writeResponse(contact == null ? NOT_FOUND : contact);
                break;
        }
    }

    private void processQueries() {
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i)
            processQuery(readQuery());
    }

    static class Query {
        String type;
        String name;
        int number;

        Query(String type, String name, int number) {
            this.type = type;
            this.name = name;
            this.number = number;
        }

        Query(String type, int number) {
            this.type = type;
            this.number = number;
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}