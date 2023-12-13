package com.masprogtechs.apitownisabel.controllers;

import com.masprogtechs.apitownisabel.dtos.UserCreateDto;
import com.masprogtechs.apitownisabel.dtos.UserResponseDto;
import com.masprogtechs.apitownisabel.dtos.UserUpdateDto;
import com.masprogtechs.apitownisabel.mapper.UserMapper;
import com.masprogtechs.apitownisabel.models.ErrorMessage;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
@Tag(name = "Usuário", description = "Endpoints para gerenciar usuário" )
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os usuários cadastrados", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    public ResponseEntity<List<UserResponseDto>> getAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('MORADOR') AND #id == authentication.principal.id)")
    @Operation(summary = "Listar um usuário pelo id", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CUSTOMER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }


    @Operation(summary = "Criar um novo usuário", description = "Criar um novo usuário. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto createDto){
        User user = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }
    @Operation(summary = "Listar usuário pelo username", description = "Listar usuário pelo username" +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário listado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Apagar um usuário", description = "Apagar um usuário" +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário apagado com sucesso",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Actualizar um usuário", description = "Actualizar um usuário. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário actualizado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> updaterUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto){
        User user = userService.update(UserMapper.toUpdateUser2(id, userUpdateDto));
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

}
