import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/*
    In this problem you will implement a feature for a text editor to find errors in the usage of brackets in the code.
 */
class Bracket {
    private char type;
    private int position;

    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        return this.type == '(' && c == ')';
    }

    int getPosition() {
        return position;
    }
}

public class CheckBrackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);
        String text = reader.readLine();
        checkBrackets(text);
    }

    private static void checkBrackets(String text) {
        Stack<Bracket> openingBracketsStack = new Stack<>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
                openingBracketsStack.push(new Bracket(next, position));
            }

            if (next == ')' || next == ']' || next == '}') {
                if (openingBracketsStack.isEmpty()) {
                    System.out.println(position + 1);
                    return;
                }
                Bracket bracket = openingBracketsStack.pop();
                if (!bracket.match(next)) {
                    System.out.println(position + 1);
                    return;
                }
            }
        }

        if (openingBracketsStack.isEmpty())
            System.out.println("Success");
        else
            System.out.println(openingBracketsStack.pop().getPosition() + 1);
    }
}