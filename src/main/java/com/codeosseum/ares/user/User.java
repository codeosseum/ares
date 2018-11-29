package com.codeosseum.ares.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private Set<Role> roles;
}
