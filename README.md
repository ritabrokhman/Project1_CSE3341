Rita Brokhman

CSE 3341 Project 1 - Core Scanner

Files Included

Main.java: Provided by Tim Carpenter, handles program execution.

Core.java: Provided by Tim Carpenter, enum defining all tokens.

Scanner.java: Implements the scanner for the Core language.

tester.sh: Provided by the Tim Carpenter, script to automate testing.

Correct/: Provided by Tim Carpenter, folder with correct test cases and expected outputs.

Error/: Provided by Tim Carpenter, folder with error test cases.

Scanner Implementation

This scanner reads an input file and tokenizes it according to Core language rules. It follows a greedy approach to recognize:

Keywords (e.g., begin, if, return)

Identifiers (letters followed by letters/digits, case-sensitive)

Constants (integers between 0 and 1000003)

Symbols (e.g., +, =, ==, ;, {, })

Strings (enclosed in single quotes)

Invalid input is handled with meaningful error messages. The scanner reads input character-by-character and constructs tokens dynamically.

nextToken Method Explanation

The nextToken method reads the next part of the input file and determines the appropriate token type. Here’s a breakdown of how it works:

Skip Whitespace: Moves past spaces and blank lines to find the next meaningful character.

Check End of File: If there are no more characters left, it sets the token to EOS (End of Stream).

Identify Keywords and Identifiers: If a letter is found, it reads all following letters and numbers. If the word matches a known keyword, it is classified as such; otherwise, it is an identifier.

Identify Constants (Numbers): If a digit is found, it reads the whole number and ensures it is within the allowed range.

Process Strings: If a single quote (') is found, it reads until another quote appears. If no closing quote is found, an error is reported.

Recognize Symbols: Single and multi-character symbols (like = vs ==) are checked and assigned their correct token.

Handle Invalid Characters: If a character doesn’t match any valid token type, an error is displayed.

Known Issues

Leading zeros in numbers: Numbers like 007 are reported as errors.

Unterminated strings: If a string is missing a closing quote, it returns an error.

Special Features

Greedy tokenization: Ensures correct parsing of symbols (== vs. =) and keywords.

Whitespace handling: Tokens are correctly recognized even without spaces.
