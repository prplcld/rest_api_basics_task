package com.epam.esm.restapibasics.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsDto {
    private String username;
    private String password;
    private String email;
}
