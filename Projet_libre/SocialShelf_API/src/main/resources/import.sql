SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE player;
TRUNCATE TABLE address;
TRUNCATE TABLE game;
TRUNCATE TABLE type;
TRUNCATE TABLE games_types;
TRUNCATE TABLE players_games;

INSERT INTO `social_shelf`.`player` (`id`, `creation_date`, `email`, `password`, `role`, `updated_date`, `username`) VALUES ('1', '2022-10-01 14:49:26', 'brice@brice.fr', '$2a$10$huURGOgbVCTW0s09PwJZIOOOWF34iY/iZwddsX0S.qrfXybI6L1FW', 'ROLE_USER', '2022-10-01 14:49:26', 'Brice');
INSERT INTO `social_shelf`.`player` (`id`, `creation_date`, `email`, `password`, `role`, `updated_date`, `username`) VALUES ('2', '2022-10-01 14:49:26', 'ja@mail.com', '$2a$10$huURGOgbVCTW0s09PwJZIOOOWF34iY/iZwddsX0S.qrfXybI6L1FW', 'ROLE_ADMIN', '2022-10-01 14:49:26', 'Jean-Antoine');

INSERT INTO `social_shelf`.`address` (`id`, `city`, `country`, `creation_date`, `postal_code`, `street`, `updated_date`, `player_id`) VALUES ('1', 'Gagny', 'France', '2022-10-01 14:49:26', '93220', '1 rue de la mairie', '2022-10-01 14:49:26', '1');
INSERT INTO `social_shelf`.`address` (`id`, `city`, `country`, `creation_date`, `postal_code`, `street`, `updated_date`, `player_id`) VALUES ('2', 'Paris', 'France', '2022-10-01 14:49:26', '75001', '22 rue de Rivoli', '2022-10-01 14:49:26', '2');


INSERT INTO `social_shelf`.`game` (`id`, `average_duration`, `creation_date`, `description`, `max_player`, `min_player`, `name`, `publisher`, `updated_date`) VALUES ('1', '20', '2022-10-01 14:49:26', 'L\'objectif du jeu Uno est simple. Il s\'agit d\'être le premier joueur de la table à ne plus avoir de cartes en main. À la fin d\'une manche, le vainqueur établit les scores en additionnant tous les points des mains des autres joueurs.', '10', '2', 'Uno', 'Mattel', '2022-10-01 14:49:26');		
INSERT INTO `social_shelf`.`game` (`id`, `average_duration`, `creation_date`, `description`, `max_player`, `min_player`, `name`, `publisher`, `updated_date`) VALUES ('2', '45', '2022-10-01 14:49:26', 'Dans 7 Wonders, vous êtes à la tête de l\'une des sept grandes cités du monde antique. Votre but est de faire prospérer votre ville pour la rendre plus influente que celles de vos adversaires. Le futur des cités légendaires comme Babylone, Éphèse ou encore Rhodes dépend de vos talents de gestionnaire. Pour inscrire votre cité dans l\'Histoire, vous devrez agir dans différents secteurs de développement. Exploitez les ressources naturelles de vos terres, participez aux progrès scientifiques, développez vos relations commerciales et affirmez votre suprématie militaire. Laissez votre empreinte dans l\'histoire des civilisations en bâtissant une merveille monumentale.', '7', '3', '7 Wonders', 'Repos Production', '2022-10-01 14:49:26');
INSERT INTO `social_shelf`.`game` (`id`, `average_duration`, `creation_date`, `description`, `max_player`, `min_player`, `name`, `publisher`, `updated_date`) VALUES ('3', '90', '2022-10-01 14:49:26', 'À vous les joies et les peines de l\'exploration de l\'île de Catane. Prenez le contrôle d\'un maximum de territoires en construisant villages, villes, ports et routes. Profitez au mieux des ressources de cette île si accueillante tout en commerçant avec vos voisins. Mais faites attention au brigand noir. La présence de ce terrible chevalier hante l\'île et peut freiner vos ardeurs de colonisateurs.', '4', '3', 'Catan', 'Kosmos', '2022-10-01 14:49:26');
INSERT INTO `social_shelf`.`game` (`id`, `average_duration`, `creation_date`, `description`, `max_player`, `min_player`, `name`, `publisher`, `updated_date`) VALUES ('4', '90', '2022-10-01 14:49:26', 'Aeon\'s End est un jeu coopératif qui explore le mécanisme de deck building avec un certain nombre de mécanismes innovants et des règles de gestion de deck qui exigent une planification minutieuse.', '4', '1', 'Aeon\'s End', 'Matagot', '2022-10-01 14:49:26');


INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('1', '2022-08-30 22:30:18', 'Coopération', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('2', '2022-08-30 22:30:18', 'Cartes', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('3', '2022-08-30 22:30:18', 'Draft', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('4', '2022-08-30 22:30:18', 'Commerce', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('5', '2022-08-30 22:30:18', 'Construction', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('6', '2022-08-30 22:30:18', 'Deck building', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('7', '2022-08-30 22:30:18', 'Historique', '2022-08-30 22:30:18');
INSERT INTO `social_shelf`.`type` (`id`, `creation_date`, `name`, `updated_date`) VALUES ('8', '2022-08-30 22:30:18', 'Fantastique', '2022-08-30 22:30:18');


INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('1', '2');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('2', '2');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('2', '3');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('2', '4');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('2', '5');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('2', '7');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('3', '4');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('3', '5');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('4', '1');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('4', '2');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('4', '6');
INSERT INTO `social_shelf`.`games_types` (`game_id`, `type_id`) VALUES ('4', '8');

INSERT INTO `social_shelf`.`players_games` (`player_id`, `game_id`) VALUES ('1', '1');
INSERT INTO `social_shelf`.`players_games` (`player_id`, `game_id`) VALUES ('2', '3');
INSERT INTO `social_shelf`.`players_games` (`player_id`, `game_id`) VALUES ('2', '4');
INSERT INTO `social_shelf`.`players_games` (`player_id`, `game_id`) VALUES ('2', '2');

INSERT INTO `social_shelf`.`event` (`id`, `creation_date`, `duration`, `max_participants`, `min_participants`, `pitch`, `start_date`, `title`, `updated_date`, `game_id`, `organizer_id`, `place_id`) VALUES ('1', '2022-09-29 14:39:26', '150', '3', '1', 'Venez découvrir Aeon\'s End, un super jeu coop ! Je ferai des gâteaux, amenez à boire !', '2022-10-21 18:00:00', 'Aeon\'sEnd à Paris', '2022-09-29 14:39:26', '4', '2', '2');
INSERT INTO `social_shelf`.`event` (`id`, `creation_date`, `duration`, `max_participants`, `min_participants`, `pitch`, `start_date`, `title`, `updated_date`, `game_id`, `organizer_id`, `place_id`) VALUES ('2', '2022-09-29 14:39:26', '40', '9', '2', 'Quelques parties de Uno sur l\'heure de pause dej ?', '2022-10-14 12:00:00', 'Uno chez moi', '2022-09-29 14:39:26', '1', '1', '1');


INSERT INTO `social_shelf`.`participants` (`player_id`, `event_id`) VALUES ('1', '1');

		
SET FOREIGN_KEY_CHECKS = 1;