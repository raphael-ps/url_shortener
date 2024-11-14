create table if not exists users(
	id varchar(255) CHARACTER SET utf8 COLLATE utf8_bin unique not null,
    username varchar(30) unique not null,
    user_password VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin,
    user_email varchar(255) unique not null,
    primary key(id)
);