create table user
(

    id           bigint       not null auto_increment,
    email        varchar(255) not null,
    codigo       integer      not null,
    data_criacao datetime not null,

    primary key (id)
);

create table validation
(

    id             bigint       not null auto_increment,
    token          integer      not null,
    email          varchar(255) not null,
    data_validacao datetime not null,

    primary key (id)

);