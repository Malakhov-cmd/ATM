package com.server.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @JsonIgnore
    private @Id @GeneratedValue Long id;
    private String userName;
    private String password;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Card> cards;
}
