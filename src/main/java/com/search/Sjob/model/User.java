package com.search.Sjob.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_unique_email", columnNames = "email")
        }
)
@Data
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id", updatable = false)
    Integer id;
    @Column(name = "first_name", updatable = true)
    String firstName;
    @Column(name = "last_name", updatable = true)
    String lastName;
    @Column(name = "email", updatable = false)
    String email;
    @Column(name = "phone", updatable = true)
    String phone;
    @Column(name = "gender", updatable = true)
    Gender gender;
    @Column(name = "country", updatable = true)
    Country country;
    @Column(name = "password", updatable = true)
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    public User(){

    }

    public User(String firstName, String lastName, String email, Country country,
                String phone, Gender gender, String password, Role role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
