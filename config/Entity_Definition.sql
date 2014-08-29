drop database datalyticx;
create database datalyticx;
use datalyticx;
create table Entity_Definition (Id BIGINT PRIMARY KEY  not null AUTO_INCREMENT ,
Entity VarChar( 50),
FieldName VarChar( 50),
LegitimateValue VarChar( 50),
Mandatory VarChar( 50), INDEX (Id) 
);

create table Material_Master_Data ( MaterialId  BIGINT PRIMARY KEY  not null AUTO_INCREMENT , 
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
EKGRP VarChar( 50),MMSTA VarChar( 50),MMSTD VarChar( 50),DISMM VarChar( 50),DISPO VarChar( 50),BSTMI VarChar( 50),BSTMA VarChar( 50),DISLS VarChar( 50),BESKZ VarChar( 50),LGPRO VarChar( 50),PLIFZ VarChar( 50),FHORI VarChar( 50),STRGR VarChar( 50),MTVFP VarChar( 50),BKLAS VarChar( 50),VERPR VarChar( 50),ZKPRS VarChar( 50),PEINH VarChar( 50),STPRS VarChar( 50),VMSTP VarChar( 50),KTGRM VarChar( 50), INDEX ( MaterialId) ) ;
