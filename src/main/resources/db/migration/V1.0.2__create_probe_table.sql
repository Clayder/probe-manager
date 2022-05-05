
create table probe_manager.probe(
    id bigint primary key,
    created_at timestamp not null,
    updated_at timestamp null,
    deleted_at timestamp null,
    x int not null,
    y int not null,
    active BOOLEAN DEFAULT true,
    planet_id bigint NOT NULL
);

ALTER TABLE probe_manager.probe ADD CONSTRAINT `fk_probe_planet` FOREIGN KEY (`planet_id` ) REFERENCES  probe_manager.planet (id) ;
