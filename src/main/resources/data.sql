CREATE IF NOT EXISTS TABLE users (id INT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255));

DROP SEQUENCE SEQ_USERS_ID;
CREATE SEQUENCE seq_users_id
    START WITH 1
    INCREMENT BY 1;


-- some seed data
INSERT INTO users VALUES(seq_users_id.nextval, 'username1', 'lola123');
INSERT INTO users VALUES(seq_users_id.nextval, 'username2', 'lola123');
INSERT INTO users VALUES(seq_users_id.nextval, 'username3', '');
INSERT INTO users VALUES(seq_users_id.nextval, 'username4', '');
INSERT INTO users VALUES(seq_users_id.nextval, 'username5', '');
INSERT INTO users VALUES(seq_users_id.nextval, 'username6', '');
INSERT INTO users VALUES(seq_users_id.nextval, 'username7', '');
INSERT INTO users VALUES(seq_users_id.nextval, 'username8', '');
INSERT INTO users VALUES(seq_users_id.nextval, 'username9', '');

