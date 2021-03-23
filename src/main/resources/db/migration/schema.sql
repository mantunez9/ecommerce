create table public.cart
(
    id_cart     serial    not null
        constraint pk_cart
            primary key,
    create_date timestamp not null
);

create table public.product
(
    id_cart     integer not null
        constraint product_id_cart references public.cart
            on update cascade on delete cascade,
    id_product  integer not null
        constraint pk_product
            primary key,
    description text,
    amount      double precision

);