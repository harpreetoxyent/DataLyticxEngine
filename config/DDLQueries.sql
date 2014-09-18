drop database datalyticx;
create database datalyticx;
use datalyticx;

drop table IF EXISTS Business_Unit;
create table Business_Unit (BU_Id BIGINT PRIMARY KEY  not null AUTO_INCREMENT ,
Department VarChar( 50), INDEX (BU_Id) 
);

drop table IF EXISTS Entities;
create table Entities (EntityId BIGINT PRIMARY KEY not null AUTO_INCREMENT ,
Entity VarChar( 50), INDEX (EntityId) 
);

drop table IF EXISTS BU_Entity;
create table BU_Entity (BU_EntityId BIGINT PRIMARY KEY  not null AUTO_INCREMENT ,
BU VarChar( 50),
Entity VarChar( 50), INDEX (BU_EntityId) 
);

drop table IF EXISTS Quality;
create table Quality (Id BIGINT PRIMARY KEY not null AUTO_INCREMENT ,
BU_EntityId BIGINT,
Quality VarChar( 50),
Percentage VarChar( 50),
RecordCount VarChar( 50), INDEX (Id) 
);

drop table IF EXISTS BU_Entity_Def;
create table BU_Entity_Def (Id BIGINT PRIMARY KEY  not null AUTO_INCREMENT ,
BU_EntityId BIGINT,
FieldName VarChar( 50),
LegitimateValue VarChar( 50),
Mandatory VarChar( 50), INDEX (Id) 
);

drop table IF EXISTS Incorrect_Data;
create table Incorrect_Data (Id BIGINT PRIMARY KEY  not null AUTO_INCREMENT ,
BU_EntityId BIGINT,
QualityName VarChar( 50),
FieldName VarChar( 50),
IdealValue VarChar( 50),
ActualValue VarChar( 50), INDEX (Id) 
);

drop table IF EXISTS Actual_Data;
create table Actual_Data ( DataId  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , 
BU VarChar( 50),
ENTITY VarChar( 50), 
MATNR VarChar( 50),
MTART VarChar( 50),
MAKTX VarChar( 50),
MEINS VarChar( 50),
MATKL VarChar( 50),
SPART VarChar( 50),
PRDHA VarChar( 50),
MSTAE VarChar( 50),
MSTDE VarChar( 50),
MTPOS VarChar( 50),
BRGEW VarChar( 50),
GEWEI VarChar( 50),
NTGEW VarChar( 50),
SPRAS VarChar( 50),
WRKST VarChar( 50),
VKORG VarChar( 50),
VTWEG VarChar( 50),
MSTAV VarChar( 50),
LFMNG VarChar( 50),
MTPOS_MARA VarChar( 50),
TRAGR VarChar( 50),
LADGR VarChar( 50),
PRCTR VarChar( 50),
STAWN VarChar( 50),
TAXIM VarChar( 50),
WERKS VarChar( 50),
EKGRP VarChar( 50),MMSTA VarChar( 50),MMSTD VarChar( 50),DISMM VarChar( 50),DISPO VarChar( 50),BSTMI VarChar( 50),BSTMA VarChar( 50),DISLS VarChar( 50),BESKZ VarChar( 50),LGPRO VarChar( 50),PLIFZ VarChar( 50),FHORI VarChar( 50),STRGR VarChar( 50),MTVFP VarChar( 50),BKLAS VarChar( 50),VERPR VarChar( 50),ZKPRS VarChar( 50),PEINH VarChar( 50),STPRS VarChar( 50),VMSTP VarChar( 50),KTGRM VarChar( 50), INDEX ( DataId) ) ;
