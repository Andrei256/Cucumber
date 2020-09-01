insert into usr values
(1, null, '1', 'gaxiya8490@cams.com', '$2a$08$m7XS20392boX6PBhVALUDeiT06NDYYNNKx7mjAoRg9R0etb6c6bC.', '+375445678921', null, 'admin'),
(2, null, '1', 'koran27557@synevde.com', '$2a$08$H.POdTyHSllf89zVTsDpVO9ZdAdB/pIIXDVwDmgh73WJl9DXMYaTq', '+375257893129', 'Работаем без выходных! Прием заказов с 9:00 до 22:00. Доставка работает по г Минск с 09.00 до 02.00 ночи', 'shop1'),
(3, null, '1', 'fobejok259@qmrbe.com', '$2a$08$vr3WnanqZ0zRFuQJL5EjLei9mJwpeaDDUlp/71lNung2CTsYeUnxa', '+375295312204', 'Звоните - мы не подведем, а наоборот удивим вас качеством нашего обслуживания! Мы работаем без выходных и праздников.', 'shop2'),
(4, null, '1', 'cixesip299@qqmimpi.com', '$2a$08$4s7patCwypwVSeZpkWdkN.VzOklGClAMUtJbkzStFMVoFljt7v.fG', '+375334215681', null, 'user');

/*USERS PASSWORDS:
admin - 1111,
shop1 - 2222,
shop2 - 3333,
user - 4444;*/

insert into user_role values
(1, 'ADMIN'),
(2, 'SHOP'),
(3, 'SHOP'),
(4, 'USER');

insert into product values
(1, '1', 1, 'Android, экран 6.53" OLED (1080x2400), HiSilicon Kirin 985, ОЗУ 8 ГБ, флэш-память 128 ГБ, карты памяти, камера 40 Мп, аккумулятор 4000 мАч, 2 SIM', 'ad3aa15b14bf1519ef3ec33d3b668fe0.jpg', 'Honor', 'Honor 30 8Gb/128Gb Black'),
(2, '1', 1, 'Apple iOS, экран 6.1" IPS (828x1792), Apple A13 Bionic, ОЗУ 4 ГБ, флэш-память 64 ГБ, камера 12 Мп, аккумулятор 3046 мАч, 1 SIM', 'iphone11-black-select-2019_GEO_EMEA-1000x1000.png', 'Apple', 'iPhone 11 64GB'),
(3, '1', 2, '10.2" IPS (2160x1620), iPadOS, Apple A10, ОЗУ 3 ГБ, флэш-память 32 ГБ, цвет темно-серый', '094a1dafc5664a619460137ed06d0753.png', 'Apple', 'iPad 10.2" 32GB MW742'),
(4, '0', 3, '15.6" 1920 x 1080 IPS, AMD Ryzen 7 4800H 2900 МГц, 16 ГБ, SSD 512 ГБ, граф. адаптер: NVIDIA GeForce GTX 1660 Ti Max-Q 6 ГБ, без ОС, цвет крышки серый', 'igrovoy_noutbuk_asus_tuf_gaming_a15_fa506iu-hn216_1.jpg', 'Asus', 'ASUS TUF Gaming A15 FA506IU-HN216');

insert into offer values
(1, 1300, 1, 2),
(2, 1250, 1, 3),
(3, 1600, 2, 3),
(4, 1630, 2, 2),
(5, 2150, 3, 2);

insert into ordr values (1, 'г.Минск, пр-т Независимости 152, кв. 31', '+375334215681', 4, 2, 3, 1630, 1, '2020-08-20');

insert into review values (1, 'Супер', 'Отличная работа магазина', 4, 3);