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
-- FRAGMENTACI�N VERTICAL Y FRAGMENTACI�N HORIZONTAL PRIMARIA 

-- TODAS LAS TABLAS QUE SE MENCIONAR�N SER�N FRAGMENTADAS HORIZONTAL PRIMARIA   Y VERTICALMENTE
-- 1) PACIENTE (PACIENTECITA_SUC1 Y PACIENTEDATOS_SUC1)
-- 2) ODONT�LOGO (ODONTOLOGOCITA_SUC1 Y ODONTOLOGODATOS_SUC1)


--TABLA PACIENTE:

---------------- PACIENTECITA_SUC1 -------------------
--Replicaci�n de los campos 
select [cedula_pac], [nombre_pac], [apellidos_pac] into PACIENTECITA_SUC1
from openquery ([CLINICADENTAL], 'select * from PACIENTE')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentaci�n
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
--Replicaci�n de los campos 
select [cedula_pac],[edad_pac], [fechaNacimiento_pac],[direccion_pac], [canton_pac], [provincia_pac]
[tel�fonoFijo_pac], [celular_pac],[correoElectronico_pac],[sexo_pac], [estado_pac], [contactoNombres], [contactoApellidos], 
[contacto_telefonoFijo],[contactoCelular],[referidoPor], [id_suc] into PACIENTEDATOS_SUC1
from openquery ([CLINICADENTAL], 'select * from PACIENTE')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentaci�n
alter table PACIENTEDATOS_SUC1 add constraint pk_id_suc primary key (id_suc, cedula_pac)
sp_help PACIENTEDATOS_SUC1

--Ingresar datos
insert into PACIENTEDATOS_SUC1
select [cedula_pac],[edad_pac], [fechaNacimiento_pac],[direccion_pac], [canton_pac], [provincia_pac]
[tel�fonoFijo_pac], [celular_pac],[correoElectronico_pac],[sexo_pac], [estado_pac], [contactoNombres], [contactoApellidos], 
[contacto_telefonoFijo],[contactoCelular],[referidoPor], [id_suc] from openquery ([CLINICADENTAL],
'select * from PACIENTE where id_suc = "SUC1"');

--Ver datos
select * from PACIENTEDATOS_SUC1

--Borrar datos
drop table PACIENTEDATOS_SUC1

--TABLA: ODONTOLOGO

---------------- ODONTOLOGOCITA_SUC1 -------------------
--Replicaci�n de los campos 
select [cedula_odon],[nombres_odon],[apellidos_odon] into ODONTOLOGOCITA_SUC1
from openquery ([CLINICADENTAL], 'select * from ODONTOLOGO')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentaci�n
alter table ODONTOLOGOCITA_SUC1 add id_suc nvarchar(10) not null 
alter table ODONTOLOGOCITA_SUC1 add constraint pk_id_suc2 primary key (id_suc, cedula_odon)
sp_help ODONTOLOGOCITA_SUC1

--Ingresar datos
insert into ODONTOLOGOCITA_SUC1
select [cedula_odon],[nombres_odon],[apellidos_odon],[id_suc] from openquery ([CLINICADENTAL],
'select * from ODONTOLOGO where id_suc = "SUC1"');


---------------- ODONTOLOGODATOS_SUC1 -------------------
--Replicaci�n de los campos 
select [cedula_odon],[telefonoFijo_odon],[celular_odon],[especialidad_odon],[id_suc] into ODONTOLOGODATOS_SUC1
from openquery ([CLINICADENTAL], 'select * from ODONTOLOGO')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentaci�n
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
--FRAGMENTACI�N HORIZONTAL PRIMARIA

--TABLA: CITA

--Replicaci�n de los campos 
select * into CITA_SUC1
from openquery ([CLINICADENTAL], 'select * from CITA')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentaci�n
alter table CITA_SUC1 add constraint pk_id_sucursal primary key (id_sucursal, id_cita)
sp_help DATOSCITA_SUC1

--Ingresar datos
insert into CITA_SUC1
select * from openquery ([CLINICADENTAL],
'select * from CITA where id_sucursal = "SUC1"');


--Seleccionar tablas
select distinct fecha_cita as 'Fecha', hora_cita as 'Hora', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'Paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'Odont�logo', t.nombre_trat as 'Tratamiento'
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
-- FRAGMENTACI�N VERTICAL Y FRAGMENTACI�N HORIZONTAL DERIVADA 
-- HISTORIA CL�NICA (HISTORIACLINICACITA_SUC1 Y HISTORIACLINICA_DATOS_SUC1)

--TABLA: HISTORIA CLINICA

---------------- HISTORIACLINICACITA_SUC1 -------------------
--Replicaci�n de los campos 
select [id_hcl] into HISTORIACLINICACITA_SUC1
from openquery ([CLINICADENTAL], 'select * from HISTORIA_CLINICA')
where 1 = 2

--Agregar como pk la pk copuesta por el id + campo de fragmentaci�n
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

--Agrega el campo de fragmentaci�n y la pk de la tabla que depende como atributos
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
--REPLICACI�N DE DATOS     --  SE REPLICAN LAS TABLAS DE SUCURSAL Y TRATAMIENTO (ya que son utilizadas mayormente para consultas en ambas sedes)
--TABLA: SUCURSAL  
select * into SUCURSAL
from openquery ([CLINICADENTAL], 'select * from SUCURSAL')
where 1 = 2

--TABLA: TRATAMIENTO
select * into TRATAMIENTO
from openquery ([CLINICADENTAL], 'select * from TRATAMIENTO')
where 1 = 2


--A�adir la pk a la tabla
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


--Comprobar la replicaci�n de SUCURSAL
select * from SUCURSAL
insert into SUCURSAL values ('SUC4','Pereda Dental Suc. Centro_Norte', 'Centro-Norte', '023488665', '03A4308D-9673-EB11-AECD-000C29628FB7')
delete from SUCURSAL where id_suc = 'SUC3'

--Conexi�n entre ambos servidores
select * from sysservers
sp_addlinkedserver 'WINSERVER2', 'SQL Server'
sp_addlinkedsrvlogin 'WINSERVER2', 'true'
sp_droplinkedsrvlogin 'WINSERVER2'

--Consultar datos en la otra sede (Servidor 2)
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[PACIENTECITA_SUC2]
select * from CITA_SUC1

-----------------------------------------------------------------------------------------------------------------------------------------------------------------
--			VISTAS PARTICIONADAS:  

--TABLA: PACIENTECITA_SUC1
--Agregar el check con el campo de fragmentaci�n igual a SUC1 
alter table [PACIENTECITA_SUC1] add constraint Ck_id_suc check (id_suc='SUC1')

--Crear la vista
create view Paciente_Cita
as
select * from [WINSERVER1].[PeredaDentalSUC1].[dbo].[PACIENTECITA_SUC1]
union all
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[PACIENTECITA_SUC2]

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
--Agregar el check con el campo de fragmentaci�n igual a SUC1
alter table [PACIENTEDATOS_SUC1] add constraint Ck_id_suc2 check (id_suc='SUC1')

--Crear la vista
create view Paciente_Datos
as
select * from [WINSERVER1].[PeredaDentalSUC1].[dbo].[PACIENTEDATOS_SUC1]
union all
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[PACIENTEDATOS_SUC2]

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
--Agregar el check con el campo de fragmentaci�n igual a SUC1
alter table [ODONTOLOGOCITA_SUC1] add constraint Ck_id_suc3 check (id_suc='SUC1')

--Crear la vista
create view Odontologo_Cita
as
select * from [WINSERVER1].[PeredaDentalSUC1].[dbo].[ODONTOLOGOCITA_SUC1]
union all
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[ODONTOLOGOCITA_SUC2]

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
--Agregar el check con el campo de fragmentaci�n igual a SUC1
alter table [ODONTOLOGODATOS_SUC1] add constraint Ck_id_suc4 check (id_suc='SUC1')

--Crear la vista
create view Odontologo_Datos
as
select * from [WINSERVER1].[PeredaDentalSUC1].[dbo].[ODONTOLOGODATOS_SUC1]
union all
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[ODONTOLOGODATOS_SUC2]

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
--Agregar el check con el campo de fragmentaci�n igual a SUC1
alter table [HISTORIACLINICACITA_SUC1] add constraint Ck_id_suc5 check (id_suc='SUC1')

--Crear la vista
create view HistoriaClinica_Cita
as
select * from [WINSERVER1].[PeredaDentalSUC1].[dbo].[HISTORIACLINICACITA_SUC1]
union all
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[HISTORIACLINICACITA_SUC2]

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
--Agregar el check con el campo de fragmentaci�n igual a SUC1
alter table [HISTORIACLINICA_DATOS_SUC1] add constraint Ck_id_suc6 check (id_suc='SUC1')

--Crear vista
create view HistoriaClinica_Datos
as
select * from [WINSERVER1].[PeredaDentalSUC1].[dbo].[HISTORIACLINICA_DATOS_SUC1]
union all
select * from [WINSERVER2].[PeredaDentalSUC2].[dbo].[HISTORIACLINICA_DATOS_SUC2]

--Seleccionar datos
select * from HistoriaClinica_Datos


--Insertar datos en la vista --INSEERTAR
SET XACT_ABORT ON
begin distributed tran
insert into HistoriaClinica_Datos values (30,'Dientes con mal oclusi�n','Ninguno',
'S�','Ninguno', 'Ortodoncia', 'Ninguno', 'ORTO', '1757203912', 'CIT30', 'SUC2')
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













































































select * from PACIENTEDATOS_SUC1
select * from PACIENTECITA_SUC1

select (rtrim(nombre_pac)+' '+ apellidos_pac) as 'nombres_odon' from PACIENTECITA_SUC1

CREATE VIEW Paciente_Info
AS
select 
p1.[cedula_pac],
p2.[nombre_pac],
p2.[apellidos_pac],
p1.[edad_pac],
p1.[fechaNacimiento_pac],
p1.[direccion_pac],
p1.[canton_pac],
p1.[telefonoFijo_pac],
p1.[celular_pac],
p1.[correoElectronico_pac],
p1.[sexo_pac],
p1.[estado_pac],
p1.[contactoNombres],
p1.[contactoApellidos],
p1.[contacto_telefonoFijo],
p1.[contactoCelular],
p1.[referidoPor],
p1.[id_suc]
from PACIENTEDATOS_SUC1 as p1 
JOIN PACIENTECITA_SUC1 as p2
ON p1.cedula_pac = p2.cedula_pac



select 
p1.[cedula_pac],
p2.[nombre_pac],
p2.[apellidos_pac],
p1.[edad_pac],
p1.[fechaNacimiento_pac],
p1.[direccion_pac],
p1.[canton_pac],
p1.[telefonoFijo_pac],
p1.[celular_pac],
p1.[correoElectronico_pac],
p1.[sexo_pac],
p1.[estado_pac],
p1.[contactoNombres],
p1.[contactoApellidos],
p1.[contacto_telefonoFijo],
p1.[contactoCelular],
p1.[referidoPor],
p1.[id_suc]
from PACIENTEDATOS_SUC1 as p1 
JOIN PACIENTECITA_SUC1 as p2
ON p1.cedula_pac = p2.cedula_pac
where cedula_pac='1754907782'



DELETE FROM PACIENTEDATOS_SUC1 WHERE cedula_pac = '1303623340'


select * from Paciente_Info where cedula_pac='1754907782'


UPDATE Paciente_Info
SET telefonoFijo_pac = '022357849'
where cedula_pac='1775352834'



INSERT INTO Paciente_Info VALUES('1720598349', 18, '1997-12-12', 'El Inca', 'Quito', '022590424', '0996098169', 'asd@asd.com', 'M', 'activo', '', '', '', '', 'Facebook', 'SUC1');


select distinct fecha_cita as 'fecha_cita', hora_cita as 'hora_cita', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'cedula_paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'cedula_odontologo', t.nombre_trat as 'id_tratamiento'
from PACIENTECITA_SUC1 as p join CITA_SUC1 as c
on p.cedula_pac = c.cedula_paciente join ODONTOLOGOCITA_SUC1 as o 
on o.cedula_odon = c.cedula_odontologo join TRATAMIENTO as t
on t.id_trat = c.id_tratamiento
order by fecha_cita


select * from cita_suc1



UPDATE PACIENTECITA_SUC1 SET apellidos_pac='Almeida' WHERE cedula_pac='1720598349'


delete from PACIENTEDATOS_SUC1 where cedula_pac='1720598349';
delete from PACIENTECITA_SUC1 where cedula_pac='1720598349';


select * from ODONTOLOGOCITA_SUC1
select * from ODONTOLOGODATOS_SUC1

select (rtrim(nombres_odon)+' '+ apellidos_odon) as 'nombres_odon' from ODONTOLOGOCITA_SUC1

delete from ODONTOLOGOCITA_SUC1 where cedula_odon=?;
delete from ODONTOLOGODATOS_SUC1 where cedula_odon=?;


CREATE VIEW Odontologo_Info
AS
select 
o1.cedula_odon,
o2.nombres_odon,
o2.apellidos_odon,
o1.telefonoFijo_odon,
o1.celular_odon,
o1.especialidad_odon,
o1.id_suc
from ODONTOLOGODATOS_SUC1 as o1  
JOIN ODONTOLOGOCITA_SUC1 as o2
ON o1.cedula_odon = o2.cedula_odon


select * from Odontologo_Info



select * from CITA_SUC1 where fecha_cita='2019-07-30' and hora_cita='01:30:00'


select descripcion_trat from TRATAMIENTO

select (rtrim(nombre_pac)+' '+ apellidos_pac) as 'nombres_pac', cedula_pac from PACIENTECITA_SUC1

select (rtrim(nombres_odon)+' '+ apellidos_odon) as 'nombres_odon', cedula_odon from ODONTOLOGOCITA_SUC1


select * from TRATAMIENTO

select descripcion_trat, id_trat from TRATAMIENTO


select * from CITA_SUC1



DELETE FROM CITA_SUC1 WHERE id_cita='CIT99'



select * from CITA_SUC1 where fecha_cita='2019-07-30' and hora_cita='01:30:00' and cedula_odontologo='1756574149'


SELECT * FROM PACIENTECITA_SUC1 WHERE cedula_pac='1754907782'
select * from ODONTOLOGOCITA_SUC1 WHERE cedula_odon='1732124555'









select distinct fecha_cita as 'fecha_cita', hora_cita as 'hora_cita', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'cedula_paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'cedula_odontologo', t.nombre_trat as 'id_tratamiento'
from PACIENTECITA_SUC1 as p join CITA_SUC1 as c
on p.cedula_pac = c.cedula_paciente join ODONTOLOGOCITA_SUC1 as o 
on o.cedula_odon = c.cedula_odontologo join TRATAMIENTO as t
on t.id_trat = c.id_tratamiento
order by fecha_cita


CREATE PROCEDURE citasPD    
AS   
select distinct fecha_cita as 'fecha_cita', hora_cita as 'hora_cita', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'cedula_paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'cedula_odontologo', t.nombre_trat as 'id_tratamiento'
from PACIENTECITA_SUC1 as p join CITA_SUC1 as c
on p.cedula_pac = c.cedula_paciente join ODONTOLOGOCITA_SUC1 as o 
on o.cedula_odon = c.cedula_odontologo join TRATAMIENTO as t
on t.id_trat = c.id_tratamiento
order by fecha_cita
GO  

EXECUTE citasPD


select distinct fecha_cita as 'fecha_cita', hora_cita as 'hora_cita', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'cedula_paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'cedula_odontologo', t.nombre_trat as 'id_tratamiento'
from PACIENTECITA_SUC1 as p join CITA_SUC1 as c
on p.cedula_pac = c.cedula_paciente join ODONTOLOGOCITA_SUC1 as o 
on o.cedula_odon = c.cedula_odontologo join TRATAMIENTO as t
on t.id_trat = c.id_tratamiento
where id_cita='CITA100'
order by fecha_cita




CREATE PROCEDURE citasPDW    
	@idCita nvarchar(10)
	@fecha_cita 
AS   
select distinct fecha_cita as 'fecha_cita', hora_cita as 'hora_cita', (rtrim(p.nombre_pac)+' '+ p.apellidos_pac) as 'cedula_paciente',
(rtrim(o.nombres_odon)+' '+ o.apellidos_odon) as 'cedula_odontologo', t.nombre_trat as 'id_tratamiento'
from PACIENTECITA_SUC1 as p join CITA_SUC1 as c
on p.cedula_pac = c.cedula_paciente join ODONTOLOGOCITA_SUC1 as o 
on o.cedula_odon = c.cedula_odontologo join TRATAMIENTO as t
on t.id_trat = c.id_tratamiento
where id_cita=@idCita
order by fecha_cita
GO  

EXECUTE citasPDW N'CITA100'











