package com.example.securitytest.dtos.user;


import com.example.securitytest.dtos.ICommonDto;
import com.example.securitytest.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements ICommonDto<User> {
    private Long id;
    private String name;
    private String username;
    private String email;

    public UserDto(User user) {
        this.setId(user.getId());
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
