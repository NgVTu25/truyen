package com.search.truyen.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.search.truyen.enums.Userrole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "Username không được để trống")
    @Column(name = "username", nullable = false, length = 128)
    private String username;

    @Email(message = "Email không hợp lệ")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Userrole role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

}
