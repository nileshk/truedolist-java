USE myconnector;
DROP TABLE release_file_xref;
CREATE TABLE release_file_xref (
       release_id	   VARCHAR(64) NOT NULL,
       file_id         VARCHAR(64) NOT NULL,
       PRIMARY KEY (release_id, file_id)
);