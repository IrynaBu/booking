create table users
(
    id       bigserial,
    username varchar(50) not null,
    email    varchar(50) not null,
    constraint pk_users primary key (id)
);
-- just for example
alter table users owner to postgres;
comment on table users is 'Список зарегистрированных пользователей';
comment on column users.id is 'Первичный ключ';

create table tickets
(
    id        bigserial,
    place     integer not null,
    filmDate  timestamp  default current_timestamp,
    film      varchar(50) not null,
    constraint pk_tickets primary key (id)
);

create table orders
(
    id        bigserial,
    userId    integer not null,
    ticketId  integer not null,
    orderDate timestamp  default current_timestamp,
    constraint pk_user_to_ticket primary key (id),
--     в этом случае мы не сможем сохранить в базу повторно один и тот же билет
    unique (ticketId),
    constraint fk_userId
        foreign key (userId)
            references users(id),
    constraint fk_ticketId
        foreign key (ticketId)
            references tickets(id)
);

insert into tickets (id, place, film)
values (1, 10, 'One'),
       (2, 11, 'Two'),
       (3, 12, 'Three'),
       (4, 13, 'Four');

insert into users (id, email, username)
values (1, '1@email.com', 'John One'),
       (2, '2@email.com', 'John Two'),
       (3, '3@email.com', 'John Three'),
       (4, '4@email.com', 'John Four');