SELECT * FROM alkewalletmodulo6.users;
USE alkewalletmodulo6;
INSERT INTO users (user_id, first_name, last_name_1, last_name_2, email, password, balance, currency)
VALUES ('17266881-K', 'Javiera', 'Acevedo', 'Cabezas', 'invented.email@example.com', '$2a$10$lmzkHKzvzlSVLauLz8XGo.rer8MOU4DRpMP2AWU6k.W.mqHUIho3e', 100000, 'CLP');

USE alkewalletmodulo6;
UPDATE users 
SET password = '$2a$10$lmzkHKzvzlSVLauLz8XGo.rer8MOU4DRpMP2AWU6k.W.mqHUIho3e' 
WHERE user_id = '17266881-K';


USE alkewalletmodulo6;
INSERT INTO users (user_id, first_name, last_name_1, last_name_2, email, password, balance, currency)
VALUES ('11111111-1', 'Joaquin', 'Perez', 'Lopez', 'joaquin.perez@example.com', '$2a$10$zLrtE.0jRLFfEDcPOvvXyuBkkSeh1yVxtuPu4B4eFBUDKr310uiSO', 100000, 'CLP');

INSERT INTO users (user_id, first_name, last_name_1, last_name_2, email, password, balance, currency)
VALUES ('22222222-2', 'Juan', 'Pablo', 'Martinez', 'juan.pablo@example.com', '$2a$10$WmIhvPef0GgFu0lLTQ.2GOuo8CMk/djgoPPSEc.bg6EXy1aTQnEli', 150000, 'CLP');

INSERT INTO users (user_id, first_name, last_name_1, last_name_2, email, password, balance, currency)
VALUES ('33333333-3', 'Adriana', 'Gomez', 'Rodriguez', 'adriana.gomez@example.com', '$2a$10$Bz4eU27Nns1/UkrrEN2nE.5KDYANVmZtY6akdmRrauiPtI908/LUO', 200000, 'CLP');

INSERT INTO users (user_id, first_name, last_name_1, last_name_2, email, password, balance, currency)
VALUES ('44444444-4', 'Roberto', 'Diaz', 'Fernandez', 'roberto.diaz@example.com', '$2a$10$HlEAC5TUutoVZFpCJxOjDehwj6LSUTuzLDkuKKslABrxjBN.O0rhO', 250000, 'CLP');

INSERT INTO users (user_id, first_name, last_name_1, last_name_2, email, password, balance, currency)
VALUES ('55555555-5', 'Gabriel', 'Hernandez', 'Mendoza', 'gabriel.hernandez@example.com', '$2a$10$Ifc50LNFunMGuPxoDQzPTukx7kocU4Uvc48.znnsRcuv0Bj7E92Ky', 300000, 'CLP');


USE alkewalletmodulo6;
DELETE FROM users WHERE user_id != '17266881-K';

USE alkewalletmodulo6;
ALTER TABLE transactions ADD COLUMN currency VARCHAR(3) DEFAULT 'CLP';

USE alkewalletmodulo6;
-- Añadir la columna a la tabla transactions
ALTER TABLE transactions ADD COLUMN name_target_user_id VARCHAR(255);

-- Actualizar la nueva columna con los valores concatenados de la tabla users
USE alkewalletmodulo6;
UPDATE transactions t
JOIN users u ON t.target_user_id = u.user_id
SET t.name_target_user_id = CONCAT(u.first_name, ' ', u.last_name_1, ' ', u.last_name_2);

USE alkewalletmodulo6;
CREATE TABLE contactos (
    contacto_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(12),
    contact_user_id VARCHAR(12),
    name_contact_user_id VARCHAR(200),
    email VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (contact_user_id) REFERENCES users(user_id));
    
USE alkewalletmodulo6;
INSERT INTO contactos (user_id, contact_user_id, name_contact_user_id, email)
VALUES ('17266881-K', '22980791-8', 'Joaco Soto Acevedo', 'prueba@gmail.com');

USE alkewalletmodulo6;
INSERT INTO contactos (user_id, contact_user_id, name_contact_user_id, email)
VALUES ('17266881-K', '26480679-9', 'Juan Pablo Acevedo', 'juanito@ejemplo.cl');




