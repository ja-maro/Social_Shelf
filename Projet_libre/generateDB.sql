/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  25/09/2022 16:09:30                      */
/*==============================================================*/


drop table if exists address;

drop table if exists describes;

drop table if exists event;

drop table if exists game;

drop table if exists game_played;

drop table if exists game_type;

drop table if exists message;

drop table if exists owns;

drop table if exists participants;

drop table if exists player;

/*==============================================================*/
/* Table : address                                              */
/*==============================================================*/
create table address
(
   id                   int not null,
   player_id            int not null,
   street               varchar(254) not null,
   postalCode           int not null,
   city                 varchar(254) not null,
   country              varchar(254) not null,
   disableDate          datetime,
   primary key (id)
);

/*==============================================================*/
/* Table : describes                                            */
/*==============================================================*/
create table describes
(
   type_id              int not null,
   game_id              int not null,
   primary key (type_id, game_id)
);

/*==============================================================*/
/* Table : event                                                */
/*==============================================================*/
create table event
(
   id                   int not null,
   player_id            int not null,
   address_id           int not null,
   title                varchar(100) not null,
   pitch                varchar(254),
   minPlayer            int not null,
   maxPlayer            int not null,
   duration             int not null,
   startDate            datetime not null,
   cancellationDate     datetime,
   primary key (id)
);

/*==============================================================*/
/* Table : game                                                 */
/*==============================================================*/
create table game
(
   id                   int not null,
   name                 varchar(254) not null,
   publisher            varchar(254) not null,
   description          varchar(4000),
   picture              varchar(254),
   minPlayer            int not null,
   maxPlayer            int not null,
   averageDuration      int,
   primary key (id)
);

/*==============================================================*/
/* Table : game_played                                          */
/*==============================================================*/
create table game_played
(
   event_id             int not null,
   game_id              int not null,
   primary key (event_id, game_id)
);

/*==============================================================*/
/* Table : game_type                                            */
/*==============================================================*/
create table game_type
(
   id                   int not null,
   name                 varchar(50) not null,
   primary key (id)
);

/*==============================================================*/
/* Table : message                                              */
/*==============================================================*/
create table message
(
   id                   int not null,
   player_id            int not null,
   event_id             int not null,
   date                 datetime not null,
   content              varchar(4000) not null,
   deleteDate           datetime,
   primary key (id)
);

/*==============================================================*/
/* Table : owns                                                 */
/*==============================================================*/
create table owns
(
   player_id            int not null,
   game_id              int not null,
   primary key (player_id, game_id)
);

/*==============================================================*/
/* Table : participants                                         */
/*==============================================================*/
create table participants
(
   player_id            int not null,
   event_id             int not null,
   primary key (player_id, event_id)
);

/*==============================================================*/
/* Table : player                                               */
/*==============================================================*/
create table player
(
   id                   int not null,
   nom                  varchar(254) not null,
   mail                 varchar(254) not null,
   password             varchar(254) not null,
   role                 varchar(10) not null,
   disableDate          datetime,
   primary key (id)
);

alter table address add constraint FK_association2 foreign key (player_id)
      references player (id) on delete restrict on update restrict;

alter table describes add constraint FK_decrit foreign key (game_id)
      references game (id) on delete restrict on update restrict;

alter table describes add constraint FK_decrit foreign key (type_id)
      references game_type (id) on delete restrict on update restrict;

alter table event add constraint FK_organizes foreign key (player_id)
      references player (id) on delete restrict on update restrict;

alter table event add constraint FK_takesPlaceIn foreign key (address_id)
      references address (id) on delete restrict on update restrict;

alter table game_played add constraint FK_association6 foreign key (event_id)
      references event (id) on delete restrict on update restrict;

alter table game_played add constraint FK_association6 foreign key (game_id)
      references game (id) on delete restrict on update restrict;

alter table message add constraint FK_authors foreign key (player_id)
      references player (id) on delete restrict on update restrict;

alter table message add constraint FK_displays foreign key (event_id)
      references event (id) on delete restrict on update restrict;

alter table owns add constraint FK_owns foreign key (game_id)
      references game (id) on delete restrict on update restrict;

alter table owns add constraint FK_owns foreign key (player_id)
      references player (id) on delete restrict on update restrict;

alter table participants add constraint FK_participates foreign key (event_id)
      references event (id) on delete restrict on update restrict;

alter table participants add constraint FK_participates foreign key (player_id)
      references player (id) on delete restrict on update restrict;

