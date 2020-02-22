DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INTEGER auto_increment  PRIMARY KEY ,
  name VARCHAR(30),
  cognom VARCHAR(50),
  nickname VARCHAR,
  password VARCHAR,
  email  VARCHAR(50),
  center VARCHAR,
  rol CHAR
  
);