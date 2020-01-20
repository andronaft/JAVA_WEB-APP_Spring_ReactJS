create table CONFERENCE
(
	ID int auto_increment,
	NAME text not null,
	ID_ROOM int not null,
	ID_PARTICIPANT int,
	CAPACITY_ROOM int not null,
	AMOUNT_PARTICIPANT int not null,
	DATEE date not null,
	TIMEE time,
	constraint CONFERENCE_pk
		primary key (ID)
);