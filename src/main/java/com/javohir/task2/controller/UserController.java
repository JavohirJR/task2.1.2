package com.javohir.task2.controller;

import com.javohir.task2.entity.User;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.payload.UserDTO;
import com.javohir.task2.service.UserService;
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
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO userDTO) {
        ApiResponse add = userService.addOne(userDTO);
        if (add.isStatus()) {
            return ResponseEntity.status(202).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.getOne(id);
        if (apiResponse.isStatus()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.deleteOne(id);
        if (apiResponse.isStatus()) {
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOne(@Valid @PathVariable Integer id, @RequestBody UserDTO userDTO) {
        ApiResponse apiResponse = userService.editOne(id, userDTO);
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
