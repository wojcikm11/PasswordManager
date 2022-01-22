CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(40) NOT NULL,
    password VARCHAR(200) NOT NULL,
    master_password VARCHAR(200) NOT NULL,
    not_locked BOOL NOT NULL DEFAULT true,
    lock_time DATETIME,
    failed_login_attempts INT DEFAULT 0
);


CREATE TABLE IF NOT EXISTS service_password (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    url VARCHAR(2083) NOT NULL,
    password VARCHAR(200) NOT NULL,
    iv VARBINARY(16) NOT NULL,
    salt VARCHAR(64) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS device (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    details VARCHAR(250) NOT NULL,
    last_logged_in DATETIME NOT NULL,
    verified BOOL NOT NULL DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
