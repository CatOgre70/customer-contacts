-- liquibase formatted sql

-- changeset vasilydemin:1
CREATE TABLE customers (
    id bigserial primary key,
    name varchar(255)
);

CREATE TABLE emails (
    id bigserial primary key,
    customer_id bigint references customers(id),
    email varchar(255)
);

CREATE TABLE phones (
    id bigserial primary key,
    customer_id bigint references customers(id),
    phone varchar(255)
);