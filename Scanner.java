import java.io.*;
import java.util.*;

/* Rita Brokhman
 * CSE 3341 SP25
 * Tim Carpenter
 * MWF 12:40 - 1:35 */

class Scanner {

    // Initialize the constants and the keywords in the language
    private BufferedReader reader;
    private String line;
    private int index;
    private Core currentToken;
    private String currentId;
    private int currentConst;
    private String currentString;
    // There is a finite amount of keywords in the Core language, so we need to initialize it as a HashSet
    private static final Set<String> keywords = new HashSet<>(Arrays.asList(
        "and", "begin", "case", "do", "else", "end", "for", "if", "in", "integer",
        "is", "new", "not", "object", "or", "print", "procedure", "read", "return", "then"
    ));

    // Initialize the scanner
    Scanner(String filename) {
        try {
            reader = new BufferedReader(new FileReader(filename));
            index = 0;
            line = reader.readLine();
            // Read the first token
            nextToken();
        } catch (IOException e) {
            // If file is unable to open, print an error
            System.out.println("ERROR: Unable to open file" + filename);
            currentToken = Core.ERROR;
        }
    }

    // Advance to the next token
    public void nextToken() {
        // If we have already reached the end of the file, return the EOS token
    if (line == null) {
        currentToken = Core.EOS;
        return;
    }

    // Skip spaces and move to the next meaningful character
    while (index < line.length() && Character.isWhitespace(line.charAt(index))) {
        index++;
    }

    // If we reached the end of the line, read the next line from the file
    if (index >= line.length()) {
        try {
            line = reader.readLine();
            index = 0;

            // If no more lines exist, return EOS
            if (line == null) {
                currentToken = Core.EOS;
                return;
            }
            // Process the next line recursively
            nextToken(); 
        } catch (IOException e) {
            currentToken = Core.ERROR;
        }
        return;
    }

    char ch = line.charAt(index);

    // Handle identifiers and keywords (words that start with a letter)
    if (Character.isLetter(ch)) {

        StringBuilder sb = new StringBuilder();

        // Append letters from 'line' to 'sb' until a non-letter is found
        while (index < line.length() && (Character.isLetterOrDigit(line.charAt(index)))) {
            sb.append(line.charAt(index++));
        }
        String word = sb.toString();

        // If it is not a keyword, it is an identifier
        if (keywords.contains(word)) {
            currentToken = Core.valueOf(word.toUpperCase());
        } else {
            currentToken = Core.ID;
            currentId = word;
        }
        return;
    }

    // Handle constants (numbers)
    if (Character.isDigit(ch)) {

        StringBuilder sb = new StringBuilder();

        // Append numbers from 'line' to 'sb' until a non-number is found
        while (index < line.length() && Character.isDigit(line.charAt(index))) {
            sb.append(line.charAt(index++));
        }

        try {
            currentConst = Integer.parseInt(sb.toString());

            // Check if the number is within the valid range
            if (currentConst < 0 || currentConst > 1000003) {
                System.out.println("ERROR: Constant out of range");
                currentToken = Core.ERROR;
            } else {
                currentToken = Core.CONST;
            }
        } catch (NumberFormatException e) {
            // If the number does not fit the Core format, print an error
            System.out.println("ERROR: Invalid constant format");
            currentToken = Core.ERROR;
        }
        return;
    }

    // Handle strings (text enclosed in single quotes)
    if (ch == '\'') {
        index++;
        StringBuilder sb = new StringBuilder();

        // Read characters until we find another quote or reach the end of the line
        while (index < line.length() && line.charAt(index) != '\'') {
            sb.append(line.charAt(index++));
        }

        // If we reached the end without a closing quote, print an error
        if (index < line.length()) {
            // Skip the closing quote
            index++; 
            currentToken = Core.STRING;
            currentString = sb.toString();
        } else {
            System.out.println("ERROR: Unclosed/Open String");
            currentToken = Core.ERROR;
        }
        return;
    }

    // Handle symbols and operators
    switch (ch) {
        case '+': currentToken = Core.ADD; break;
        case '-': currentToken = Core.SUBTRACT; break;
        case '*': currentToken = Core.MULTIPLY; break;
        case '/': currentToken = Core.DIVIDE; break;

        // Handle "==" and "=" by distinguihing between assignment and equality
        case '=':
            if (peek() == '=') { index++; currentToken = Core.EQUAL; }
            else currentToken = Core.ASSIGN;
            break;
        case '<': currentToken = Core.LESS; break;
        case ':': currentToken = Core.COLON; break;
        case ';': currentToken = Core.SEMICOLON; break;
        case '.': currentToken = Core.PERIOD; break;
        case ',': currentToken = Core.COMMA; break;
        case '(': currentToken = Core.LPAREN; break;
        case ')': currentToken = Core.RPAREN; break;
        case '[': currentToken = Core.LSQUARE; break;
        case ']': currentToken = Core.RSQUARE; break;
        case '{': currentToken = Core.LCURL; break;
        case '}': currentToken = Core.RCURL; break;
        default:
            // Print an error for any character that doesn't match a valid token
            System.out.println("ERROR: Invalid character '" + ch + "'");
            currentToken = Core.ERROR;
            break;
    }
    // Move to the next character
    index++;
    }

    // Return the current token
    public Core currentToken() {
        return currentToken;
    }

	// Return the identifier String
    public String getId() {
        return currentId;
    }

	// Return the constant value
    public int getConst() {
        return currentConst;
    }
	
	// Return the character String
    public String getString() {
        return currentString;
    }

    // Peeks at the next character without consuming it
    // Used mainly to distinguish between "=" and "=="
    private char peek() {
        return (index + 1 < line.length()) ? line.charAt(index + 1) : '\0';
    }

}
