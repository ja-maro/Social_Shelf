SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE user;
TRUNCATE TABLE address;
insert into quest_web.user (id, creation_date, password, role, updated_date, username)
values  (1, '2022-08-30 22:30:18', '$2a$10$huURGOgbVCTW0s09PwJZIOOOWF34iY/iZwddsX0S.qrfXybI6L1FW', 'ROLE_USER', null, 'Brice'),
        (2, '2022-08-30 22:30:18', '$2a$10$huURGOgbVCTW0s09PwJZIOOOWF34iY/iZwddsX0S.qrfXybI6L1FW', 'ROLE_ADMIN', null, 'JeanAntoine');

insert into quest_web.address (id, city, country, creation_date, postal_code, street, updated_date, user_id)
values  (1, 'Gagny', 'France', '2022-09-07 09:48:56', '93220', 'rue de la mairie', '2022-09-07 09:48:56', 1),
        (2, 'Villemomble', 'France', '2022-09-07 09:51:23', '93250', 'rue de la paix', '2022-09-07 09:51:23', 2);
SET FOREIGN_KEY_CHECKS = 1;