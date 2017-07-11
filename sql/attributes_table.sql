
-- Create a table to store predefined types of an attribute

create table attribute_type(
	id int not null generated always as identity
	constraint attribute_pk primary key,
	a_type varchar(50) not null
);
insert into attribute_type(a_type)
	values ('interger'),
	('Decimal'), 
	('Boolean'), 
	('Text');
	


