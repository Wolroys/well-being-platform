package com.wolroys.wellbeing.domain.user.entity;

import com.wolroys.wellbeing.domain.question.entity.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @NotEmpty(message = "The last name must not be empty")
    private String lastName;

    @Email
    private String email;

    @NotEmpty(message = "The password must not be empty")
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    public String getFullName() {
        return name + " " + lastName;
    }
}
