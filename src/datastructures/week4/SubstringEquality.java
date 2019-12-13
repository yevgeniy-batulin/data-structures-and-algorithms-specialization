package datastructures.week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
    In this problem, you will use hashing to design an algorithm that is able to preprocess a given string 𝑠
    to answer any query of the form “are these two substrings of 𝑠 equal?” efficiently.

    Input Format. The first line contains a string 𝑠 consisting of small Latin letters.
    The second line contains the number of queries 𝑞. Each of the next 𝑞 lines specifies a query by three integers 𝑎, 𝑏, and 𝑙.

    Output Format. For each query, output “Yes” if 𝑠𝑎𝑠𝑎+1...𝑠𝑎+𝑙−1 = 𝑠𝑏𝑠𝑏+1...𝑠𝑏+𝑙−1 are equal, and “No” otherwise.
 */
public class SubstringEquality {
    public static class Solver {
        private String string;
        private long[] hashesFromFirstFunction;
        private long[] hashesFromSecondFunction;
        private long firstPrime = 1000000007;
        private long secondPrime = 1000000009;
        private int multiplier = 13;

        Solver(String string) {
            this.string = string;
            computeHashes();
        }

        boolean ask(int a, int b, int length) {
            if (equalSubStrings(a, b, length, hashesFromFirstFunction, firstPrime)) {
                return equalSubStrings(a, b, length, hashesFromSecondFunction, secondPrime);
            }

            return false;
        }

        private void computeHashes() {
            hashesFromFirstFunction = new long[string.length() + 1];
            hashesFromSecondFunction = new long[string.length() + 1];

            for (int i = 1; i < hashesFromFirstFunction.length; i++) {
                hashesFromFirstFunction[i] = (((multiplier * hashesFromFirstFunction[i - 1]) % firstPrime) + firstPrime + string.charAt(i - 1)) % firstPrime;
                hashesFromSecondFunction[i] = (((multiplier * hashesFromSecondFunction[i - 1]) % secondPrime) + secondPrime + string.charAt(i - 1)) % secondPrime;
            }
        }

        private boolean equalSubStrings(int a, int b, int length, long[] hashes, long prime) {
            long multiplierInPowerLength = getMultiplierInPowerModPrime(length, prime);
            long aSubstringHash = getSubstringHash(a, length, multiplierInPowerLength, hashes, prime);
            long bSubstringHash = getSubstringHash(b, length, multiplierInPowerLength, hashes, prime);

            return aSubstringHash == bSubstringHash;
        }

        private long getSubstringHash(int index, int length, long multiplierInPowerLength, long[] hashes, long prime) {
            long result = hashes[index + length] - ((multiplierInPowerLength * hashes[index]) % prime);
            if (result < 0) result += prime;
            return result;
        }

        private long getMultiplierInPowerModPrime(int exponent, long prime) {
            long result = 1;
            long base = multiplier;
            while (exponent > 0) {
                if (exponent % 2 == 1) {
                    result = (result * base) % prime; // multiplying with base
                }
                base = (base * base) % prime; // squaring the base
                exponent /= 2;
            }
            return result % prime;
        }
    }

    private void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String string = in.next();
        int quantity = in.nextInt();
        Solver solver = new Solver(string);
        for (int i = 0; i < quantity; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int l = in.nextInt();
            out.println(solver.ask(a, b, l) ? "Yes" : "No");
        }
        out.close();
    }

    static public void main(String[] args) throws IOException {
        new SubstringEquality().run();
    }

    static class FastScanner {
        StringTokenizer tokenizer = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tokenizer.hasMoreElements())
                tokenizer = new StringTokenizer(in.readLine());
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}