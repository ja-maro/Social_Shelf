SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE user;
insert into quest_web.user (id, creation_date, password, role, updated_date, username)
values  (1, '2022-08-30 22:30:18', '$2a$10$huURGOgbVCTW0s09PwJZIOOOWF34iY/iZwddsX0S.qrfXybI6L1FW', 'ROLE_USER', null, 'BriCe3');
SET FOREIGN_KEY_CHECKS = 1;