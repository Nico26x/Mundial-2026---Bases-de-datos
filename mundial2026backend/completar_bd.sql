USE Mundial2026;

CREATE TABLE IF NOT EXISTS Confederacion (
    id_confederacion INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    siglas VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Equipo (
    id_equipo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    pais VARCHAR(100) NOT NULL,
    valor_total_equipo DECIMAL(15,2) DEFAULT 0,
    id_confederacion INT NOT NULL,
    FOREIGN KEY (id_confederacion) REFERENCES Confederacion(id_confederacion)
);

CREATE TABLE IF NOT EXISTS DirectorTecnico (
    id_dt INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    nacionalidad VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    id_equipo INT UNIQUE NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo)
);

CREATE TABLE IF NOT EXISTS Jugador (
    id_jugador INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(200) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    posicion VARCHAR(50) NOT NULL,
    peso DECIMAL(5,2) NOT NULL,
    estatura DECIMAL(3,2) NOT NULL,
    valor_mercado DECIMAL(15,2) NOT NULL,
    id_equipo INT NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo),
    CONSTRAINT chk_peso CHECK (peso > 0 AND peso < 200),
    CONSTRAINT chk_estatura CHECK (estatura > 0 AND estatura < 2.50),
    CONSTRAINT chk_valor CHECK (valor_mercado >= 0)
);

CREATE TABLE IF NOT EXISTS PaisAnfitrion (
    id_pais_anfitrion INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Ciudad (
    id_ciudad INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    id_pais_anfitrion INT NOT NULL,
    FOREIGN KEY (id_pais_anfitrion) REFERENCES PaisAnfitrion(id_pais_anfitrion),
    UNIQUE KEY unique_ciudad_pais (nombre, id_pais_anfitrion)
);

CREATE TABLE IF NOT EXISTS Estadio (
    id_estadio INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    capacidad INT NOT NULL,
    id_ciudad INT NOT NULL,
    FOREIGN KEY (id_ciudad) REFERENCES Ciudad(id_ciudad),
    CONSTRAINT chk_capacidad CHECK (capacidad > 0)
);

CREATE TABLE IF NOT EXISTS Grupo (
    id_grupo INT PRIMARY KEY AUTO_INCREMENT,
    nombre_grupo VARCHAR(1) NOT NULL,
    CONSTRAINT chk_grupo CHECK (nombre_grupo IN ('A','B','C','D','E','F','G','H','I','J','K','L'))
);

CREATE TABLE IF NOT EXISTS Equipo_Grupo (
    id_equipo_grupo INT PRIMARY KEY AUTO_INCREMENT,
    id_equipo INT NOT NULL,
    id_grupo INT NOT NULL,
    FOREIGN KEY (id_equipo) REFERENCES Equipo(id_equipo),
    FOREIGN KEY (id_grupo) REFERENCES Grupo(id_grupo),
    UNIQUE KEY unique_equipo_grupo (id_equipo, id_grupo)
);

CREATE TABLE IF NOT EXISTS Partido (
    id_partido INT PRIMARY KEY AUTO_INCREMENT,
    fecha_hora DATETIME NOT NULL,
    id_estadio INT NOT NULL,
    id_grupo INT NOT NULL,
    id_equipo_local INT NOT NULL,
    id_equipo_visitante INT NOT NULL,
    goles_local INT DEFAULT 0,
    goles_visitante INT DEFAULT 0,
    FOREIGN KEY (id_estadio) REFERENCES Estadio(id_estadio),
    FOREIGN KEY (id_grupo) REFERENCES Grupo(id_grupo),
    FOREIGN KEY (id_equipo_local) REFERENCES Equipo(id_equipo),
    FOREIGN KEY (id_equipo_visitante) REFERENCES Equipo(id_equipo),
    CONSTRAINT chk_equipos_diferentes CHECK (id_equipo_local != id_equipo_visitante),
    CONSTRAINT chk_goles CHECK (goles_local >= 0 AND goles_visitante >= 0)
);

CREATE TABLE IF NOT EXISTS Bitacora (
    id_registro INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    fecha_hora_ingreso DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_hora_salida DATETIME NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

INSERT IGNORE INTO Confederacion (id_confederacion, nombre, siglas) VALUES
(1, 'Unión de Federaciones Europeas de Fútbol', 'UEFA'),
(2, 'Confederación Sudamericana de Fútbol', 'CONMEBOL'),
(3, 'Confederación de Fútbol de Norte, Centroamérica y el Caribe', 'CONCACAF'),
(4, 'Confederación Africana de Fútbol', 'CAF'),
(5, 'Confederación Asiática de Fútbol', 'AFC'),
(6, 'Confederación de Fútbol de Oceanía', 'OFC');

INSERT IGNORE INTO PaisAnfitrion (id_pais_anfitrion, nombre) VALUES
(1, 'México'),
(2, 'Estados Unidos'),
(3, 'Canadá');

INSERT IGNORE INTO Grupo (id_grupo, nombre_grupo) VALUES
(1, 'A'), (2, 'B'), (3, 'C'), (4, 'D'), (5, 'E'), (6, 'F'),
(7, 'G'), (8, 'H'), (9, 'I'), (10, 'J'), (11, 'K'), (12, 'L');

INSERT IGNORE INTO Ciudad (id_ciudad, nombre, id_pais_anfitrion) VALUES
(1, 'Ciudad de México', 1),
(2, 'Guadalajara', 1),
(3, 'Monterrey', 1),
(4, 'Los Ángeles', 2),
(5, 'Nueva York', 2),
(6, 'Miami', 2),
(7, 'Toronto', 3),
(8, 'Vancouver', 3),
(9, 'Montreal', 3);

INSERT IGNORE INTO Estadio (id_estadio, nombre, capacidad, id_ciudad) VALUES
(1, 'Estadio Azteca', 87523, 1),
(2, 'Estadio Akron', 49850, 2),
(3, 'Estadio BBVA', 53500, 3),
(4, 'Rose Bowl', 92542, 4),
(5, 'MetLife Stadium', 82500, 5),
(6, 'Hard Rock Stadium', 65326, 6),
(7, 'BMO Field', 30991, 7),
(8, 'BC Place', 54500, 8),
(9, 'Stade Olympique', 56040, 9);

INSERT IGNORE INTO Equipo (id_equipo, nombre, pais, valor_total_equipo, id_confederacion) VALUES
(1, 'Brasil', 'Brasil', 850000000, 2),
(2, 'Argentina', 'Argentina', 780000000, 2),
(3, 'Francia', 'Francia', 920000000, 1),
(4, 'España', 'España', 750000000, 1),
(5, 'México', 'México', 280000000, 3),
(6, 'Estados Unidos', 'Estados Unidos', 320000000, 3),
(7, 'Canadá', 'Canadá', 180000000, 3),
(8, 'Japón', 'Japón', 150000000, 5);

INSERT IGNORE INTO DirectorTecnico (id_dt, nombre, nacionalidad, fecha_nacimiento, id_equipo) VALUES
(1, 'Tite', 'Brasileña', '1961-05-25', 1),
(2, 'Lionel Scaloni', 'Argentina', '1978-05-16', 2),
(3, 'Didier Deschamps', 'Francesa', '1968-10-15', 3),
(4, 'Luis de la Fuente', 'Española', '1961-06-21', 4),
(5, 'Jaime Lozano', 'Mexicana', '1978-09-29', 5),
(6, 'Gregg Berhalter', 'Estadounidense', '1973-08-01', 6),
(7, 'John Herdman', 'Inglesa', '1975-07-19', 7),
(8, 'Hajime Moriyasu', 'Japonesa', '1968-08-23', 8);

INSERT IGNORE INTO Jugador (id_jugador, nombre, fecha_nacimiento, posicion, peso, estatura, valor_mercado, id_equipo) VALUES
(1, 'Neymar Jr', '1992-02-05', 'Delantero', 68.5, 1.75, 90000000, 1),
(2, 'Vinicius Jr', '2000-07-12', 'Delantero', 73.0, 1.76, 150000000, 1),
(3, 'Lionel Messi', '1987-06-24', 'Delantero', 72.0, 1.70, 50000000, 2),
(4, 'Enzo Fernández', '2001-01-17', 'Centrocampista', 78.0, 1.78, 75000000, 2),
(5, 'Kylian Mbappé', '1998-12-20', 'Delantero', 73.0, 1.78, 180000000, 3),
(6, 'Eduardo Camavinga', '2002-11-10', 'Centrocampista', 68.0, 1.82, 85000000, 3),
(7, 'Pedri', '2002-11-25', 'Centrocampista', 60.0, 1.74, 90000000, 4),
(8, 'Gavi', '2004-08-05', 'Centrocampista', 70.0, 1.73, 80000000, 4),
(9, 'Santiago Giménez', '2001-04-18', 'Delantero', 76.0, 1.82, 40000000, 5),
(10, 'Edson Álvarez', '1997-10-24', 'Defensa', 75.0, 1.87, 35000000, 5),
(11, 'Christian Pulisic', '1998-09-18', 'Delantero', 69.0, 1.78, 45000000, 6),
(12, 'Weston McKennie', '1998-08-28', 'Centrocampista', 81.0, 1.85, 25000000, 6),
(13, 'Alphonso Davies', '2000-11-02', 'Defensa', 75.0, 1.83, 70000000, 7),
(14, 'Jonathan David', '2000-01-14', 'Delantero', 70.0, 1.75, 45000000, 7),
(15, 'Takefusa Kubo', '2001-06-04', 'Centrocampista', 67.0, 1.73, 30000000, 8),
(16, 'Ritsu Doan', '1998-06-16', 'Centrocampista', 70.0, 1.72, 20000000, 8);

INSERT IGNORE INTO Equipo_Grupo (id_equipo_grupo, id_equipo, id_grupo) VALUES
(1, 1, 1), (2, 2, 1), (3, 3, 2), (4, 4, 2),
(5, 5, 3), (6, 6, 3), (7, 7, 4), (8, 8, 4);

INSERT IGNORE INTO Partido (id_partido, fecha_hora, id_estadio, id_grupo, id_equipo_local, id_equipo_visitante) VALUES
(1, '2026-06-14 15:00:00', 1, 1, 1, 2),
(2, '2026-06-15 18:00:00', 4, 2, 3, 4),
(3, '2026-06-16 20:00:00', 7, 3, 5, 6),
(4, '2026-06-17 14:00:00', 2, 4, 7, 8);
