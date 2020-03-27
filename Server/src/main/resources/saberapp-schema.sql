DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INTEGER auto_increment  PRIMARY KEY ,
  name VARCHAR(30),
  cognom VARCHAR(50),
  nickname VARCHAR UNIQUE,
  password VARCHAR,
  email  VARCHAR(50)UNIQUE,
  center VARCHAR,
  rol CHAR
  
);

CREATE TABLE centres (
	id INTEGER auto_increment  PRIMARY KEY,
	centre VARCHAR(50)
);

CREATE TABLE curses (
	 curso INTEGER PRIMARY KEY
);

CREATE TABLE materies (
	id INTEGER auto_increment  PRIMARY KEY,
	materia VARCHAR(50)
);

CREATE TABLE aules (
    id INTEGER PRIMARY KEY,
    aula CHAR
);

CREATE TABLE preguntas (
	id INTEGER auto_increment PRIMARY KEY,
	pregunta VARCHAR not null,
	respuesta1 VARCHAR,
	respuesta2 VARCHAR,
	respuesta3 VARCHAR,
	respuesta4 VARCHAR,
	respuesta_correcta INTEGER,
	fecha_aparicion TIMESTAMP,
	materia_id INTEGER,
	FOREIGN KEY (materia_id) 
    REFERENCES materies(id)
)