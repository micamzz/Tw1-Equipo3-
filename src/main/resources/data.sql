INSERT INTO Usuario(id, email, password, rol, activo)
VALUES (null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

-- JUGADORES
INSERT INTO Jugador(id, nombre, apellido, posicion, precio, dni, foto, altura, peso, nacionalidad, edad,
                    fechaNacimiento, draft, experiencia)
VALUES
-- Golden State Warriors
(1, 'Stephen', 'Curry', 'BASE', 175000, 1, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201939.png',
 '6''2" (1.88m)', '185lb (84kg)', 'USA', 37, 'March 14, 1988', '2009 R1 Pick 7', 15),
(2, 'Moses', 'Moody', 'ALERO', 85000, 2, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630541.png',
 '6''6" (1.98m)', '205lb (93kg)', 'USA', 22, 'May 31, 2002', '2021 R1 Pick 14', 3),
(3, 'Draymond', 'Green', 'PIVOT', 120000, 3, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203110.png',
 '6''6" (1.98m)', '230lb (104kg)', 'USA', 34, 'March 4, 1990', '2012 R2 Pick 35', 12),
(4, 'Andrew', 'Wiggins', 'ALERO', 110000, 4, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203952.png',
 '6''7" (2.01m)', '197lb (89kg)', 'Canada', 29, 'February 23, 1995', '2014 R1 Pick 1', 10),
(5, 'Jonathan', 'Kuminga', 'ALERO', 90000, 5, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630228.png',
 '6''7" (2.01m)', '215lb (98kg)', 'Congo', 22, 'October 6, 2002', '2021 R1 Pick 7', 3),
(6, 'Kevon', 'Looney', 'PIVOT', 80000, 6, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1626172.png',
 '6''9" (2.06m)', '222lb (101kg)', 'USA', 28, 'February 6, 1996', '2015 R1 Pick 30', 9),

-- Boston Celtics
(7, 'Jayson', 'Tatum', 'ALERO', 160000, 7, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628369.png',
 '6''8" (2.03m)', '210lb (95kg)', 'USA', 26, 'March 3, 1998', '2017 R1 Pick 3', 7),
(8, 'Jaylen', 'Brown', 'ALERO', 145000, 8, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1627759.png',
 '6''6" (1.98m)', '223lb (101kg)', 'USA', 27, 'October 24, 1996', '2016 R1 Pick 3', 8),
(9, 'Jrue', 'Holiday', 'BASE', 125000, 9, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201950.png',
 '6''4" (1.93m)', '205lb (93kg)', 'USA', 34, 'June 12, 1990', '2009 R1 Pick 17', 15),
(10, 'Kristaps', 'Porzingis', 'PIVOT', 130000, 10, 'https://cdn.nba.com/headshots/nba/latest/1040x760/204001.png',
 '7''2" (2.18m)', '240lb (109kg)', 'Letonia', 28, 'August 2, 1995', '2015 R1 Pick 4', 9),
(11, 'Al', 'Horford', 'PIVOT', 100000, 11, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201143.png',
 '6''9" (2.06m)', '240lb (109kg)', 'Rep. Dom.', 38, 'June 3, 1986', '2007 R1 Pick 3', 17),
(12, 'Payton', 'Pritchard', 'BASE', 75000, 12, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630202.png',
 '6''1" (1.85m)', '195lb (88kg)', 'USA', 26, 'January 28, 1998', '2020 R1 Pick 26', 4),

-- Los Angeles Lakers
(13, 'LeBron', 'James', 'ALERO', 100000, 13, 'https://cdn.nba.com/headshots/nba/latest/1040x760/2544.png',
 '6''9" (2.06m)', '250lb (113kg)', 'USA', 40, 'December 30, 1984', '2003 R1 Pick 1', 21),
(14, 'Anthony', 'Davis', 'PIVOT', 165000, 14, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203076.png',
 '6''10" (2.08m)', '253lb (115kg)', 'USA', 31, 'March 11, 1993', '2012 R1 Pick 1', 12),
(15, 'Austin', 'Reaves', 'BASE', 95000, 15, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630559.png',
 '6''5" (1.96m)', '197lb (89kg)', 'USA', 26, 'May 29, 1998', '2021 UD', 3),
(16, 'Rui', 'Hachimura', 'ALERO', 90000, 16, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629628.png',
 '6''8" (2.03m)', '230lb (104kg)', 'Japon', 26, 'February 8, 1998', '2019 R1 Pick 9', 5),
(17, 'D Angelo', 'Russell', 'BASE', 110000, 17, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1626156.png',
 '6''4" (1.93m)', '193lb (88kg)', 'USA', 28, 'February 23, 1996', '2015 R1 Pick 2', 9),
(18, 'Jarred', 'Vanderbilt', 'PIVOT', 80000, 18, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629012.png',
 '6''9" (2.06m)', '214lb (97kg)', 'USA', 25, 'April 3, 1999', '2018 R2 Pick 41', 6),

-- Miami Heat
(19, 'Jimmy', 'Butler', 'ALERO', 125000, 19, 'https://cdn.nba.com/headshots/nba/latest/1040x760/202710.png',
 '6''7" (2.01m)', '230lb (104kg)', 'USA', 35, 'September 14, 1989', '2011 R1 Pick 30', 13),
(20, 'Bam', 'Adebayo', 'PIVOT', 120000, 20, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628389.png',
 '6''9" (2.06m)', '255lb (116kg)', 'USA', 27, 'July 18, 1997', '2017 R1 Pick 14', 7),
(21, 'Tyler', 'Herro', 'BASE', 115000, 21, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629639.png',
 '6''4" (1.93m)', '195lb (88kg)', 'USA', 24, 'January 20, 2000', '2019 R1 Pick 13', 5),
(22, 'Kevin', 'Love', 'PIVOT', 85000, 22, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201567.png',
 '6''8" (2.03m)', '251lb (114kg)', 'USA', 36, 'September 7, 1988', '2008 R1 Pick 5', 16),
(23, 'Duncan', 'Robinson', 'ALERO', 90000, 23, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629130.png',
 '6''7" (2.01m)', '215lb (98kg)', 'USA', 30, 'April 22, 1994', '2018 UD', 6),
(24, 'Nikola', 'Jovic', 'BASE', 75000, 24, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631107.png',
 '6''10" (2.08m)', '215lb (98kg)', 'Serbia', 21, 'June 8, 2003', '2022 R1 Pick 27', 2),

-- Jugadores libres
(25, 'Joel', 'Embiid', 'PIVOT', 200000, 25, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203954.png',
 '7''0" (2.13m)', '280lb (127kg)', 'Camerun', 30, 'March 16, 1994', '2014 R1 Pick 3', 9),
(26, 'Damian', 'Lillard', 'BASE', 185000, 26, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203081.png',
 '6''2" (1.88m)', '195lb (88kg)', 'USA', 34, 'July 15, 1990', '2012 R1 Pick 6', 12),
(27, 'Ja', 'Morant', 'BASE', 180000, 27, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629630.png',
 '6''2" (1.88m)', '174lb (79kg)', 'USA', 25, 'August 10, 1999', '2019 R1 Pick 2', 5),
(28, 'Karl-Anthony', 'Towns', 'PIVOT', 150000, 28, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1626157.png',
 '7''0" (2.13m)', '270lb (122kg)', 'USA', 29, 'November 15, 1995', '2015 R1 Pick 1', 9);

-- RENDIMIENTOS
INSERT INTO RendimientoJugador(jugador_id, puntos, rebotes, asistencias, robos, bloqueos, perdidas)
VALUES (1, 26, 5, 6, 1, 0, 2),
       (2, 14, 3, 2, 1, 0, 1),
       (3, 9, 7, 7, 1, 1, 2),
       (4, 17, 5, 2, 1, 0, 1),
       (5, 16, 5, 2, 1, 0, 1),
       (6, 6, 7, 1, 0, 1, 1),
       (7, 26, 8, 5, 1, 1, 2),
       (8, 23, 5, 4, 1, 0, 2),
       (9, 12, 4, 5, 2, 0, 1),
       (10, 20, 7, 2, 1, 2, 1),
       (11, 8, 6, 3, 1, 1, 1),
       (12, 11, 3, 3, 1, 0, 1),
       (13, 25, 7, 8, 1, 1, 3),
       (14, 24, 12, 3, 1, 2, 2),
       (15, 15, 4, 5, 1, 0, 1),
       (16, 13, 4, 2, 1, 0, 1),
       (17, 18, 3, 6, 1, 0, 2),
       (18, 7, 6, 1, 1, 1, 1),
       (19, 21, 5, 5, 2, 1, 2),
       (20, 19, 10, 3, 1, 1, 2),
       (21, 21, 4, 4, 1, 0, 2),
       (22, 8, 6, 2, 0, 0, 1),
       (23, 14, 4, 2, 1, 0, 1),
       (24, 10, 4, 3, 1, 0, 1),
       (25, 30, 11, 5, 1, 2, 3),
       (26, 24, 4, 7, 1, 0, 3),
       (27, 20, 5, 8, 1, 0, 3),
       (28, 21, 8, 4, 1, 1, 2);

-- TEMPORADA
INSERT INTO Temporada (id, nombre, anio, fechaInicio, fechaFin)
VALUES (1, 'Taller Web Unlam 2026', 2026, '2026-03-30', '2026-07-17');


-- EQUIPOS NBA
INSERT INTO EquipoNBA(id, nombre, escudoImagen)
VALUES (1, 'Golden State Warriors', 'https://cdn.nba.com/logos/nba/1610612744/global/L/logo.svg'),
       (2, 'Boston Celtics', 'https://cdn.nba.com/logos/nba/1610612738/global/L/logo.svg'),
       (3, 'Los Angeles Lakers', 'https://cdn.nba.com/logos/nba/1610612747/global/L/logo.svg'),
       (4, 'Miami Heat', 'https://cdn.nba.com/logos/nba/1610612748/global/L/logo.svg');

-- ASIGNACIONES EQUIPO-JUGADOR
INSERT INTO EquipoNBAJugador(equipoNBA_id, jugador_id)
VALUES
-- Golden State Warriors
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
-- Boston Celtics
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
-- Los Angeles Lakers
(3, 13),
(3, 14),
(3, 15),
(3, 16),
(3, 17),
(3, 18),
-- Miami Heat
(4, 19),
(4, 20),
(4, 21),
(4, 22),
(4, 23),
(4, 24);

---- EQUIPO TIENE TEMPORADA
UPDATE EquipoNBA
SET temporada_id = 1
WHERE id IN (1, 2, 3, 4);


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