package com.canbagi.donor.domain;


import com.canbagi.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "donor_profile")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonorProfile extends BaseEntity {

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
    private Address address;
}
