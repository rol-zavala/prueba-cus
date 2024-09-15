CREATE TABLE users (
                       id bigserial not null,
                       email varchar(80),
                       username varchar(30),
                       password varchar(255),
                       name varchar(30),
                       last_name varchar(30),
                       num_id varchar(13),
                       phone_number integer,
                       birthday TIMESTAMP,
                       department_id BIGINT,
                       gender_id BIGINT,
                       is_enabled boolean,
                       primary key (id)
);

create table roles (
                       id bigserial not null,
                       name varchar(255),
                       primary key (id)
);

create table user_roles (
                            user_id bigint not null references users(id),
                            role_id bigint not null references roles(id),
                            primary key (user_id, role_id)
                        );

create table confirmation_user (
                                   token_id bigint not null,
                                   confirmation_token varchar(255),
                                   created_date timestamp(6),
                                   id bigint not null references users(id),
                                   primary key (token_id)
                               );


create table password_reset_token(
    token_id        bigserial not null,
    expiration_time timestamp(6),
    token           varchar(255),
    id              bigint references users (id),
    primary key (token_id)
);