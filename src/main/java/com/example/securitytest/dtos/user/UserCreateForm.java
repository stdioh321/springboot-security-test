package com.example.securitytest.dtos.user;


import com.example.securitytest.dtos.ICommonDto;
import com.example.securitytest.enums.UserRole;
import com.example.securitytest.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateForm implements ICommonDto<User> {
    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = User.PASSWORD_PATTERN, message = "Password should have a minimum of 8 characters long and includes at least one uppercase letter, one number, and one special character.")
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private UserRole role;

    public UserCreateForm(User user) {
        this.setName(user.getName());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
    }

    public User toEntity() {
        User user = new User();
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        user.setRole(this.getRole());
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        return user;
    }



}
