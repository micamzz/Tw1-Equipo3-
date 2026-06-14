INSERT INTO Usuario(id, email, password, rol, activo)
VALUES (null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- TEMPORADA
INSERT INTO Temporada (id, nombre, anio, fechaInicio, fechaFin)
VALUES (1, 'Taller Web Unlam 2026', 2026, '2026-03-30', '2026-07-17');

-- EQUIPOS NBA
INSERT INTO EquipoNBA(id, nombre, escudoImagen)
VALUES (1, 'Golden State Warriors', 'https://cdn.nba.com/logos/nba/1610612744/global/L/logo.svg'),
       (2, 'Boston Celtics', 'https://cdn.nba.com/logos/nba/1610612738/global/L/logo.svg'),
       (3, 'Los Angeles Lakers', 'https://cdn.nba.com/logos/nba/1610612747/global/L/logo.svg'),
       (4, 'Miami Heat', 'https://cdn.nba.com/logos/nba/1610612748/global/L/logo.svg');

-- JUGADORES
INSERT INTO Jugador(id, nombre, apellido, posicion, precio, dni, foto, altura, peso, nacionalidad, edad,
                    fechaNacimiento, draft, experiencia)
VALUES
-- Golden State Warriors
(1, 'Stephen', 'Curry', 'BASE', 175000, 1, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201939.png',
 '6''3" (1.91m)', '185lb (84kg)', 'USA', 37, 'March 14, 1988', '2009 R1 Pick 7', 16),
(2, 'Draymond', 'Green', 'PIVOT', 90000, 2, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203110.png',
 '6''7" (2.01m)', '230lb (104kg)', 'USA', 35, 'March 4, 1990', '2012 R2 Pick 35', 13),
(3, 'Moses', 'Moody', 'ALERO', 80000, 3, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630541.png',
 '6''5" (1.96m)', '205lb (93kg)', 'USA', 23, 'May 31, 2002', '2021 R1 Pick 14', 4),
(4, 'Brandin', 'Podziemski', 'BASE', 85000, 4, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1641705.png',
 '6''4" (1.93m)', '200lb (91kg)', 'USA', 22, 'February 25, 2003', '2023 R1 Pick 19', 2),
(5, 'Jonathan', 'Kuminga', 'ALERO', 95000, 5, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630228.png',
 '6''7" (2.01m)', '215lb (98kg)', 'Congo', 22, 'October 6, 2002', '2021 R1 Pick 7', 4),
(6, 'Gui', 'Santos', 'ALERO', 70000, 6, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631217.png',
 '6''6" (1.98m)', '210lb (95kg)', 'Brasil', 23, 'June 22, 2002', '2022 R2 Pick 55', 3),
(7, 'Kevon', 'Looney', 'PIVOT', 70000, 7, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1626172.png',
 '6''9" (2.06m)', '222lb (101kg)', 'USA', 29, 'February 6, 1996', '2015 R1 Pick 30', 10),
(8, 'Quinten', 'Post', 'PIVOT', 65000, 8, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1642366.png',
 '7''0" (2.13m)', '240lb (109kg)', 'Holanda', 25, 'March 21, 2000', '2024 R2 Pick 52', 1),

-- Boston Celtics
(9, 'Jaylen', 'Brown', 'ALERO', 160000, 9, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1627759.png',
 '6''7" (2.01m)', '223lb (101kg)', 'USA', 29, 'October 24, 1996', '2016 R1 Pick 3', 9),
(10, 'Jayson', 'Tatum', 'ALERO', 150000, 10, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628369.png',
 '6''8" (2.03m)', '210lb (95kg)', 'USA', 27, 'March 3, 1998', '2017 R1 Pick 3', 8),
(11, 'Payton', 'Pritchard', 'BASE', 95000, 11, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630202.png',
 '6''1" (1.85m)', '195lb (88kg)', 'USA', 27, 'January 28, 1998', '2020 R1 Pick 26', 5),
(12, 'Derrick', 'White', 'BASE', 105000, 12, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628401.png',
 '6''4" (1.93m)', '190lb (86kg)', 'USA', 31, 'July 2, 1994', '2017 R1 Pick 29', 7),
(13, 'Al', 'Horford', 'PIVOT', 80000, 13, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201143.png',
 '6''9" (2.06m)', '240lb (109kg)', 'Rep. Dom.', 38, 'June 3, 1986', '2007 R1 Pick 3', 18),
(14, 'Neemias', 'Queta', 'PIVOT', 75000, 14, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629664.png',
 '7''0" (2.13m)', '245lb (111kg)', 'Portugal', 26, 'July 13, 1999', '2021 R2 Pick 39', 4),
(15, 'Sam', 'Hauser', 'ALERO', 70000, 15, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629774.png',
 '6''7" (2.01m)', '210lb (95kg)', 'USA', 27, 'December 8, 1997', 'UD', 3),
(16, 'Jrue', 'Holiday', 'BASE', 100000, 16, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201950.png',
 '6''4" (1.93m)', '205lb (93kg)', 'USA', 35, 'June 12, 1990', '2009 R1 Pick 17', 16),

-- Los Angeles Lakers
(17, 'LeBron', 'James', 'ALERO', 100000, 17, 'https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png',
 '6''9" (2.06m)', '250lb (113kg)', 'USA', 41, 'December 30, 1984', '2003 R1 Pick 1', 22),
(18, 'Luka', 'Doncic', 'BASE', 185000, 18, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629029.png',
 '6''6" (1.98m)', '230lb (104kg)', 'Eslovenia', 26, 'February 28, 1999', '2018 R1 Pick 3', 7),
(19, 'Austin', 'Reaves', 'BASE', 120000, 19, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630559.png',
 '6''5" (1.96m)', '197lb (89kg)', 'USA', 27, 'May 29, 1998', '2021 UD', 4),
(20, 'Rui', 'Hachimura', 'ALERO', 90000, 20, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629628.png',
 '6''8" (2.03m)', '230lb (104kg)', 'Japon', 27, 'February 8, 1998', '2019 R1 Pick 9', 6),
(21, 'Deandre', 'Ayton', 'PIVOT', 110000, 21, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629028.png',
 '7''1" (2.16m)', '250lb (113kg)', 'Bahamas', 27, 'July 23, 1998', '2018 R1 Pick 1', 7),
(22, 'Dorian', 'Finney-Smith', 'ALERO', 80000, 22, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628384.png',
 '6''7" (2.01m)', '220lb (100kg)', 'USA', 31, 'May 4, 1993', 'UD', 8),
(23, 'Jarred', 'Vanderbilt', 'PIVOT', 70000, 23, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629012.png',
 '6''9" (2.06m)', '214lb (97kg)', 'USA', 26, 'April 3, 1999', '2018 R2 Pick 41', 7),
(24, 'Jake', 'LaRavia', 'ALERO', 65000, 24, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631108.png',
 '6''7" (2.01m)', '210lb (95kg)', 'USA', 23, 'November 3, 2001', '2022 R1 Pick 19', 3),

-- Miami Heat
(25, 'Bam', 'Adebayo', 'PIVOT', 140000, 25, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628389.png',
 '6''9" (2.06m)', '255lb (116kg)', 'USA', 28, 'July 18, 1997', '2017 R1 Pick 14', 8),
(26, 'Tyler', 'Herro', 'BASE', 110000, 26, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629639.png',
 '6''5" (1.96m)', '195lb (88kg)', 'USA', 25, 'January 20, 2000', '2019 R1 Pick 13', 6),
(27, 'Jaime', 'Jaquez Jr.', 'ALERO', 85000, 27, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1641727.png',
 '6''6" (1.98m)', '210lb (95kg)', 'USA', 24, 'February 18, 2001', '2023 R1 Pick 18', 2),
(28, 'Norman', 'Powell', 'ALERO', 100000, 28, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1626181.png',
 '6''4" (1.93m)', '215lb (98kg)', 'USA', 32, 'May 25, 1993', '2015 R2 Pick 46', 10),
(29, 'Kel''el', 'Ware', 'PIVOT', 70000, 29, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1642362.png',
 '7''0" (2.13m)', '230lb (104kg)', 'USA', 21, 'April 20, 2004', '2024 R1 Pick 15', 1),
(30, 'Davion', 'Mitchell', 'BASE', 75000, 30, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630218.png',
 '6''0" (1.83m)', '195lb (88kg)', 'USA', 27, 'September 5, 1998', '2021 R1 Pick 9', 4),
(31, 'Nikola', 'Jovic', 'PIVOT', 65000, 31, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631107.png',
 '6''10" (2.08m)', '215lb (98kg)', 'Serbia', 22, 'June 9, 2003', '2022 R1 Pick 27', 3),
(32, 'Duncan', 'Robinson', 'ALERO', 90000, 32, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629130.png',
 '6''7" (2.01m)', '215lb (98kg)', 'USA', 31, 'April 22, 1994', '2018 UD', 7),

-- Jugadores libres
(33, 'Giannis', 'Antetokounmpo', 'PIVOT', 220000, 33, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203507.png',
 '6''11" (2.11m)', '242lb (110kg)', 'Grecia', 31, 'December 6, 1994', '2013 R1 Pick 15', 12),
(34, 'Kevin', 'Durant', 'ALERO', 200000, 34, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201142.png',
 '6''10" (2.08m)', '240lb (109kg)', 'USA', 36, 'September 29, 1988', '2007 R1 Pick 2', 17),
(35, 'Nikola', 'Jokic', 'PIVOT', 210000, 35, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203999.png',
 '6''11" (2.11m)', '284lb (129kg)', 'Serbia', 31, 'February 19, 1995', '2014 R2 Pick 41', 10),
(36, 'Shai', 'Gilgeous-Alexander', 'BASE', 195000, 36, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628983.png',
 '6''6" (1.98m)', '195lb (88kg)', 'Canada', 26, 'July 12, 1998', '2018 R1 Pick 11', 7),
(37, 'Anthony', 'Edwards', 'ALERO', 185000, 37, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630162.png',
 '6''4" (1.93m)', '225lb (102kg)', 'USA', 24, 'August 5, 2001', '2020 R1 Pick 1', 5);

-- RENDIMIENTOS
INSERT INTO RendimientoJugador(jugador_id, puntos, rebotes, asistencias, robos, bloqueos, perdidas)
VALUES
-- GSW
(1, 27, 4, 5, 1, 0, 3),
(2, 8, 6, 6, 1, 1, 2),
(3, 12, 3, 2, 1, 0, 1),
(4, 14, 5, 4, 1, 0, 2),
(5, 12, 6, 3, 1, 0, 2),
(6, 9, 4, 2, 1, 0, 1),
(7, 6, 7, 1, 0, 1, 1),
(8, 8, 4, 1, 0, 1, 1),
-- BOS
(9, 29, 7, 5, 1, 0, 2),
(10, 22, 10, 5, 1, 1, 2),
(11, 17, 4, 5, 1, 0, 2),
(12, 17, 4, 5, 1, 1, 1),
(13, 8, 6, 3, 1, 1, 1),
(14, 10, 8, 2, 1, 2, 1),
(15, 9, 4, 2, 0, 0, 1),
(16, 12, 4, 5, 2, 0, 1),
-- LAL
(17, 21, 6, 7, 1, 1, 3),
(18, 34, 8, 8, 1, 0, 4),
(19, 23, 5, 6, 1, 0, 2),
(20, 12, 3, 1, 1, 0, 1),
(21, 13, 8, 1, 1, 1, 2),
(22, 10, 4, 2, 1, 0, 1),
(23, 4, 5, 1, 1, 1, 1),
(24, 8, 4, 2, 1, 0, 1),
-- MIA
(25, 20, 10, 3, 1, 1, 2),
(26, 21, 5, 4, 1, 0, 2),
(27, 15, 5, 5, 2, 0, 2),
(28, 22, 4, 3, 1, 0, 2),
(29, 11, 9, 1, 0, 2, 1),
(30, 9, 3, 7, 2, 0, 2),
(31, 7, 3, 2, 1, 0, 1),
(32, 14, 4, 2, 1, 0, 1),
-- Libres
(33, 28, 11, 6, 1, 2, 3),
(34, 26, 7, 4, 1, 2, 2),
(35, 29, 13, 9, 1, 1, 3),
(36, 32, 5, 7, 2, 1, 3),
(37, 27, 5, 4, 2, 0, 3);


-- ASIGNACIONES EquipoNBAJugador con temporada
INSERT INTO EquipoNBAJugador(equipoNBA_id, jugador_id, temporada_id)
VALUES
-- Golden State Warriors
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(1, 4, 1),
(1, 5, 1),
(1, 6, 1),
(1, 7, 1),
(1, 8, 1),
-- Boston Celtics
(2, 9, 1),
(2, 10, 1),
(2, 11, 1),
(2, 12, 1),
(2, 13, 1),
(2, 14, 1),
(2, 15, 1),
(2, 16, 1),
-- Los Angeles Lakers
(3, 17, 1),
(3, 18, 1),
(3, 19, 1),
(3, 20, 1),
(3, 21, 1),
(3, 22, 1),
(3, 23, 1),
(3, 24, 1),
-- Miami Heat
(4, 25, 1),
(4, 26, 1),
(4, 27, 1),
(4, 28, 1),
(4, 29, 1),
(4, 30, 1),
(4, 31, 1),
(4, 32, 1);


-- TORNEO
INSERT INTO `tw1`.`torneo`
(`id`,
 `fechaFin`,
 `fechaInicio`,
 `nombreTorneo`,
 `TipoTorneo`)
VALUES (1,
        '2026-12-31',
        '2026-01-01',
        'Temporada NBA 2026',
        'REAL');


INSERT INTO `tw1`.`torneo`
(`id`,
 `fechaFin`,
 `fechaInicio`,
 `nombreTorneo`,
 `TipoTorneo`)
VALUES (2,
        '2026-12-31',
        '2026-01-01',
        'UNLAM Basquet 2026',
        'VIRTUAL');



INSERT INTO Calendario (nombre)
VALUES ('Temporada 2026');

INSERT INTO PartidoNBA (equipoLocal, equipoVisitante, fechaYhora, calendario_id)
VALUES ('Spurs', 'Knicks', '2026-06-04 22:30:00', 1);

INSERT INTO PartidoNBA (equipoLocal, equipoVisitante, fechaYhora, calendario_id)
VALUES ('Knicks', 'Spurs', '2026-06-06 21:30:00', 1);

INSERT INTO PartidoNBA (equipoLocal, equipoVisitante, fechaYhora, calendario_id)
VALUES ('Spurs', 'Nets', '2026-06-09 22:30:00', 1);

INSERT INTO PartidoNBA (equipoLocal, equipoVisitante, fechaYhora, calendario_id)
VALUES ('Spurs', 'Mavericks', '2026-06-11 22:00:00', 1);