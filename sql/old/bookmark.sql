USE myconnector;
DROP TABLE bookmark;
CREATE TABLE bookmark (
       id			   VARCHAR(64),
       user_id         VARCHAR(64),
       url             VARCHAR(1024)     NOT NULL,
       title           VARCHAR(255),
       folder          VARCHAR(255),
       description     VARCHAR(255),
       add_date        DATE,
       last_visit      DATE,
       viewable        TINYINT UNSIGNED,
       keywords        VARCHAR(255),
       PRIMARY KEY (id),
       UNIQUE (id)
);
