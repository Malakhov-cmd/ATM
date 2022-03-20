package com.server.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {
    private @Id @GeneratedValue Long id;

    private Long number;
    private String dateValid;
    private String owner;
    private String CVV;

    @ManyToOne()
    @JoinTable(name = "user_id")
    private User user_id;
}
