CREATE TABLE user_data (
       id			   VARCHAR(64),
       user_login      VARCHAR(64)	   NOT NULL,
       user_password   VARCHAR(64)     NOT NULL,
       security_level  INTEGER NOT NULL,
       PRIMARY KEY (id),
       UNIQUE (id, user_login)
);

create table todo_lists (
	id varchar(255) not null, 
	title varchar(255) not null,
	USER_ID varchar(255) not null,
	primary key(id)); 	

create table todo_items (
	id varchar(255) not null, 
	title varchar(255) not null, 
	USER_ID varchar(255) not null, 
	todo_list_id varchar(255) not null,
	primary key (id));	
	
create table cookies (
	id 			varchar(255) not null, 
	user_id 	varchar(64) not null, 
	create_date date not null, 
	primary key (id));

alter table cookies add constraint FK_COOKIE_USER foreign key (USER_ID) references user_data;
alter table todo_lists add constraint FK_TODO_LISTS_USER foreign key (USER_ID) references user_data;
alter table todo_items add constraint FK_TODO_ITEMS_USER foreign key (USER_ID) references user_data;
alter table todo_items add constraint FK_TODO_ITEMS_TODO_LISTS foreign key (todo_list_id) references todo_lists;