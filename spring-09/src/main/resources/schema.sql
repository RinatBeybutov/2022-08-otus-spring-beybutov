DROP TABLE IF EXISTS PERSONS;
CREATE TABLE PERSONS(ID BIGINT PRIMARY KEY, NAME VARCHAR(255));

drop table if exists genres;
create table genres(ID bigint primary key, name varchar(255));

drop table if exists books;
create table books(
ID bigint primary key,
name varchar(255),
id_author bigint,
id_genre bigint,
FOREIGN KEY (id_author) REFERENCES persons(ID),
FOREIGN KEY (id_genre) REFERENCES genres(ID)
);
