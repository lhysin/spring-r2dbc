CREATE SCHEMA ADM;

create table ADM.POINT
(
    ID         integer not null,
    CUST_NO    varchar(255),
    AMOUNT     integer,
    primary key (ID)
);
