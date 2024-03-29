package com.nighthawk.spring_portfolio.mvc.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CalculatorObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String owner;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "operators")
    @Column(name = "result")
    private final Map<String, Integer> OPERATORS = new HashMap<>();
    {
        // Map<"token", precedence>
        OPERATORS.put("(", 0);
        OPERATORS.put(")", 0);
        OPERATORS.put("^", 1);
        OPERATORS.put("root", 2);
        OPERATORS.put("sqrt", 2);
        OPERATORS.put("ncr", 2);
        OPERATORS.put("sin", 2);
        OPERATORS.put("cos", 2);
        OPERATORS.put("tan", 2);
        OPERATORS.put("ln", 2);
        OPERATORS.put("sinh", 2);
        OPERATORS.put("cosh", 2);
        OPERATORS.put("tanh", 2);
        OPERATORS.put("*", 3);
        OPERATORS.put("/", 3);
        OPERATORS.put("%", 3);
        OPERATORS.put("+", 4);
        OPERATORS.put("-", 4);
    }

    private ArrayList<String> tokens = new ArrayList<String>();
    private ArrayList<String> operators = new ArrayList<String>();
    private String expression;
    private double finalAnswer;

    public CalculatorObject(String expressionInput, String username) {
        expression = expressionInput;
        owner = username;
        finalAnswer = calculate(expression);
    }

    public String getUsername() {
        return owner;
    }

    public double setFinalAnswer(String expressionInput) {
        finalAnswer = calculate(expressionInput);
        return finalAnswer;
    }

    public String setDouble(String expressionInput) {
        expression = expressionInput;
        return expression;
    }

    public void updateOperators(ArrayList<String> tokensInput) {
        for (int i = 0; i < tokensInput.size(); i++) {
            if (OPERATORS.get(tokensInput.get(i)) != null) {
                if (operators.size() == 0) {
                    operators.add(tokensInput.get(i));
                } else {
                    for (int j = 0; j < operators.size(); j++) {
                        if (OPERATORS.get(tokensInput.get(i)) < OPERATORS.get(operators.get(j))) {
                            operators.add(j, tokensInput.get(i));
                            break;
                        } else if (j == operators.size() - 1) {
                            operators.add(tokensInput.get(i));
                            break;
                        }
                    }
                }
            }
        }
    }

    public double calculate(String expression) {
        tokens.clear();
        operators.clear();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) != ' ') {
                if (i == expression.length() - 1) {
                    tokens.add(expression.substring(i, i + 1));
                } else  {
                    boolean isOperator = OPERATORS.get(expression.substring(i, i+1)) != null;
                    if (isOperator) {
                        if (expression.charAt(i) == '-' && expression.charAt(i + 1) != ' ') {
                            for (int j = i + 1; j < expression.length(); j++) {
                                if (expression.charAt(j) == ' ' || OPERATORS.get(expression.substring(j, j+1)) != null) {
                                    tokens.add(expression.substring(i, j));
                                    i = j - 1;
                                    break;
                                } else if (j == expression.length() - 1) {
                                    tokens.add(expression.substring(i, j + 1));
                                    break;
                                }
                            }
                        } else {
                            for (int j = i; j < expression.length(); j++) {
                                if (expression.charAt(j) == ' ' || OPERATORS.get(expression.substring(j, j+1)) == null) {
                                    tokens.add(expression.substring(i, j));
                                    i = j - 1;
                                    break;
                                }
                            }
                        }
                    } else {
                        for (int x = i; x < expression.length(); x++) {
                            if (expression.charAt(x) == ' ' || OPERATORS.get(expression.substring(x, x+1)) != null) {
                                tokens.add(expression.substring(i, x));
                                i = x - 1;
                                break;
                            } else if (x == expression.length() - 1) {
                                tokens.add(expression.substring(i, x + 1));
                                break;
                            }
                        }
                    }
                }
            }
        }

        updateOperators(tokens);

        return calculation();
    }

    public double calculation() {
        for (int x = 0; x < operators.size(); x++) {
            double computation;
            for (int y = 0; y < tokens.size(); y++) {
                if (tokens.get(y).equals(operators.get(x))) {
                    switch (tokens.get(y)) {
                        case "+":
                            try {
                                computation = Double.parseDouble(tokens.get(y-1)) + Double.parseDouble(tokens.get(y+1));
                                Double newComputation = computation;
                                tokens.set(y, Double.toString(newComputation));
                                tokens.remove(y-1);
                                tokens.remove(y);
                                y = -1;
                            } catch (Exception e) {
                                System.out.println("Computational Error");
                            }
                            break;
                        case "-":
                            try {
                                computation = Double.parseDouble(tokens.get(y-1)) - Double.parseDouble(tokens.get(y+1));
                                Double newComputation = computation;
                                tokens.set(y, Double.toString(newComputation));
                                tokens.remove(y-1);
                                tokens.remove(y);
                                y = -1;
                            } catch (Exception e) {
                                System.out.println("Computational Error");
                            }
                            break;
                        case "*":
                            try {
                                computation = Double.parseDouble(tokens.get(y-1)) * Double.parseDouble(tokens.get(y+1));
                                Double newComputation = computation;
                                tokens.set(y, Double.toString(newComputation));
                                tokens.remove(y-1);
                                tokens.remove(y);
                                y = -1;
                            } catch (Exception e) {
                                System.out.println("Computational Error");
                            }
                            break;
                        case "/":
                            try {
                                computation = Double.parseDouble(tokens.get(y-1)) / Double.parseDouble(tokens.get(y+1));
                                Double newComputation = computation;
                                tokens.set(y, Double.toString(newComputation));
                                tokens.remove(y-1);
                                tokens.remove(y);
                                y = -1;
                            } catch (Exception e) {
                                System.out.println("Computational Error");
                            }
                            break;
                    }
                }
            }
        }
        
        finalAnswer = Double.parseDouble(tokens.get(0));
        return finalAnswer;
    }

    public static void main(String[] args) {
        CalculatorObject test = new CalculatorObject("1 + 2 * 2", "test");
        System.out.println(test);
        System.out.println(test.finalAnswer);
    }
}
