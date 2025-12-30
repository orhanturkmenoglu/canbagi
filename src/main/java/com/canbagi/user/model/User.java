package com.canbagi.user.model;

import com.canbagi.common.base.BaseEntity;
import com.canbagi.user.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String profileImage;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,unique = true,length = 20)
    private String phone;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="user_roles",
            joinColumns=@JoinColumn(name="user_id")
    )
    private List<ERole> roles;


}
