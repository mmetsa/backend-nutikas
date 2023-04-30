CREATE TABLE IF NOT EXISTS school_code (
    id bigserial PRIMARY KEY,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    code varchar(255) not null,
    school_id bigint,
    constraint school_fkey foreign key (school_id) references school(id) on update cascade on delete restrict
);