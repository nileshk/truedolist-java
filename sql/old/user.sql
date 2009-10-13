USE myconnector;
DROP TABLE user_data;
CREATE TABLE user_data (
       id			   VARCHAR(64),
       user_login      VARCHAR(64)	   NOT NULL,
       user_password   VARCHAR(64)     NOT NULL,
       security_level  TINYINT UNSIGNED NOT NULL,
       PRIMARY KEY (id),
       UNIQUE (id, user_login)
);
