-- SET GLOBAL EVENT_SCHEDULER = ON MANUALLY ON DATABASE

delimiter //
CREATE EVENT IF NOT EXISTS move_expired_url
	ON  SCHEDULE EVERY 1 DAY
		STARTS CONCAT(current_date(), " 00:00:01")
	ON COMPLETION PRESERVE
DO
BEGIN

    INSERT INTO expired_url (url_id, user_id, nickname, clicks_count, accesses_count, creation_date, expiration_date)
    SELECT url_id, user_id, nickname, clicks_count, accesses_count, creation_date, expiration_date
    FROM short_url
    WHERE expiration_date <= current_timestamp;

	DELETE FROM short_url
    WHERE expiration_date < CURRENT_TIMESTAMP;
END //

DELIMITER ;