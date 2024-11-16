CREATE TABLE IF NOT EXISTS short_url(
	id CHAR(36) NOT NULL,
    user_id CHAR(36) DEFAULT NULL,
    url_id CHAR(36) NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    clicks_count INT NOT NULL DEFAULT 0,
    accesses_count INT NOT NULL DEFAULT 0,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiration_date TIMESTAMP NOT NULL,
    password VARCHAR(100) DEFAULT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY(url_id) REFERENCES original_url(id) ON DELETE CASCADE,
    UNIQUE(nickname),
    INDEX idx_expiration_date (expiration_date)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;