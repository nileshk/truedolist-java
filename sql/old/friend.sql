USE myconnector;
DROP TABLE friend;
CREATE TABLE friend (
       id			   VARCHAR(64),
       friend_id       VARCHAR(64),
       PRIMARY KEY (id, friend_id)
);