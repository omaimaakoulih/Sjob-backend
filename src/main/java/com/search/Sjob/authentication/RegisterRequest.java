package com.search.Sjob.authentication;

import com.search.Sjob.model.Country;
import com.search.Sjob.model.Gender;
import com.search.Sjob.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Country country;
    private String phone;
    private Gender gender;
    private String password;
    private Role role;
}
