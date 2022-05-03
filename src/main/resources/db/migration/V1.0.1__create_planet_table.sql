create schema if not exists probe_manager;

create table probe_manager.planet(
    id bigint primary key,
    created_at timestamp not null,
    updated_at timestamp null,
    deleted_at timestamp null,
    name varchar(100) not null,
    width int not null,
    height int not null
);
