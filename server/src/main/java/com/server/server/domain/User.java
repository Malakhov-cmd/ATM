package com.server.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private @Id @GeneratedValue Long id;
    private String userName;
    private String password;

    @OneToMany(mappedBy = "user_id")
    private Set<Card> cards;

}
