package com.server.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    private List<Operation> operations;
}
