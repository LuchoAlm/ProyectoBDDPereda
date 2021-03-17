----------------------------------------------------------------------------------------------------------------------------------
--													SEDE 1																--			
----------------------------------------------------------------------------------------------------------------------------------
--CREAR BASE DE DATOS DE SEDE 1
CREATE DATABASE PeredaDentalSUC1
--USAR LA BASE DE DATOS
USE PeredaDentalSUC1 
--BORRAR LA BASE DE DATOS
DROP DATABASE PeredaDentalSUC1
----------------------------------------------------------------------------------------------------------------------------------

--CONSULTAS A LAS TABLAS ORIGINALES (MySQL)
select * from openquery ([CLINICADENTAL],
'select * from PACIENTE');
select * from openquery ([CLINICADENTAL],
'select * from HISTORIA_CLINICA')
select * from openquery ([CLINICADENTAL],
'select * from CITA');
select * from openquery ([CLINICADENTAL],
'select * from ODONTOLOGO')
select * from openquery ([CLINICADENTAL],
'select * from TRATAMIENTO');

-----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- FRAGMENTACIÓN VERTICAL Y FRAGMENTACIÓN HORIZONTAL PRIMARIA 

-- TODAS LAS TABLAS QUE SE MENCIONARÁN SERÁN FRAGMENTADAS HORIZONTAL PRIMARIA   Y VERTICALMENTE
-- 1) PACIENTE (PACIENTECITA_SUC1 Y PACIENTEDATOS_SUC1)
-- 2) ODONTÓLOGO (ODONTOLOGOCITA_SUC1 Y ODONTOLOGODATOS_SUC1)


--TABLA PACIENTE:

---------------- PACIENTECITA_SUC1 -------------------
--Replicación de los campos 
select [cedula_pac], [nombre_pac], [apellidos_pac] into PACIENTECITA_SUC1
from openquery ([CLINICADENTAL], 'select * from PACIENTE')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentación
alter table PACIENTECITA_SUC1 add id_suc nvarchar(10) not null 
alter table PACIENTECITA_SUC1 add constraint pk_id_suc1 primary key (id_suc, cedula_pac)
sp_help PACIENTECITA_SUC1

--Ingresar datos
insert into PACIENTECITA_SUC1
select [cedula_pac],[nombre_pac],[apellidos_pac],[id_suc]  from openquery ([CLINICADENTAL],
'select * from PACIENTE where id_suc = "SUC1"');

--Ver datos
select * from PACIENTECITA_SUC1

--Borrar datos
drop table PACIENTECITA_SUC1

---------------- PACIENTEDATOS_SUC1 -------------------
--Replicación de los campos 
select [cedula_pac],[edad_pac], [fechaNacimiento_pac],[direccion_pac], [canton_pac], [provincia_pac]
[teléfonoFijo_pac], [celular_pac],[correoElectronico_pac],[sexo_pac], [estado_pac], [contactoNombres], [contactoApellidos], 
[contacto_telefonoFijo],[contactoCelular],[referidoPor], [id_suc] into PACIENTEDATOS_SUC1
from openquery ([CLINICADENTAL], 'select * from PACIENTE')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentación
alter table PACIENTEDATOS_SUC1 add constraint pk_id_suc primary key (id_suc, cedula_pac)
sp_help PACIENTEDATOS_SUC1

--Ingresar datos
insert into PACIENTEDATOS_SUC1
select [cedula_pac],[edad_pac], [fechaNacimiento_pac],[direccion_pac], [canton_pac], [provincia_pac]
[teléfonoFijo_pac], [celular_pac],[correoElectronico_pac],[sexo_pac], [estado_pac], [contactoNombres], [contactoApellidos], 
[contacto_telefonoFijo],[contactoCelular],[referidoPor], [id_suc] from openquery ([CLINICADENTAL],
'select * from PACIENTE where id_suc = "SUC1"');

--Ver datos
select * from PACIENTEDATOS_SUC1

--Borrar datos
drop table PACIENTEDATOS_SUC1

--TABLA: ODONTOLOGO

---------------- ODONTOLOGOCITA_SUC1 -------------------
--Replicación de los campos 
select [cedula_odon],[nombres_odon],[apellidos_odon] into ODONTOLOGOCITA_SUC1
from openquery ([CLINICADENTAL], 'select * from ODONTOLOGO')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentación
alter table ODONTOLOGOCITA_SUC1 add id_suc nvarchar(10) not null 
alter table ODONTOLOGOCITA_SUC1 add constraint pk_id_suc2 primary key (id_suc, cedula_odon)
sp_help ODONTOLOGOCITA_SUC1

--Ingresar datos
insert into ODONTOLOGOCITA_SUC1
select [cedula_odon],[nombres_odon],[apellidos_odon],[id_suc] from openquery ([CLINICADENTAL],
'select * from ODONTOLOGO where id_suc = "SUC1"');


---------------- ODONTOLOGODATOS_SUC1 -------------------
--Replicación de los campos 
select [cedula_odon],[telefonoFijo_odon],[celular_odon],[especialidad_odon],[id_suc] into ODONTOLOGODATOS_SUC1
from openquery ([CLINICADENTAL], 'select * from ODONTOLOGO')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentación
alter table ODONTOLOGODATOS_SUC1 add constraint pk_id_suc4 primary key (id_suc, cedula_odon)
sp_help ODONTOLOGODATOS_SUC1

--Ingresar datos
insert into ODONTOLOGODATOS_SUC1
select [cedula_odon],[telefonoFijo_odon],[celular_odon],[especialidad_odon],[id_suc] from openquery ([CLINICADENTAL],
'select * from ODONTOLOGO where id_suc = "SUC1"');


--Seleccionar tablas
select * from ODONTOLOGOCITA_SUC1
select * from ODONTOLOGODATOS_SUC1

--Borrar tablas
drop table ODONTOLOGOCITA_SUC1
drop table ODONTOLOGODATOS_SUC1

-----------------------------------------------------------------------------------------------------------------------------------------------------------------
--FRAGMENTACIÓN HORIZONTAL PRIMARIA

--TABLA: CITA

--Replicación de los campos 
select * into CITA_SUC1
from openquery ([CLINICADENTAL], 'select * from CITA')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentación
alter table CITA_SUC1 add constraint pk_id_sucursal primary key (id_sucursal, id_cita)
sp_help DATOSCITA_SUC1

--Ingresar datos
insert into CITA_SUC1
select * from openquery ([CLINICADENTAL],
'select * from CITA where id_sucursal = "SUC1"');


--Seleccionar tablas
select distinct fecha_cita as 'Fecha', hora_cita as 'Hora', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'Paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'Odontólogo', t.nombre_trat as 'Tratamiento'
from PACIENTECITA_SUC1 as p join CITA_SUC1 as c
on p.cedula_pac = c.cedula_paciente join ODONTOLOGOCITA_SUC1 as o 
on o.cedula_odon = c.cedula_odontologo join TRATAMIENTO as t
on t.id_trat = c.id_tratamiento
order by fecha_cita

--Borrar tablas
drop table CITA_SUC1

select * from ODONTOLOGOCITA_SUC1
select * from CITA_SUC1
select * from PACIENTECITA_SUC1


-----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- FRAGMENTACIÓN VERTICAL Y FRAGMENTACIÓN HORIZONTAL DERIVADA 
-- HISTORIA CLÍNICA (HISTORIACLINICACITA_SUC1 Y HISTORIACLINICA_DATOS_SUC1)

--TABLA: HISTORIA CLINICA

---------------- HISTORIACLINICACITA_SUC1 -------------------
--Replicación de los campos 
select [id_hcl] into HISTORIACLINICACITA_SUC1
from openquery ([CLINICADENTAL], 'select * from HISTORIA_CLINICA')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentación
alter table HISTORIACLINICACITA_SUC1 add id_suc nvarchar(10) not null 
alter table HISTORIACLINICACITA_SUC1 add constraint pk_id_suc5 primary key (id_suc, id_hcl)
sp_help HISTORIACLINICACITA_SUC1

--Ingresar datos
insert into HISTORIACLINICACITA_SUC1 
select * from openquery ([CLINICADENTAL],
'select hcl.id_hcl,c.id_sucursal  
from HISTORIA_CLINICA as hcl 
join CITA as c
ON hcl.id_hcl = c.id_hclinica WHERE c.id_sucursal = "SUC1"')


--Seleccionar tablas
select * from HISTORIACLINICACITA_SUC1

--Borrar tablas
drop table HISTORIACLINICACITA_SUC1


---------------- HISTORIACLINICA_DATOS_SUC1 -------------------
select * into HISTORIACLINICA_DATOS_SUC1
from openquery ([CLINICADENTAL], 'select * from HISTORIA_CLINICA')
where 1 = 2

--Agrega el campo de fragmentación y la pk de la tabla que depende como atributos
--1
alter table HISTORIACLINICA_DATOS_SUC1 add id_cita nvarchar(10) not null
alter table HISTORIACLINICA_DATOS_SUC1 add id_suc nvarchar(10) not null
--2
alter table HISTORIACLINICA_DATOS_SUC1 add constraint fk_id_cita2 foreign key (id_suc, id_cita)
references CITA_SUC1 (id_sucursal, id_cita)
--3
alter table HISTORIACLINICA_DATOS_SUC1 add constraint pk_id_hcl2 primary key (id_hcl, id_suc)


--Ingresar datos
insert into HISTORIACLINICA_DATOS_SUC1 
select * from openquery ([CLINICADENTAL],
'select HCL.id_hcl, HCL.historiaEnfermedad,HCL.antecedentesPatologicos,HCL.consentimientoInformado,
HCL.descripcion_Odontograma,HCL.planTratamiento,HCL.notasEvolucion,HCL.id_tratam,HCL.cedula_pac,
C.id_cita, C.id_sucursal
from HISTORIA_CLINICA as HCL join CITA as C 
on HCL.id_hcl = C.id_hclinica
where C.id_sucursal = "SUC1" and C.id_hclinica = HCL.id_hcl')


--Seleccionar tablas
select * from HISTORIACLINICA_DATOS_SUC1

--Borrar tablas
drop table HISTORIACLINICA_DATOS_SUC1


----------------------------------------------------------------------------------------------------------------------------------------------------------------
--REPLICACIÓN DE DATOS     --  SE REPLICAN LAS TABLAS DE SUCURSAL Y TRATAMIENTO (ya que son utilizadas mayormente para consultas en ambas sedes)
--TABLA: SUCURSAL  
select * into SUCURSAL
from openquery ([CLINICADENTAL], 'select * from SUCURSAL')
where 1 = 2

--TABLA: TRATAMIENTO
select * into TRATAMIENTO
from openquery ([CLINICADENTAL], 'select * from TRATAMIENTO')
where 1 = 2


--Añadir la pk a la tabla
alter table SUCURSAL add constraint pk_id_suc3 primary key (id_suc)
sp_help SUCURSAL

alter table TRATAMIENTO add constraint pk_id_trat primary key (id_trat)
sp_help TRATAMIENTOCITA_SUC1


--Llenar los datos
insert into SUCURSAL
select * from openquery ([CLINICADENTAL],
'select * from SUCURSAL');

insert into TRATAMIENTO
select * from openquery ([CLINICADENTAL], 
'select * from TRATAMIENTO')

--Seleccionar tablas
select * from SUCURSAL
select * from TRATAMIENTO

--Borrar tablas
drop table SUCURSAL
drop table TRATAMIENTO


--Comprobar la replicación de SUCURSAL
select * from SUCURSAL
insert into SUCURSAL values ('SUC4','Pereda Dental Suc. Centro_Norte', 'Centro-Norte', '023488665', '03A4308D-9673-EB11-AECD-000C29628FB7')
delete from SUCURSAL where id_suc = 'SUC3'

--Conexión entre ambos servidores
select * from sysservers
sp_addlinkedserver 'WIN-URK336K1LJS', 'SQL Server'
sp_addlinkedsrvlogin 'WIN-URK336K1LJS', 'true'
sp_droplinkedsrvlogin 'WIN-URK336K1LJS'

--Consultar datos en la otra sede (Servidor 2)
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[PACIENTECITA_SUC2]
select * from CITA_SUC1

-----------------------------------------------------------------------------------------------------------------------------------------------------------------
--			VISTAS PARTICIONADAS:  

--TABLA: PACIENTECITA_SUC1
--Agregar el check con el campo de fragmentación igual a SUC1 
alter table [PACIENTECITA_SUC1] add constraint Ck_id_suc check (id_suc='SUC1')

--Crear la vista
create view Paciente_Cita
as
select * from [WIN-BBGCNF1VMO9].[PeredaDentalSUC1].[dbo].[PACIENTECITA_SUC1]
union all
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[PACIENTECITA_SUC2]

--Seleccionar los datos de la tabla
select * from Paciente_Cita

--Insertar datos en la vista
SET XACT_ABORT ON
begin distributed tran
insert into Paciente_Cita values ('1757203912','Rebeca','Zambrano Pereda','SUC2')
commit

--Borrar datos en la vista
SET XACT_ABORT ON
begin distributed tran
delete from Paciente_Cita where cedula_pac = '1757203912'
commit

----------------------------------------------------------------------------------
--TABLA: PACIENTEDATOS_SUC1
--Agregar el check con el campo de fragmentación igual a SUC1
alter table [PACIENTEDATOS_SUC1] add constraint Ck_id_suc2 check (id_suc='SUC1')

--Crear la vista
create view Paciente_Datos
as
select * from [WIN-BBGCNF1VMO9].[PeredaDentalSUC1].[dbo].[PACIENTEDATOS_SUC1]
union all
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[PACIENTEDATOS_SUC2]

--Seleccionar los datos de la tabla
select * from Paciente_Datos

--Insertar datos en la vista
SET XACT_ABORT ON
begin distributed tran
insert into Paciente_Datos values ('1753407619',43, '1996-12-08', 'Jipijapa','Quito', 'Pichincha', '0978655432', 
'rebeca.zambrano@gmail.com', 'F', 'pasivo', '', '', '', '', 'Facebook', 'SUC2')
commit

--Borrar datos en la vista
SET XACT_ABORT ON
begin distributed tran
delete from Paciente_Datos where cedula_pac = '1753407619'
commit

----------------------------------------------------------------------------------
--TABLA: ODONTOLOGOCITA_SUC1
--Agregar el check con el campo de fragmentación igual a SUC1
alter table [ODONTOLOGOCITA_SUC1] add constraint Ck_id_suc3 check (id_suc='SUC1')

--Crear la vista
create view Odontologo_Cita
as
select * from [WIN-BBGCNF1VMO9].[PeredaDentalSUC1].[dbo].[ODONTOLOGOCITA_SUC1]
union all
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[ODONTOLOGOCITA_SUC2]

--Seleccionar los datos de la tabla
select * from Odontologo_Cita

--Insertar datos en la vista
SET XACT_ABORT ON
begin distributed tran
insert into Odontologo_Cita values ('1767245129','Dayana','Hernan Perez','SUC2')
commit

--Borrar datos en la vista
SET XACT_ABORT ON
begin distributed tran
delete from Odontologo_Cita where cedula_odon = '1767245129'
commit

----------------------------------------------------------------------------------
--TABLA: ODONTOLOGODATOS_SUC1
--Agregar el check con el campo de fragmentación igual a SUC1
alter table [ODONTOLOGODATOS_SUC1] add constraint Ck_id_suc4 check (id_suc='SUC1')

--Crear la vista
create view Odontologo_Datos
as
select * from [WIN-BBGCNF1VMO9].[PeredaDentalSUC1].[dbo].[ODONTOLOGODATOS_SUC1]
union all
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[ODONTOLOGODATOS_SUC2]

--Seleccionar los datos de la tabla
select * from Odontologo_Datos

--Insertar datos en la vista
SET XACT_ABORT ON
begin distributed tran
insert into Odontologo_Datos values ('1778650413','02345678','0986547654','Ortodoncista', 'SUC2')
commit

--Borrar datos en la vista
SET XACT_ABORT ON
begin distributed tran
delete from Odontologo_Datos where cedula_odon = '1778650413'
commit

----------------------------------------------------------------------------------
--TABLA: [HISTORIACLINICACITA_SUC1
--Agregar el check con el campo de fragmentación igual a SUC1
alter table [HISTORIACLINICACITA_SUC1] add constraint Ck_id_suc5 check (id_suc='SUC1')

--Crear la vista
create view HistoriaClinica_Cita
as
select * from [WIN-BBGCNF1VMO9].[PeredaDentalSUC1].[dbo].[HISTORIACLINICACITA_SUC1]
union all
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[HISTORIACLINICACITA_SUC2]

--Seleccionar datos
select * from HistoriaClinica_Cita

--Insertar datos en la vista --INSERTAR
SET XACT_ABORT ON
begin distributed tran
insert into HistoriaClinica_Cita values (30,'SUC2')
commit

--Borrar datos en la vista
SET XACT_ABORT ON
begin distributed tran
delete from HistoriaClinica_Cita where id_hcl = '30'
commit


----------------------------------------------------------------------------------
--TABLA: HISTORIACLINICA_DATOS_SUC1
--Agregar el check con el campo de fragmentación igual a SUC1
alter table [HISTORIACLINICA_DATOS_SUC1] add constraint Ck_id_suc6 check (id_suc='SUC1')

--Crear vista
create view HistoriaClinica_Datos
as
select * from [WIN-BBGCNF1VMO9].[PeredaDentalSUC1].[dbo].[HISTORIACLINICA_DATOS_SUC1]
union all
select * from [WIN-URK336K1LJS].[PeredaDentalSUC2].[dbo].[HISTORIACLINICA_DATOS_SUC2]

--Seleccionar datos
select * from HistoriaClinica_Datos


--Insertar datos en la vista --INSEERTAR
SET XACT_ABORT ON
begin distributed tran
insert into HistoriaClinica_Datos values (30,'Dientes con mal oclusión','Ninguno',
'Sí','Ninguno', 'Ortodoncia', 'Ninguno', 'ORTO', '1757203912', 'CIT30', 'SUC2')
commit

--Borrar datos en la vista
SET XACT_ABORT ON
begin distributed tran
delete from HistoriaClinica_Datos where id_hcl = '30'
commit

----Borrar vistas
drop view Paciente_Cita
drop view Paciente_Datos
--
drop view [Odontologo_Cita]
drop view [Odontologo_Datos]
--
drop view HistoriaClinica_Cita
drop view HistoriaClinica_Datos



