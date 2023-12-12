package com.masprogtechs.apitownisabel.controllers;

import com.masprogtechs.apitownisabel.dtos.UserCreateDto;
import com.masprogtechs.apitownisabel.dtos.UserResponseDto;
import com.masprogtechs.apitownisabel.mapper.UserMapper;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Usuário", description = "Endpoints para gerenciar usuário" )
public class UserController {

    private final UserService userService;

    @Operation(summary = "Criar um novo usuário", description = "Criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto createDto){
        User user = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }
    @Operation(summary = "Listar usuário pelo username", description = "Listar usuário pelo username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso listado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }


}
