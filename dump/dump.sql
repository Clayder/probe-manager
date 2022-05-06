create schema if not exists probe_manager_test;

create table probe_manager_test.planet(
    id bigint primary key,
    created_at timestamp not null,
    updated_at timestamp null,
    deleted_at timestamp null,
    name varchar(100) not null,
    width int not null,
    height int not null
);
ALTER TABLE probe_manager_test.planet ADD CONSTRAINT constraint_name UNIQUE (name);



create table probe_manager_test.probe(
    id bigint primary key,
    created_at timestamp not null,
    updated_at timestamp null,
    deleted_at timestamp null,
    x int not null,
    y int not null,
    active BOOLEAN DEFAULT true,
    planet_id bigint NOT NULL
);

ALTER TABLE probe_manager_test.probe ADD CONSTRAINT `fk_probe_planet` FOREIGN KEY (`planet_id` ) REFERENCES  probe_manager_test.planet (id) ;
ALTER TABLE probe_manager_test.probe ADD CONSTRAINT constraint_probe_x_y_planetid_active UNIQUE (x, y, planet_id, active);


create table probe_manager_test.user(
    id bigint primary key,
    email varchar(100) not null,
    user_name varchar(100) not null,
    password varchar(100) not null
);

create table probe_manager_test.role(
    id bigint primary key,
    name varchar(100) not null
);

create table probe_manager_test.user_role(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

ALTER TABLE probe_manager_test.user_role ADD CONSTRAINT `fk_probe_user_user_role` FOREIGN KEY (`user_id` ) REFERENCES  probe_manager_test.user (id);
ALTER TABLE probe_manager_test.user_role ADD CONSTRAINT `fk_probe_role_user_role` FOREIGN KEY (`role_id` ) REFERENCES  probe_manager_test.role (id);

INSERT INTO probe_manager_test.user(id, user_name, email, password) VALUES(1, 'admin', 'admin@gmail.com', '$2a$10$sDt2Qfh3mMMseUQkRw0fBeMfNTq2Pr8Zsgp353t9H0RPlDBsCs3LG');

