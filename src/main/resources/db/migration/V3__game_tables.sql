CREATE TABLE IF NOT EXISTS question_set (
    id bigserial PRIMARY KEY,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    owner_id bigserial not null,
    visibility varchar(256) NOT NULL,
    name varchar(256) NOT NULL,
    constraint owner_id_fkey foreign key (owner_id) references users(id) on update cascade on delete restrict
);

CREATE TABLE IF NOT EXISTS question (
    id bigserial PRIMARY KEY,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    text varchar(2048) not null,
    question_set_id bigserial not null,
    answer_type varchar(256) not null,
    point_value integer not null default 1,
    constraint question_set_fkey foreign key (question_set_id) references question_set(id) on update cascade on delete restrict
);

CREATE TABLE IF NOT EXISTS answer (
    id bigserial PRIMARY KEY,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    value varchar(2048) not null,
    is_correct boolean not null,
    question_id bigserial not null,
    constraint question_fkey foreign key (question_id) references question(id) on delete restrict on update cascade
);

CREATE TABLE IF NOT EXISTS game (
    id bigserial PRIMARY KEY,
    name varchar(1024),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    creator_id bigint not null,
    code varchar(10) not null,
    game_time integer,
    game_type varchar(256) not null,
    question_set_id bigserial not null,
    class_id bigserial not null,
    allowed_play_times integer,
    starting_time timestamp,
    ending_time timestamp,
    hints_enabled boolean default false,
    point_penalty integer,
    points_per_question integer not null,
    power_ups_enabled boolean default false,
    streaks_enabled boolean default false,
    constraint game_creator_fkey foreign key (creator_id) references users(id) on update cascade on delete restrict,
    constraint code_unique unique (code),
    constraint class_fkey foreign key (class_id) references class(id) on update cascade on delete restrict,
    constraint question_set_fkey foreign key (question_set_id) references question_set(id) on update cascade on delete restrict
);

CREATE TABLE IF NOT EXISTS game_participant (
    id bigserial primary key,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    user_id bigserial not null,
    game_id bigserial not null,
    score integer not null default 0,
    constraint user_fkey foreign key (user_id) references users(id) on update cascade on delete restrict,
    constraint game_fkey foreign key (game_id) references game(id) on update cascade on delete restrict
);

