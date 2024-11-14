-- SET GLOBAL EVENT_SCHEDULER = ON MANUALLY ON DATABASE

delimiter //
CREATE EVENT IF NOT EXISTS MoveExpiredUrls
	ON  SCHEDULE every 1 day
		starts concat(current_date(), " 00:00:01")
	ON COMPLETION PRESERVE
DO
BEGIN

    INSERT INTO expired_urls (Original_Id, Original_url, Url_nickname, Clicks_count, ACCESSES_count, Created_date, Expiration_date, Url_password, Created_by)
    SELECT Id, Original_url, Url_nickname, Clicks_count, ACCESSES_count, Created_date, Expiration_date, Url_password, Created_by
    FROM urls
    WHERE Expiration_date <= current_timestamp;

	DELETE FROM urls
    WHERE Expiration_date < CURRENT_TIMESTAMP;
END //

DELIMITER ;