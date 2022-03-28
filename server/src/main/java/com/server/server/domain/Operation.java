package com.server.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "operation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Operation {
    @JsonIgnore
    private @Id @GeneratedValue Long id;

    private long cardNumber;
    private String type;
    private String username;
    private double value;

    private Date time;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    @JsonIgnore
    private Card card;
}
