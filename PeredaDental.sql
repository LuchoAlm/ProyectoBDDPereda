CREATE DATABASE PeredaDentalSystem;
DROP DATABASE PeredaDentalSystem;
USE PeredaDentalSystem;

-- Table SUCURSAL

CREATE TABLE `SUCURSAL`
(
  `id_suc` Varchar(10) NOT NULL,
  `nombre_suc` Varchar(50) NOT NULL,
  `direccion_suc` Varchar(50) NOT NULL,
  `telefono_suc` Varchar(9) NOT NULL
)
;

ALTER TABLE `SUCURSAL` ADD PRIMARY KEY (`id_suc`)
;
ALTER TABLE `SUCURSAL` ADD UNIQUE `id_suc` (`id_suc`)
;

-- Table PACIENTE
DROP TABLE PACIENTE;
-- Table PACIENTE

CREATE TABLE `PACIENTE`
(
  `cedula_pac` Nvarchar(12) NOT NULL,
  `nombre_pac` Varchar(70) NOT NULL,
  `apellidos_pac` Varchar(70) NOT NULL,
  `edad_pac` smallint NOT NULL,
  `fechaNacimiento_pac` Date NOT NULL,
  `direccion_pac` Varchar(150) NOT NULL,
  `canton_pac` Varchar(100) NOT NULL,
  `provincia_pac` Varchar(100) NOT NULL,
  `telefonoFijo_pac` Varchar(12) NOT NULL,
  `celular_pac` Varchar(12) NOT NULL,
  `correoElectronico_pac` Varchar(70) NOT NULL,
  `sexo_pac` Char(1) NOT NULL,
  `estado_pac` Varchar(10) NOT NULL,
  `contactoNombres` Nvarchar(70) NULL,
  `contactoApellidos` Nvarchar(70) NULL,
  `contacto_telefonoFijo` Nvarchar(10) NULL,
  `contactoCelular` Nvarchar(12) NULL,
  `referidoPor` Text NOT NULL,
  `id_suc` Varchar(10) NOT NULL
)
;

ALTER TABLE `PACIENTE` ADD PRIMARY KEY (`cedula_pac`)
;
ALTER TABLE `PACIENTE` ADD UNIQUE `cedula_pac` (`cedula_pac`)
;

DROP TABLE PACIENTE

-- Table ODONTOLOGO

CREATE TABLE `ODONTOLOGO`
(
  `cedula_odon` Varchar(10) NOT NULL,
  `nombres_odon` Varchar(50) NOT NULL,
  `apellidos_odon` Varchar(50) NOT NULL,
  `telefonoFijo_odon` Varchar(9) NOT NULL,
  `celular_odon` Varchar(12) NOT NULL,
  `especialidad_odon` Varchar(50) NOT NULL,
  `id_suc` Varchar(10) NOT NULL
)
;

ALTER TABLE `ODONTOLOGO` ADD PRIMARY KEY (`cedula_odon`)
;
ALTER TABLE `ODONTOLOGO` ADD UNIQUE `cedula_odon` (`cedula_odon`)
;
DROP TABLE ODONTOLOGO

-- Table CITA

CREATE TABLE `CITA`
(
  `id_cita` Varchar(10) NOT NULL,
  `fecha_cita` Date NOT NULL,
  `hora_cita` Time NOT NULL,
  `id_sucursal` Varchar(10) NOT NULL,
  `cedula_paciente` Varchar(10) NOT NULL,
  `cedula_odontologo` Varchar(10) NOT NULL,
  `id_tratamiento` Varchar(10) NOT NULL,
  `id_hclinica` Varchar(10) NOT NULL
)
;

ALTER TABLE `CITA` ADD PRIMARY KEY (`id_cita`, `id_sucursal`,`cedula_paciente`, `cedula_odontologo`, `id_tratamiento`,`id_hclinica`)
;

DROP TABLE CITA


-- Table HISTORIA_CLINICA

CREATE TABLE `HISTORIA_CLINICA`
(
  `id_hcl` Varchar(10) NOT NULL,
  `historiaEnfermedad` Varchar(100),
  `antecedentesPatologicos` Varchar(100),
  `consentimientoInformado` Char(2),
  `descripcion_Odontograma` Varchar(100),
  `planTratamiento` Varchar(100),
  `notasEvolucion` Varchar(1000),
  `id_tratam` Varchar(10) NOT NULL,
  `cedula_pac` Varchar(10) NOT NULL
)
;

CREATE INDEX `IX_Relationship3` ON `HISTORIA_CLINICA` (`cedula_pac`)
;
ALTER TABLE `HISTORIA_CLINICA` ADD PRIMARY KEY (`id_hcl`)
;
DROP TABLE HISTORIA_CLINICA

-- Table TRATAMIENTO

CREATE TABLE `TRATAMIENTO`
(
  `id_trat` Varchar(10) NOT NULL,
  `nombre_trat` Varchar(20) NOT NULL,
  `descripcion_trat` Varchar(50) NOT NULL,
  `precio_trat` Decimal(15,2) NOT NULL
)
;

ALTER TABLE `TRATAMIENTO` ADD PRIMARY KEY (`id_trat`)
;
ALTER TABLE `TRATAMIENTO` ADD UNIQUE `id_trat` (`id_trat`)
;
DROP TABLE TRATAMIENTO


INSERT INTO ODONTOLOGO VALUES 
  ('1789653210' , 'Ivonne'       , 'Guerrero Ricardo'   , '022567890' , '0987486671'  , 'Odontóloga General', 'SUC2')
, ('1756574149' , 'María Elena'  , 'Pereda Rojas'       , '022462533' , '0958769372'  , 'Ortodoncista', 'SUC1')
, ('1743012356' , 'Ximena'       , 'Borja Celi'         , '022660132' , '0984644370'  , 'Cirujana', 'SUC1')
, ('1757678900' , 'Luis Miguel'  , 'Vera Este'          , '022483321' , '0962786967'  , 'Endodoncista', 'SUC2')
, ('1732124555' , 'Jessica'      , 'Logroño Fernández'  , '022772677' , '0999713898'  , 'Odontopediatra', 'SUC1');


INSERT INTO TRATAMIENTO VALUES 
  ('OGEN' , 'Profilaxis simple' , 'Limpia las superficies dentarias'              , 20)
, ('ORTO' , 'Ortodoncia'        , 'Tratamiento correctivo de los dientes'         , 950)
, ('CRGA' , 'Frenectomía'       , 'Extirpación quirúrgica del frenillo del labio' , 120)
, ('ENDO' , 'Endodoncia'        , 'Extirpación de la pulpa dental'                , 160)
, ('ODON' , 'Exodoncia'         , 'Extracción de un diente o porción del mismo'   , 20);


INSERT INTO SUCURSAL VALUES 
  ('SUC1' , 'Pereda Dental Matriz' , 'Av.Río Coca y Amazonas'                   , '022590434')
, ('SUC2' , 'Pereda Dental Suc. Sur' , 'Av. Mariscal Sucre y Nicolás Ceballos'  , '023411272');

SELECT * FROM SUCURSAL
DROP TABLE PACIENTE;

SELECT * FROM ODONTOLOGO;
SELECT * FROM TRATAMIENTO;
SELECT * FROM SUCURSAL;
SELECT * FROM PACIENTE;
SELECT * FROM HISTORIA_CLINICA;
SELECT * FROM CITA;

INSERT INTO PACIENTE VALUES ('1754907782' , 'Jessica', 'Castillo Nole', '29', '1992-03-13', 'El Inca', 'Quito','Pichincha', '023341919', '0986840577','jessicaC@gmail.com', 'F', 'activo', 'Luis', 'ALmeida Zambrano', '', '0987413874', 'Directorio médico de Quito', 'SUC1');
INSERT INTO PACIENTE VALUES ('1712456210' , 'José Alonso', 'Reinoso Fuentes', '27', '1994-11-25', 'Comité del Pueblo',  'Quito','Pichincha','024567002', '0959463299','joseARF@gmail.com', 'M', 'pasivo', 'Rafael Alejandro', 'Reinoso Perez', '02346785', '0962702265', 'Facebook','SUC2');
INSERT INTO PACIENTE VALUES ('1775352834' , 'Johana', 'Sillagana Toapanta', '17','2004-07-06', '6 de julio', 'Quito','Pichincha', '026758901', '0988608218','jtoapanta@gmail.com', 'F', 'activo', 'Catalina', 'Sillagana Toapanta', '', '0956788935', 'Pte. Dayana Beatriz Silva','SUC1');
INSERT INTO PACIENTE VALUES ('1765578901' , 'Arnold', 'Nicolalde Oña', '19', '2002-06-27', 'Carcelén Bajo', 'Quito','Pichincha','022803697', '0992830174','arnicol@gmail.com', 'M', 'activo', '', '', '', '', 'Directorio médico de Quito','SUC1');
INSERT INTO PACIENTE VALUES ('1737437298' , 'Carlos', 'Cruz González', '36', '1985-11-11', 'Guayabamba', 'Quito','Pichincha', '022447812', '0981727953','carlosgonzalez@gmail.com', 'M', 'pasivo','Rubén Reinaldo', 'González Prieto', '', '0979044567', 'Facebook','SUC2');


INSERT INTO HISTORIA_CLINICA VALUES 
('112' , 'Mordida abierta anterior, afectación estética', 'Artrosis', 'Sí', 'Pérdida del 28 por caries', 'Ortodoncia', 'Brackets metálicos en la arcada superior','ORTO', '1754907782'),
('35' , 'Dolor en el diente, sensación de hormigueo', 'Ninguno', 'Sí', 'Extracción del 21 pendiente', 'Endodoncia', 'Endodoncia en el 33','ENDO', '1712456210'),
('380' , 'Dolor en la muela del juicio superior derecha', 'Ninguno', 'Sí', 'Carie en el 36', 'Cirugía', 'Cirugía del 18','CRGA', '1775352834'),
('02' , 'Caninos rotados y línea media mal posicionada', 'Hipertensión', 'NO', 'Implante en el 14', 'Ortodoncia', 'No se realizó el tratamiento porque el paciente se negó','ORTO', '1765578901'),
('54' , 'Sangramiento de las encías', 'Diabetis', 'Sí', 'No presenta extracciones', 'Profilaxis', 'Profilaxis realizada','OGEN', '1737437298');

INSERT INTO CITA VALUES 
('CIT112' , '2020-12-14', '02:30:00', 'SUC1', '1754907782', '1756574149', 'ORTO', '112'),
('CIT35' , '2019-05-18', '09:00:00', 'SUC2', '1712456210', '1757678900', 'ENDO','35'),
('CIT380' ,'2020-10-12', '11:00:00', 'SUC1', '1775352834', '1743012356', 'CRGA','380'),
('CIT02' , '2019-07-30', '01:30:00', 'SUC1', '1765578901', '1756574149', 'ORTO','02'),
('CIT54' , '2020-09-09', '5:30:00', 'SUC2', '1737437298', '1789653210', 'OGEN','54');

DROP TABLE CITA;
DELETE FROM CITA WHERE cédula_odontologo = '1743012356';

select * from HISTORIA_CLINICA
join PACIENTE 
ON HISTORIA_CLINICA.cédula_pac = PACIENTE.cedula_pac WHERE PACIENTE.id_suc = 'SUC1';

select T.id_trat, T.descripcion_trat,T.precio_trat, C.id_sucursal,C.id_cita
from TRATAMIENTO as T join CITA as C 
on T.id_trat = C.id_tratamiento
where C.id_sucursal = 'SUC1' and C.id_tratamiento = T.id_trat;

select HCL.id_hcl, HCL.historiaEnfermedad,HCL.antecedentesPatológicos,HCL.consentimientoInformado,
HCL.descripción_Odontograma,HCL.planTratamiento,HCL.notasEvolucion,HCL.id_tratam,HCL.cédula_pac,
C.id_sucursal,C.id_cita
from HISTORIA_CLINICA as HCL join CITA as C 
on HCL.id_hcl = C.id_hclinica
where C.id_sucursal = "SUC1" and C.id_hclinica = HCL.id_hcl;


select HCL.id_hcl, HCL.historiaEnfermedad,HCL.antecedentesPatológicos,HCL.consentimientoInformado,
HCL.descripción_Odontograma,HCL.planTratamiento,HCL.notasEvolucion,HCL.id_tratam,HCL.cédula_pac,
C.id_cita, C.id_sucursal
from HISTORIA_CLINICA as HCL join CITA as C 
on C.id_tratamiento = HCL.id_tratam
where C.id_sucursal = "SUC2";

select hcl.id_hcl,c.id_sucursal  
from HISTORIA_CLINICA as hcl 
join CITA as c
ON hcl.id_hcl = c.id_hclinica WHERE c.id_sucursal = "SUC1"




  `cedula_pac` Nvarchar(12) NOT NULL,
  `nombre_pac` Varchar(70) NOT NULL,
  `apellidos_pac` Varchar(70) NOT NULL,
  `edad_pac` smallint NOT NULL,
  `fechaNacimiento_pac` Date NOT NULL,
  `direccion_pac` Varchar(150) NOT NULL,
  `canton_pac` Varchar(100) NOT NULL,
  `provincia_pac` Varchar(100) NOT NULL,
  `telefonoFijo_pac` Varchar(12) NOT NULL,
  `celular_pac` Varchar(12) NOT NULL,
  `correoElectronico_pac` Varchar(70) NOT NULL,
  `sexo_pac` Char(1) NOT NULL,
  `estado_pac` Varchar(10) NOT NULL,
  `contactoNombres` Nvarchar(70) NULL,
  `contactoApellidos` Nvarchar(70) NULL,
  `contacto_telefonoFijo` Nvarchar(10) NULL,
  `contactoCelular` Nvarchar(12) NULL,
  `referidoPor` Text NOT NULL,
  `id_suc` Varchar(10) NOT NULL


