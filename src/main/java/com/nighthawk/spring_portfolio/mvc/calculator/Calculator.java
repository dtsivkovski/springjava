package com.nighthawk.spring_portfolio.mvc.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.lang.Math;

public class Calculator {
    // Key instance variables
    private String expression;
    private ArrayList<String> tokens;
    private ArrayList<String> reverse_polish;
    private Double result = 0.0;

    // Helper definition for supported operators
    private final Map<String, Integer> OPERATORS = new HashMap<>();
    {
        // Map<"token", precedence>
        OPERATORS.put("^", 1);
        OPERATORS.put("root", 2);
        OPERATORS.put("sqrt", 2);
        OPERATORS.put("ncr", 2);
        OPERATORS.put("*", 3);
        OPERATORS.put("/", 3);
        OPERATORS.put("%", 3);
        OPERATORS.put("+", 4);
        OPERATORS.put("-", 4);
    }

    // Helper definition for supported operators
    private final Map<String, Integer> SEPARATORS = new HashMap<>();
    {
        // Map<"separator", not_used>
        SEPARATORS.put(" ", 0);
        SEPARATORS.put("(", 0);
        SEPARATORS.put(")", 0);
    }

    // Create a 1 argument constructor expecting a mathematical expression
    public Calculator(String expression) {
        // original input
        this.expression = expression;

        // parse expression into terms
        this.termTokenizer();
        
        // place terms into reverse polish notation
        this.tokensToReversePolishNotation();
        // calculate reverse polish notation
        this.rpnToResult();
    }

    // Test if token is an operator
    private boolean isOperator(String token) {
        // find the token in the hash map
        return OPERATORS.containsKey(token);
    }

    // Test if token is an separator
    private boolean isSeparator(String token) {
        // find the token in the hash map
        return SEPARATORS.containsKey(token);
    }

    // Compare precedence of operators.
    private Boolean isPrecedent(String token1, String token2) {
        // token 1 is precedent if it is greater than token 2
        return (OPERATORS.get(token1) - OPERATORS.get(token2) >= 0) ;
    }

    // Term Tokenizer takes original expression and converts it to ArrayList of tokens
    private void termTokenizer() {
        // contains final list of tokens
        this.tokens = new ArrayList<>();

        int start = 0;  // term split starting index
        StringBuilder multiCharTerm = new StringBuilder();    // term holder
        for (int i = 0; i < this.expression.length(); i++) {
            Character c = this.expression.charAt(i);
            if ( isOperator(c.toString() ) || isSeparator(c.toString())  ) {
                // 1st check for working term and add if it exists
                if (multiCharTerm.length() > 0) {
                    tokens.add(this.expression.substring(start, i));
                }
                // Add operator or parenthesis term to list
                if (c != ' ') {
                    tokens.add(c.toString());
                }
                // Get ready for next term
                start = i + 1;
                multiCharTerm = new StringBuilder();
            } else {
                // multi character terms: numbers, functions, perhaps non-supported elements
                // Add next character to working term
                multiCharTerm.append(c);
            }

        }
        // Add last term
        if (multiCharTerm.length() > 0) {
            tokens.add(this.expression.substring(start));
        }
    }

    // Takes tokens and converts to Reverse Polish Notation (RPN), this is one where the operator follows its operands.
    private void tokensToReversePolishNotation () {
        // contains final list of tokens in RPN
        this.reverse_polish = new ArrayList<>();

        // stack is used to reorder for appropriate grouping and precedence
        Stack<String> tokenStack = new Stack<String>();
        for (String token : tokens) {
            switch (token) {
                // If left bracket push token on to stack
                case "(":
                    tokenStack.push(token);
                    break;
                case ")":
                    while (tokenStack.peek() != null && !tokenStack.peek().equals("("))
                    {
                        reverse_polish.add( tokenStack.pop() );
                    }
                    tokenStack.pop();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                case "^":
                case "sqrt":
                case "root":
                case "ncr":
                    // While stack
                    // not empty AND stack top element
                    // and is an operator
                    while (tokenStack.size() > 0 && isOperator(tokenStack.peek()))
                    {
                        if ( isPrecedent(token, tokenStack.peek() )) {
                            reverse_polish.add(tokenStack.pop());
                            continue;
                        }
                        break;
                    }
                    // Push the new operator on the stack
                    tokenStack.push(token);
                    break;
                default: 
                    try
                    {
                        Double.parseDouble(token);
                    }
                    catch(NumberFormatException e)
                    {
                        // Resolve variable to 0 in order for the rest of the function to successfully run.
                        this.reverse_polish.add("0");
                        this.expression = "Parsing error \'" + this.expression + "\'. Check the validity of your expression and try again.";
                        break;
                    }
                    this.reverse_polish.add(token);
            }
        }
        // Empty remaining tokens
        while (tokenStack.size() > 0) {
            reverse_polish.add(tokenStack.pop());
        }
    }

    // Takes RPN and produces a final result
    private void rpnToResult()
    {
        try {
            // stack is used to hold operands and each calculation
            Stack<Double> calcStack = new Stack<Double>();

            // RPN is processed, ultimately calcStack has final result
            for (String token : this.reverse_polish)
            {
                // If the token is an operator, calculate
                if (isOperator(token))
                {
                                
                    // Store the top two entries into variables x and y then pop them
                    double x = calcStack.pop();
                    double y = calcStack.pop();

                    // Calculate intermediate results
                    switch (token) {
                        // Variable y should be on the left of each computation
                        case "+":
                            result = y + x;
                            break;
                        case "-":
                            result = y - x;
                            break;
                        case "*":
                            result = y * x;
                            break; 
                        case "/":
                            result = y / x;
                            break;
                        case "%":
                            result = y % x;
                            break;
                        case "^":
                            result = Math.pow(y, x);
                            break;
                        case "root":
                            result = Math.pow(x, 1/y);
                            break;
                        case "sqrt":
                            result = Math.pow(y, 1/2);
                            break;
                        case "ncr":
                            int nFac = 1;
                            for (int i = 1; i <= y; i++) {
                                nFac = nFac * i;
                            }
                            int rFac = 1;
                            for (int i = 1; i <= x; i++) {
                                rFac = rFac * i;
                            }
                            int nMinusRFac = 1;
                            for (int i = 1; i <= y-x; i++) {
                                nMinusRFac=  nMinusRFac * i;
                            }
                            result = (double) nFac/(rFac * nMinusRFac);
                            break;
                        default:
                            break;
                    }

                    // Push intermediate result back onto the stack
                    calcStack.push( result );
                }
                // else the token is a number push it onto the stack
                else
                {
                    calcStack.push(Double.valueOf(token));
                }
            }
            // Pop final result and set as final result for expression
            this.result = calcStack.pop();
        } catch (Exception e) {
            System.out.println("Something went wrong");
            if (isBalanced(tokens) == false) {
                System.out.println("Error: The number of delimiters are not balanced!");
            }
            System.out.println("");
        }
    }

    public String toString() {
        return ( "{ \"expression\": "  + this.expression +  ", " + "\"tokens\": "  + this.tokens.toString() + ", " + "\"reverse_polish\": "  + this.reverse_polish.toString() +
        ", " + "\"result\": "  + String.format("%.2f", this.result) + ", " + "\"isBalanced\": "  + isBalanced(this.tokens) + " }"); 
    }
    
    public boolean isBalanced(ArrayList<String> tokens) {
        // Counters for the number of open delimiters and close delimiters
        int openCount = 0;
        int closeCount = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("(")) {
                openCount++;
            } else if (tokens.get(i).equals(")")) {
                closeCount++;
            }

            // First condition is broken
            if (openCount < closeCount) {
                return false;
            }
        }
        // First and second condition are both met
        if (openCount == closeCount) {
            return true;
        } 
        
        // Second condition is broken
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        Calculator myCalculator1 = new Calculator("1 + 2 * 4");
        System.out.println("First Calculator Example:");
        System.out.println("-------------------------");
        System.out.println("Original Expression: " + myCalculator1.expression);
        System.out.println("Tokens ArrayList: " + myCalculator1.tokens);
        System.out.println("Reverse Polish Notation: " + myCalculator1.reverse_polish);
        System.out.println("Calculator Output: " + myCalculator1.result);
        System.out.println("");

        Calculator myCalculator2 = new Calculator("(1 + 2) * (8 / 2)");
        System.out.println("Second Calculator Example:");
        System.out.println("-------------------------");
        System.out.println("Original Expression: " + myCalculator2.expression);
        System.out.println("Tokens ArrayList: " + myCalculator2.tokens);
        System.out.println("Reverse Polish Notation: " + myCalculator2.reverse_polish);
        System.out.println("Calculator Output: " + myCalculator2.result);
        System.out.println("");

        Calculator myCalculator3 = new Calculator("3 ^ 4 + (8 % 3)");
        System.out.println("Third Calculator Example:");
        System.out.println("-------------------------");
        System.out.println("Original Expression: " + myCalculator3.expression);
        System.out.println("Tokens ArrayList: " + myCalculator3.tokens);
        System.out.println("Reverse Polish Notation: " + myCalculator3.reverse_polish);
        System.out.println("Calculator Output: " + myCalculator3.result);
        System.out.println("");

        Calculator myCalculator4 = new Calculator("(3 root 8 ^ 2");
        System.out.println("Fourth Calculator Example:");
        System.out.println("-------------------------");
        System.out.println("Original Expression: " + myCalculator4.expression);
        System.out.println("Tokens ArrayList: " + myCalculator4.tokens);
        System.out.println("Reverse Polish Notation: " + myCalculator4.reverse_polish);
        System.out.println("Calculator Output: " + myCalculator4.result);
        System.out.println("");

        Calculator myCalculator5 = new Calculator("8 ncr 3");
        System.out.println("Fifth Calculator Example:");
        System.out.println("-------------------------");
        System.out.println("Original Expression: " + myCalculator5.expression);
        System.out.println("Tokens ArrayList: " + myCalculator5.tokens);
        System.out.println("Reverse Polish Notation: " + myCalculator5.reverse_polish);
        System.out.println("Calculator Output: " + myCalculator5.result);
        System.out.println("");
    }
}
