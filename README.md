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

**Implementation**

Welcome page with the possibility of authorization through Google and Github.

![img of type login](https://i.ibb.co/jbzY3RT/welcompage.jpg)

Google login example.

![img of google login example](https://i.ibb.co/V2P09Cg/register.jpg)

Filling in bank card information.

![img of filling card info](https://i.ibb.co/71SYMB0/inputcard.jpg)

Created bank card.

![img of created card](https://i.ibb.co/pXhsTzh/createdcard.jpg)

Selecting the type of transaction and the amount for it.

![img of test how make operation](https://i.ibb.co/85bPrG1/createoperation.jpg)

Demonstration of the history of their change in the state of the account using a graph.

![img of test graphic of operations](https://i.ibb.co/fNqNjWM/historygraph.jpg)

Demonstration of the history of their change in the state of the account using a graph.

![img of test table of operations](https://i.ibb.co/GMHY7Bz/tablehistory.jpg)

**About project**

Program components:

+ Server - responsible for database management.
+ Client - responsible for validating and organizing the formation of requests to the server.
+ Frontend - responsible for the graphical presentation and dynamic update of the data presented to the user.
+ Message Broker - Responsible for passing and processing messages between two backend applications

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

+ Client start on - http://localhost:9000

+ Server starts on - http://localhost:9090

+ Apache Kafka starts on - http://localhost:9092

+ Npm server start on -  http://localhost:8000

**Different data bases**

To test the application, an exact copy of the development environment database was prepared. To do this, the backend has two development profiles: ``` dev ``` and ``` test ```.

You can easily manage these modes by entering in ```ServerApplication -> Edit configuration -> Environment variables ``` value: ``` PROFILE=dev ``` to activate the development profile and ``` PROFILE=test ``` to run tests.

**Test execution visualization**

To see graphical reports on the results of the tests performed, open ```teminal ``` and enter the command ``` allure serve ```.

Example of tets results:

![img of test result](https://i.ibb.co/WWqy4B0/Report.png)

To enable the frontend side don't forget to run the ``` npm start``` command.

**Server communication modes**

On the client side of the backend, you can choose how the servers interact:

1. **_Rest_** - classical interaction through controllers and exchange of objects by serializable means of spring.
2. **_Kafka_** - messaging system for specific topics between application servers.

To select the server communication mode, do the following ```ClientApplication -> Edit configuration -> Environment variables ``` and type: ```TypeOfSpringAppsCommunication=rest```
to select interaction based on the Rest principle or type ```TypeOfSpringAppsCommunication=kafka```
for interaction according to the Kafka broker principle.

**Convenient system start**

In order to make it easier for you to deal with the launch, I have prepared special scripts that you can find in the project root folder.

Run them in the following order:

1. ```start-zookeper.bat```,
2. ```start-kafka-server.bat```,
3. ```start-server.bat```,
4. ```start-client.bat```,
5. ```start-front-side.bat```

_Footnot_
  
start-kafka-server.bat:  
```
start zookeeper-server-start.bat C:/Program/kafka_2.13-3.1.0/config/zookeeper.properties
start C:/Program/kafka_2.13-3.1.0/bin/windows/kafka-server-start.bat C:/Program/kafka_2.13-3.1.0/config/server.properties
```

You will need to specify your paths to the file located in the folder with the installed Kafka application.

Configurations I use: 

zookeeper.properties

```editorconfig
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
# 
#    http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# the directory where the snapshot is stored.
dataDir=C:\Program\kafka_2.13-3.1.0\zookeeper-data
# the port at which the clients will connect
clientPort=5181
# disable the per-ip limit on the number of connections since this is a non-production config
maxClientCnxns=5
# Disable the adminserver by default to avoid port conflicts.
# Set the port to something non-conflicting if choosing to enable this
admin.enableServer=false
# admin.serverPort=8080
```

server.properties

```editorconfig
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# see kafka.server.KafkaConfig for additional details and defaults

############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
broker.id=0

############################# Socket Server Settings #############################

# The address the socket server listens on. It will get the value returned from 
# java.net.InetAddress.getCanonicalHostName() if not configured.
#   FORMAT:
#     listeners = listener_name://host_name:port
#   EXAMPLE:
#     listeners = PLAINTEXT://your.host.name:9092
#listeners=PLAINTEXT://:9092

# Hostname and port the broker will advertise to producers and consumers. If not set, 
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
#advertised.listeners=PLAINTEXT://your.host.name:9092

# Maps listener names to security protocols, the default is for them to be the same. See the config documentation for more details
#listener.security.protocol.map=PLAINTEXT:PLAINTEXT,SSL:SSL,SASL_PLAINTEXT:SASL_PLAINTEXT,SASL_SSL:SASL_SSL

# The number of threads that the server uses for receiving requests from the network and sending responses to the network
num.network.threads=3

# The number of threads that the server uses for processing requests, which may include disk I/O
num.io.threads=8

# The send buffer (SO_SNDBUF) used by the socket server
socket.send.buffer.bytes=102400

# The receive buffer (SO_RCVBUF) used by the socket server
socket.receive.buffer.bytes=102400

# The maximum size of a request that the socket server will accept (protection against OOM)
socket.request.max.bytes=104857600


############################# Log Basics #############################

# A comma separated list of directories under which to store log files
log.dirs=C:\Program\kafka_2.13-3.1.0\logs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
num.partitions=1

# The number of threads per data directory to be used for log recovery at startup and flushing at shutdown.
# This value is recommended to be increased for installations with data dirs located in RAID array.
num.recovery.threads.per.data.dir=1

############################# Internal Topic Settings  #############################
# The replication factor for the group metadata internal topics "__consumer_offsets" and "__transaction_state"
# For anything other than development testing, a value greater than 1 is recommended to ensure availability such as 3.
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1

############################# Log Flush Policy #############################

# Messages are immediately written to the filesystem but by default we only fsync() to sync
# the OS cache lazily. The following configurations control the flush of data to disk.
# There are a few important trade-offs here:
#    1. Durability: Unflushed data may be lost if you are not using replication.
#    2. Latency: Very large flush intervals may lead to latency spikes when the flush does occur as there will be a lot of data to flush.
#    3. Throughput: The flush is generally the most expensive operation, and a small flush interval may lead to excessive seeks.
# The settings below allow one to configure the flush policy to flush data after a period of time or
# every N messages (or both). This can be done globally and overridden on a per-topic basis.

# The number of messages to accept before forcing a flush of data to disk
#log.flush.interval.messages=10000

# The maximum amount of time a message can sit in a log before we force a flush
#log.flush.interval.ms=1000

############################# Log Retention Policy #############################

# The following configurations control the disposal of log segments. The policy can
# be set to delete segments after a period of time, or after a given size has accumulated.
# A segment will be deleted whenever *either* of these criteria are met. Deletion always happens
# from the end of the log.

# The minimum age of a log file to be eligible for deletion due to age
log.retention.hours=168

# A size-based retention policy for logs. Segments are pruned from the log unless the remaining
# segments drop below log.retention.bytes. Functions independently of log.retention.hours.
#log.retention.bytes=1073741824

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
log.retention.check.interval.ms=300000

############################# Zookeeper #############################

# Zookeeper connection string (see zookeeper docs for details).
# This is a comma separated host:port pairs, each corresponding to a zk
# server. e.g. "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002".
# You can also append an optional chroot string to the urls to specify the
# root directory for all kafka znodes.
zookeeper.connect=localhost:2181

# Timeout in ms for connecting to zookeeper
zookeeper.connection.timeout.ms=18000


############################# Group Coordinator Settings #############################

# The following configuration specifies the time, in milliseconds, that the GroupCoordinator will delay the initial consumer rebalance.
# The rebalance will be further delayed by the value of group.initial.rebalance.delay.ms as new members join the group, up to a maximum of max.poll.interval.ms.
# The default value for this is 3 seconds.
# We override this to 0 here as it makes for a better out-of-the-box experience for development and testing.
# However, in production environments the default value of 3 seconds is more suitable as this will help to avoid unnecessary, and potentially expensive, rebalances during application startup.
group.initial.rebalance.delay.ms=0

```