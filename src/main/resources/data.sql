-- LIMPIEZA DE DATOS AL INICIO
-- TIENE UPDATE EL HIBERNATECONFG PERO HAY QUE BORRAR LOS DATOS
--POR LAS KEY DUPLICADAS.
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE RendimientoJugador;
TRUNCATE TABLE EquipoJugador;
TRUNCATE TABLE EquipoNBAJugador;
TRUNCATE TABLE CronologiaNBA;
TRUNCATE TABLE PartidoNBA;
TRUNCATE TABLE Equipo;
TRUNCATE TABLE EquipoNBA;
TRUNCATE TABLE Jugador;
TRUNCATE TABLE Fecha;
TRUNCATE TABLE Torneo;
TRUNCATE TABLE Temporada;
TRUNCATE TABLE Usuario;
SET FOREIGN_KEY_CHECKS = 1;
-- ====================================

INSERT INTO Usuario(id, nombre, email, password, rol, activo)
VALUES (null, 'Admin Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true),
       (null, 'Micaela', 'mica@unlam.com', 'test', 'ADMIN', true),
       (null, 'Lucas', 'lucas@unlam.com', 'test', 'USER', true),
       (4, 'Juan', 'juan@unlam.com', 'test', 'USER', true),
       (5, 'Marcos', 'marcos@unlam.com', 'test', 'USER', true),
       (6, 'Sofia', 'sofia@unlam.com', 'test', 'USER', true),
       (7, 'Valentina', 'valentina@unlam.com', 'test', 'USER', true);


/* TEMPORADA VIEJA 2025 08 DE MARZO A 22 DE DICIEMBRE*/
INSERT INTO Temporada (id, nombre, anio, fechaInicio, fechaFin)
VALUES (1, 'Temporada 2025', 2025, '2025-03-08', '2025-12-23');

/* TEMPORADA ACTUAL ID 2 - 2026 DE 01 MARZO A 21 DE DICIEMBRE*/
INSERT INTO Temporada (id, nombre, anio, fechaInicio, fechaFin)
VALUES (2, 'Temporada 2026', 2026, '2026-03-01', '2026-12-21');

/* TORNEO REAL 07   DE MARZO A 20 DE DICIEMBRE */
INSERT INTO Torneo (id, fechaFin, fechaInicio, nombreTorneo, tipoTorneo, temporada_id)
VALUES (1, '2026-12-20', '2026-03-07', 'NBA 2026', 'REAL', 2);

INSERT INTO Torneo (id, fechaFin, fechaInicio, nombreTorneo, tipoTorneo, temporada_id)
VALUES (2, '2026-12-20', '2026-03-07', 'UNLAM Basquet 2026', 'VIRTUAL', 2);

/* TORNEO REAL PASADO DEL 2025 ASOCIADO A TEMPORADA ID 1  DE MARZO A DE DICIEMBRE */
INSERT INTO Torneo (id, fechaFin, fechaInicio, nombreTorneo, tipoTorneo, temporada_id)
VALUES (3, '2025-12-20', '2025-03-01', 'Torneo 2025', 'REAL', 1);

-- CAMBIE LA ASOCIACION AL TORNEO ID 1 QUE ES EL TORNO REAL.
INSERT INTO Fecha (id, numero_fecha, estado, torneo_id)
VALUES (1, 1, 'FINALIZADA', 1);

INSERT INTO Fecha (id, numero_fecha, estado, torneo_id)
VALUES (2, 2, 'EN_CURSO', 1);

INSERT INTO Fecha (id, numero_fecha, estado, torneo_id)
VALUES (3, 3, 'PROGRAMADA', 1);


INSERT INTO EquipoNBA(id, nombre, escudoImagen)
VALUES (1, 'Golden State Warriors', 'https://cdn.nba.com/logos/nba/1610612744/global/L/logo.svg'),
       (2, 'Boston Celtics', 'https://cdn.nba.com/logos/nba/1610612738/global/L/logo.svg'),
       (3, 'Los Angeles Lakers', 'https://cdn.nba.com/logos/nba/1610612747/global/L/logo.svg'),
       (4, 'Miami Heat', 'https://cdn.nba.com/logos/nba/1610612748/global/L/logo.svg'),
       (5, 'San Antonio Spurs', 'https://cdn.nba.com/logos/nba/1610612759/global/L/logo.svg'),
       (6, 'New York Knicks', 'https://cdn.nba.com/logos/nba/1610612752/global/L/logo.svg'),
       (7, 'Brooklyn Nets', 'https://cdn.nba.com/logos/nba/1610612751/global/L/logo.svg'),
       (8, 'Dallas Mavericks', 'https://cdn.nba.com/logos/nba/1610612742/global/L/logo.svg');

INSERT INTO Jugador(id, nombre, apellido, posicion, precio, dni, foto, altura, peso, nacionalidad, edad,
                    fechaNacimiento, draft, experiencia)
VALUES (1, 'Stephen', 'Curry', 'BASE', 175000, 1, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201939.png',
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
       (22, 'Dorian', 'Finney-Smith', 'ALERO', 80000, 22,
        'https://cdn.nba.com/headshots/nba/latest/1040x760/1628384.png', '6''7" (2.01m)', '220lb (100kg)', 'USA', 31,
        'May 4, 1993', 'UD', 8),
       (23, 'Jarred', 'Vanderbilt', 'PIVOT', 70000, 23, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629012.png',
        '6''9" (2.06m)', '214lb (97kg)', 'USA', 26, 'April 3, 1999', '2018 R2 Pick 41', 7),
       (24, 'Jake', 'LaRavia', 'ALERO', 65000, 24, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631108.png',
        '6''7" (2.01m)', '210lb (95kg)', 'USA', 23, 'November 3, 2001', '2022 R1 Pick 19', 3),
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
       (33, 'Giannis', 'Antetokounmpo', 'PIVOT', 220000, 33,
        'https://cdn.nba.com/headshots/nba/latest/1040x760/203507.png', '6''11" (2.11m)', '242lb (110kg)', 'Grecia', 31,
        'December 6, 1994', '2013 R1 Pick 15', 12),
       (34, 'Kevin', 'Durant', 'ALERO', 200000, 34, 'https://cdn.nba.com/headshots/nba/latest/1040x760/201142.png',
        '6''10" (2.08m)', '240lb (109kg)', 'USA', 36, 'September 29, 1988', '2007 R1 Pick 2', 17),
       (35, 'Nikola', 'Jokic', 'PIVOT', 210000, 35, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203999.png',
        '6''11" (2.11m)', '284lb (129kg)', 'Serbia', 31, 'February 19, 1995', '2014 R2 Pick 41', 10),
       (36, 'Shai', 'Gilgeous-Alexander', 'BASE', 195000, 36,
        'https://cdn.nba.com/headshots/nba/latest/1040x760/1628983.png', '6''6" (1.98m)', '195lb (88kg)', 'Canada', 26,
        'July 12, 1998', '2018 R1 Pick 11', 7),
       (37, 'Anthony', 'Edwards', 'ALERO', 185000, 37, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630162.png',
        '6''4" (1.93m)', '225lb (102kg)', 'USA', 24, 'August 5, 2001', '2020 R1 Pick 1', 5),
       (38, 'Victor', 'Wembanyama', 'PIVOT', 180000, 38,
        'https://cdn.nba.com/headshots/nba/latest/1040x760/1641706.png', '7''3\" (2.24m)', '229lb (104kg)', 'Francia',
        21, 'January 4, 2004', '2023 R1 Pick 1', 2),
       (39, 'Devin', 'Vassell', 'ALERO', 100000, 39, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630170.png',
        '6''5\" (1.96m)', '200lb (91kg)', 'USA', 25, 'October 23, 2000', '2020 R1 Pick 11', 5),
       (40, 'Jeremy', 'Sochan', 'ALERO', 90000, 40, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631109.png',
        '6''9\" (2.06m)', '230lb (104kg)', 'Polonia', 22, 'May 20, 2003', '2022 R1 Pick 9', 3),
       (41, 'Chris', 'Paul', 'BASE', 75000, 41, 'https://cdn.nba.com/headshots/nba/latest/1040x760/101108.png',
        '6''0\" (1.83m)', '175lb (79kg)', 'USA', 40, 'May 6, 1985', '2005 R1 Pick 4', 20),
       (42, 'Jalen', 'Brunson', 'BASE', 160000, 42, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628973.png',
        '6''2\" (1.88m)', '190lb (86kg)', 'USA', 29, 'December 31, 1996', '2018 R2 Pick 33', 7),
       (43, 'Karl-Anthony', 'Towns', 'PIVOT', 170000, 43,
        'https://cdn.nba.com/headshots/nba/latest/1040x760/1626157.png', '7''0\" (2.13m)', '270lb (122kg)', 'USA', 30,
        'November 15, 1995', '2015 R1 Pick 1', 10),
       (44, 'OG', 'Anunoby', 'ALERO', 120000, 44, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628384.png',
        '6''7\" (2.01m)', '232lb (105kg)', 'Nigeria', 27, 'July 17, 1997', '2017 R1 Pick 23', 8),
       (45, 'Mikal', 'Bridges', 'ALERO', 105000, 45, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628969.png',
        '6''7\" (2.01m)', '209lb (95kg)', 'USA', 28, 'August 30, 1996', '2018 R1 Pick 10', 7),
       (46, 'Cam', 'Thomas', 'BASE', 110000, 46, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630560.png',
        '6''4\" (1.93m)', '210lb (95kg)', 'USA', 23, 'December 3, 2001', '2021 R1 Pick 27', 4),
       (47, 'Nic', 'Claxton', 'PIVOT', 85000, 47, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629651.png',
        '6''11\" (2.11m)', '215lb (98kg)', 'USA', 26, 'April 18, 1999', '2019 R2 Pick 31', 6),
       (48, 'Ben', 'Simmons', 'BASE', 80000, 48, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1627732.png',
        '6''10\" (2.08m)', '240lb (109kg)', 'Australia', 28, 'July 20, 1996', '2016 R1 Pick 1', 9),
       (49, 'Day''Ron', 'Sharpe', 'PIVOT', 70000, 49, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630549.png',
        '6''10\" (2.08m)', '260lb (118kg)', 'USA', 23, 'December 3, 2001', '2021 R2 Pick 29', 4),
       (50, 'Dennis', 'Schroder', 'BASE', 75000, 50, 'https://cdn.nba.com/headshots/nba/latest/1040x760/203471.png',
        '6''1\" (1.85m)', '180lb (82kg)', 'Alemania', 32, 'September 15, 1993', '2013 R1 Pick 17', 12),
       (51, 'Kyrie', 'Irving', 'BASE', 155000, 51, 'https://cdn.nba.com/headshots/nba/latest/1040x760/202681.png',
        '6''2\" (1.88m)', '193lb (88kg)', 'USA', 34, 'March 23, 1992', '2011 R1 Pick 1', 14),
       (52, 'PJ', 'Washington', 'ALERO', 95000, 52, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629023.png',
        '6''7\" (2.01m)', '230lb (104kg)', 'USA', 27, 'August 23, 1998', '2019 R1 Pick 12', 6),
       (53, 'Quentin', 'Grimes', 'ALERO', 80000, 53, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630208.png',
        '6''5\" (1.96m)', '210lb (95kg)', 'USA', 25, 'May 8, 2000', '2021 R1 Pick 25', 4),
       (54, 'Daniel', 'Gafford', 'PIVOT', 85000, 54, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629655.png',
        '6''11\" (2.11m)', '234lb (106kg)', 'USA', 26, 'October 1, 1998', '2019 R2 Pick 38', 6),
       (55, 'Josh', 'Green', 'ALERO', 75000, 55, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630182.png',
        '6''5\" (1.96m)', '210lb (95kg)', 'Australia', 24, 'November 16, 2000', '2020 R1 Pick 18', 5),
       (56, 'Klay', 'Thompson', 'ALERO', 100000, 56, 'https://cdn.nba.com/headshots/nba/latest/1040x760/202691.png',
        '6''6\" (1.98m)', '215lb (98kg)', 'USA', 35, 'February 8, 1990', '2011 R1 Pick 11', 14),
       (63, 'Zion', 'Williamson', 'PIVOT', 150000, 63, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629627.png',
        '6''6\" (1.98m)', '284lb (129kg)', 'USA', 24, 'July 6, 2000', '2019 R1 Pick 1', 6),
       (64, 'Trae', 'Young', 'BASE', 155000, 64, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629029.png',
        '6''1\" (1.85m)', '180lb (82kg)', 'USA', 27, 'September 19, 1998', '2018 R1 Pick 5', 7),
       (65, 'Donovan', 'Mitchell', 'BASE', 150000, 65, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628378.png',
        '6''1\" (1.85m)', '215lb (98kg)', 'USA', 28, 'September 7, 1996', '2017 R1 Pick 13', 8),
       (66, 'Jaren', 'Jackson Jr.', 'PIVOT', 130000, 66,
        'https://cdn.nba.com/headshots/nba/latest/1040x760/1628991.png', '6''11\" (2.11m)', '242lb (110kg)', 'USA', 25,
        'September 15, 1999', '2018 R1 Pick 4', 7),
       (67, 'De''Aaron', 'Fox', 'BASE', 145000, 67, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1628368.png',
        '6''3\" (1.91m)', '186lb (84kg)', 'USA', 28, 'December 20, 1997', '2017 R1 Pick 5', 8),
       (68, 'Darius', 'Garland', 'BASE', 120000, 68, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1629636.png',
        '6''1\" (1.85m)', '192lb (87kg)', 'USA', 25, 'January 26, 2000', '2019 R1 Pick 5', 6),


-- 6 Jugadores libres,mati del futuro no les asignes equipo por favor
       (57, 'Paolo', 'Banchero', 'ALERO', 130000, 57, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631094.png',
        '6''10\" (2.08m)', '250lb (113kg)', 'USA', 23, 'November 12, 2002', '2022 R1 Pick 1', 3),
       (58, 'Cade', 'Cunningham', 'BASE', 140000, 58, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630595.png',
        '6''6\" (1.98m)', '220lb (100kg)', 'USA', 24, 'September 25, 2001', '2021 R1 Pick 1', 4),
       (59, 'Evan', 'Mobley', 'PIVOT', 120000, 59, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630596.png',
        '7''0\" (2.13m)', '215lb (98kg)', 'USA', 24, 'June 18, 2001', '2021 R1 Pick 3', 4),
       (60, 'Franz', 'Wagner', 'ALERO', 115000, 60, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630532.png',
        '6''10\" (2.08m)', '220lb (100kg)', 'Alemania', 24, 'August 27, 2001', '2021 R1 Pick 8', 4),
       (61, 'Scottie', 'Barnes', 'ALERO', 110000, 61, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1630567.png',
        '6''8\" (2.03m)', '225lb (102kg)', 'USA', 25, 'August 1, 2001', '2021 R1 Pick 4', 4),
       (62, 'Jabari', 'Smith Jr.', 'ALERO', 105000, 62, 'https://cdn.nba.com/headshots/nba/latest/1040x760/1631095.png',
        '6''10\" (2.08m)', '220lb (100kg)', 'USA', 23, 'November 27, 2002', '2022 R1 Pick 3', 3);


INSERT INTO RendimientoJugador(jugador_id, puntos, rebotes, asistencias, robos, bloqueos, perdidas, torneo_id)
VALUES (1, 27, 4, 5, 1, 0, 3, 1),
       (2, 8, 6, 6, 1, 1, 2, 1),
       (3, 12, 3, 2, 1, 0, 1, 1),
       (4, 14, 5, 4, 1, 0, 2, 1),
       (5, 12, 6, 3, 1, 0, 2, 1),
       (6, 9, 4, 2, 1, 0, 1, 1),
       (7, 6, 7, 1, 0, 1, 1, 1),
       (8, 8, 4, 1, 0, 1, 1, 1),
       (9, 29, 7, 5, 1, 0, 2, 1),
       (10, 22, 10, 5, 1, 1, 2, 1),
       (11, 17, 4, 5, 1, 0, 2, 1),
       (12, 17, 4, 5, 1, 1, 1, 1),
       (13, 8, 6, 3, 1, 1, 1, 1),
       (14, 10, 8, 2, 1, 2, 1, 1),
       (15, 9, 4, 2, 0, 0, 1, 1),
       (16, 12, 4, 5, 2, 0, 1, 1),
       (17, 21, 6, 7, 1, 1, 3, 1),
       (18, 34, 8, 8, 1, 0, 4, 1),
       (19, 23, 5, 6, 1, 0, 2, 1),
       (20, 12, 3, 1, 1, 0, 1, 1),
       (21, 13, 8, 1, 1, 1, 2, 1),
       (22, 10, 4, 2, 1, 0, 1, 1),
       (23, 4, 5, 1, 1, 1, 1, 1),
       (24, 8, 4, 2, 1, 0, 1, 1),
       (25, 20, 10, 3, 1, 1, 2, 1),
       (26, 21, 5, 4, 1, 0, 2, 1),
       (27, 15, 5, 5, 2, 0, 2, 1),
       (28, 22, 4, 3, 1, 0, 2, 1),
       (29, 11, 9, 1, 0, 2, 1, 1),
       (30, 9, 3, 7, 2, 0, 2, 1),
       (31, 7, 3, 2, 1, 0, 1, 1),
       (32, 14, 4, 2, 1, 0, 1, 1),
       (33, 28, 11, 6, 1, 2, 3, 1),
       (34, 26, 7, 4, 1, 2, 2, 1),
       (35, 29, 13, 9, 1, 1, 3, 1),
       (36, 32, 5, 7, 2, 1, 3, 1),
       (37, 27, 5, 4, 2, 0, 3, 1),
       (38, 24, 10, 3, 1, 3, 2, 1),
       (39, 15, 4, 3, 1, 0, 2, 1),
       (40, 12, 5, 3, 1, 0, 2, 1),
       (41, 8, 3, 8, 2, 0, 2, 1),
       (42, 26, 4, 7, 1, 0, 3, 1),
       (43, 24, 9, 3, 1, 1, 2, 1),
       (44, 17, 5, 2, 2, 1, 1, 1),
       (45, 19, 4, 3, 2, 0, 2, 1),
       (46, 22, 4, 3, 1, 0, 2, 1),
       (47, 12, 9, 1, 1, 3, 1, 1),
       (48, 10, 7, 8, 2, 1, 3, 1),
       (49, 8, 8, 1, 0, 2, 1, 1),
       (50, 14, 3, 6, 2, 0, 2, 1),
       (51, 24, 5, 6, 2, 0, 3, 1),
       (52, 15, 6, 3, 1, 0, 2, 1),
       (53, 13, 4, 3, 1, 0, 1, 1),
       (54, 11, 7, 1, 0, 2, 1, 1),
       (55, 10, 4, 2, 1, 0, 1, 1),
       (56, 18, 4, 2, 1, 0, 1, 1),
       (57, 22, 8, 4, 1, 1, 2, 1),
       (58, 26, 5, 7, 2, 0, 3, 1),
       (59, 18, 9, 2, 1, 3, 1, 1),
       (60, 20, 6, 4, 1, 1, 2, 1),
       (61, 19, 7, 4, 2, 1, 2, 1),
       (62, 16, 7, 2, 1, 2, 1, 1),
       (63, 26, 7, 5, 1, 1, 3, 1),
       (64, 28, 4, 10, 1, 0, 4, 1),
       (65, 27, 5, 6, 2, 0, 3, 1),
       (66, 22, 8, 2, 1, 3, 2, 1),
       (67, 29, 4, 7, 2, 0, 3, 1),
       (68, 22, 3, 8, 1, 0, 3, 1);


/* JUGADORES ASOCIADO A EQUIPO EN TORNEO VIGENTE (2026)*/
-- Golden State Warriors (equipoNBA_id = 1)
INSERT INTO EquipoNBAJugador(equipoNBA_id, jugador_id, torneo_id)
VALUES (1, 1, 1),
       (1, 2, 1),
       (1, 3, 1),
       (1, 34, 1),
       (1, 4, 1),
       (1, 5, 1),
       (1, 7, 1),
-- Boston Celtics (equipoNBA_id = 2)
       (2, 9, 1),
       (2, 10, 1),
       (2, 11, 1),
       (2, 12, 1),
       (2, 13, 1),
       (2, 16, 1),
       (2, 38, 1),
       (2, 39, 1),
-- Los Angeles Lakers (equipoNBA_id = 3)
       (3, 17, 1),
       (3, 19, 1),
       (3, 20, 1),
       (3, 22, 1),
       (3, 23, 1),
       (3, 21, 1),
       (3, 36, 1),
       (3, 24, 1),
-- Miami Heat (equipoNBA_id = 4)
       (4, 25, 1),
       (4, 26, 1),
       (4, 27, 1),
       (4, 28, 1),
       (4, 29, 1),
       (4, 30, 1),
       (4, 40, 1),
       (4, 42, 1),
-- San Antonio Spurs (equipoNBA_id = 5)
       (5, 63, 1),
       (5, 64, 1),
       (5, 67, 1),
       (5, 43, 1),
       (5, 44, 1),
-- New York Knicks (equipoNBA_id = 6)
       (6, 65, 1),
       (6, 66, 1),
       (6, 68, 1),
       (6, 45, 1),
       (6, 46, 1),
-- Brooklyn Nets (equipoNBA_id = 7)
       (7, 41, 1),
       (7, 50, 1),
       (7, 56, 1),
       (7, 47, 1),
-- Dallas Mavericks (equipoNBA_id = 8)
       (8, 51, 1),
       (8, 52, 1),
       (8, 53, 1),
       (8, 54, 1),
       (8, 55, 1),
       (8, 48, 1),
       (8, 49, 1);

-- 6 jugadores libres (57-62): no asignados a ningun equipo en el torneo vigente,
-- quedan disponibles para que un equipo nuevo los pueda fichar

-- TORNEO 2025 - ASOCIADO A TORNEO ID 3
-- FALTA ASOCIAR JUGADORES A EQUIPO 1 2 3 4 AL TORNEO VIEJO
-- San Antonio Spurs (equipoNBA_id = 5)
INSERT INTO EquipoNBAJugador(equipoNBA_id, jugador_id, torneo_id)
VALUES (5, 33, 3),
       (5, 34, 3),
       (5, 38, 3),
       (5, 39, 3),
       (5, 40, 3),
       (5, 41, 3),
-- New York Knicks (equipoNBA_id = 6)
       (6, 35, 3),
       (6, 36, 3),
       (6, 42, 3),
       (6, 43, 3),
       (6, 44, 3),
       (6, 45, 3),
-- Brooklyn Nets (equipoNBA_id = 7)
       (7, 37, 3),
       (7, 46, 3),
       (7, 47, 3),
       (7, 48, 3),
       (7, 49, 3),
       (7, 50, 3),
-- Dallas Mavericks (equipoNBA_id = 8)
       (8, 51, 3),
       (8, 52, 3),
       (8, 53, 3),
       (8, 54, 3),
       (8, 55, 3),
       (8, 56, 3);


/*  PROXIMOS PARTIDOS  SABADO  18 DE JULIO -- FECHA 2  */
INSERT INTO PartidoNBA (id, equipoLocal_id, equipoVisitante_id, horaInicio, minutoFin, torneo_id, estadoPartido,
                        fecha_id)
VALUES (50, 3, 7, '2026-07-18 19:00:00', NULL, 1, 'PROGRAMADO', 2),
       (51, 4, 2, '2026-07-18 21:00:00', NULL, 1, 'PROGRAMADO', 2);

/* PARTIDOS DOMINGO 19 DE JULIO */
INSERT INTO PartidoNBA (id, equipoLocal_id, equipoVisitante_id, horaInicio, minutoFin, torneo_id, estadoPartido,
                        fecha_id)
VALUES (53, 8, 5, '2026-07-19 19:00:00', NULL, 1, 'PROGRAMADO', 3),
       (54, 6, 1, '2026-07-19 21:00:00', NULL, 1, 'PROGRAMADO', 3);


-- EQUIPOS USUARIO (torneo virtual id = 2)
INSERT INTO equipo (id, nombreEquipo, presupuesto, puntaje, torneo_id, usuario_id)
VALUES (1, 'Paren La Mano', 0, 0, 2, 3),
       (2, 'Winter is coming', 0, 0, 2, 4),
       (3, 'Dracarys', 0, 0, 2, 5),
       (4, 'Los Condores', 0, 0, 2, 6),
       (5, 'Los Dragones', 0, 0, 2, 7);

-- EQUIPOS USUARIO CON SUS JUGADORES ASOCIADOS (fecha_id = 2, que es la EN_CURSO)
INSERT INTO equipojugador (id, numeroOrden, posicionDelJugador, equipo_id, jugador_id, fecha_id)
VALUES
    -- Equipo de LUCAS (equipo 1)
    (1, 1, 'CAPITAN', 1, 1, 2),
    (2, 2, 'TITULAR', 1, 2, 2),
    (3, 3, 'TITULAR', 1, 3, 2),
    (4, 4, 'TITULAR', 1, 4, 2),
    (5, 5, 'TITULAR', 1, 5, 2),
    (6, 6, 'SEXTO_HOMBRE', 1, 6, 2),
    (7, 7, 'SUPLENTE', 1, 7, 2),
    (8, 8, 'SUPLENTE', 1, 8, 2),
    (9, 9, 'SUPLENTE', 1, 9, 2),
    (10, 10, 'SUPLENTE', 1, 10, 2),
    -- Equipo de JUAN (equipo 2)
    (11, 1, 'CAPITAN', 2, 11, 2),
    (12, 2, 'TITULAR', 2, 12, 2),
    (13, 3, 'TITULAR', 2, 13, 2),
    (14, 4, 'TITULAR', 2, 14, 2),
    (15, 5, 'TITULAR', 2, 15, 2),
    (16, 6, 'SEXTO_HOMBRE', 2, 16, 2),
    (17, 7, 'SUPLENTE', 2, 17, 2),
    (18, 8, 'SUPLENTE', 2, 18, 2),
    (19, 9, 'SUPLENTE', 2, 19, 2),
    (20, 10, 'SUPLENTE', 2, 20, 2),
    -- Equipo de Marcos (equipo 3)
    (21, 1, 'CAPITAN', 3, 21, 2),
    (22, 2, 'TITULAR', 3, 22, 2),
    (23, 3, 'TITULAR', 3, 23, 2),
    (24, 4, 'TITULAR', 3, 24, 2),
    (25, 5, 'TITULAR', 3, 25, 2),
    (26, 6, 'SEXTO_HOMBRE', 3, 26, 2),
    (27, 7, 'SUPLENTE', 3, 27, 2),
    (28, 8, 'SUPLENTE', 3, 28, 2),
    (29, 9, 'SUPLENTE', 3, 29, 2),
    (30, 10, 'SUPLENTE', 3, 30, 2),
    -- Equipo de Sofia (equipo 4)
    (31, 1, 'CAPITAN', 4, 31, 2),
    (32, 2, 'TITULAR', 4, 32, 2),
    (33, 3, 'TITULAR', 4, 33, 2),
    (34, 4, 'TITULAR', 4, 34, 2),
    (35, 5, 'TITULAR', 4, 35, 2),
    (36, 6, 'SEXTO_HOMBRE', 4, 36, 2),
    (37, 7, 'SUPLENTE', 4, 37, 2),
    (38, 8, 'SUPLENTE', 4, 38, 2),
    (39, 9, 'SUPLENTE', 4, 39, 2),
    (40, 10, 'SUPLENTE', 4, 40, 2),
    -- Equipo de Valentina (equipo 5)
    (41, 1, 'CAPITAN', 5, 41, 2),
    (42, 2, 'TITULAR', 5, 42, 2),
    (43, 3, 'TITULAR', 5, 43, 2),
    (44, 4, 'TITULAR', 5, 44, 2),
    (45, 5, 'TITULAR', 5, 45, 2),
    (46, 6, 'SEXTO_HOMBRE', 5, 46, 2),
    (47, 7, 'SUPLENTE', 5, 47, 2),
    (48, 8, 'SUPLENTE', 5, 48, 2),
    (49, 9, 'SUPLENTE', 5, 49, 2),
    (50, 10, 'SUPLENTE', 5, 50, 2);

-- NO BORRAR POR AHORA - ES PARA QUE CALCULE EL PRESUPUESTO Y NO SE MUESTRE EN CERO
UPDATE equipo e
SET e.presupuesto = 2000000 - (SELECT COALESCE(SUM(j.precio), 0)
                               FROM equipojugador ej
                                        JOIN Jugador j ON ej.jugador_id = j.id
                               WHERE ej.equipo_id = e.id);