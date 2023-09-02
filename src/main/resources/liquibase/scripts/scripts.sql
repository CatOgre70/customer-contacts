-- liquibase formatted sql

-- changeset vasilydemin:1
CREATE TABLE customers (
    id bigserial primary key,
    name varchar(255)
);

CREATE TABLE emails (
    id bigserial primary key,
    customer_id bigint references customers(id) on delete cascade,
    email varchar(255)
);

CREATE TABLE phones (
    id bigserial primary key,
    customer_id bigint references customers(id) on delete cascade,
    phone varchar(255)
);