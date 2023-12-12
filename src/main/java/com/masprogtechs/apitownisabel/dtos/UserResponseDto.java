package com.masprogtechs.apitownisabel.dtos;


import com.masprogtechs.apitownisabel.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {

    private Long id;
    private String fullName;
    private String username;
    private String role;

}
