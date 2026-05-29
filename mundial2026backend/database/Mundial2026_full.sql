/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.16-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: Mundial2026
-- ------------------------------------------------------
-- Server version	8.4.8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `Mundial2026`
--

/*!40000 DROP DATABASE IF EXISTS `Mundial2026`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `Mundial2026` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `Mundial2026`;

--
-- Table structure for table `Bitacora`
--

DROP TABLE IF EXISTS `Bitacora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Bitacora` (
  `id_registro` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `fecha_hora_ingreso` datetime DEFAULT CURRENT_TIMESTAMP,
  `fecha_hora_salida` datetime DEFAULT NULL,
  PRIMARY KEY (`id_registro`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `Bitacora_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bitacora`
--

LOCK TABLES `Bitacora` WRITE;
/*!40000 ALTER TABLE `Bitacora` DISABLE KEYS */;
INSERT INTO `Bitacora` VALUES
(1,1,'2026-05-14 10:00:00','2026-05-14 10:30:00'),
(2,1,'2026-05-28 22:31:45','2026-05-28 22:32:02'),
(3,1,'2026-05-28 22:32:07',NULL),
(4,1,'2026-05-28 22:39:12','2026-05-28 22:40:20'),
(5,1,'2026-05-28 22:46:42',NULL),
(6,1,'2026-05-28 22:47:34',NULL),
(7,1,'2026-05-28 22:56:07','2026-05-28 22:57:00'),
(8,4,'2026-05-28 22:57:09',NULL),
(9,1,'2026-05-28 22:57:42',NULL),
(10,1,'2026-05-28 23:06:13','2026-05-28 23:06:49');
/*!40000 ALTER TABLE `Bitacora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ciudad`
--

DROP TABLE IF EXISTS `Ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ciudad` (
  `id_ciudad` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_pais_anfitrion` int NOT NULL,
  PRIMARY KEY (`id_ciudad`),
  UNIQUE KEY `unique_ciudad_pais` (`nombre`,`id_pais_anfitrion`),
  KEY `id_pais_anfitrion` (`id_pais_anfitrion`),
  CONSTRAINT `Ciudad_ibfk_1` FOREIGN KEY (`id_pais_anfitrion`) REFERENCES `PaisAnfitrion` (`id_pais_anfitrion`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ciudad`
--

LOCK TABLES `Ciudad` WRITE;
/*!40000 ALTER TABLE `Ciudad` DISABLE KEYS */;
INSERT INTO `Ciudad` VALUES
(1,'Ciudad de México',1),
(2,'Guadalajara',1),
(4,'Los Ángeles',2),
(6,'Miami',2),
(3,'Monterrey',1),
(9,'Montreal',3),
(5,'Nueva York',2),
(7,'Toronto',3),
(8,'Vancouver',3);
/*!40000 ALTER TABLE `Ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Confederacion`
--

DROP TABLE IF EXISTS `Confederacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Confederacion` (
  `id_confederacion` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `siglas` varchar(10) NOT NULL,
  PRIMARY KEY (`id_confederacion`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Confederacion`
--

LOCK TABLES `Confederacion` WRITE;
/*!40000 ALTER TABLE `Confederacion` DISABLE KEYS */;
INSERT INTO `Confederacion` VALUES
(1,'Unión de Federaciones Europeas de Fútbol','UEFA'),
(2,'Confederación Sudamericana de Fútbol','CONMEBOL'),
(3,'Confederación de Fútbol de Norte, Centroamérica y el Caribe','CONCACAF'),
(4,'Confederación Africana de Fútbol','CAF'),
(5,'Confederación Asiática de Fútbol','AFC'),
(6,'Confederación de Fútbol de Oceanía','OFC');
/*!40000 ALTER TABLE `Confederacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DirectorTecnico`
--

DROP TABLE IF EXISTS `DirectorTecnico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `DirectorTecnico` (
  `id_dt` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) NOT NULL,
  `nacionalidad` varchar(100) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `id_equipo` int NOT NULL,
  PRIMARY KEY (`id_dt`),
  UNIQUE KEY `id_equipo` (`id_equipo`),
  CONSTRAINT `DirectorTecnico_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `Equipo` (`id_equipo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DirectorTecnico`
--

LOCK TABLES `DirectorTecnico` WRITE;
/*!40000 ALTER TABLE `DirectorTecnico` DISABLE KEYS */;
INSERT INTO `DirectorTecnico` VALUES
(1,'Tite','Brasileña','1961-05-25',1),
(2,'Lionel Scaloni','Argentina','1978-05-16',2),
(3,'Didier Deschamps','Francesa','1968-10-15',3),
(4,'Luis de la Fuente','Española','1961-06-21',4),
(5,'Jaime Lozano','Mexicana','1978-09-29',5),
(6,'Gregg Berhalter','Estadounidense','1973-08-01',6),
(7,'John Herdman','Inglesa','1975-07-19',7),
(8,'Hajime Moriyasu','Japonesa','1968-08-23',8);
/*!40000 ALTER TABLE `DirectorTecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Equipo`
--

DROP TABLE IF EXISTS `Equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Equipo` (
  `id_equipo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `pais` varchar(100) NOT NULL,
  `valor_total_equipo` decimal(15,2) DEFAULT '0.00',
  `id_confederacion` int NOT NULL,
  PRIMARY KEY (`id_equipo`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `id_confederacion` (`id_confederacion`),
  CONSTRAINT `Equipo_ibfk_1` FOREIGN KEY (`id_confederacion`) REFERENCES `Confederacion` (`id_confederacion`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Equipo`
--

LOCK TABLES `Equipo` WRITE;
/*!40000 ALTER TABLE `Equipo` DISABLE KEYS */;
INSERT INTO `Equipo` VALUES
(1,'Brasil','Brasil',340000000.00,2),
(2,'Argentina','Argentina',90000000.00,2),
(3,'Francia','Francia',265000000.00,1),
(4,'España','España',365000000.00,1),
(5,'México','México',75000000.00,3),
(6,'Estados Unidos','Estados Unidos',70000000.00,3),
(7,'Canadá','Canadá',7.00,3),
(8,'Japón','Japón',15000000.00,5),
(9,'Portugal','Portugal',125000000.00,1),
(10,'Colombia','Colombia',150000000.00,2),
(11,'Inglaterra','Inglaterra',180000000.00,1);
/*!40000 ALTER TABLE `Equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Equipo_Grupo`
--

DROP TABLE IF EXISTS `Equipo_Grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Equipo_Grupo` (
  `id_equipo_grupo` int NOT NULL AUTO_INCREMENT,
  `id_equipo` int NOT NULL,
  `id_grupo` int NOT NULL,
  PRIMARY KEY (`id_equipo_grupo`),
  UNIQUE KEY `unique_equipo_grupo` (`id_equipo`,`id_grupo`),
  KEY `id_grupo` (`id_grupo`),
  CONSTRAINT `Equipo_Grupo_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `Equipo` (`id_equipo`),
  CONSTRAINT `Equipo_Grupo_ibfk_2` FOREIGN KEY (`id_grupo`) REFERENCES `Grupo` (`id_grupo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Equipo_Grupo`
--

LOCK TABLES `Equipo_Grupo` WRITE;
/*!40000 ALTER TABLE `Equipo_Grupo` DISABLE KEYS */;
INSERT INTO `Equipo_Grupo` VALUES
(1,1,1),
(2,2,1),
(3,3,2),
(4,4,2),
(5,5,3),
(6,6,3),
(7,7,4),
(8,8,4);
/*!40000 ALTER TABLE `Equipo_Grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Estadio`
--

DROP TABLE IF EXISTS `Estadio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Estadio` (
  `id_estadio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `capacidad` int NOT NULL,
  `id_ciudad` int NOT NULL,
  PRIMARY KEY (`id_estadio`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `id_ciudad` (`id_ciudad`),
  CONSTRAINT `Estadio_ibfk_1` FOREIGN KEY (`id_ciudad`) REFERENCES `Ciudad` (`id_ciudad`),
  CONSTRAINT `chk_capacidad` CHECK ((`capacidad` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Estadio`
--

LOCK TABLES `Estadio` WRITE;
/*!40000 ALTER TABLE `Estadio` DISABLE KEYS */;
INSERT INTO `Estadio` VALUES
(1,'Estadio Azteca',87523,1),
(2,'Estadio Akron',49850,2),
(3,'Estadio BBVA',53500,3),
(4,'Rose Bowl',92542,4),
(5,'MetLife Stadium',82500,5),
(6,'Hard Rock Stadium',65326,6),
(7,'BMO Field',30991,7),
(8,'BC Place',54500,8),
(9,'Stade Olympique',56040,9);
/*!40000 ALTER TABLE `Estadio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Grupo`
--

DROP TABLE IF EXISTS `Grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Grupo` (
  `id_grupo` int NOT NULL AUTO_INCREMENT,
  `nombre_grupo` varchar(1) NOT NULL,
  PRIMARY KEY (`id_grupo`),
  CONSTRAINT `chk_grupo` CHECK ((`nombre_grupo` in (_utf8mb3'A',_utf8mb3'B',_utf8mb3'C',_utf8mb3'D',_utf8mb3'E',_utf8mb3'F',_utf8mb3'G',_utf8mb3'H',_utf8mb3'I',_utf8mb3'J',_utf8mb3'K',_utf8mb3'L')))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Grupo`
--

LOCK TABLES `Grupo` WRITE;
/*!40000 ALTER TABLE `Grupo` DISABLE KEYS */;
INSERT INTO `Grupo` VALUES
(1,'A'),
(2,'B'),
(3,'C'),
(4,'D'),
(5,'E'),
(6,'F'),
(7,'G'),
(8,'H'),
(9,'I'),
(10,'J'),
(11,'K'),
(12,'L');
/*!40000 ALTER TABLE `Grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Jugador`
--

DROP TABLE IF EXISTS `Jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Jugador` (
  `id_jugador` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(200) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `posicion` varchar(50) NOT NULL,
  `peso` decimal(5,2) NOT NULL,
  `estatura` decimal(3,2) NOT NULL,
  `valor_mercado` decimal(15,2) NOT NULL,
  `id_equipo` int NOT NULL,
  PRIMARY KEY (`id_jugador`),
  KEY `id_equipo` (`id_equipo`),
  CONSTRAINT `Jugador_ibfk_1` FOREIGN KEY (`id_equipo`) REFERENCES `Equipo` (`id_equipo`),
  CONSTRAINT `chk_estatura` CHECK (((`estatura` > 0) and (`estatura` < 2.50))),
  CONSTRAINT `chk_peso` CHECK (((`peso` > 0) and (`peso` < 200))),
  CONSTRAINT `chk_valor` CHECK ((`valor_mercado` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Jugador`
--

LOCK TABLES `Jugador` WRITE;
/*!40000 ALTER TABLE `Jugador` DISABLE KEYS */;
INSERT INTO `Jugador` VALUES
(1,'Neymar Jr','1992-02-05','Delantero',68.50,1.75,90000000.00,1),
(2,'Vinicius Jr','2000-07-12','Delantero',73.00,1.76,150000000.00,1),
(3,'Lionel Messi','1987-06-24','Delantero',72.00,1.69,15000000.00,2),
(4,'Enzo Fernández','2001-01-17','Centrocampista',78.00,1.78,75000000.00,2),
(5,'Kylian Mbappé','1998-12-20','Delantero',73.00,1.78,180000000.00,3),
(6,'Eduardo Camavinga','2002-11-10','Centrocampista',68.00,1.82,85000000.00,3),
(7,'Pedri','2002-11-25','Centrocampista',60.00,1.74,90000000.00,4),
(8,'Gavi','2004-08-05','Centrocampista',70.00,1.73,75000000.00,4),
(9,'Santiago Giménez','2001-04-18','Delantero',76.00,1.82,40000000.00,5),
(10,'Edson Álvarez','1997-10-24','Defensa',75.00,1.87,35000000.00,5),
(11,'Christian Pulisic','1998-09-18','Delantero',69.00,1.78,45000000.00,6),
(12,'Weston McKennie','1998-08-28','Centrocampista',81.00,1.85,25000000.00,6),
(13,'Alphonso Davies','2000-11-02','Defensa',75.00,1.83,70000000.00,7),
(17,'Cristiano Ronaldo','1985-02-05','Delantero',85.00,1.87,15000000.00,9),
(18,'Vitinha','2000-02-13','Centrocampista',64.00,1.72,110000000.00,9),
(19,'Rodrygo Goes','2000-07-13','Delantero',75.00,1.72,100000000.00,1),
(20,'Luis Diaz','2000-12-12','Delantero',75.00,1.70,150000000.00,10),
(21,'Zunoda','1998-03-21','Portero',85.00,1.81,15000000.00,8),
(22,'Jude Bellingham','2003-06-29','Centrocampista',75.00,1.86,180000000.00,11),
(23,'Lamine Yamal','2007-07-13','Delantero',72.00,1.79,200000000.00,4);
/*!40000 ALTER TABLE `Jugador` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_actualizar_valor_equipo_insert` AFTER INSERT ON `Jugador` FOR EACH ROW BEGIN
    UPDATE Equipo
    SET valor_total_equipo = (
        SELECT IFNULL(SUM(valor_mercado), 0)
        FROM Jugador
        WHERE id_equipo = NEW.id_equipo
    )
    WHERE id_equipo = NEW.id_equipo;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_actualizar_valor_equipo_update` AFTER UPDATE ON `Jugador` FOR EACH ROW BEGIN
    UPDATE Equipo
    SET valor_total_equipo = (
        SELECT IFNULL(SUM(valor_mercado), 0)
        FROM Jugador
        WHERE id_equipo = NEW.id_equipo
    )
    WHERE id_equipo = NEW.id_equipo;

    IF OLD.id_equipo != NEW.id_equipo THEN
        UPDATE Equipo
        SET valor_total_equipo = (
            SELECT IFNULL(SUM(valor_mercado), 0)
            FROM Jugador
            WHERE id_equipo = OLD.id_equipo
        )
        WHERE id_equipo = OLD.id_equipo;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_actualizar_valor_equipo_delete` AFTER DELETE ON `Jugador` FOR EACH ROW BEGIN
    UPDATE Equipo
    SET valor_total_equipo = (
        SELECT IFNULL(SUM(valor_mercado), 0)
        FROM Jugador
        WHERE id_equipo = OLD.id_equipo
    )
    WHERE id_equipo = OLD.id_equipo;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `PaisAnfitrion`
--

DROP TABLE IF EXISTS `PaisAnfitrion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `PaisAnfitrion` (
  `id_pais_anfitrion` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_pais_anfitrion`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PaisAnfitrion`
--

LOCK TABLES `PaisAnfitrion` WRITE;
/*!40000 ALTER TABLE `PaisAnfitrion` DISABLE KEYS */;
INSERT INTO `PaisAnfitrion` VALUES
(3,'Canadá'),
(2,'Estados Unidos'),
(1,'México');
/*!40000 ALTER TABLE `PaisAnfitrion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Partido`
--

DROP TABLE IF EXISTS `Partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Partido` (
  `id_partido` int NOT NULL AUTO_INCREMENT,
  `fecha_hora` datetime NOT NULL,
  `id_estadio` int NOT NULL,
  `id_grupo` int NOT NULL,
  `id_equipo_local` int NOT NULL,
  `id_equipo_visitante` int NOT NULL,
  `goles_local` int DEFAULT '0',
  `goles_visitante` int DEFAULT '0',
  PRIMARY KEY (`id_partido`),
  KEY `id_estadio` (`id_estadio`),
  KEY `id_grupo` (`id_grupo`),
  KEY `id_equipo_local` (`id_equipo_local`),
  KEY `id_equipo_visitante` (`id_equipo_visitante`),
  CONSTRAINT `Partido_ibfk_1` FOREIGN KEY (`id_estadio`) REFERENCES `Estadio` (`id_estadio`),
  CONSTRAINT `Partido_ibfk_2` FOREIGN KEY (`id_grupo`) REFERENCES `Grupo` (`id_grupo`),
  CONSTRAINT `Partido_ibfk_3` FOREIGN KEY (`id_equipo_local`) REFERENCES `Equipo` (`id_equipo`),
  CONSTRAINT `Partido_ibfk_4` FOREIGN KEY (`id_equipo_visitante`) REFERENCES `Equipo` (`id_equipo`),
  CONSTRAINT `chk_equipos_diferentes` CHECK ((`id_equipo_local` <> `id_equipo_visitante`)),
  CONSTRAINT `chk_goles` CHECK (((`goles_local` >= 0) and (`goles_visitante` >= 0)))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Partido`
--

LOCK TABLES `Partido` WRITE;
/*!40000 ALTER TABLE `Partido` DISABLE KEYS */;
INSERT INTO `Partido` VALUES
(1,'2026-06-14 15:00:00',1,1,1,2,0,0),
(2,'2026-06-15 18:00:00',4,2,3,4,0,0),
(4,'2026-06-17 14:00:00',2,4,7,8,0,0),
(5,'2026-06-16 14:30:00',6,7,10,9,2,2);
/*!40000 ALTER TABLE `Partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `Usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(255) NOT NULL,
  `contrasena_hash` varchar(255) NOT NULL,
  `tipo_usuario` varchar(50) NOT NULL,
  `fecha_creacion` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES
(1,'admin','240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9','Administrador','2026-05-07 15:28:09'),
(4,'tradicional2','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','Tradicional','2026-05-28 22:56:32');
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'Mundial2026'
--

--
-- Dumping routines for database 'Mundial2026'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-28 23:08:04
