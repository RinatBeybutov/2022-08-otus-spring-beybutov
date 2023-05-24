DROP TABLE IF EXISTS authors;
CREATE TABLE authors(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255));

drop table if exists genres;
create table genres(ID IDENTITY primary key, name varchar(255));

drop table if exists books;
create table books(
ID IDENTITY primary key,
name varchar(255),
id_author bigint,
id_genre bigint,
FOREIGN KEY (id_author) REFERENCES authors(ID),
FOREIGN KEY (id_genre) REFERENCES genres(ID)
);
