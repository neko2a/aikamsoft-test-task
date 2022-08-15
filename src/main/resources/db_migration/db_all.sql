create table if not exists customer
(
    id         serial
        constraint customer_pk
            primary key,
    first_name varchar not null,
    last_name  varchar not null
);

alter table customer
    owner to postgres;

create unique index customer_id_uindex
    on customer (id);

create table if not exists product
(
    id           serial
        constraint product_pk
            primary key,
    product_name varchar not null,
    price        integer not null
);

alter table product
    owner to postgres;

create unique index product_id_uindex
    on product (id);

create table if not exists purchase
(
    id            serial
        constraint purchase_pk
            primary key,
    product_id    integer not null
        constraint purchase_product_id_fk
            references product
            on update cascade on delete cascade,
    customer_id   integer not null
        constraint purchase_customer_id_fk
            references customer
            on update cascade on delete cascade,
    purchase_date date    not null
);

alter table purchase
    owner to postgres;

create unique index purchase_id_uindex
    on purchase (id);

INSERT INTO public.customer (id, first_name, last_name)
VALUES (1, 'Евген', 'Тутович');
INSERT INTO public.customer (id, first_name, last_name)
VALUES (2, 'Дмитрий', 'Здесович');
INSERT INTO public.customer (id, first_name, last_name)
VALUES (3, 'Александр', 'Тутович');
INSERT INTO public.customer (id, first_name, last_name)
VALUES (4, 'Евген', 'Тутович');

INSERT INTO public.product (id, product_name, price)
VALUES (2, 'Бутерброд', 120);
INSERT INTO public.product (id, product_name, price)
VALUES (1, 'Минеральная вода', 4499);
INSERT INTO public.product (id, product_name, price)
VALUES (3, 'Стол', 1729);

INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (1, 1, 2, '2022-08-02');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (3, 1, 1, '2022-08-12');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (4, 2, 1, '2022-07-04');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (5, 2, 2, '2022-08-14');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (6, 1, 1, '2022-08-10');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (7, 1, 1, '2022-08-09');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (8, 2, 1, '2022-08-12');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (9, 2, 1, '2022-08-10');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (10, 3, 1, '2022-08-10');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (11, 1, 4, '2022-08-12');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (12, 1, 4, '2022-08-12');
INSERT INTO public.purchase (id, product_id, customer_id, purchase_date)
VALUES (13, 1, 4, '2022-08-12');
