-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 02-03-2023 a las 01:59:37
-- Versión del servidor: 5.7.40
-- Versión de PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dbparking`
--

DELIMITER $$
--
-- Procedimientos
--
DROP PROCEDURE IF EXISTS `SP_I_Cliente`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Cliente` (`pnombre` VARCHAR(45), `papellido` VARCHAR(45), `pgenero` VARCHAR(45), `pcedula` INT(10), `ppass` TEXT, `pcorreo` VARCHAR(45))   BEGIN
Insert into cliente (nombre,apellido,genero,cedula,pass,correo)
values(pnombre,papellido,pgenero,pcedula,hex(aes_encrypt(ppass, "clave")),pcorreo);
END$$

DROP PROCEDURE IF EXISTS `SP_I_Empleado`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Empleado` (`pnombre` VARCHAR(45), `papellido` VARCHAR(45), `pgenero` VARCHAR(45), `pdireccion` VARCHAR(45), `pcedula` INT(10), `ppass` TEXT, `pcorreo` VARCHAR(45), `psueldo` DECIMAL(8,2), `pestado` VARCHAR(45), `pfktipousuario` INT(11))   BEGIN
Insert into empleado (nombre,apellido,sexo,direccion,cedula,pass,correo,sueldo,estado,fk_tipoUsuario)
values(pnombre,papellido,pgenero,pdireccion,pcedula,hex(aes_encrypt(ppass, "clave")),pcorreo,psueldo,pestado,pfktipousuario);
END$$

DROP PROCEDURE IF EXISTS `SP_I_ValidarEmp`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_ValidarEmp` (`pcedula` INT(10), `ppass` TEXT, `pestado` VARCHAR(45), `pfk_tipoUsuario` INT(11))   BEGIN
SELECT * FROM empleado  where cedula=pcedula and AES_DECRYPT(UNHEX(pass), 'clave')=ppass and estado=pestado and pfk_tipoUsuario=fk_tipoUsuario;
END$$

DROP PROCEDURE IF EXISTS `SP_I_ValidarUsuario`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_ValidarUsuario` (`pcedula` INT(10), `ppass` TEXT)   BEGIN
SELECT * FROM cliente  where cedula =pcedula and AES_DECRYPT(UNHEX(pass), 'clave')=ppass;
END$$

DROP PROCEDURE IF EXISTS `SP_I_Vehiculo`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_I_Vehiculo` (`pplaca` VARCHAR(45), `pmarca` VARCHAR(45), `pfecha` VARCHAR(45), `phora_ingreso` VARCHAR(45), `pfk_cliente` INT(11), `pfk_parqueadero` INT(11), `pfk_tipovehiculo` INT(11))   BEGIN
Insert into vehiculo (placa,marca,fecha,hor_ingreso,fk_cliente,fk_parqueadero,fk_tipovehiculo)
values(pplaca,pmarca,pfecha,phora_ingreso,pfk_cliente,pfk_parqueadero,pfk_tipovehiculo);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `genero` varchar(45) NOT NULL,
  `cedula` int(20) NOT NULL,
  `pass` text NOT NULL,
  `correo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nombre`, `apellido`, `genero`, `cedula`, `pass`, `correo`) VALUES
(1, 'Danny', 'Suntaxi', 'Femenino', 1724574676, 'CF9EE6F98D1436218774B1A9B685D3EC', 'danny.19931@hotmail.es'),
(2, 'Diego', 'Cacuago', 'Masculino', 1722892195, '7BC5570986930E0067E31F15ECCC6C86', 'diego@hotmail.es'),
(3, 'Jose ', 'cacuango', 'Masculino', 1722892155, '7BC5570986930E0067E31F15ECCC6C86', 'daniel@hotmail.es'),
(4, 'apps', 'aspp', 'Femenino', 12345, 'CF9EE6F98D1436218774B1A9B685D3EC', 'apps@hotmail.es'),
(6, 'JosueP', 'Coyago', 'Masculino', 450055504, 'CF9EE6F98D1436218774B1A9B685D3EC', 'elian@hotmail.es');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

DROP TABLE IF EXISTS `empleado`;
CREATE TABLE IF NOT EXISTS `empleado` (
  `id_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `sexo` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `cedula` int(10) DEFAULT NULL,
  `pass` text,
  `correo` varchar(45) DEFAULT NULL,
  `sueldo` decimal(8,2) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `fk_tipoUsuario` int(11) NOT NULL,
  PRIMARY KEY (`id_empleado`),
  KEY `fk_empleado_TipoUsuario1_idx` (`fk_tipoUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id_empleado`, `nombre`, `apellido`, `sexo`, `direccion`, `cedula`, `pass`, `correo`, `sueldo`, `estado`, `fk_tipoUsuario`) VALUES
(1, 'Daniel', 'Suntaxi', 'Masculino', 'Tabacundo', 1724574676, 'CF9EE6F98D1436218774B1A9B685D3EC', 'danny.19931@hotmqail.es', '400.00', 'ACTIVO', 2),
(2, 'admin', 'admin', '', ' ', 100, 'CF9EE6F98D1436218774B1A9B685D3EC', 'admin.19931@hotmqail.es', '0.00', 'ACTIVO', 1),
(4, 'Karen', 'Perez', 'Femenino', 'Cayambe', 1724574, '8BCB2B4F913813F8AC9CAF48D6DF2008', 'daniel@hotmail.es', '500.00', 'ACTIVO', 2),
(5, 'Bryan', 'Chorlango', 'Masculino', 'Cubiche', 1722457858, '68AF87744C92D95082EC96189255E507', 'bryan@hotmail.es', '400.00', 'ACTIVO', 2),
(8, 'Marisol', 'flores', 'Femenino', 'Quito-Valle', 17224575, '8BCB2B4F913813F8AC9CAF48D6DF2008', 'marisol@hotmail.es', '250.00', 'ACTIVO', 2),
(9, 'a', 'Cacuango', 'Femenino', 'diego@hotmail.es', 555, 'CF9EE6F98D1436218774B1A9B685D3EC', 'daniel@hotmail.es', '500.00', 'ACTIVO', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parqueadero`
--

DROP TABLE IF EXISTS `parqueadero`;
CREATE TABLE IF NOT EXISTS `parqueadero` (
  `id_Parqueadero` int(11) NOT NULL AUTO_INCREMENT,
  `Pnombre` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_Parqueadero`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `parqueadero`
--

INSERT INTO `parqueadero` (`id_Parqueadero`, `Pnombre`, `estado`) VALUES
(1, 'Estacion1', 'OCUPADO'),
(2, 'Estacion2', 'DISPONIBLE'),
(3, 'Estacion3', 'DISPONIBLE'),
(4, 'Estacion4', 'DISPONIBLE'),
(5, 'Estacion5', 'DISPONIBLE'),
(6, 'Estacion6', 'DISPONIBLE'),
(7, 'Estacion7', 'DISPONIBLE'),
(8, 'Estacion8', 'DISPONIBLE'),
(9, 'Estacion9', 'DISPONIBLE'),
(10, 'Estacion10', 'DISPONIBLE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
CREATE TABLE IF NOT EXISTS `tarifas` (
  `id_precio` int(11) NOT NULL AUTO_INCREMENT,
  `Precio` varchar(45) DEFAULT NULL,
  `hor_salida` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `fk_vehiculo` int(11) NOT NULL,
  `fk_empleado` int(11) NOT NULL,
  PRIMARY KEY (`id_precio`),
  KEY `fk_precio_vehiculo1_idx` (`fk_vehiculo`),
  KEY `fk_tarifas_empleado1_idx` (`fk_empleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipodevehiculo`
--

DROP TABLE IF EXISTS `tipodevehiculo`;
CREATE TABLE IF NOT EXISTS `tipodevehiculo` (
  `id_tipodevehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `vehiculo` varchar(45) DEFAULT NULL,
  `precio` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id_tipodevehiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipodevehiculo`
--

INSERT INTO `tipodevehiculo` (`id_tipodevehiculo`, `vehiculo`, `precio`) VALUES
(1, 'Automovil', '1.00'),
(2, 'Camioneta', '1.00'),
(3, 'Moto', '1.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
CREATE TABLE IF NOT EXISTS `tipousuario` (
  `id_TipoUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id_TipoUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipousuario`
--

INSERT INTO `tipousuario` (`id_TipoUsuario`, `descripcion`) VALUES
(1, 'ADMINISTRADOR'),
(2, 'EMPLEADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
CREATE TABLE IF NOT EXISTS `vehiculo` (
  `id_vehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `placa` varchar(45) DEFAULT NULL,
  `marca` varchar(45) DEFAULT NULL,
  `fecha` varchar(45) DEFAULT NULL,
  `hor_ingreso` varchar(45) DEFAULT NULL,
  `fk_cliente` int(11) NOT NULL,
  `fk_parqueadero` int(11) NOT NULL,
  `fk_tipovehiculo` int(11) NOT NULL,
  PRIMARY KEY (`id_vehiculo`),
  KEY `fk_vehiculo_cliente1_idx` (`fk_cliente`),
  KEY `fk_vehiculo_Parqueadero1_idx` (`fk_parqueadero`),
  KEY `fk_vehiculo_tipodevehiculo1_idx` (`fk_tipovehiculo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id_vehiculo`, `placa`, `marca`, `fecha`, `hor_ingreso`, `fk_cliente`, `fk_parqueadero`, `fk_tipovehiculo`) VALUES
(1, 'PBN7052', 'Chery', '2-25-2023', '10:00', 1, 1, 1),
(3, 'mazda', 'Renau', '2023-02-23', '03:11', 2, 2, 1),
(8, 'Ibb7074', 'Maszda', '2023-02-23', '18:23', 3, 3, 2),
(9, 'KAta', 'preuba', '2023-02-24', '04:18', 4, 4, 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `fk_empleado_TipoUsuario1` FOREIGN KEY (`fk_tipoUsuario`) REFERENCES `tipousuario` (`id_TipoUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `tarifas`
--
ALTER TABLE `tarifas`
  ADD CONSTRAINT `fk_precio_vehiculo1` FOREIGN KEY (`fk_vehiculo`) REFERENCES `vehiculo` (`id_vehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tarifas_empleado1` FOREIGN KEY (`fk_empleado`) REFERENCES `empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD CONSTRAINT `fk_vehiculo_Parqueadero1` FOREIGN KEY (`fk_parqueadero`) REFERENCES `parqueadero` (`id_Parqueadero`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_vehiculo_cliente1` FOREIGN KEY (`fk_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_vehiculo_tipodevehiculo1` FOREIGN KEY (`fk_tipovehiculo`) REFERENCES `tipodevehiculo` (`id_tipodevehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
