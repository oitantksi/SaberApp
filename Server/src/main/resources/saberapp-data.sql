INSERT INTO users VALUES (0, 'Sistema','Master', 'master','$2a$10$DjTz.jTSd3QDHIFRa/HGrO..FtUUnCxAx6I7gT6NScLNJuINnrSX.','master@omega.com','IOC','M');--password: omega , usuario master
INSERT INTO users VALUES (1, 'ramon','omega', 'server','$2a$10$DjTz.jTSd3QDHIFRa/HGrO..FtUUnCxAx6I7gT6NScLNJuINnrSX.','ramon@omega.com','IOC','M');
INSERT INTO users VALUES (2, 'montse','omega','desktop','$2a$10$DjTz.jTSd3QDHIFRa/HGrO..FtUUnCxAx6I7gT6NScLNJuINnrSX.', 'montse@omega.com','IOC','M');
INSERT INTO users VALUES (3, 'isaac', 'omega','android','$2a$10$HGEfGUqru6I0KfZXxTirx.QszGXOrCYnPET/zRsadb975exGMzlIy','isaac@omega.com','IOC','M');
INSERT INTO Users VALUES (4, 'Android', 'Test User', 'androidTestUser', '$2a$10$lbT.g6IdbY390c/EDW8BeOSFcR9bihRbBQdCcc.HtVc428SUkvDsW', 'android@omega.org', 'IES JOAN MARAGALL', 'A');


INSERT INTO centres VALUES (1,'IOC');
INSERT INTO centres VALUES(2,'IES JOAN MARAGALL');
INSERT INTO centres VALUES (3,'IES JAUME BALMES');

INSERT INTO curses VALUES ('1');
INSERT INTO curses VALUES ('2');
INSERT INTO curses VALUES ('3');
INSERT INTO curses VALUES ('4');

INSERT INTO materies VALUES (1,'Geografia');
INSERT INTO materies VALUES (2,'Historia');
INSERT INTO materies VALUES (3,'Arte');
INSERT INTO materies VALUES (4,'Matemáticas');

INSERT INTO aules VALUES (1, 'A');
INSERT INTO aules VALUES (2, 'B');
INSERT INTO aules VALUES (3, 'C');
INSERT INTO aules VALUES (4, 'D');

INSERT INTO preguntas VALUES (1, 'País amb més habitants del món', 'EEUU', 'India', 'Xina', 'Russia', 3, CURDATE(),1);
INSERT INTO preguntas VALUES (2, 'Com s''anomenen les línies imaginàries que tallen perpendicularment l''eix de rotació de la Terra', 'Paral·lels', 'Latitud', 'Hemisferis', 'Meridians', 1, CURDATE()-2,1);
INSERT INTO preguntas VALUES (4, 'Quina és la capital del Marroc?', 'Marrakech', 'Casablanca', 'Fez', 'Rabat', 4, CURDATE()+7,1);
INSERT INTO preguntas VALUES (5, 'Els tres bens natural Patrimoni de la Humanitat d''Espanya són...', 'Garajonay, Teide i Doñana', 'Roquenublo, Cabo de Gata i Monte Perdido', 'Doñana, Muniellos y Cabañeros', 'Illa de El Hierro, Tablas de Daimiel y Teide', 1, CURDATE()+11,1);
INSERT INTO preguntas VALUES (6, 'Cuantes llengúes oficials té Espanya?', '1', '3', '4', '5', 4, CURDATE()-4,1);
INSERT INTO preguntas VALUES (7, 'Com s''anomena el riu més llarg de la península ibèrica?', 'Guadiana', 'Tajo', 'Ebre', 'Guadalquivir', 2, CURDATE()-7,1);
INSERT INTO preguntas VALUES (8, 'On es troba el principal jaciment d''hominids de la peninsula ibèrica?', 'Santimamiñe', 'Atapuerca', 'Torralba', 'Ambrona', 2, CURDATE()+1,2);
INSERT INTO preguntas VALUES (9, 'Quin general cartaginès va envair Italia durant la 2a Guerra Púnica, fins la seva derrota definitiva l''any 201 a.c.?', 'Anibal', 'Atila', 'Alexandre Magne', 'Genghis Khan', 1, CURDATE()-3,2);
INSERT INTO preguntas VALUES (10, 'Quina va ser la última faraona d''Egipte, que va governar fins l''any 30 a.c.?', 'Nefertiti', 'Isis', 'Eneida', 'Cleopatra', 4, CURDATE()+4,2);
INSERT INTO preguntas VALUES (11, 'Quins nom van posar els romans als deus gracs Zeus, Afrodita i Ares?', 'Jupiter, Minerva i Hades', 'Júpiter, Venus i Mart', 'Saturn, Venus i Hades', 'Saturn, Minerva i Mart', 2, CURDATE()+10,2);
INSERT INTO preguntas VALUES (12, 'En quin any va tindre lloc la batalla de Trafalgar?', '1795', '1590', '1610', '1805', 4, CURDATE()-6,2);
INSERT INTO preguntas VALUES (13, 'Quins són els 8 primers elements de la successió de Fibonacci?', '0, 2, 1, 4, 3, 6, 5, 8', '1, 3, 5, 7, 9, 11, 13, 15', '0, -1, 1, -2, 2, -3, 3, -4', '0, 1, 1, 2, 3, 5, 8, 13', 4, CURDATE()+2,4);
INSERT INTO preguntas VALUES (14, 'Quin és el cosinus d''un angle de 90 graus', '0', '1/2', '1', '-1', 1, CURDATE()-1,4);
INSERT INTO preguntas VALUES (15, 'Quin és el valor aproximat del número "e"?', '5,445361', '1,142565', '2,718282', '3,141598', 3, CURDATE()+6,4);
INSERT INTO preguntas VALUES (16, 'Amb 39 litres de gasolina el marcador d''un cotxe senyala 3/4 de dipòsit. Quina és la capacitat total del dipòsit?', '49 litres', '54 litres', '52 litres', '55 litres', 3, CURDATE()+8,4);
INSERT INTO preguntas VALUES (3, 'Què diu el teorema del sinus?', 'En els tiangles equilaters, la longitud dels seus costats és proporcional als sinus dels angles oposats', 'En tot triangle, la longitud dels seus costats és proporcional als sinus dels angles oposats', 'En tot triangle, la longitud dels seus costats és proporcional als cosinus dels angles oposats', 'En els triagles rectangles, la longitud dels seus costats és proporcional als cosinus dels angles oposats', 2, CURDATE()-5,4);

INSERT INTO respuestas VALUES (1,1,1,1,CURDATE());

-- INSERT INTO puntuacion VALUES (1,1, 3);

-- Usuaris varis per a jugar amb les puntuacions

INSERT INTO Users VALUES (5, 'Joan', 'Test User', 'joan', 'dummy', 'joan@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (6, 'Maria', 'Test User', 'maria', 'dummy', 'maria@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (7, 'Carla', 'Test User', 'carla', 'dummy', 'carla@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (8, 'Josep Maria', 'Test User', 'jm', 'dummy', 'jm@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (9, 'Nora', 'Test User', 'nora', 'dummy', 'nora@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (10, 'Aura', 'Test User', 'aura', 'dummy', 'aura@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (11, 'Albert', 'Test User', 'albert', 'dummy', 'albert@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (12, 'Lara', 'Test User', 'lara', 'dummy', 'lara@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (13, 'marc', 'Test User', 'marc', 'dummy', 'marc@omega.org', 'IES JOAN MARAGALL', 'A');
INSERT INTO Users VALUES (14, 'agnes', 'Test User', 'agnes', 'dummy', 'agnes@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (15, 'marta', 'Test User', 'marta', 'dummy', 'marta@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (16, 'ignasi', 'Test User', 'ignasi', 'dummy', 'ignasi@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (17, 'Jordina', 'Test User', 'jordina', 'dummy', 'jordina@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (18, 'adria', 'Test User', 'adria', 'dummy', 'adria@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (19, 'laila', 'Test User', 'laila', 'dummy', 'laila@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (20, 'patricia', 'Test User', 'patricia', 'dummy', 'patricia@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (21, 'manel', 'Test User', 'manel', 'dummy', 'manel@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (22, 'dani', 'Test User', 'dani', 'dummy', 'dani@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (23, 'oscar', 'Test User', 'oscar', 'dummy', 'oscar@omega.org', 'IOC', 'A');
INSERT INTO Users VALUES (24, 'kathy', 'Test User', 'kathy', 'dummy', 'kathy@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (25, 'montse', 'Test User', 'montse', 'dummy', 'montse@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (26, 'anna', 'Test User', 'anna', 'dummy', 'anna@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (27, 'miquel', 'Test User', 'miquel', 'dummy', 'miquel@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (28, 'david', 'Test User', 'david', 'dummy', 'david@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (29, 'agusti', 'Test User', 'agusti', 'dummy', 'agusti@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (30, 'esther', 'Test User', 'esther', 'dummy', 'esther@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (31, 'pere', 'Test User', 'pere', 'dummy', 'pere@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (32, 'homer', 'Test User', 'homer', 'dummy', 'homer@omega.org', 'IES JAUME BALMES', 'A');
INSERT INTO Users VALUES (33, 'mar', 'Test User', 'mar', 'dummy', 'mar@omega.org', 'IES JAUME BALMES', 'A');

-- Respostes per a calcular puntuacions

INSERT INTO respuestas VALUES (2,2,7,4,CURDATE()-7);
INSERT INTO respuestas VALUES (3,3,7,6,CURDATE()-7);
INSERT INTO respuestas VALUES (4,2,7,8,CURDATE()-7);
INSERT INTO respuestas VALUES (5,2,7,12,CURDATE()-7);
INSERT INTO respuestas VALUES (6,4,7,14,CURDATE()-7);
INSERT INTO respuestas VALUES (7,2,7,16,CURDATE()-7);
INSERT INTO respuestas VALUES (8,3,7,19,CURDATE()-7);
INSERT INTO respuestas VALUES (9,2,7,20,CURDATE()-7);
INSERT INTO respuestas VALUES (10,1,7,23,CURDATE()-7);
INSERT INTO respuestas VALUES (11,2,7,25,CURDATE()-7);
INSERT INTO respuestas VALUES (12,1,7,26,CURDATE()-7);
INSERT INTO respuestas VALUES (13,4,7,28,CURDATE()-7);
INSERT INTO respuestas VALUES (14,2,7,29,CURDATE()-7);
INSERT INTO respuestas VALUES (15,3,7,31,CURDATE()-7);

INSERT INTO respuestas VALUES (16,3,12,4,CURDATE()-6);
INSERT INTO respuestas VALUES (17,4,12,6,CURDATE()-6);
INSERT INTO respuestas VALUES (18,1,12,9,CURDATE()-6);
INSERT INTO respuestas VALUES (19,1,12,11,CURDATE()-6);
INSERT INTO respuestas VALUES (20,4,12,15,CURDATE()-6);
INSERT INTO respuestas VALUES (21,4,12,16,CURDATE()-6);
INSERT INTO respuestas VALUES (22,4,12,18,CURDATE()-6);
INSERT INTO respuestas VALUES (23,2,12,19,CURDATE()-6);
INSERT INTO respuestas VALUES (24,4,12,20,CURDATE()-6);
INSERT INTO respuestas VALUES (25,4,12,23,CURDATE()-6);
INSERT INTO respuestas VALUES (26,4,12,27,CURDATE()-6);
INSERT INTO respuestas VALUES (27,2,12,29,CURDATE()-6);
INSERT INTO respuestas VALUES (28,4,12,32,CURDATE()-6);

INSERT INTO respuestas VALUES (29,2,3,4,CURDATE()-5);
INSERT INTO respuestas VALUES (30,2,3,5,CURDATE()-5);
INSERT INTO respuestas VALUES (31,3,3,6,CURDATE()-5);
INSERT INTO respuestas VALUES (32,2,3,7,CURDATE()-5);
INSERT INTO respuestas VALUES (33,2,3,13,CURDATE()-5);
INSERT INTO respuestas VALUES (34,2,3,14,CURDATE()-5);
INSERT INTO respuestas VALUES (35,1,3,15,CURDATE()-5);
INSERT INTO respuestas VALUES (36,3,3,16,CURDATE()-5);
INSERT INTO respuestas VALUES (37,4,3,20,CURDATE()-5);
INSERT INTO respuestas VALUES (38,1,3,21,CURDATE()-5);
INSERT INTO respuestas VALUES (39,2,3,25,CURDATE()-5);
INSERT INTO respuestas VALUES (40,2,3,26,CURDATE()-5);
INSERT INTO respuestas VALUES (41,3,3,28,CURDATE()-5);
INSERT INTO respuestas VALUES (42,1,3,29,CURDATE()-5);
INSERT INTO respuestas VALUES (43,4,3,30,CURDATE()-5);
INSERT INTO respuestas VALUES (44,1,3,31,CURDATE()-5);
INSERT INTO respuestas VALUES (45,3,3,32,CURDATE()-5);

INSERT INTO respuestas VALUES (46,4,6,6,CURDATE()-4);
INSERT INTO respuestas VALUES (47,4,6,8,CURDATE()-4);
INSERT INTO respuestas VALUES (48,3,6,11,CURDATE()-4);
INSERT INTO respuestas VALUES (49,4,6,13,CURDATE()-4);
INSERT INTO respuestas VALUES (50,4,6,16,CURDATE()-4);
INSERT INTO respuestas VALUES (51,2,6,19,CURDATE()-4);
INSERT INTO respuestas VALUES (52,1,6,21,CURDATE()-4);
INSERT INTO respuestas VALUES (53,1,6,23,CURDATE()-4);
INSERT INTO respuestas VALUES (54,4,6,24,CURDATE()-4);
INSERT INTO respuestas VALUES (55,2,6,28,CURDATE()-4);
INSERT INTO respuestas VALUES (56,3,6,30,CURDATE()-4);
INSERT INTO respuestas VALUES (57,4,6,33,CURDATE()-4);

INSERT INTO respuestas VALUES (58,2,9,7,CURDATE()-3);
INSERT INTO respuestas VALUES (59,3,9,9,CURDATE()-3);
INSERT INTO respuestas VALUES (60,4,9,10,CURDATE()-3);
INSERT INTO respuestas VALUES (61,1,9,16,CURDATE()-3);
INSERT INTO respuestas VALUES (62,1,9,17,CURDATE()-3);
INSERT INTO respuestas VALUES (63,1,9,20,CURDATE()-3);
INSERT INTO respuestas VALUES (64,1,9,25,CURDATE()-3);
INSERT INTO respuestas VALUES (65,1,9,27,CURDATE()-3);
INSERT INTO respuestas VALUES (66,3,9,30,CURDATE()-3);

INSERT INTO respuestas VALUES (67,1,2,4,CURDATE()-2);
INSERT INTO respuestas VALUES (68,2,2,5,CURDATE()-2);
INSERT INTO respuestas VALUES (69,2,2,7,CURDATE()-2);
INSERT INTO respuestas VALUES (70,1,2,8,CURDATE()-2);
INSERT INTO respuestas VALUES (71,3,2,12,CURDATE()-2);
INSERT INTO respuestas VALUES (72,3,2,25,CURDATE()-2);
INSERT INTO respuestas VALUES (73,1,2,29,CURDATE()-2);
INSERT INTO respuestas VALUES (74,1,2,31,CURDATE()-2);

INSERT INTO respuestas VALUES (75,1,14,4,CURDATE()-1);
INSERT INTO respuestas VALUES (76,2,14,5,CURDATE()-1);
INSERT INTO respuestas VALUES (77,1,14,6,CURDATE()-1);
INSERT INTO respuestas VALUES (78,1,14,9,CURDATE()-1);
INSERT INTO respuestas VALUES (79,1,14,16,CURDATE()-1);
INSERT INTO respuestas VALUES (80,1,14,18,CURDATE()-1);
INSERT INTO respuestas VALUES (81,4,14,19,CURDATE()-1);
INSERT INTO respuestas VALUES (82,3,14,24,CURDATE()-1);
INSERT INTO respuestas VALUES (83,4,14,26,CURDATE()-1);
INSERT INTO respuestas VALUES (84,1,14,28,CURDATE()-1);
INSERT INTO respuestas VALUES (85,1,14,31,CURDATE()-1);

-- Puntuacions dacord amb les respostes introduïdes

INSERT INTO puntuacion VALUES (4,1,13);
INSERT INTO puntuacion VALUES (6,1,5);
INSERT INTO puntuacion VALUES (8,1,18);
INSERT INTO puntuacion VALUES (12,1,5);
INSERT INTO puntuacion VALUES (13,1,5);
INSERT INTO puntuacion VALUES (16,1,10);
INSERT INTO puntuacion VALUES (20,1,5);
INSERT INTO puntuacion VALUES (24,1,5);
INSERT INTO puntuacion VALUES (25,1,5);
INSERT INTO puntuacion VALUES (29,1,13);
INSERT INTO puntuacion VALUES (31,1,8);
INSERT INTO puntuacion VALUES (33,1,5);
INSERT INTO puntuacion VALUES (6,2,4);
INSERT INTO puntuacion VALUES (15,2,4);
INSERT INTO puntuacion VALUES (16,2,9);
INSERT INTO puntuacion VALUES (17,2,5);
INSERT INTO puntuacion VALUES (18,2,4);
INSERT INTO puntuacion VALUES (20,2,9);
INSERT INTO puntuacion VALUES (23,2,4);
INSERT INTO puntuacion VALUES (25,2,5);
INSERT INTO puntuacion VALUES (27,2,9);
INSERT INTO puntuacion VALUES (32,2,4);
INSERT INTO puntuacion VALUES (4,4,9);
INSERT INTO puntuacion VALUES (5,4,5);
INSERT INTO puntuacion VALUES (6,4,4);
INSERT INTO puntuacion VALUES (7,4,5);
INSERT INTO puntuacion VALUES (9,4,4);
INSERT INTO puntuacion VALUES (13,4,5);
INSERT INTO puntuacion VALUES (14,4,5);
INSERT INTO puntuacion VALUES (16,4,4);
INSERT INTO puntuacion VALUES (18,4,4);
INSERT INTO puntuacion VALUES (25,4,5);
INSERT INTO puntuacion VALUES (26,4,5);
INSERT INTO puntuacion VALUES (28,4,4);
INSERT INTO puntuacion VALUES (31,4,4);

