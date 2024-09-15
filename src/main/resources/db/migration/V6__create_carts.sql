CREATE TABLE carts (
                       id bigserial not null,
                       user_id BIGINT not null references users(id),
                       primary key (id)
);

create table carts_products (
                       id bigserial not null,
                       product_id BIGINT not null references carts(id),
                       quantity BIGINT,
                       is_paid BOOLEAN,
                       primary key (id)
);
