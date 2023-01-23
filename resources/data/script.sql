-- CREACION DE LA DB
DROP DATABASE IF EXISTS pokedex;
CREATE DATABASE pokedex;

USE pokedex;

-- CREACION DE LAS TABLAS
-- TABLA tipo
DROP TABLE IF EXISTS tipo;
CREATE TABLE tipo (
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(15),
	PRIMARY KEY (id)
);

-- DATOS INAMOVIBLES DE LA TABLA tipo
INSERT INTO tipo(nombre) VALUES ("ACERO");
INSERT INTO tipo(nombre) VALUES ("AGUA");
INSERT INTO tipo(nombre) VALUES ("BICHO");
INSERT INTO tipo(nombre) VALUES ("DRAGON");
INSERT INTO tipo(nombre) VALUES ("ELECTRICO");
INSERT INTO tipo(nombre) VALUES ("FANTASMA");
INSERT INTO tipo(nombre) VALUES ("FUEGO");
INSERT INTO tipo(nombre) VALUES ("HADA");
INSERT INTO tipo(nombre) VALUES ("HIELO");
INSERT INTO tipo(nombre) VALUES ("LUCHA");
INSERT INTO tipo(nombre) VALUES ("NORMAL");
INSERT INTO tipo(nombre) VALUES ("PLANTA");
INSERT INTO tipo(nombre) VALUES ("PSIQUICO");
INSERT INTO tipo(nombre) VALUES ("ROCA");
INSERT INTO tipo(nombre) VALUES ("SINIESTRO");
INSERT INTO tipo(nombre) VALUES ("TIERRA");
INSERT INTO tipo(nombre) VALUES ("VENENO");
INSERT INTO tipo(nombre) VALUES ("VOLADOR");
INSERT INTO tipo(nombre) VALUES ("NULL");

-- TABLA pokemon
DROP TABLE IF EXISTS pokemon;
CREATE TABLE pokemon (
	id INT NOT NULL AUTO_INCREMENT,
	num_pokedex INT NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	tipo1 INT NOT NULL,
	tipo2 INT DEFAULT 19,
	shiny BOOLEAN DEFAULT FALSE,
	url VARCHAR(256) DEFAULT 'URL',
	PRIMARY KEY (id),
	UNIQUE (num_pokedex),
	FOREIGN KEY (tipo1) REFERENCES tipo(id),
	FOREIGN KEY (tipo2) REFERENCES tipo(id)
);
