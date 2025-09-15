CREATE DATABASE zabora;
USE zabora;
drop database zabora;

CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) NOT NULL UNIQUE,
   password VARCHAR(100) NOT NULL,
   email VARCHAR(100) NOT NULL UNIQUE,
   role ENUM('ADMIN', 'USER', 'CLIENT') NOT NULL,
   reset_token VARCHAR(255),
   reset_token_expiry DATETIME
);

INSERT INTO users (username, password, email, role)
VALUES ('admin', '$2a$12$5s2s3s4s5s6s7s8s9s0s1s2s', 'admin@example.com', 'ADMIN');

CREATE TABLE recipes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    ingredients TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select * from users;
select * from recipes;
select * from user_saved_recipes;

CREATE TABLE user_saved_recipes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    recipe_id BIGINT NOT NULL,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
    UNIQUE(user_id, recipe_id) -- evita que el usuario guarde la misma receta dos veces
);
