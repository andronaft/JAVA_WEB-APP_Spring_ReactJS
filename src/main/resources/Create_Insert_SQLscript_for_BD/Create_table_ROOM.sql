create table ROOM
(
	ID int auto_increment,
	NAME CHAR(150) not null,
	FIRSTFLOORCAPACITY int not null,
	SECONDFLOORCAPACITY int not null,
	constraint ROOM_pk
		primary key (ID)
);