package com.javabackend.domain;

import com.javabackend.common.Gender;
import com.javabackend.common.UserStatus;
import com.javabackend.common.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "gender", length = 255)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private Date birthday;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "type", length = 255)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "status", length = 255)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "created_at", length = 255)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", length = 255)
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date updatedAt;


}
