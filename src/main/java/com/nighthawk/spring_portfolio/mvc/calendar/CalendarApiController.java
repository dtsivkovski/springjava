package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Calendar API
 * Calendar Endpoint: /api/calendar/isLeapYear/2022, Returns: {"year":2020,"isLeapYear":false}
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarApiController {

    /** GET isLeapYear endpoint
     * ObjectMapper throws exceptions on bad JSON
     *  @throws JsonProcessingException
     *  @throws JsonMappingException
     */
    @GetMapping("/isLeapYear/{year}")
    public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable int year) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Year year_obj = new Year();
      year_obj.setYear(year);  // evaluates Leap Year

      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper(); 
      JsonNode json = mapper.readTree(year_obj.isLeapYearToString()); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }


    @GetMapping("/firstDayOfYear/{year}")
    public ResponseEntity<JsonNode> firstDayOfYear(@PathVariable int year) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Year year_obj = new Year();
      year_obj.setYear(year);  // evaluates Leap Year

      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(year_obj.firstDayOfYearToString(year)); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }

    @GetMapping("/dayOfYear/{month}/{day}/{year}")
    public ResponseEntity<JsonNode> firstDayOfYear(@PathVariable int month, @PathVariable int day, @PathVariable int year) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Year year_obj = new Year();
      year_obj.setYear(year);  // sets year

      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(year_obj.dayOfYearToString(month,day,year)); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }

    @GetMapping("/numberOfLeapYears/{year1}/{year2}")
    public ResponseEntity<JsonNode> numberOfLeapYears(@PathVariable int year1, @PathVariable int year2) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Year year_obj = new Year();
      year_obj.setYear(year1);  // evaluates Leap Year

      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(year_obj.numLeapYearToString(year1,year2)); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }

    @GetMapping("/dayOfWeek/{month}/{day}/{year}")
    public ResponseEntity<JsonNode> dayOfWeek(@PathVariable int month, @PathVariable int day, @PathVariable int year) throws JsonMappingException, JsonProcessingException {
      // Backend Year Object
      Year year_obj = new Year();
      year_obj.setYear(year);  // evaluates Leap Year

      // Turn Year Object into JSON
      ObjectMapper mapper = new ObjectMapper();
      JsonNode json = mapper.readTree(year_obj.dayOfWeekToString(month,day,year)); // this requires exception handling

      return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }

}