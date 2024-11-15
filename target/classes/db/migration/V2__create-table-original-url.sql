CREATE TABLE IF NOT EXISTS original_url(
	id CHAR(36) NOT NULL,
    url VARCHAR(2048) NOT NULL,
    times_shortened INT DEFAULT 0,

    PRIMARY KEY (id),
    UNIQUE(url(512))
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;