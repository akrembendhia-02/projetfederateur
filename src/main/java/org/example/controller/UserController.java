package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.example.dto.CreateUserRequest;
import org.example.dto.UserResponse;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved users"
    )
    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user using the provided user details"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
    )
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody CreateUserRequest request) {

        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

