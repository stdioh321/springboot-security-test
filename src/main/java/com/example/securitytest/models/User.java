package com.example.securitytest.models;

import com.example.securitytest.common.Utils;
import com.example.securitytest.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_users")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private UserRole role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    public void onCreate() {
        handleSetTimesOnCreateOrUpdate(true);
        handleSetsOnCreateOrUpdate();
    }

    @PreUpdate
    public void onUpdate() {
        handleSetTimesOnCreateOrUpdate();
        handleSetsOnCreateOrUpdate();
    }


    private void handleSetsOnCreateOrUpdate() {
        if (this.getEmail() != null) this.setEmail(this.getEmail().toLowerCase().trim());
        if (this.getUsername() != null) this.setUsername(this.getUsername().toLowerCase().trim());
    }

    private void handleSetTimesOnCreateOrUpdate() {
        this.handleSetTimesOnCreateOrUpdate(false);
    }

    private void handleSetTimesOnCreateOrUpdate(boolean createdAt) {
        Date d = Utils.getCurrentDate();
        this.setUpdatedAt(d);
        if (createdAt == true) this.setCreatedAt(d);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.getRole() == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
