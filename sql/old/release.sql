USE myconnector;
DROP TABLE releases;
CREATE TABLE releases (
       id			   VARCHAR(64),
       created_by      VARCHAR(64),
       title           VARCHAR(255),
       description     VARCHAR(255),
       instructions	   VARCHAR(255),
       version_number  VARCHAR(255),
       created_date    DATE,
       ready           TINYINT(1),
       keywords        VARCHAR(255),
       PRIMARY KEY (id),
       UNIQUE (id)
);
