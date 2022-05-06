
create table probe_manager.user(
    id bigint primary key,
    email varchar(100) not null,
    user_name varchar(100) not null,
    password varchar(100) not null
);

create table probe_manager.role(
    id bigint primary key,
    name varchar(100) not null
);

create table probe_manager.user_role(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

ALTER TABLE probe_manager.user_role ADD CONSTRAINT `fk_probe_user_user_role` FOREIGN KEY (`user_id` ) REFERENCES  probe_manager.user (id);
ALTER TABLE probe_manager.user_role ADD CONSTRAINT `fk_probe_role_user_role` FOREIGN KEY (`role_id` ) REFERENCES  probe_manager.role (id);

INSERT INTO probe_manager.user(id, user_name, email, password) VALUES(1, 'admin', 'admin@gmail.com', '$2a$10$sDt2Qfh3mMMseUQkRw0fBeMfNTq2Pr8Zsgp353t9H0RPlDBsCs3LG');
