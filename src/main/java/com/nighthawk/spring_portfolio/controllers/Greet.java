package com.nighthawk.spring_portfolio.controllers;
/* MVC code that shows defining a simple Model, calling View, and this file serving as Controller
 * Web Content with Spring MVCSpring Example: https://spring.io/guides/gs/serving-web-con
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class Greet {

    // @GetMapping handles GET request for /greet, maps it to greeting() method
    @GetMapping("/greet")
    // @RequestParam handles variables binding to frontend, defaults, etc
    public String greeting(@RequestParam(name="num1", required=false, defaultValue="0") String num1, @RequestParam(name="num2", required=false, defaultValue="0") String num2, Model model) {

        // model attributes are visible to Thymeleaf when HTML is "pre-processed"
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        // Convert nums to int
        int int1 = Integer.parseInt(num1);
        int int2 = Integer.parseInt(num2);
        int sum =  int1 + int2;
        // add sum attribute
        // model.addAttribute("int1", int1);
        // model.addAttribute("int2", int2);
        model.addAttribute("sum", sum);

        // load HTML VIEW (greet.html)
        return "greet"; 

    }

}