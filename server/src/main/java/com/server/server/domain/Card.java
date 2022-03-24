package com.server.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Card {
    @JsonIgnore
    private @Id @GeneratedValue Long id;

    private Long number;
    private String dateValid;
    private String owner;
    private String CVV;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    @JsonIgnore
    private List<Operation> operations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return id != null && Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
