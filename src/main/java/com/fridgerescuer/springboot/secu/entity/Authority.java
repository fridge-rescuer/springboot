package com.fridgerescuer.springboot.secu.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authority")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Authority {
    @Id
    private String authorityName;
}
