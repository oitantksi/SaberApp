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

INSERT INTO preguntas VALUES (1,'País con más habitantes del mundo','EEUU','India','China','Rusia',3, TO_DATE('13-01-2020', 'DD-MM-YYYY'),1);