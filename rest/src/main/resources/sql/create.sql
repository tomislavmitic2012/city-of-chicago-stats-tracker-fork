create table users(
        id integer not null auto_increment,
        uuid varchar(36) not null,
        first_name varchar(50) not null,
        last_name varchar(80) not null,
        email varchar(256) not null,
        password varchar(256) not null,
        enabled boolean not null default true,
        created_date date not null default now(),
    primary key (id)
);

create table user_roles(
        id integer not null auto_increment,
        user_id integer not null,
        "role" varchar(45),
    primary key (id),
    unique (user_id, role),
    foreign key (user_id) references users(id)
);

create table favorite_datasets(
        id integer not null auto_increment,
        user_id integer not null,
        query varchar(1000000),
        notes varchar(1000000),
        created_date date not null default now(),
    primary key (id),
    foreign key (user_id) references users(id)
);

create table alert_queries(
        id integer not null auto_increment,
        user_id integer not null,
        query varchar(1000000),
        notes varchar(1000000),
        startdate date not null,
        enddate date,
        enabled boolean not null default true,
        interval integer not null default 0,
        constraint inter_gt_zero check (interval >= 0),
    primary key (id),
    foreign key (user_id) references users(id)
);