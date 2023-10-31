package com.example.securitytest.dtos.user;


import com.example.securitytest.dtos.ICommonDto;
import com.example.securitytest.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPatchForm implements ICommonDto<User> {
    private String name;
    private String username;
    private String email;

    @Size(min = 8)
    @Pattern(regexp = User.PASSWORD_PATTERN, message = "Password should have a minimum of 8 characters long and includes at least one uppercase letter, one number, and one special character.")
    private String password;

    public UserPatchForm(User user) {
        this.setName(user.getName());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
    }

    public User toEntity() {
        User user = new User();
        user.setUsername(this.getUsername());
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        return user;
    }
}
