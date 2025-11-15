package com.canbagi.donor.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "address")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String country;
    @Column(nullable = false)
    private String city;
    private String district;
    private String street;
    private String state;
    private String postalCode;

    @CreationTimestamp
    private Instant createdDate ;

    @UpdateTimestamp
    private Instant lastModifiedDate;
}
