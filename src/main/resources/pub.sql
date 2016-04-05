DROP DATABASE IF EXISTS pub;
CREATE DATABASE pub;

USE pub;

CREATE TABLE post_code (
postCode VARCHAR(20) NOT NULL DEFAULT '',
PRIMARY KEY (postCode)
) ENGINE=InnoDB;

create table pub (
pubId VARCHAR(200) NOT NULL,
pubName VARCHAR(100) NOT NULL,
locality VARCHAR(100) NOT NULL,
PRIMARY KEY (pubId)
) ENGINE=InnoDB;

CREATE TABLE distance (
distanceId INT NOT NULL AUTO_INCREMENT,
postCode VARCHAR(20) NOT NULL,
pubId VARCHAR(200) NOT NULL,
CONSTRAINT FK_PC FOREIGN KEY (postCode) REFERENCES post_code(postCode),
CONSTRAINT FK_PUB FOREIGN KEY (pubId) REFERENCES pub(pubId),
distance VARCHAR(100),
PRIMARY KEY (distanceId)
) ENGINE=InnoDB;

