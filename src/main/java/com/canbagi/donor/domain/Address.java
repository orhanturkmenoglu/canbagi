package com.canbagi.donor.domain;

import com.canbagi.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "address")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address  extends BaseEntity {

    private String country;
    @Column(nullable = false)
    private String city;
    private String district;
    private String street;
    private String postalCode;
}
