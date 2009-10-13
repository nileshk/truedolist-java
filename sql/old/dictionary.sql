USE myconnector;
DROP TABLE dictionary;
CREATE TABLE dictionary(
	word VARCHAR(64) NOT NULL PRIMARY KEY,
	def VARCHAR(255)
	);