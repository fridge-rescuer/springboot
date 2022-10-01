package com.fridgerescuer.springboot.data.entity;

import com.fridgerescuer.springboot.security.entity.Authority;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.Set;

@Document(collection = "member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Member {

    @Id
    private String id;
    private String password;
    private String name;

    @DocumentReference
    private List<ExpirationData> expirationDataList;
    @DocumentReference
    private List<PrivateExpirationData> privateExpirationDataList;

    @DocumentReference
    private List<Recipe> recipes;

    @DocumentReference
    private List<Comment> comments;

    @DocumentReference
    private Set<Authority> authorities;

    private boolean activated;
}
