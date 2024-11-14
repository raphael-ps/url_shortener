create table IF not exists urls(
	id varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL unique,
    original_url text not null,
    url_nickname varchar(20) CHARACTER SET utf8 COLLATE utf8_bin unique not null ,
    clicks int not null default 0,
    accesses int not null default 0,
    created_date timestamp default current_timestamp,
    expiration_date timestamp not null,
    url_password varchar(255) CHARACTER SET utf8 COLLATE utf8_bin default null,
    created_by varchar(255) CHARACTER SET utf8 COLLATE utf8_bin default null,

    primary key(Id),
    foreign key(Created_by) references users(Id) on delete set null,
    INDEX idx_expiration_date (Expiration_date)
);