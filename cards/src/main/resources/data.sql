insert ignore into card (id, number, holder_name, expiration, security_code, issuing_company, total_limit, available_limit, full_name, address, email, monthly_fee) values
(1, '1111222233334444', 'ANDERSON DA SILVA', '2022-07', '123', 'MasterCard', 4000.0, 3769.4, 'Anderson Manuel da Silva', 'Rua Bruxelas, 123, São Paulo - SP', 'anderson@silva.com', 7.0),
(2, '1234123412341234', 'CAROLINE SOUZA', '2023-06', '234', 'Visa', 15000.0, 15000.0, 'Caroline Mendes de Sousa', 'Rua Tupinambás, 456, Belo Horizonte - SP', 'carol@sousa.com', 0.0);

insert ignore into transaction (id, amount, created_at, description, status, uuid, card_id) values
(1, 80.9, '2021-06-18 13:17:17', 'Pantufa Slipper Chewbacca Star Wars Marrom', 'CREATED', '51df3c11-4839-465b-b991-4849929896ce', 1),
(2, 39.9, '2021-05-15 09:05:18', 'Camiseta Infantil Manga Curta Super Mario Azul Tam 4 a 10', 'CREATED', 'c57e19cb-702f-42b4-984f-6454d8c4606b', 1),
(3, 59.9, '2021-03-18 22:09:51', 'Camiseta com Capuz Fortnite Juvenil Preto Tam 12 a 18', 'CONFIRMED', '0e4ed209-6ab6-4db0-a297-80bdd5e0713f', 1),
(4, 39.9, '2021-03-15 12:19:13', 'Camiseta Plus Size Stranger Things Preto', 'CONFIRMED', 'fe8c0090-165f-401f-a2e0-3cbf31ec3d2d', 1);

insert ignore into fraud_verifier(id, type, enabled) values
(1, 'EXPENDS_ALL_LIMIT', true),
(2, 'TOO_FAST', true);