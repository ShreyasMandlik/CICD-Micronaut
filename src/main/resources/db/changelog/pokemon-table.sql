--liquibase formatted sql

--changeset aman:pokemon-table
CREATE TABLE pokemon (
	id BIGINT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	power varchar(100) NULL,
	imageUrl varchar(100) NULL,
	CONSTRAINT pokemon_pk PRIMARY KEY (id),
	CONSTRAINT pokemon_name_unique UNIQUE KEY (name)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

--changeset aman:pokemon-table-changed-datatypesize
ALTER TABLE pokemon MODIFY COLUMN imageUrl varchar(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL NULL;

--changeset aman:master-data-power-created
CREATE TABLE power (
	id BIGINT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT power_pk PRIMARY KEY (id),
	CONSTRAINT power_un UNIQUE KEY (name)
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

--changeset aman:pokemon-table-power-deleted
ALTER TABLE pokemon DROP COLUMN power;

--changeset aman:pokemon-table-power-added
ALTER TABLE pokemon ADD power BIGINT NULL;

--changeset aman:pokemon-table-fk-connect-master-power
ALTER TABLE pokemon ADD CONSTRAINT pokemon_FK FOREIGN KEY (power) REFERENCES power(id);

--changeset aman:master-data-power-inserted
INSERT INTO power (name)
	VALUES ('Grass'),('Fire'),('Water'),('Poison');


