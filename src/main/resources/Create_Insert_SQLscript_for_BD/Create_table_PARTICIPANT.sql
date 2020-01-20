create table PARTICIPANT
(
	ID int auto_increment,
	FIRSTNAME VARCHAR not null,
	LASTNAME VARCHAR not null,
	LOGIN VARCHAR not null,
	PASSWORD VARCHAR not null,
	ROLE VARCHAR,
	BIRTHDAY DATE not null,
	ID_CONFERENCE_PARTICIPANT TEXT,
	constraint PARTICIPANT_pk
		primary key (ID)
);
