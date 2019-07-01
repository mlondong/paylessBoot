DROP table if exists country;

CREATE TABLE country (
  COUNTRY_ID bigint(20) NOT NULL AUTO_INCREMENT,
  NAME varchar(255) NOT NULL,
  PRIMARY KEY (`COUNTRY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP table if exists usser_role;
CREATE TABLE usser_role (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK714gxv76bep6l2u7il9wmmuay` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP table if exists city;
CREATE TABLE city (
  city_id bigint(20) NOT NULL AUTO_INCREMENT,
  name_city varchar(255) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP table if exists role;
CREATE TABLE role (
  role_id bigint(20) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP table if exists zone;
CREATE TABLE zone (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name_zone varchar(255) NOT NULL,
  city_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsfb5wv3ucatw619qy5bgkph9d` (`city_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
