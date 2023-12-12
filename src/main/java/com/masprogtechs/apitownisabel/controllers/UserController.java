package com.masprogtechs.apitownisabel.controllers;

import com.masprogtechs.apitownisabel.dtos.UserCreateDto;
import com.masprogtechs.apitownisabel.dtos.UserResponseDto;
import com.masprogtechs.apitownisabel.mapper.UserMapper;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto createDto){
        User user = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }


}
