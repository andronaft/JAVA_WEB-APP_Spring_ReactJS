create table CONFERENCE
(
	ID int auto_increment,
	NAME text not null,
	NAME_ROOM text,
	ID_ROOM int not null,
	ID_PARTICIPANT text,
	CAPACITY_ROOM int not null,
	AMOUNT_PARTICIPANT int not null,
	DATEE date not null,
	TIMEE time,
	constraint CONFERENCE_pk
		primary key (ID)
);