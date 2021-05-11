package com.javohir.task2.controller;

import com.javohir.task2.entity.Category;
import com.javohir.task2.entity.StarBadge;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.payload.CategoryDTO;
import com.javohir.task2.payload.StarBadgeDTO;
import com.javohir.task2.service.StarBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/category")
public class StarBadgeController {

    @Autowired
    StarBadgeService starBadgeService;


    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody StarBadgeDTO starBadgeDTO) {
        ApiResponse add = this.starBadgeService.addOne(starBadgeDTO);
        if (add.isStatus()) {
            return ResponseEntity.status(202).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<StarBadge> getAll() {
        return starBadgeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        ApiResponse apiResponse = starBadgeService.getOne(id);
        if (apiResponse.isStatus()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
        ApiResponse apiResponse = starBadgeService.deleteOne(id);
        if (apiResponse.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOne(@Valid @PathVariable Integer id, @RequestBody StarBadgeDTO starBadgeDTO) {
        ApiResponse apiResponse = starBadgeService.editOne(id, starBadgeDTO);
        if (apiResponse.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
