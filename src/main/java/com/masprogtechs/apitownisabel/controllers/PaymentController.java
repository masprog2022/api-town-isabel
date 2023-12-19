package com.masprogtechs.apitownisabel.controllers;

import com.masprogtechs.apitownisabel.dtos.PaymentCreateDto;
import com.masprogtechs.apitownisabel.dtos.UserCreateDto;
import com.masprogtechs.apitownisabel.dtos.UserResponseDto;
import com.masprogtechs.apitownisabel.mapper.UserMapper;
import com.masprogtechs.apitownisabel.models.ErrorMessage;
import com.masprogtechs.apitownisabel.models.Payment;
import com.masprogtechs.apitownisabel.models.User;
import com.masprogtechs.apitownisabel.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/payments")
@Tag(name = "Pagamento", description = "Endpoints para gerenciar Pagamento" )
public class PaymentController {

    private final PaymentService paymentService;


    @Operation(summary = "Efectuar pagamento", description = "Efectuar pagamento. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento efectuado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid PaymentCreateDto paymentCreateDto) {

        Payment payment = paymentService.makePayment(paymentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
