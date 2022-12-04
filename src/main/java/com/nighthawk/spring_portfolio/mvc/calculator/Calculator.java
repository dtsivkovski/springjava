package com.nighthawk.spring_portfolio.mvc.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.lang.Math;

/* In mathematics,
    an expression or mathematical expression is a finite combination of symbols that is well-formed
    according to rules that depend on the context.
   In computers,
    expression can be hard to calculate with precedence rules and user input errors
    to handle computer math we often convert strings into reverse polish notation
    to handle errors we perform try / catch or set default conditions to trap errors
     */
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
        OPERATORS.put("RT", 1);
        OPERATORS.put("^", 2);
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
                case "RT":
                case "+":
                case "-":
                case "*":
                case "/":
                case "%":
                case "^":
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
                case "pi":
                case "PI":
                case "Pi":
                    // recognize pi variable and replace that token with it
                    this.reverse_polish.add("3.141592653589793238");
                    break;
                case "g":
                case "G":
                    // Added a g value to represent earth's gravitational force
                    this.reverse_polish.add("9.8");
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
                        this.expression = "Error with parsing your expression \'" + this.expression + "\'. Please enter valid numbers, operators, or variables and try again.";
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
        // stack is used to hold operands and each calculation
        Stack<Double> calcStack = new Stack<Double>();

        // RPN is processed, ultimately calcStack has final result
        for (String token : this.reverse_polish)
        {
            // If the token is an operator, calculate
            if (isOperator(token))
            {
                              
                // Pop the top two entries
                double a = calcStack.pop();
                double b = calcStack.pop();

                // Calculate intermediate results
                switch (token) {
                    // b goes first, as it is popped second and must be on the left to make the equation work
                    case "RT":
                        // rt is the only exception as the first value is the value of the root being done to the second value
                        result = Math.pow(a, (1/b));
                        break;
                    case "+":
                        result = b + a;
                        break;
                    case "-":
                        result = b - a;
                        break;
                    case "*":
                        result = b * a;
                        break; 
                    case "/":
                        result = b / a;
                        break;
                    case "%":
                        result = b % a;
                        break;
                    case "^":
                        // Using Math.pow() function because it supports doubles
                        result = Math.pow(b,a);
                        break;
                    default:
                        break;
                }

                // Pop the two top entries


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
    }

    public String calcToString(boolean x) {
        if (x) {
        System.out.println("--------");
        System.out.println("Result: " + this.expression + " = " + this.result);
        System.out.println("Tokens: " + this.tokens + " , RPN: " + this.reverse_polish);
        }

        String output = this.expression + " = " + this.result;
        return output;
    }

    public String jsonify() {
        String json = "{ \"Expression\": \"" + this.expression + "\", \"Tokens\": \"" + this.tokens + "\", \"RPN\": \"" + this.reverse_polish + "\", \"Result\": " + this.result + " }";
        return json;
    }
    
}