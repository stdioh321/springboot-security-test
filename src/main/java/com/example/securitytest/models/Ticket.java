package com.example.securitytest.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String field01;

    @Column(nullable = false)
    private String field02;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    public void onCreate() {
        var d = Date.from(Instant.now());
        this.createdAt = d;
        this.updatedAt = d;

        if (this.getName() != null) this.setName(this.getName().toLowerCase());
    }

    @PreUpdate
    public void onUpdate() {
        var d = Date.from(Instant.now());
        this.updatedAt = d;
        if (this.getName() != null) this.setName(this.getName().toLowerCase());
    }
}
