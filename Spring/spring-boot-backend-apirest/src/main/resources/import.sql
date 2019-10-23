INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (8024, 'RÉGIMEN GENERAL DE JUBILACIONES, PENSIONES Y RETIROS', '1990-12-19', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (7854, 'LEY ORGÁNICA DE FISCALÍA DE ESTADO', '1989-11-21', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (9361, 'ESCALAFÓN PARA EL PERSONAL DE LA ADMINISTRACIÓN PÚBLICA PROVINCIAL', '2007-02-21', '2007-03-06');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (7233, 'ESTATUTO DEL PERSONAL DE LA ADMINISTRACIÓN PÚBLICA PROVINCIAL', '1984-12-26', '1985-01-07');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (1234, 'LEY PROVINCIAL', '1990-12-19', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (4567, 'LEY CORDOBA', '1989-11-21', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (7894, 'ESTATUO DE LA PROVINCIA', '2007-02-21', '2007-03-06');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (3698, 'EXAMEN PROVINCIAL', '1984-12-26', '1985-01-07');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (5664, 'ORGANICO E INORGANICO', '1990-12-19', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (6548, 'ETICA CIVICA', '1989-11-21', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (5786, 'EMPRENDEDORISMO', '2007-02-21', '2007-03-06');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (7443, 'MINISTERIO DE LA SALUD', '1984-12-26', '1985-01-07');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (4787, 'LEY DEL ABORTO', '1990-12-19', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (1144, 'INDUSTRIA EL PETROLEO', '1989-11-21', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (5564, 'MINISTERIO DE EDUCACION', '2007-02-21', '2007-03-06');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (1975, 'MINISTERIO JUSTICIA', '1984-12-26', '1985-01-07');

/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('flor','$2a$10$ppv7n/4yIBmBHYH0a0GXDutNtzjjnU5w970M/zKqCfXyyBHlak3sO',1, 'Flor', 'Gonzales','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('fabi','$2a$10$H2ANnuFIyU22NnAIlWKoB.x/m2eRpyqxdh63WHA6ux.FpgbYRMj92',1, 'Fabi', 'Mercado','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);
