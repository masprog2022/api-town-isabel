package com.masprogtechs.apitownisabel.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdateDto {

    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 5, max = 100)
    private String username;

    @NotBlank
    private String password;

    private String role;
}
