CREATE TABLESPACE ESPABD0302SQLINJECTION DATAFILE 'ABD0302SQLINJECTION' SIZE 2M
AUTOEXTEND OFF;

CREATE USER ABD0302SQLINJECTION IDENTIFIED BY ABD0302SQLINJECTION DEFAULT TABLESPACE
ESPABD0302SQLINJECTION TEMPORARY TABLESPACE TEMP QUOTA UNLIMITED ON
ESPABD0302SQLINJECTION;


GRANT create table, delete any table, select any dictionary, connect, create session , create synonym , create public synonym, create sequence, create view , create trigger, create procedure, alter any procedure, drop any procedure, execute any procedure, create trigger, alter any trigger, drop any trigger
TO ABD0302SQLINJECTION;