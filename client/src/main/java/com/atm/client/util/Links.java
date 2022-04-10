package com.atm.client.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Links {
    createUser("http://localhost:9090/user/registration"),
    createCard("http://localhost:9090/card/create"),
    findAllUserCards("http://localhost:9090/card/get/all?username="),
    findUserCard("http://localhost:9090/card/get/?username="),
    createOperation("http://localhost:9090/operation/create");

    @Getter
    private String link;
}
