package com.study.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(name = "login_id")
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    protected User() {

    }

    public static User create(String loginId, String name, String password) {
        return new User(null, loginId, name, password);
    }
}
