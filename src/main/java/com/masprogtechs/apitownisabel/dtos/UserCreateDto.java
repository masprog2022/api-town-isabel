package com.masprogtechs.apitownisabel.dtos;

import com.masprogtechs.apitownisabel.enums.Role;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateDto {

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 5, max = 100)
    private String username;

    @NotBlank
    private String password;

    private String role;


}
