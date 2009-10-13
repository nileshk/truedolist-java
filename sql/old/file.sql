USE myconnector;
DROP TABLE files;
CREATE TABLE files(
	id VARCHAR(32) NOT NULL PRIMARY KEY,
	file_name VARCHAR(64),
	file_size MEDIUMINT UNSIGNED,
	file_type VARCHAR(255),
	file_description VARCHAR(255),
	file LONGBLOB NOT NULL);