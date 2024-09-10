package com.dayliBlog.models;

import com.dayliBlog.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String username;
    private String profilePic = "";
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostModel> posts;
    @Enumerated(EnumType.STRING)
    private Role role;
}
