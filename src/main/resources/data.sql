INSERT INTO perfis(id, nome) VALUES(1,'ROLE_USUARIO');
INSERT INTO perfis(id, nome) VALUES(2,'ROLE_MODERADOR');
INSERT INTO perfis(id, nome) VALUES(3,'ROLE_ADMINISTRADOR');

INSERT INTO usuarios (id,email,nome, senha) VALUES (1,'adm@email.com', 'Admin', '$2a$10$ADu4FHIn9awOqsiVsL6Rv.40zLIj362ifpH12ZZsbqJnzrhfbuHC6');
INSERT INTO usuarios (id,email,nome, senha) VALUES (2,'moderador@email.com', 'Moderador', '$2a$10$ADu4FHIn9awOqsiVsL6Rv.40zLIj362ifpH12ZZsbqJnzrhfbuHC6');
INSERT INTO usuarios (id,email,nome, senha) VALUES (3,'usuario@email.com', 'Usuario', '$2a$10$ADu4FHIn9awOqsiVsL6Rv.40zLIj362ifpH12ZZsbqJnzrhfbuHC6');
INSERT INTO usuarios_perfis (usuario_id,perfil_id) VALUES (1,3);
INSERT INTO usuarios_perfis (usuario_id,perfil_id) VALUES (2,2);
INSERT INTO usuarios_perfis (usuario_id,perfil_id) VALUES (3,1);

INSERT INTO criterios (id, nome, descricao) VALUES (1,'Atendimento','Você foi bem atendido? De forma rápida, mas também eficiente? Perguntaram sobre forma de pagamento e troco?');
INSERT INTO criterios (id, nome, descricao) VALUES (2,'Comida','A comida estava boa? Veio o que foi pedido? Veio na temperatura adequada?');
INSERT INTO criterios (id, nome, descricao) VALUES (3,'Tempo de entrega','Informaram o tempo médio para entrega? Chegou no tempo previsto?');
INSERT INTO criterios (id, nome, descricao) VALUES (4,'Custo Benefício','Qual a relação custo/benefício? Valeu a pena o dinheiro investido?');