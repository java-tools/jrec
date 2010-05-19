
CREATE TABLE TBL_RF_RECORDFIELDS
(
  RECORDID           NUMBER(10)                 NOT NULL,
  SUBKEY             NUMBER(6)                  NOT NULL,
  FIELDPOS           NUMBER(6),
  FIELDLENGTH        NUMBER(6),
  FIELDNAME          VARCHAR2(30 BYTE),
  DESCRIPTION        VARCHAR2(255 BYTE),
  FIELDTYPE          NUMBER(3),
  DECIMALPOS         NUMBER(3),
  DEFAULTVALUE       VARCHAR2(15 BYTE),
  COBOLNAME          VARCHAR2(30 BYTE),
  FORMATDESCRIPTION  VARCHAR2(255 BYTE)
  Cell_Format        NUMBER(6),
  Parameter          varchar2(50),

)
TABLESPACE SMS_CONV_D
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX TBL_RF_RECORDFIELDS_PK ON TBL_RF_RECORDFIELDS
(RECORDID, SUBKEY)
LOGGING
TABLESPACE SMS_CONV_D
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


ALTER TABLE TBL_RF_RECORDFIELDS ADD (
  CONSTRAINT TBL_RF_RECORDFIELDS_PK PRIMARY KEY (RECORDID, SUBKEY)
    USING INDEX 
    TABLESPACE SMS_CONV_D
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          1M
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       2147483645
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));



CREATE TABLE TBL_RS_SUBRECORDS
(
  RECORDID     NUMBER(9)                        NOT NULL,
  CHILDRECORD  NUMBER(9)                        NOT NULL,
  FIELDSTART   NUMBER(9),
  FIELD        VARCHAR2(30 BYTE),
  FIELDVALUE   VARCHAR2(30 BYTE)
  Parent_RecordId  NUMBER(9),
)
TABLESPACE SMS_CONV_D
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX TBL_RS_SUBRECORDS_PK ON TBL_RS_SUBRECORDS
(RECORDID, CHILDRECORD)
LOGGING
TABLESPACE SMS_CONV_D
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


ALTER TABLE TBL_RS_SUBRECORDS ADD (
  CONSTRAINT TBL_RS_SUBRECORDS_PK PRIMARY KEY (RECORDID, CHILDRECORD)
    USING INDEX 
    TABLESPACE SMS_CONV_D
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          1M
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       2147483645
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));




CREATE TABLE TBL_R_RECORDS
(
  RECORDID     NUMBER(9)                        NOT NULL,
  RECORDNAME   VARCHAR2(50 BYTE),
  DESCRIPTION  VARCHAR2(250 BYTE),
  RECORDTYPE   NUMBER(6),
  SYSTEM       NUMBER(6),
  LISTCHAR     CHAR(1 BYTE),
  COPYBOOK     CHAR(11 BYTE),
  DELIMITER    VARCHAR2(7 BYTE),
  QUOTE        CHAR(1 BYTE),
  POSRECIND    NUMBER(6),
  RECSEPLIST   VARCHAR2(50 BYTE),
  RECORDSEP    VARCHAR2(50 BYTE),
  EXTERNALID   NUMBER(9),
  Canonical_Name     varchar2(30),
  Record_Style       NUMBER(6),
)
TABLESPACE SMS_CONV_D
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;


ALTER TABLE TBL_R_RECORDS ADD (
  PRIMARY KEY (RECORDID)
    USING INDEX 
    TABLESPACE SMS_CONV_D
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          1M
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       2147483645
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));




CREATE TABLE TBL_TI_INTTBLS
(
  TBLID    NUMBER(6)                            NOT NULL,
  TBLKEY   NUMBER(6)                            NOT NULL,
  DETAILS  VARCHAR2(40 BYTE)
)
TABLESPACE SMS_CONV_D
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;


CREATE UNIQUE INDEX TBL_TI_INTTBLS_PK ON TBL_TI_INTTBLS
(TBLID, TBLKEY)
LOGGING
TABLESPACE SMS_CONV_D
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
NOPARALLEL;


ALTER TABLE TBL_TI_INTTBLS ADD (
  CONSTRAINT TBL_TI_INTTBLS_PK PRIMARY KEY (TBLID, TBLKEY)
    USING INDEX 
    TABLESPACE SMS_CONV_D
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          1M
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       2147483645
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));



CREATE TABLE TBL_T_TABLE
(
  TBLID           NUMBER(6)                     NOT NULL,
  TBLNAME         VARCHAR2(20 BYTE),
  TBLDESCRIPTION  VARCHAR2(80 BYTE)
)
TABLESPACE SMS_CONV_D
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       2147483645
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCACHE
NOPARALLEL;


ALTER TABLE TBL_T_TABLE ADD (
  PRIMARY KEY (TBLID)
    USING INDEX 
    TABLESPACE SMS_CONV_D
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          1M
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       2147483645
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));


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




	     
