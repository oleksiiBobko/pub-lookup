DROP DATABASE IF EXISTS pub;
CREATE DATABASE pub;

USE pub;

CREATE TABLE point (
point_id VARCHAR(20),
PRIMARY KEY (point_id)
) ENGINE=InnoDB;

create table pub (
pub_id VARCHAR(200),
pub_name VARCHAR(200) NOT NULL DEFAULT '',
address VARCHAR(200) NOT NULL  DEFAULT '',
post_code varchar(30),
country varchar(200),
city varchar(200),
district varchar(200),
PRIMARY KEY (pub_id)
) ENGINE=InnoDB;

CREATE TABLE distance (
id INT NOT NULL AUTO_INCREMENT,
point_id VARCHAR(20) NOT NULL,
pub_id VARCHAR(200) NOT NULL,
CONSTRAINT FK_PC FOREIGN KEY (point_id) REFERENCES point(point_id),
CONSTRAINT FK_PUB FOREIGN KEY (pub_id) REFERENCES pub(pub_id),
distance VARCHAR(100),
PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE geo_search (
search_id VARCHAR(200),
PRIMARY KEY (search_id)
) ENGINE=InnoDB;

CREATE TABLE geo_result (
id INT NOT NULL AUTO_INCREMENT,
search_id VARCHAR(200),
formatted VARCHAR(200),
locality VARCHAR(200),
postal_town VARCHAR(200),
administrative_area_level_1 VARCHAR(200),
administrative_area_level_2 VARCHAR(200),
administrative_area_level_3 VARCHAR(200),
administrative_area_level_4 VARCHAR(200),
administrative_area_level_5 VARCHAR(200),
country varchar(200),
postal_code varchar(30),
CONSTRAINT FK_GEOSEARCH FOREIGN KEY (search_id) REFERENCES geo_search(search_id),
PRIMARY KEY (id)
) ENGINE=InnoDB;
