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