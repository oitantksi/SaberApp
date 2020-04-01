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