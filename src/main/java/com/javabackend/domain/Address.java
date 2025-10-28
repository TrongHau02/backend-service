package com.javabackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tbl_addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="apartment_number", length = 255)
    private String apartmentNumber;

    @Column(name="floor", length = 255)
    private String floor;

    @Column(name="building", length = 255)
    private String building;

    @Column(name="street_number", length = 255)
    private String streetNumber;

    @Column(name="street", length = 255)
    private String street;

    @Column(name="city", length = 255)
    private String city;

    @Column(name="country", length = 255)
    private String country;

    @Column(name="address_type", length = 255)
    private Integer addressType;

    @Column(name = "created_at", length = 255)
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", length = 255)
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "user_id",  length = 255)
    private Long userId;
}
