CREATE TABLE IF NOT EXISTS expired_url(
	id CHAR(36) NOT NULL,
    user_id CHAR(36) DEFAULT NULL,
    url_id CHAR(36) NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    clicks_count INT NOT NULL,
    accesses_count INT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    moved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE SET NULL,
    FOREIGN KEY(url_id) REFERENCES original_url(id) ON DELETE CASCADE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;