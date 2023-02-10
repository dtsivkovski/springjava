package com.nighthawk.spring_portfolio.mvc.calculator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorAPIController {

    @GetMapping("/{expression}")
    public ResponseEntity<String> getResult(@PathVariable String expression) {

        // Returns jsonified result of expression with tokens and everything
        Calculator a = new Calculator(expression);
        String result = a.jsonify();
        if (result != null) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        }

        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

}
