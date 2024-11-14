create table IF not exists expired_urls(
	id varchar(255) CHARACTER SET utf8 COLLATE utf8_bin unique not null ,
	id_original varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    url_original text not null,
    Url_nickname varchar(20) CHARACTER SET utf8 COLLATE utf8_bin not null,
    Clicks_count int not null default 0,
    Accesses_count int not null default 0,
    Created_date timestamp not null,
    Expiration_date timestamp not null,
    Url_password varchar(255) CHARACTER SET utf8 COLLATE utf8_bin default null,
    Created_by varchar(255) CHARACTER SET utf8 COLLATE utf8_bin default NULL,

    constraint expired_urls_id primary key(Id),
    foreign key(Created_by) references users(Id) on delete set null
);