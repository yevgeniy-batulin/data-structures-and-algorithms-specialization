package course2.week4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

/*
    In the longest common substring problem one is given two strings 𝑠 and 𝑡
    and the goal is to find a string 𝑤 of maximal length that is a substring of both 𝑠 and 𝑡.
 */
public class CommonSubstring {
    private static int MULTIPLIER = 31;
    private static long FIRST_PRIME = 1000000007;
    private static long SECOND_PRIME = 1000000009;

    public static class Answer {
        int i, j, length;

        Answer(int i, int j, int length) {
            this.i = i;
            this.j = j;
            this.length = length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Answer answer = (Answer) o;
            return i == answer.i &&
                    j == answer.j &&
                    length == answer.length;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j, length);
        }
    }

    private Answer solve(String first, String second) {
        Answer answer = new Answer(0, 0, 0);
        int left = 0;
        int right = Math.min(first.length(), second.length());
        while (left <= right) {
            int mid = (int) Math.floor((left + right) / 2d);

            Answer commonSubString = findCommonSubStringOfLength(first, second, mid);
            if (commonSubString != null) {
                left = mid + 1;
                answer = commonSubString;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }

    private Answer findCommonSubStringOfLength(String first, String second, int length) {
        Set<Answer> possibleMatchesUsingFirstHash = getPossibleMatches(first, second, length, FIRST_PRIME);
        Set<Answer> possibleMatchesUsingSecondHash = getPossibleMatches(first, second, length, SECOND_PRIME);

        for (Answer matchesUsingFirstHash : possibleMatchesUsingFirstHash) {
            if (possibleMatchesUsingSecondHash.contains(matchesUsingFirstHash)) return matchesUsingFirstHash;
        }
        return null;
    }

    private Set<Answer> getPossibleMatches(String first, String second, int length, long prime) {
        long multiplierInPowerPatternLengthModPrime = getMultiplierInPowerLengthModPrime(length, prime);
        long[] hashesOfFirst = computeHashes(first, length, prime, multiplierInPowerPatternLengthModPrime);

        Map<Long, Integer> hashesToIndexMap = new HashMap<>();
        for (int i = 0; i < hashesOfFirst.length; i++) {
            hashesToIndexMap.put(hashesOfFirst[i], i);
        }

        long[] hashesOfSecond = computeHashes(second, length, prime, multiplierInPowerPatternLengthModPrime);
        Set<Answer> possibleAnswers = new HashSet<>();
        for (int j = 0; j < hashesOfSecond.length; j++) {
            Integer i = hashesToIndexMap.get(hashesOfSecond[j]);
            if (i != null) {
                possibleAnswers.add(new Answer(i, j, length));
            }
        }

        return possibleAnswers;
    }

    private static long getMultiplierInPowerLengthModPrime(int length, long prime) {
        long result = 1;
        long base = MULTIPLIER;
        while (length > 0) {
            if (length % 2 == 1) {
                result = (result * base) % prime; // multiplying with base
            }
            base = (base * base) % prime; // squaring the base
            length /= 2;
        }
        return result % prime;
    }

    private static long[] computeHashes(String text, int substringLength, long prime, long multiplierInPowerPatternLength) {
        int textLength = text.length();
        String lastSubstring = text.substring(textLength - substringLength, textLength);
        long[] result = new long[textLength - substringLength + 1];
        result[result.length - 1] = hash(lastSubstring, prime);

        for (int i = result.length - 2; i >= 0; i--) {
            long lastCharacter = multiplierInPowerPatternLength * text.charAt(i + substringLength);
            char firstCharacter = text.charAt(i);
            long hash = (MULTIPLIER * result[i + 1] + firstCharacter - lastCharacter) % prime;
            if (hash < 0) hash += prime;
            result[i] = hash;
        }
        return result;
    }

    private static int hash(String string, long prime) {
        long hash = 0;
        for (int i = string.length() - 1; i >= 0; i--)
            hash = (hash * MULTIPLIER + string.charAt(i)) % prime;
        return (int) hash;
    }

    private void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        in.lines().forEach(line -> {
            if (line != null && !line.isEmpty()) {
                StringTokenizer tok = new StringTokenizer(line);
                String s = tok.nextToken();
                String t = tok.nextToken();
                Answer ans = solve(s, t);
                out.format("%d %d %d\n", ans.i, ans.j, ans.length);
            }
        });
        out.close();
    }

    static public void main(String[] args) {
        new CommonSubstring().run();
    }
}