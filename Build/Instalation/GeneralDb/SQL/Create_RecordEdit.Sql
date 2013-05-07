-- ======================================================================
-- ===   Sql Script for Database : RecordEdit
-- ===
-- === Build : 32
-- ======================================================================

CREATE TABLE TBL_R_RECORDS
  (
    RECORDID        INTEGER,
    RECORDNAME      varchar(50),
    DESCRIPTION     varchar(250),
    RECORDTYPE      smallint,
    SYSTEM          smallint,
    LISTCHAR        char(1),
    COPYBOOK        char(31),
    DELIMITER       char(7),
    QUOTE           char(1),
    POSRECIND       INTEGER,
    RECSEPLIST      varchar(50),
    RECORDSEP       varchar(50),
    EXTERNALID      INTEGER,
    Canonical_Name  varchar(30),
    Record_Style    int,
    FILE_STRUCTURE    int
    
    --,

    --primary key(RECORDID)
  );

--- Alternatively use the following to create the index -

  CREATE UNIQUE INDEX TBL_R_RECORDS_PK ON TBL_R_RECORDS(RECORDID);

-- ======================================================================

CREATE TABLE TBL_RF1_RECORDFIELDS
  (
    RECORDID            INTEGER,
    SUB_KEY             integer,
    FIELD_POS           integer,
    FIELD_LENGTH        integer,
    FIELD_NAME          varchar(120),
    FIELD_DESCRIPTION   varchar(350),
    FIELD_TYPE          integer,
    DECIMAL_POS         smallint,
    DEFAULT_VALUE       varchar(120),
    COBOL_NAME          varchar(30),
    FORMAT_DESCRIPTION  varchar(250),
    Cell_Format         integer,
    Field_Parameter     varchar(120)
    
--,
--    primary key(RECORDID,SUBKEY),
--
--    foreign key(RECORDID) references TBL_R_RECORDS(RECORDID)
  );

  CREATE UNIQUE INDEX TBL_RF1_RECORDFIELDS_PK ON TBL_RF1_RECORDFIELDS(RECORDID,SUB_KEY);

  
  
--CREATE TABLE TBL_RF_RECORDFIELDS
--  (
--    RECORDID           INTEGER,
--    SUBKEY             smallint,
--    FIELDPOS           smallint,
--    FIELDLENGTH        smallint,
--    FIELDNAME          varchar(30),
--    DESCRIPTION        varchar(250),
--    FIELDTYPE          smallint,
--    DECIMALPOS         smallint,
--    DEFAULTVALUE       varchar(15),
--    COBOLNAME          varchar(30),
--    FORMATDESCRIPTION  varchar(250),
--    Cell_Format        smallint,
--    Parameter          varchar(30)
--    
----,
----    primary key(RECORDID,SUBKEY),
--
----    foreign key(RECORDID) references TBL_R_RECORDS(RECORDID)
--  );
--
--  CREATE UNIQUE INDEX TBL_RF_RECORDFIELDS_PK ON TBL_RF_RECORDFIELDS(RECORDID,SUBKEY);

-- ======================================================================

CREATE TABLE TBL_RS2_SUBRECORDS
(
   RECORDID integer,
   CHILD_KEY integer,
   CHILD_RECORD integer,
   FIELD_START integer,
   FIELD_NAME varchar(30),
   FIELD_VALUE varchar(30),
   PARENT_RECORDID integer,
   OPERATOR_SEQUENCE smallint,
   DEFAULT_RECORD varchar(1),
   CHILD_NAME varchar(30),
   CHILD_ID integer
)
;
CREATE UNIQUE INDEX TBL_RS2_SUBRECORDSPK ON TBL_RS2_SUBRECORDS
(
  RECORDID,
  CHILD_KEY
)
;


-- ======================================================================
--
-- CREATE TABLE TBL_RFS_FIELDSELECTION
-- (
--   RECORDID integer,
--   CHILD_KEY smallint,
--   FIELD_NO smallint,
--   BOOLEAN_OPERATOR smallint,
--   FIELD_NAME varchar(30),
--   OPERATOR varchar(2),
--   FIELD_VALUE varchar(30)
--)
--;
--CREATE UNIQUE INDEX TBL_RFS_FIELDSELECTIONPK ON TBL_RFS_FIELDSELECTION
--(
--  RECORDID,
--  CHILD_KEY,
--  FIELD_NO
--)
--;
--
--
-- ======================================================================

CREATE TABLE TBL_T_TABLE
  (
    TBLID           smallint,
    TBLNAME         varchar(20),
    TBLDESCRIPTION  varchar(80),

    primary key(TBLID)
  );

  CREATE UNIQUE INDEX TBL_T_TABLE_PK ON TBL_T_TABLE(TBLID);

-- ======================================================================

CREATE TABLE TBL_TI_INTTBLS
  (
    TBLID    smallint,
    TBLKEY   smallint,
    DETAILS  varchar(40)
    
--,
--    primary key(TBLID,TBLKEY),

--    foreign key(TBLID) references TBL_T_TABLE(TBLID)
  );

  CREATE UNIQUE INDEX TBL_TI_INTTBLS_PK ON TBL_TI_INTTBLS(TBLID,TBLKEY);

-- ======================================================================


CREATE TABLE TBL_C_COMBOS
(
   COMBO_ID integer,
   SYSTEM smallint,
   COMBO_NAME varchar(30),
   COLUMN_TYPE smallint
);
CREATE UNIQUE INDEX TBL_C_COMBOS_PK1 ON TBL_C_COMBOS(COMBO_NAME);
CREATE UNIQUE INDEX TBL_C_COMBOS_PK ON TBL_C_COMBOS(COMBO_ID);


CREATE TABLE TBL_CI_COMBOITEMS
(
   COMBO_ID integer,
   COMBO_CODE varchar(30),
   COMBO_VALUE varchar(60)
);
CREATE UNIQUE INDEX TBL_CI_COMBOITEMS_PK ON TBL_CI_COMBOITEMS
(
  COMBO_ID,
  COMBO_CODE
);


