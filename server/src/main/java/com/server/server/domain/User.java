package com.server.server.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private @Id @GeneratedValue Long id;
    private String userName;
    private String password;

}
