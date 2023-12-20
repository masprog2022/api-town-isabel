package com.masprogtechs.apitownisabel.controllers;


import com.masprogtechs.apitownisabel.services.AttachedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/attachment/invoice")
@Tag(name = "Comprovativo", description = "Anexar comprovativo" )
public class AttachedController {

    private final AttachedService attachedService;

    @Operation(summary = "Anexar comprovativo pagamento", description = "Anexar comprovativo pagamento. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "anexada factura efectuado com sucesso",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> attachmentInvoice(@RequestParam("attached")MultipartFile file,@RequestParam("paymentId") Long paymentId ) throws IOException {
        String attached = attachedService.attachedInvoice(file, paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(attached);
    }

}
