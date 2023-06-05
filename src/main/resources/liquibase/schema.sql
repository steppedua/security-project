create table users
(
    id       bigserial
        primary key,
    password varchar(255),
    username varchar(255)
);

create table roles
(
    id   bigserial
        primary key,
    name varchar(255)
);

create table user_roles
(
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    primary key (user_id, role_id)
);


insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password)
values ('john', '12345'),
       ('mike', '12345');

insert into user_roles(role_id, user_id)
values (1,1),
       (2,1),
       (1,2);