CREATE TABLE IF NOT EXISTS `sessions` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `userId` INT NOT NULL UNIQUE,
    `token` VARCHAR(600) NOT NULL,
    CONSTRAINT `fk_sessions_userId` FOREIGN KEY(`userId`) REFERENCES users(`id`)
);