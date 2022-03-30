# ATM

___
**Main info**

An application that simulates the operation of a banking terminal

**Technical task**

> Build a model for a client to receive a balance on a card at an ATM. The domain model classes need not know anything about the technical environment. Details the technical environment must be hidden behind layers of abstraction.
>
>Maven must be used as the build system. In the final version, the application should be 2 Spring Boot applications. One will act as a client / ATM, and the other is the server role. An application with the server role must store its data in an H2 database. Applications must use REST to communicate. Modules must be covered by JUnit tests.
>
>An application with a server role must require login and password authentication using Spring Security.
>
>As a 5+ challenge, applications should be able to switch to communicating through Apache Kafka.

**About project**

Program components:

+ Server - responsible for database management.
+ Client - responsible for validating and organizing the formation of requests to the server.
+ Frontend - responsible for the graphical presentation and dynamic update of the data presented to the user.

**Data transfer objects**

UserDTO - an instance of an object representing a system user.

```java

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String password;

    private Set<CardDTO> cards;
}
```

CardDTO - an instance of a bank card object with the appropriate attributes.

```java
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO {
    private long number;
    private short dateValid;
    private String owner;
    private short CVV;

    private double balance;

    private String username;

    List<OperationDTO> operationDTOList;
}
```

OperationDTO - an instance of an object representing an operation is required to generate history and collect statistics
for the analysis of expenses and expenses.

```java

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO {
    private long cardNumber;
    private String type;
    private String username;
    private double value;

    private Date time;
}
```

**Sql script**

```postgresql
create sequence hibernate_sequence start 1 increment 1;

create table card
(
    id         int8   not null,
    cvv        int2   not null,
    balance    float8 not null,
    date_valid int2   not null,
    number     int8,
    owner      varchar(128),
    user_id    int8   not null,
    primary key (id)
);
create table operation
(
    id          int8   not null,
    card_number int8   not null,
    time        timestamp,
    type        varchar(8),
    username    varchar(128),
    value       float8 not null,
    card_id     int8   not null,
    primary key (id)
);
create table usr
(
    id        int8 not null,
    password  varchar(128),
    user_name varchar(128),
    primary key (id)
);

alter table if exists card
    add constraint Card_To_User_FK foreign key (user_id) references usr on delete cascade;
alter table if exists operation
    add constraint Operation_To_Card_FK foreign key (card_id) references card on delete cascade
```

**Application properties**

Client start on - http://localhost:9000

Server starts on - http://localhost:9090

Npm server start on -  http://localhost:8000

To test the application, an exact copy of the development environment database was prepared. To do this, the backend has two development profiles: ``` dev ``` and ``` test ```.

You can easily manage these modes by entering in ```ServerApplication -> Edit configuration -> Environment variables ``` value: ``` PROFILE=dev ``` to activate the development profile and ``` PROFILE=test ``` to run tests.

To see graphical reports on the results of the tests performed, open ```teminal ``` and enter the command ``` allure serve ```.

![img of test result]()

To enable the frontend side don't forget to run the ``` npm start``` command.