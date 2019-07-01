INSERT INTO country  VALUES (1,'Colombia');
INSERT INTO country  VALUES (2,'Argentina');
INSERT INTO country  VALUES (3,'Canada');

INSERT INTO role (role_id,role_name) VALUES (1,'ROLE_ADMIN');
INSERT INTO role (role_id,role_name) VALUES (2,'ROLE_CONSUMER');
INSERT INTO role (role_id,role_name) VALUES (3,'ROLE_TRADER');


INSERT INTO usser (user_id,name,password,state) VALUES (1,'admin','$2a$04$KGDvD7u6SgKEGNfwzzeo3eF7ymEDwsoYouEd2XZHY1hq0kJx2blR2','1');
INSERT INTO trader (cuit,email,name_enterprise,score,user_id) VALUES (11111,'admin@admin.com','admin',5,1);
INSERT INTO usser_role (user_id,role_id) VALUES (1,1);


INSERT INTO city (city_id,name_city) VALUES (1,'Buenos Aires');
INSERT INTO city (city_id,name_city) VALUES (2,'La Plata');
INSERT INTO city (city_id,name_city) VALUES (3,'Vicente Lopez');
INSERT INTO city (city_id,name_city) VALUES (4,'Quilmes');

INSERT INTO zone (id,name_zone,city_id) VALUES (1,'Belgrano',1);
INSERT INTO zone (id,name_zone,city_id) VALUES (2,'Palermo',1);
INSERT INTO zone (id,name_zone,city_id) VALUES (3,'Boedo',1);
INSERT INTO zone (id,name_zone,city_id) VALUES (4,'Recoleta',1);
INSERT INTO zone (id,name_zone,city_id) VALUES (5,'Balvanera',1);


