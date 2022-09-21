package com.fridgerescuer.springboot.security.dto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDTO {
    private String authorityName;
}