package com.canbagi.donor.domain;


import com.canbagi.user.domain.User;
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
@Table(name = "donor_profile")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    private String phone;

    private String bloodType;

    private Boolean active;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address Address;

    @CreationTimestamp
    private Instant createdDate ;

    @UpdateTimestamp
    private Instant lastModifiedDate;
}
