package com.maction.springmvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime updated;
    private String password;
    private boolean active;
    private boolean isAdmin;
    private boolean isDeleted;

    @Column(length = 32)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();


    public User(Long id) {
        this.id = id;
    }
}
