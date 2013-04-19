create table items (
	id smallint not null,
	item_type varchar(10), /* 'Album', 'Audiobook', or 'Film' */
	item_name varchar(1000),
	year_of_release smallint,
	duration int,
	genre varchar(50),
	preview varchar(1000),
	number_sold smallint,
	price float,
	hidden boolean,
	cumulative_ratings smallint,
	num_ratings smallint,
	creator varchar(400),

	constraint pk_items primary key (id)
);
create table users (
	username varchar(32),
	password varchar(200),
	billing_name varchar(200),
	address varchar(200),
	credit float,
	administrator boolean,
	purchase_history varchar(8000),
	ratings varchar(8000),
	constraint pk_users primary key (username)
);

insert into users
	(username, password, billing_name, address, credit, administrator, purchase_history, ratings) values
	('administrator', 'admin', '', '', 30.00, true, '', '');
insert into users
	(username, password, billing_name, address, credit, administrator, purchase_history, ratings) values
	('new user', 'password', 'name', 'address', 30.00, false, '1', '0'); --Starts with one purchased album
insert into users
	(username, password, billing_name, address, credit, administrator, purchase_history, ratings) values
	('Username', 'Password', 'name', 'address', 30.00, false, '', '');
insert into items
	(id, item_type, item_name, year_of_release, duration, genre, price, creator) values
	(1, 'Album', 'Congratulations', 2012, 3600, 'Synth Rock', 10, 'MGMT');
insert into items
	(id, item_type, item_name, year_of_release, duration, genre, price, creator) values
	(2, 'Album', 'A Wasteland Companion', 2012, 3630, 'Folk Rock', 5, 'M. Ward');
insert into items
	(id, item_type, item_name, year_of_release, duration, genre, price, creator) values
	(3, 'Audiobook', 'Life of Pi', 2003, 41700, 'Fiction', 13, 'Yann Martel');
insert into items
	(id, item_type, item_name, year_of_release, duration, genre, price, creator) values
	(4, 'Film', 'Kill Bill', 2003, 7200, 'Action', 5, 'Tarantino');

	
