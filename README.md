# ATM
___
**Main info**

An application that simulates the operation of a banking terminal

**Technical task**  

>Build a model for a client to receive a balance on a card at an ATM.
The domain model classes need not know anything about the technical environment. Details
the technical environment must be hidden behind layers of abstraction.
>
>Maven must be used as the build system. In the final version, the application should
be 2 Spring Boot applications. One will act as a client / ATM,
and the other is the server role. An application with the server role must store its data in an H2 database.
Applications must use REST to communicate. Modules must be covered by JUnit tests.
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
@RequiredArgsConstructor
@Data
public class CardDTO {
    private Long number;
    private String dateValid;
    private String owner;
    private String CVV;

    private Double balance;

    private String username;

    List<OperationDTO> operationDTOList;
}
```

OperationDTO - an instance of an object representing an operation is required to generate history and collect statistics for the analysis of expenses and expenses.

```java
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDTO {
    private Long cardNumber;
    private String type;
    private String username;
    private Double value;

    private Date time;
}
```

**Application properties**

Client start on - http://localhost:9000

Server starts on - http://localhost:9090

To enable the frontend side don't forget to run the ``` npm start``` command.