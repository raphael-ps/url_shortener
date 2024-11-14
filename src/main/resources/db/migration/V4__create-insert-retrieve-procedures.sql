delimiter //
CREATE PROCEDURE IF NOT EXISTS `InsertURL`(
	in input_url text,
    in nickname varchar(20),
    in expiration_date timestamp,
    in user_id varchar(255),
    in url_password varchar(255)
)
begin
	if (expiration_date is null) then
		set expiration_date = current_timestamp() + interval 3 day;
	end if;

	INSERT INTO urls (Original_url, Url_nickname, Expiration_date, Url_password, Created_by)
    VALUES (input_url, nickname, expiration_date, url_password, user_id);
end //

CREATE PROCEDURE IF NOT EXISTS `RetrieveUrl`(
    IN proc_url_nickname VARCHAR(20),
    IN proc_password VARCHAR(255),
    OUT proc_original_url_output text,
    OUT proc_message VARCHAR(100)
)
BEGIN
    DECLARE temp_stored_url text;
    declare temp_password varchar(255);
    declare temp_exp_date timestamp;

	select Original_url, Url_password, Expiration_date
    into temp_stored_url, temp_password, temp_exp_date
    from urls
    where Url_nickname = proc_url_nickname;

    set proc_original_url_output = null;

    If (temp_stored_url is NULL) then
		set proc_message = "URL Not Found";
	else
		if (temp_exp_date < CURRENT_TIMESTAMP) then
			set proc_message = "URL Expirado";
		elseif (not temp_password = proc_password) or (temp_password is not null and proc_password is null) then
			set proc_message = "Invalid Password";
		else
			set proc_message = "URL Retrieved Succefully";
			set proc_original_url_output = temp_stored_url;
            update urls set accesses = accesses + 1 where Url_nickname = proc_url_nickname;
		end if;
        update urls set clicks = clicks + 1 where Url_nickname = proc_url_nickname;
	end If;
END //

delimiter ;
