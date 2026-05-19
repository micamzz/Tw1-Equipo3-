INSERT INTO Usuario(id, email, password, rol, activo)
VALUES (null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);
INSERT INTO Jugador(nombre, apellido, posicion, precio, dni)
VALUES
-- BASES
('Stephen', 'Curry', 'BASE', 6500000, 1),
('Luka', 'Doncic', 'BASE', 6200000, 2),
('Ja', 'Morant', 'BASE', 5400000, 3),
('Damian', 'Lillard', 'BASE', 5600000, 4),
('Trae', 'Young', 'BASE', 5100000, 5),
('Shai', 'Gilgeous-Alexander', 'BASE', 6300000, 6),
('Kyrie', 'Irving', 'BASE', 5000000, 7),

-- ALEROS
('LeBron', 'James', 'ALERO', 7000000, 8),
('Kevin', 'Durant', 'ALERO', 6700000, 9),
('Jayson', 'Tatum', 'ALERO', 6100000, 10),
('Jimmy', 'Butler', 'ALERO', 5200000, 11),
('Kawhi', 'Leonard', 'ALERO', 5900000, 12),
('Anthony', 'Edwards', 'ALERO', 5500000, 13),
('Paul', 'George', 'ALERO', 5000000, 14),

-- PIVOTS
('Nikola', 'Jokic', 'PIVOT', 7200000, 15),
('Joel', 'Embiid', 'PIVOT', 6900000, 16),
('Giannis', 'Antetokounmpo', 'PIVOT', 7100000, 17),
('Anthony', 'Davis', 'PIVOT', 6000000, 18),
('Bam', 'Adebayo', 'PIVOT', 4800000, 19),
('Domantas', 'Sabonis', 'PIVOT', 5300000, 20),
('Rudy', 'Gobert', 'PIVOT', 4700000, 21);