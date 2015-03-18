start transaction;
set STANDARD_CONFORMING_STRINGS =off;
set escape_string_warning=off;
set constraints all deferred;

drop table if exists users;

create table users(
        "id" bigint not null,
        "uuid" varchar(36) not null,
        "first_name" varchar(50) not null,
        "last_name" varchar(80) not null,
        "email" varchar(256) not null,
        "password" varchar(256) not null,
        "enabled" boolean not null default true,
        "created_date" timestamp with time zone not null default current_timestamp,
    primary key ("id")
);

insert into users("id", "uuid", "first_name", "last_name", "email", "password", "enabled") values
                        (1, '067e6162-3b6f-4ae2-a171-2470b63dff00', 'root', 'user', 'admin@chicagostatstracker.com', '9005848241c320b36126bfbe0e788bf480bfed428e4cacb94788935b0e5fa63a24ae4dc1d9a49b6d', true);

drop table if exists user_roles;

-- Two roles so far 'ROLE_ADMIN' and 'ROLE_USER'
create table user_roles(
        "id" bigint not null,
        "user_id" bigint not null,
        "role" varchar(45),
    primary key ("id"),
    unique ("user_id", "role"),
    foreign key ("user_id") references users("id")
);

insert into user_roles("id", "user_id", "role") values
                      (1, 1, 'ROLE_ADMIN');
                      
drop table if exists alert_queries;

create table alert_queries(
        "id" bigint not null,
        "user_id" bigint not null,
        "query" text,
        "notes" text,
        "startdate" timestamp without time zone not null,
        "enddate" timestamp without time zone,
        "enabled" boolean not null default true,
        "interval" bigint not null default 0 check ("interval" >= 0),
    primary key ("id"),
    foreign key ("user_id") references users("id")
);

insert into alert_queries("id", "user_id", "query", "notes", "startdate", "enddate", "enabled", "interval") values
                         (1, 1, 'https://soda.demo.socrata.com/resource/4tka-6guv?$where=magnitude > 3.0&source=pr',
                         'SOQL query example', '02/11/2015', '02/13/2015', true, 100000);

drop table if exists favorite_datasets;

create table favorite_datasets(
        "id" bigint not null,
        "user_id" bigint not null,
        "query" text,
        "notes" text,
        "created_date" timestamp with time zone not null default current_timestamp,
    primary key ("id"),
    foreign key ("user_id") references users("id")
);

insert into favorite_datasets("id", "user_id", "query", "notes") values
                         (1, 1, 'https://soda.demo.socrata.com/resource/4tka-6guv?$where=magnitude > 3.0&source=pr', 'SOQL query example');

commit;

start transaction;

create sequence user_id_seq;
select setval('user_id_seq', max(id)) from users;
alter table "users" alter column "id" set default nextval('user_id_seq');

create sequence user_role_seq;
select setval('user_role_seq', max(id)) from user_roles;
alter table "user_roles" alter column "id" set default nextval('user_role_seq');

create sequence alert_queries_seq;
select setval('alert_queries_seq', max(id)) from alert_queries;
alter table "alert_queries" alter column "id" set default nextval('alert_queries_seq');

create sequence favorite_datasets_seq;
select setval('favorite_datasets_seq', max(id)) from favorite_datasets;
alter table "favorite_datasets" alter column "id" set default nextval('favorite_datasets_seq');

commit;
