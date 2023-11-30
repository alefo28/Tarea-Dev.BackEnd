INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('gustavoo', '$2Å$iO$LCBJØdRzcyuBQINstZäg,mRk1Rmh0790j2hpKcvxyS$auTFOp210i', 1, 'Gsutavo', 'Rodriguez', 'gustavo@uanl.com')
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('nissan', '$2a$10$dHreMyiqeoy5/1ArkVFH1. nm7UCc4i8yHb3ASFbVqcmFXb8ubCdum', 1, 'Nissan','Gtr', 'nissan@uanl.com');

INSERT INTO roles (nombre) Values ("ROLE_USER");
INSERT INTO roles (nombre) VALUES ("ROLE_ADMIN");

INSERT INTO usuarios_to_roles (user_id,roles_id) VALUES (1,1);
INSERT INTO usuarios_to_roles (user_id,roles_id) VALUES (2,2);
INSERT INTO usuarios_to_roles (user_id,roles_id) VALUES (2,1);