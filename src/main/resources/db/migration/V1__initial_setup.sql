create table school (
    id bigserial not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    name varchar(255) not null,
    short_name varchar(255),
    address varchar(255),
    email varchar(255),
    phone varchar(255),
    constraint school_pkey primary key (id),
    constraint name_unique_key unique (name),
    constraint short_name_unique_key unique (short_name)
);

create table class (
   id bigserial not null,
   created_at timestamp not null default now(),
   updated_at timestamp not null default now(),
   name varchar(255) not null,
   number int not null,
   school_id bigint not null,
   constraint class_pkey primary key (id),
   constraint class_school_fkey foreign key (school_id) references school(id) on update cascade on delete restrict,
   constraint class_name_unique unique (name, school_id)
);

create table users (
   id bigserial not null,
   created_at timestamp not null default now(),
   updated_at timestamp not null default now(),
   first_name varchar(255),
   last_name varchar(255),
   nickname varchar(255) not null,
   birthday date,
   coins bigint default 0,
   experience bigint default 0,
   email varchar(255),
   password_hash varchar(255) not null,
   constraint users_pkey primary key (id),
   constraint nickname_unique_key unique (nickname),
   constraint email_unique_key unique (email)
);

create table roles (
   id bigserial not null,
   created_at timestamp not null default now(),
   updated_at timestamp not null default now(),
   name varchar(255) not null,
   constraint role_pkey primary key (id)
);

create table users_roles (
    id bigserial not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    roles_id bigint not null,
    users_id bigint not null,
    school_id bigint,
    class_id bigint,
    expire_date timestamp not null default now() + interval '1 year',
    disabled boolean default true,
    constraint user_roles_pkey primary key (id),
    constraint user_role_school_unique unique (roles_id, users_id, school_id),
    constraint user_fkey foreign key (users_id) references users(id) on update cascade on delete restrict,
    constraint role_fkey foreign key (roles_id) references roles(id) on update cascade on delete restrict,
    constraint school_fkey foreign key (school_id) references school(id),
    constraint class_fkey foreign key (class_id) references class(id)
);