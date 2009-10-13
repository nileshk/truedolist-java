USE myconnector;
DROP TABLE release_user_xref;
CREATE TABLE release_user_xref (
       release_id	   VARCHAR(64) NOT NULL,
       user_id         VARCHAR(64) NOT NULL,
       PRIMARY KEY (release_id, user_id)
);