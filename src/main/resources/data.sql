-- Users examples
-- password: imagina
insert into users(nif, email, password, name, last_name, number_phone, enabled, status, created_at) values ('21525241D', 'fernando@example.com', '$2a$10$qINz45KQzkLHtzOovvgjn.y1SKh8zfR5Meyh61FHz3bvSlO4uez.O', 'Fernando', 'Alonso García', '123456789', true, 'pendiente', '2021-05-14 08:35:50');
insert into users(nif, email, password, name, last_name, number_phone, enabled, status, created_at) values ('12545421H', 'ned@example.com', '$2a$10$qINz45KQzkLHtzOovvgjn.y1SKh8zfR5Meyh61FHz3bvSlO4uez.O', 'Ned', 'Flandes', '325652147', true, 'pendiente', '2021-05-14 08:35:50');
insert into users(nif, email, password, name, last_name, number_phone, enabled, status, created_at) values ('65215478S', 'juan@example.com', '$2a$10$qINz45KQzkLHtzOovvgjn.y1SKh8zfR5Meyh61FHz3bvSlO4uez.O', 'Juan', 'Fernandez García', '652325471', true, 'validado', '2021-05-14 08:35:50');
insert into users(nif, email, password, name, last_name, number_phone, enabled, status, created_at) values ('96574852L', 'maria@example.com', '$2a$10$qINz45KQzkLHtzOovvgjn.y1SKh8zfR5Meyh61FHz3bvSlO4uez.O', 'María', 'Rivera García', '965856547', true, 'validado', '2021-05-14 08:35:50');

-- Categories examples
insert into categories(name) values ('Ocio');
insert into categories(name) values ('Gasolina');
insert into categories(name) values ('Restauración');
insert into categories(name) values ('Alimentación');
insert into categories(name) values ('Otros');

-- Bank Account examples
INSERT INTO bank_accounts (id, balance, created_at, deleted, enabled, num_account, updated_at) VALUES(1, 3155.0, '2020-02-11 11:00:00.779829000', 0, 1, 'ES5455006510809800604001', NULL);
INSERT INTO bank_accounts (id, balance, created_at, deleted, enabled, num_account, updated_at) VALUES(2, 4545.0, '2019-02-11 23:00:00.579329000', 0, 1, 'ES6763411850043966660600', NULL);
INSERT INTO bank_accounts (id, balance, created_at, deleted, enabled, num_account, updated_at) VALUES(3, 700.0, '2019-02-11 12:00:00.279329000', 0, 1, 'ES7050412140599008089518', NULL);
INSERT INTO bank_accounts (id, balance, created_at, deleted, enabled, num_account, updated_at) VALUES(4, 500.0, '2019-02-11 21:00:00.179329000', 0, 1, 'ES6763400850043966660600', NULL);
INSERT INTO bank_accounts (id, balance, created_at, deleted, enabled, num_account, updated_at) VALUES(5, 0.0, '2021-05-24 21:40:01.479329000', 0, 1, 'ES6969715810330369060091', NULL);
INSERT INTO bank_accounts (id, balance, created_at, deleted, enabled, num_account, updated_at) VALUES(6, 0.0, '2021-05-24 21:41:42.632885000', 1, 1, 'ES7700290200512380580842', NULL);



-- Users - Bank Accounts relation
insert into users_bank_accounts(user_id, bank_account_id) values (1, 1);
insert into users_bank_accounts(user_id, bank_account_id) values (1, 2);
insert into users_bank_accounts(user_id, bank_account_id) values (2, 2);
insert into users_bank_accounts(user_id, bank_account_id) values (3, 4);
insert into users_bank_accounts(user_id, bank_account_id) values (3, 1);
insert into users_bank_accounts(user_id, bank_account_id) values (4, 4);

-- Credit Card examples

INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(1, 'MasterCard', '2021-05-14 08:35:50', '239', 0, 1, '2022-02-11', '5254646244652394', '1234', 'Thomas Conley', 'debito', NULL, 1);
INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(2, 'Visa', '2021-05-14 08:35:50', '122', 0, 1, '2022-02-11', '8254645534652344', '1234', 'John Connor', 'credito', NULL, 2);
INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(3, 'Visa', '2021-05-24 21:29:35.920672000', '244', 0, 1, '2024-10-11', '4328529948622506', '7583', 'Maria Conley', 'debito', NULL, 3);
INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(4, 'MasterCard', '2021-05-24 21:30:40.869573000', '471', 0, 1, '2023-07-11', '5228001299641830', '5171', 'Fia Trantham', 'debito', NULL, 4);
INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(5, 'American Express', '2021-05-24 21:31:53.995448000', '445', 0, 1, '2024-01-11', '376284018517181', '6635', 'Kaysen Aguilera', 'debito', NULL, 1);
INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(6, 'MasterCard', '2021-05-24 21:33:16.440620000', '755', 0, 1, '2026-04-21', '5576802823868346', '7495', 'Camron Paz', 'debito', NULL, 2);
INSERT INTO credit_cards (id, card_provider, created_at, cvv, deleted, enabled, expiration_date, num_credit_card, pin, placeholder, `type`, updated_at, id_user) VALUES(7, 'Visa', '2021-05-24 21:34:09.119202000', '170', 0, 1, '2025-04-21', '4309574815549294', '8682', 'Rayanna Blain', 'debito', NULL, 3);


-- Transaction examples

INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(1, 2445.0, 'abono caja3', '2021-01-20 13:14:17.170196000', 100.0, NULL, 'ABONO', 1, 2, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(2, 3445.0, 'compra televisor', '2021-02-20 13:16:02.672396000', 1000.0, NULL, 'TRANSFERENCIA', 1, 1, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(3, 3405.0, 'fruta', '2021-03-17 13:16:51.672396000', 40.0, NULL, 'PAGO', 1, 2, 1);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(4, 3155.0, 'gas', '2021-03-20 17:17:28.210196000', 250.0, NULL, 'RECIBO', 1, 4, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(5, 3500.0, 'bizum comida', '2021-04-10 13:18:07.670196000', 345.0, NULL, 'ABONO', 1, 2, 1);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(6, 4000.0, 'beneficios bitcoin', '2021-04-10 13:18:49.990196000', 500.0, NULL, 'TRANSFERENCIA', 1, 5, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(7, 3911.0, 'internet', '2021-04-21 13:19:10.870196000', 89.0, NULL, 'RECIBO', 1, 5, 1);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(8, 3722.0, 'luz', '2021-04-14 13:19:26.670116000', 189.0, NULL, 'PAGO', 1, 5, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(9, 3672.0, 'agua', '2021-04-28 13:19:32.270196000', 50.0, NULL, 'PAGO', 1, 2, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(10, 3500.0, 'bizum viaje', '2021-04-27 13:18:07.670136000', 1000.0, NULL, 'ABONO', 1, 2, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(11, 500.0, 'pago portatil', '2021-05-04 20:29:47.503415000', 500.0, NULL, 'ABONO', 4, 1, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(12, 750.0, 'mensualidad alquiler', '2021-05-04 20:31:31.637321000', 750.0, NULL, 'TRANSFERENCIA', 3, 2, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(13, 3095.0, 'compra ebay', '2021-05-06 20:32:53.670196000', 250.0, NULL, 'PAGO', 2, 3, 2);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(14, 1795.0, 'compra amazon', '2021-05-06 20:33:41.349070000', 550.0, NULL, 'PAGO', 1, 3, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(15, 700.0, 'compra aliexpress', '2021-05-10 20:33:56.365523000', 50.0, NULL, 'PAGO', 3, 3, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(16, 3245.0, 'reparacion coche', '2021-05-11 20:35:15.797710000', 1450.0, NULL, 'ABONO', 1, 5, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(17, 4545.0, 'viaje canarias', '2021-05-11 20:35:30.791501000', 1450.0, NULL, 'ABONO', 2, 4, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(18, 3155.0, 'compra mercadona', '2021-05-11 20:38:35.745394000', 90.0, NULL, 'PAGO', 1, 4, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(19, 900.0, 'regalo boda', '2021-05-13 22:18:43.245676000', 200.0, NULL, 'ABONO', 3, 4, 3);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(20, 650.0, 'factura luz', '2021-05-24 22:20:06.834923000', 250.0, NULL, 'RECIBO', 3, 3, NULL);
INSERT INTO transactions (id, balance_after_transaction, concepto, created_date, importe, last_modified, tipo_movimiento, id_bank_account, id_category, id_credit_card)
VALUES(21, 750.0, 'venta wallapop', '2021-05-24 22:21:41.770916000', 250.0, NULL, 'ABONO', 4, 2, 4);

