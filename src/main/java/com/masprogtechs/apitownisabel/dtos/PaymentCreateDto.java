package com.masprogtechs.apitownisabel.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentCreateDto {

    private Long userId;
    private List<Long> monthIds;
    private Integer year;

}
