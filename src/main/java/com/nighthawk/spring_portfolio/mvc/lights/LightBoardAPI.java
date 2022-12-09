package com.nighthawk.spring_portfolio.mvc.lights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@RestController
@RequestMapping("/api/lightboard/")
public class LightBoardAPI {

    @GetMapping("/generate/{rows}/{cols}")
    public ResponseEntity<JsonNode> generateLightBoard(@PathVariable int rows, @PathVariable int cols) throws JsonMappingException, JsonProcessingException {
        LightBoard lightBoard = new LightBoard(rows, cols);

        // Create objectmapper to convert String to JSON
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode json = mapper.readTree(lightBoard.toString()); 

        return ResponseEntity.ok(json);

    }
    
}
