INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (8024, 'RÉGIMEN GENERAL DE JUBILACIONES, PENSIONES Y RETIROS', '1990-12-19', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (7854, 'LEY ORGÁNICA DE FISCALÍA DE ESTADO', '1989-11-21', '1991-01-21');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (9361, 'ESCALAFÓN PARA EL PERSONAL DE LA ADMINISTRACIÓN PÚBLICA PROVINCIAL', '2007-02-21', '2007-03-06');
INSERT INTO leyes (numero, titulo, fecha_sancion, publicacion_BO) VALUES (7233, 'ESTATUTO DEL PERSONAL DE LA ADMINISTRACIÓN PÚBLICA PROVINCIAL', '1984-12-26', '1985-01-07');

/* Creamos algunos usuarios con sus roles */
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('flor','$2a$10$ppv7n/4yIBmBHYH0a0GXDutNtzjjnU5w970M/zKqCfXyyBHlak3sO',1, 'Flor', 'Gonzales','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, apellido, email) VALUES ('fabi','$2a$10$H2ANnuFIyU22NnAIlWKoB.x/m2eRpyqxdh63WHA6ux.FpgbYRMj92',1, 'Fabi', 'Mercado','jhon.doe@bolsadeideas.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);
