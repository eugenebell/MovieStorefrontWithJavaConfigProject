create table MOVIE (
MOVIE_ID integer identity primary key,
PRICE double not null,
RATING integer not null,
YEAR integer not null,
POSTER_FILENAME varchar(100) not null,
DISPLAY_RUNTIME varchar(9) not null, 
TITLE varchar(50) not null,
SUMMARY varchar(2000) not null,
ACTORS_DISPLAY varchar(150) not null,
DIRECTORS_DISPLAY varchar(150) not null,
PRODUCERS_DISPLAY varchar(150),
STUDIO_DISPLAY varchar(150),
unique(TITLE));

create table GENRE (
GENRE_ID integer identity primary key,
GENRE_NAME varchar(30) not null, 
unique(GENRE_NAME));

create table MOVIE_GENRE_LINK (
MOVIE_ID integer not null, 
GENRE_ID integer not null);

create table VIDEO_STORE_MEMBER (
VIDEO_STORE_MEMBER_ID integer identity primary key, 
USERNAME VARCHAR(50) not null,
ACCOUNT_ID integer not null,
MEMBER_NAME varchar(60) not null,
LOCATION varchar(60) not null, 
MEMBERSHIP_NUMBER varchar(10) not null);


create table MOVIE_RESERVATION (
MOVIE_RESERVATION_ID integer identity primary key,
VIDEO_STORE_MEMBER_ID integer not null,
MOVIE_ID integer not null,
RENTED boolean not null,
RESERVATION_DATE date not null);   

create table ACCOUNT (
ACCOUNT_ID integer identity primary key, 
TOTAL double not null);

create table USERS (
USERNAME VARCHAR(50) primary key, 
PASSWORD VARCHAR(50) NOT NULL,
ENABLED BOOLEAN NOT NULL
);

create table AUTHORITIES (
AUTHORITIESID integer identity primary key,
USERNAME VARCHAR(50) NOT NULL,
AUTHORITY VARCHAR(50) NOT NULL
);

alter table MOVIE_RESERVATION add constraint FK_MOVIE_RESERVATION_MOVIE foreign key (MOVIE_ID) references MOVIE(MOVIE_ID) on delete cascade;
alter table MOVIE_RESERVATION add constraint FK_MOVIE_RESERVATION_MEMBER foreign key (VIDEO_STORE_MEMBER_ID) references VIDEO_STORE_MEMBER(VIDEO_STORE_MEMBER_ID) on delete cascade;

