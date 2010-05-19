

/*
 * @Author Generate by XmplLineBuilder
 *
 * Purpose: line to access AmsReceipt records
 */
package net.sf.RecordEditor.examples.genCode;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.utils.CopyBookInterface;

/**
 * Record to Access
 *
 */
public class AmsReceipt extends Line {

    public static final String COPYBOOK_NAME = "ams Receipt";

    public static final String RECORD_AMS_RECEIPT_FH_HEADER = "ams Receipt FH Header";
    public static final String RECORD_AMS_RECEIPT_RH_RECEIPT_HEADER = "ams Receipt RH Receipt Header";
    public static final String RECORD_AMS_RECEIPT_RD_RECIPT_PRODUCT = "ams Receipt RD Recipt Product";
    public static final String RECORD_AMS_RECEIPT_RS_RECIPT_STORE = "ams Receipt RS Recipt Store";
    public static final String RECORD_AMS_RECEIPT_AS = "ams Receipt AS";
    public static final String RECORD_AMS_RECEIPT_SO = "ams Receipt SO";
    public static final String RECORD_AMS_RECEIPT_SC = "ams Receipt SC";
    public static final String RECORD_AMS_RECEIPT_AP = "ams Receipt AP";
    public static final String RECORD_AMS_RECEIPT_AR = "ams Receipt AR";
    public static final String RECORD_AMS_RECEIPT_FT_FILE_TRAILER = "ams Receipt FT File Trailer";

    private static LayoutDetail copyBook;

    private static boolean toInit = true;
    private static int indexAmsReceiptFhHeader;
    private static int indexAmsReceiptRhReceiptHeader;
    private static int indexAmsReceiptRdReciptProduct;
    private static int indexAmsReceiptRsReciptStore;
    private static int indexAmsReceiptAs;
    private static int indexAmsReceiptSo;
    private static int indexAmsReceiptSc;
    private static int indexAmsReceiptAp;
    private static int indexAmsReceiptAr;
    private static int indexAmsReceiptFtFileTrailer;


  // record fields for "ams Receipt FH Header"

    public static final int R0_RECORD_TYPE = 0;
    public static final int R0_FH_DESCRIPTION4 = 1;
    public static final int R0_FH_BRAND_ID = 2;
    public static final int R0_CREATION_DATE = 3;
    public static final int R0_CREATION_TIME = 4;
    public static final int R0_FH_FILLER = 5;

  // record fields for "ams Receipt RH Receipt Header"

    public static final int R1_RECORD_TYPE = 0;
    public static final int R1_BRAND_ID_RH5 = 1;
    public static final int R1_ORDER_NO_RH = 2;
    public static final int R1_RECEIPT_LOCN_RH = 3;
    public static final int R1_SUP_ID_RH = 4;
    public static final int R1_RECEIPT_DATE_RH = 5;
    public static final int R1_RECEIPT_TIME_RH = 6;
    public static final int R1_BRAND_XREF_RH = 7;
    public static final int R1_BRAND_DC_NO_RH = 8;
    public static final int R1_RCPT_FINAL_STAT_RH = 9;
    public static final int R1_PROCESSED_IND_RH = 10;
    public static final int R1_ASN_RH = 11;
    public static final int R1_ASN_SEQ_NO_RH = 12;
    public static final int R1_SMPL_CHCK_CTN_RH = 13;
    public static final int R1_SMPL_CHCK_U_RH = 14;
    public static final int R1_INVOICE_NO_RH = 15;
    public static final int R1_TOT_RCPT_AMT_RH = 16;
    public static final int R1_TOT_RECV_QTY_RH = 17;
    public static final int R1_TOT_OUTERS_RH = 18;
    public static final int R1_CARRIER_CONNOTE_RH = 19;
    public static final int R1_ERS_STATUS_RH = 20;
    public static final int R1_CHK_RESULT_ADJD_RH = 21;
    public static final int R1_BRAND_STK_UPD_RH = 22;
    public static final int R1_ORGINL_SCM_CNT_RH = 23;
    public static final int R1_PICK_PACK_IND_RH = 24;
    public static final int R1_C_USR_UPDT_RH = 25;
    public static final int R1_TS_UPDT_RH = 26;
    public static final int R1_RH_NUM_RD = 27;
    public static final int R1_RH_NUM_AS = 28;
    public static final int R1_RH_FILLER = 29;

  // record fields for "ams Receipt RD Recipt Product"

    public static final int R2_RECORD_TYPE = 0;
    public static final int R2_BRAND_ID_RD6 = 1;
    public static final int R2_ORDER_NO_RD = 2;
    public static final int R2_RECEIPT_LOCN_RD = 3;
    public static final int R2_PROD_NO_RD = 4;
    public static final int R2_KEYCODE_RD = 5;
    public static final int R2_SEQ_NO_RD = 6;
    public static final int R2_PROD_QUALIFIER_RD = 7;
    public static final int R2_RCVD_QTY_RD = 8;
    public static final int R2_ITMS_PER_CTN_QTYRD = 9;
    public static final int R2_UNIT_COST_RD = 10;
    public static final int R2_C_USR_UPDT_RD = 11;
    public static final int R2_TS_UPDT_RD = 12;
    public static final int R2_RD_NUM_RS = 13;
    public static final int R2_RD_FILLER = 14;

  // record fields for "ams Receipt RS Recipt Store"

    public static final int R3_RECORD_TYPE = 0;
    public static final int R3_BRAND_ID7 = 1;
    public static final int R3_ORDER_NO = 2;
    public static final int R3_RECEIPT_LOCATION = 3;
    public static final int R3_PROD_NO = 4;
    public static final int R3_KEYCODE = 5;
    public static final int R3_SEQUENCE_NO = 6;
    public static final int R3_STORE_NO = 7;
    public static final int R3_RECEIVE_ALC_QTY = 8;
    public static final int R3_OUTSTANDING_ORD_QTY = 9;
    public static final int R3_C_USR_UPDT = 10;
    public static final int R3_TS_UPDT = 11;
    public static final int R3_ACT_RECV_QTY = 12;

  // record fields for "ams Receipt AS"

    public static final int R4_RECORD_TYPE = 0;
    public static final int R4_ASN_AS8 = 1;
    public static final int R4_ASN_SEQ_NO_AS = 2;
    public static final int R4_SUP_EDI_ADDR_AS = 3;
    public static final int R4_BRAND_ID_AS = 4;
    public static final int R4_PROD_TEST_IND_AS = 5;
    public static final int R4_TRANSACTION_DT_AS = 6;
    public static final int R4_TRANSACTION_TM_AS = 7;
    public static final int R4_TRANS_SET_PURP_AS = 8;
    public static final int R4_ASN_STRUCTURE_AS = 9;
    public static final int R4_SHIP_GROSS_VOL_AS = 10;
    public static final int R4_VOL_MEASURE_CD_AS = 11;
    public static final int R4_SHIP_PACK_CODE_AS = 12;
    public static final int R4_SHIP_LDG_QTY_AS = 13;
    public static final int R4_SHIP_LDG_WGT_AS = 14;
    public static final int R4_SHIP_WGT_CODE_AS = 15;
    public static final int R4_SHIP_STATUS_AS = 16;
    public static final int R4_CARRIER_CONNOTE_AS = 17;
    public static final int R4_SHIP_SCHED_DT_AS = 18;
    public static final int R4_DLVY_SCHED_DT_AS = 19;
    public static final int R4_M_DC_DLVY_AS = 20;
    public static final int R4_SUP_ID_AS = 21;
    public static final int R4_SUP_NAME_AS = 22;
    public static final int R4_A_ALLOC_REG_NET_AS = 23;
    public static final int R4_VALID_IND_AS = 24;
    public static final int R4_REASON_CODE_AS = 25;
    public static final int R4_ASN_STATE_AS = 26;
    public static final int R4_FULL_DLVY_IND_AS = 27;
    public static final int R4_SHIP_SSCC_AS = 28;
    public static final int R4_EDI_ERR_SEVER_I_AS = 29;
    public static final int R4_ASN_RCPT_DT_AS = 30;
    public static final int R4_MERCH_RCPT_DT_AS = 31;
    public static final int R4_SUP_RATING_AS = 32;
    public static final int R4_INTEG_FLG_AS = 33;
    public static final int R4_CANCEL_REASON_C_AS = 34;
    public static final int R4_P_CHECKED_CTNS_AS = 35;
    public static final int R4_P_CHECKED_UNITS_AS = 36;
    public static final int R4_ASN_ARRV_TM_AS = 37;
    public static final int R4_EXTRACT_STATUS_AS = 38;
    public static final int R4_C_USR_UPDT_AS = 39;
    public static final int R4_TS_UPDT_AS = 40;
    public static final int R4_AS_DLVY_TYPE = 41;
    public static final int R4_AS_NUM_SO = 42;
    public static final int R4_AS_NUM_AR = 43;
    public static final int R4_AS_FILLER = 44;

  // record fields for "ams Receipt SO"

    public static final int R5_RECORD_TYPE = 0;
    public static final int R5_ASN_SO9 = 1;
    public static final int R5_ASN_SEQ_NO_SO = 2;
    public static final int R5_ORDER_NO_SO = 3;
    public static final int R5_STORE_SO = 4;
    public static final int R5_ORDER_DATE_SO = 5;
    public static final int R5_ORDER_PACK_CODE_SO = 6;
    public static final int R5_ORDER_LDG_QTY_SO = 7;
    public static final int R5_ORDER_LDG_WGT_SO = 8;
    public static final int R5_ORDER_WGT_CODE_SO = 9;
    public static final int R5_SHIP_ORD_STATUS_SO = 10;
    public static final int R5_SUP_INV_NO_SO = 11;
    public static final int R5_ERROR_CODE_SO = 12;
    public static final int R5_ORDER_NO_NUM_SO = 13;
    public static final int R5_ORDER_NO_850_SO = 14;
    public static final int R5_C_USR_UPDT_SO = 15;
    public static final int R5_TS_UPDT_SO = 16;
    public static final int R5_SO_NUM_SC = 17;
    public static final int R5_SO_FILLER = 18;

  // record fields for "ams Receipt SC"

    public static final int R6_RECORD_TYPE = 0;
    public static final int R6_ASN_SC10 = 1;
    public static final int R6_ASN_SEQ_NO_SC = 2;
    public static final int R6_ORDER_NO_SC = 3;
    public static final int R6_STORE_SC = 4;
    public static final int R6_SCM_SC = 5;
    public static final int R6_SCM_SEQ_NO_SC = 6;
    public static final int R6_CONTAINER_TYPE_SC = 7;
    public static final int R6_PLT_PACK_CODE_SC = 8;
    public static final int R6_PLT_LADING_QTY_SC = 9;
    public static final int R6_PLT_LADING_WGT_SC = 10;
    public static final int R6_PLT_WGT_MSRE_CD_SC = 11;
    public static final int R6_PARENT_SCM_SC = 12;
    public static final int R6_PARENT_SCM_SEQ_SC = 13;
    public static final int R6_ERROR_CODE_SC = 14;
    public static final int R6_SCM_RCEIV_QUALFRSC = 15;
    public static final int R6_SCM_SAMPLE_IND_SC = 16;
    public static final int R6_C_USR_UPDT_SC = 17;
    public static final int R6_TS_UPDT_SC = 18;
    public static final int R6_SC_NUM_AP = 19;
    public static final int R6_SC_FILLER = 20;

  // record fields for "ams Receipt AP"

    public static final int R7_RECORD_TYPE = 0;
    public static final int R7_ASN_AP11 = 1;
    public static final int R7_ASN_SEQ_NO_AP = 2;
    public static final int R7_ORDER_NO_AP = 3;
    public static final int R7_STORE_AP = 4;
    public static final int R7_SCM_AP = 5;
    public static final int R7_SCM_SEQ_NO_AP = 6;
    public static final int R7_PROD_NO_AP = 7;
    public static final int R7_PROD_SEQ_NO_AP = 8;
    public static final int R7_PROD_QUALIFIER_AP = 9;
    public static final int R7_QTY_SHIPPED_AP = 10;
    public static final int R7_PROD_MEASURE_CD_AP = 11;
    public static final int R7_USE_BY_DATE_AP = 12;
    public static final int R7_KEYCODE_AP = 13;
    public static final int R7_ERROR_CODE_AP = 14;
    public static final int R7_DANGR_GOODS_NO_AP = 15;
    public static final int R7_DANGR_GOODS_CLS_AP = 16;
    public static final int R7_C_USR_UPDT_AP = 17;
    public static final int R7_TS_UPDT_AP = 18;
    public static final int R7_AP_FILLER = 19;

  // record fields for "ams Receipt AR"

    public static final int R8_RECORD_TYPE = 0;
    public static final int R8_BRAND_ID_AR12 = 1;
    public static final int R8_ORDER_NO_AR = 2;
    public static final int R8_LOGISTICS_LOC_NOAR = 3;
    public static final int R8_SCM_AR = 4;
    public static final int R8_PROD_NO_AR = 5;
    public static final int R8_PROD_QUALIFIER_AR = 6;
    public static final int R8_QUALITY_CODE_AR = 7;
    public static final int R8_CHECKED_QTY_AR = 8;
    public static final int R8_SUP_ID_AR = 9;
    public static final int R8_ASN_AR = 10;
    public static final int R8_BRAND_DC_AR = 11;
    public static final int R8_VALID_PROD_IND_AR = 12;
    public static final int R8_PROCESSED_IND_AR = 13;
    public static final int R8_CHECKED_BY_AR = 14;
    public static final int R8_CHECKED_DATE_AR = 15;
    public static final int R8_CHECKED_TIME_AR = 16;
    public static final int R8_C_USR_UPDT_AR = 17;
    public static final int R8_TS_UPDT_AR = 18;
    public static final int R8_AR_FILLER = 19;

  // record fields for "ams Receipt FT File Trailer"

    public static final int R9_RECORD_TYPE = 0;
    public static final int R9_FT_NUM_RECDS13 = 1;
    public static final int R9_FT_NUM_FH = 2;
    public static final int R9_FT_NUM_RH = 3;
    public static final int R9_FT_NUM_RS = 4;
    public static final int R9_FT_NUM_AS = 5;
    public static final int R9_FT_NUM_SO = 6;
    public static final int R9_FT_NUM_SC = 7;
    public static final int R9_FT_NUM_AP = 8;
    public static final int R9_FT_NUM_AR = 9;
    public static final int R9_FT_NUM_FT = 10;
    public static final int R9_FT_FILLER = 11;



  // record fields for "ams Receipt FH Header"

    public static final FieldDetail FIELD_R0_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R0_FH_DESCRIPTION4
          = new FieldDetail("Fh Description", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R0_FH_BRAND_ID
          = new FieldDetail("Fh Brand Id", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R0_CREATION_DATE
          = new FieldDetail("Creation Date", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R0_CREATION_TIME
          = new FieldDetail("Creation Time", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R0_FH_FILLER
          = new FieldDetail("Fh Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt RH Receipt Header"

    public static final FieldDetail FIELD_R1_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_BRAND_ID_RH5
          = new FieldDetail("Brand Id Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_ORDER_NO_RH
          = new FieldDetail("Order No Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RECEIPT_LOCN_RH
          = new FieldDetail("Receipt Locn Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_SUP_ID_RH
          = new FieldDetail("Sup Id Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RECEIPT_DATE_RH
          = new FieldDetail("Receipt Date Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RECEIPT_TIME_RH
          = new FieldDetail("Receipt Time Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_BRAND_XREF_RH
          = new FieldDetail("Brand Xref Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_BRAND_DC_NO_RH
          = new FieldDetail("Brand Dc No Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RCPT_FINAL_STAT_RH
          = new FieldDetail("Rcpt Final Stat Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_PROCESSED_IND_RH
          = new FieldDetail("Processed Ind Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_ASN_RH
          = new FieldDetail("Asn Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_ASN_SEQ_NO_RH
          = new FieldDetail("Asn Seq No Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_SMPL_CHCK_CTN_RH
          = new FieldDetail("Smpl Chck Ctn Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_SMPL_CHCK_U_RH
          = new FieldDetail("Smpl Chck U Rh", "", 8, 2, "", 0, "");
    public static final FieldDetail FIELD_R1_INVOICE_NO_RH
          = new FieldDetail("Invoice No Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_TOT_RCPT_AMT_RH
          = new FieldDetail("Tot Rcpt Amt Rh", "", 8, 2, "", 0, "");
    public static final FieldDetail FIELD_R1_TOT_RECV_QTY_RH
          = new FieldDetail("Tot Recv Qty Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_TOT_OUTERS_RH
          = new FieldDetail("Tot Outers Rh", "", 8, 2, "", 0, "");
    public static final FieldDetail FIELD_R1_CARRIER_CONNOTE_RH
          = new FieldDetail("Carrier Connote Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_ERS_STATUS_RH
          = new FieldDetail("Ers Status Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_CHK_RESULT_ADJD_RH
          = new FieldDetail("Chk Result Adjd Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_BRAND_STK_UPD_RH
          = new FieldDetail("Brand Stk Upd Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_ORGINL_SCM_CNT_RH
          = new FieldDetail("Orginl Scm Cnt Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_PICK_PACK_IND_RH
          = new FieldDetail("Pick Pack Ind Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_C_USR_UPDT_RH
          = new FieldDetail("C Usr Updt Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_TS_UPDT_RH
          = new FieldDetail("Ts Updt Rh", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RH_NUM_RD
          = new FieldDetail("Rh Num Rd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RH_NUM_AS
          = new FieldDetail("Rh Num As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R1_RH_FILLER
          = new FieldDetail("Rh Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt RD Recipt Product"

    public static final FieldDetail FIELD_R2_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_BRAND_ID_RD6
          = new FieldDetail("Brand Id Rd", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_ORDER_NO_RD
          = new FieldDetail("Order No Rd", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_RECEIPT_LOCN_RD
          = new FieldDetail("Receipt Locn Rd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_PROD_NO_RD
          = new FieldDetail("Prod No Rd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_KEYCODE_RD
          = new FieldDetail("Keycode Rd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_SEQ_NO_RD
          = new FieldDetail("Seq No Rd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_PROD_QUALIFIER_RD
          = new FieldDetail("Prod Qualifier Rd", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_RCVD_QTY_RD
          = new FieldDetail("Rcvd Qty Rd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_ITMS_PER_CTN_QTYRD
          = new FieldDetail("Itms Per Ctn Qtyrd", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_UNIT_COST_RD
          = new FieldDetail("Unit Cost Rd", "", 8, 3, "", 0, "");
    public static final FieldDetail FIELD_R2_C_USR_UPDT_RD
          = new FieldDetail("C Usr Updt Rd", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_TS_UPDT_RD
          = new FieldDetail("Ts Updt Rd", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_RD_NUM_RS
          = new FieldDetail("Rd Num Rs", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R2_RD_FILLER
          = new FieldDetail("Rd Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt RS Recipt Store"

    public static final FieldDetail FIELD_R3_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_BRAND_ID7
          = new FieldDetail("Brand Id", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_ORDER_NO
          = new FieldDetail("Order No", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_RECEIPT_LOCATION
          = new FieldDetail("Receipt Location", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_PROD_NO
          = new FieldDetail("Prod No", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_KEYCODE
          = new FieldDetail("Keycode", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_SEQUENCE_NO
          = new FieldDetail("Sequence No", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_STORE_NO
          = new FieldDetail("Store No", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_RECEIVE_ALC_QTY
          = new FieldDetail("Receive Alc Qty", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_OUTSTANDING_ORD_QTY
          = new FieldDetail("Outstanding Ord Qty", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_C_USR_UPDT
          = new FieldDetail("C Usr Updt", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_TS_UPDT
          = new FieldDetail("Ts Updt", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R3_ACT_RECV_QTY
          = new FieldDetail("Act Recv Qty", "", 7, 0, "", 0, "");

  // record fields for "ams Receipt AS"

    public static final FieldDetail FIELD_R4_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_ASN_AS8
          = new FieldDetail("Asn As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_ASN_SEQ_NO_AS
          = new FieldDetail("Asn Seq No As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SUP_EDI_ADDR_AS
          = new FieldDetail("Sup Edi Addr As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_BRAND_ID_AS
          = new FieldDetail("Brand Id As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_PROD_TEST_IND_AS
          = new FieldDetail("Prod Test Ind As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_TRANSACTION_DT_AS
          = new FieldDetail("Transaction Dt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_TRANSACTION_TM_AS
          = new FieldDetail("Transaction Tm As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_TRANS_SET_PURP_AS
          = new FieldDetail("Trans Set Purp As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_ASN_STRUCTURE_AS
          = new FieldDetail("Asn Structure As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_GROSS_VOL_AS
          = new FieldDetail("Ship Gross Vol As", "", 8, 3, "", 0, "");
    public static final FieldDetail FIELD_R4_VOL_MEASURE_CD_AS
          = new FieldDetail("Vol Measure Cd As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_PACK_CODE_AS
          = new FieldDetail("Ship Pack Code As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_LDG_QTY_AS
          = new FieldDetail("Ship Ldg Qty As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_LDG_WGT_AS
          = new FieldDetail("Ship Ldg Wgt As", "", 8, 3, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_WGT_CODE_AS
          = new FieldDetail("Ship Wgt Code As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_STATUS_AS
          = new FieldDetail("Ship Status As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_CARRIER_CONNOTE_AS
          = new FieldDetail("Carrier Connote As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_SCHED_DT_AS
          = new FieldDetail("Ship Sched Dt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_DLVY_SCHED_DT_AS
          = new FieldDetail("Dlvy Sched Dt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_M_DC_DLVY_AS
          = new FieldDetail("M Dc Dlvy As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SUP_ID_AS
          = new FieldDetail("Sup Id As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SUP_NAME_AS
          = new FieldDetail("Sup Name As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_A_ALLOC_REG_NET_AS
          = new FieldDetail("A Alloc Reg Net As", "", 8, 4, "", 0, "");
    public static final FieldDetail FIELD_R4_VALID_IND_AS
          = new FieldDetail("Valid Ind As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_REASON_CODE_AS
          = new FieldDetail("Reason Code As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_ASN_STATE_AS
          = new FieldDetail("Asn State As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_FULL_DLVY_IND_AS
          = new FieldDetail("Full Dlvy Ind As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SHIP_SSCC_AS
          = new FieldDetail("Ship Sscc As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_EDI_ERR_SEVER_I_AS
          = new FieldDetail("Edi Err Sever I As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_ASN_RCPT_DT_AS
          = new FieldDetail("Asn Rcpt Dt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_MERCH_RCPT_DT_AS
          = new FieldDetail("Merch Rcpt Dt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_SUP_RATING_AS
          = new FieldDetail("Sup Rating As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_INTEG_FLG_AS
          = new FieldDetail("Integ Flg As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_CANCEL_REASON_C_AS
          = new FieldDetail("Cancel Reason C As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_P_CHECKED_CTNS_AS
          = new FieldDetail("P Checked Ctns As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_P_CHECKED_UNITS_AS
          = new FieldDetail("P Checked Units As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_ASN_ARRV_TM_AS
          = new FieldDetail("Asn Arrv Tm As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_EXTRACT_STATUS_AS
          = new FieldDetail("Extract Status As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_C_USR_UPDT_AS
          = new FieldDetail("C Usr Updt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_TS_UPDT_AS
          = new FieldDetail("Ts Updt As", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_AS_DLVY_TYPE
          = new FieldDetail("As Dlvy Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_AS_NUM_SO
          = new FieldDetail("As Num So", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_AS_NUM_AR
          = new FieldDetail("As Num Ar", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R4_AS_FILLER
          = new FieldDetail("As Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt SO"

    public static final FieldDetail FIELD_R5_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ASN_SO9
          = new FieldDetail("Asn So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ASN_SEQ_NO_SO
          = new FieldDetail("Asn Seq No So", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_NO_SO
          = new FieldDetail("Order No So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_STORE_SO
          = new FieldDetail("Store So", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_DATE_SO
          = new FieldDetail("Order Date So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_PACK_CODE_SO
          = new FieldDetail("Order Pack Code So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_LDG_QTY_SO
          = new FieldDetail("Order Ldg Qty So", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_LDG_WGT_SO
          = new FieldDetail("Order Ldg Wgt So", "", 8, 3, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_WGT_CODE_SO
          = new FieldDetail("Order Wgt Code So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_SHIP_ORD_STATUS_SO
          = new FieldDetail("Ship Ord Status So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_SUP_INV_NO_SO
          = new FieldDetail("Sup Inv No So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ERROR_CODE_SO
          = new FieldDetail("Error Code So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_NO_NUM_SO
          = new FieldDetail("Order No Num So", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_ORDER_NO_850_SO
          = new FieldDetail("Order No 850 So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_C_USR_UPDT_SO
          = new FieldDetail("C Usr Updt So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_TS_UPDT_SO
          = new FieldDetail("Ts Updt So", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_SO_NUM_SC
          = new FieldDetail("So Num Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R5_SO_FILLER
          = new FieldDetail("So Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt SC"

    public static final FieldDetail FIELD_R6_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_ASN_SC10
          = new FieldDetail("Asn Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_ASN_SEQ_NO_SC
          = new FieldDetail("Asn Seq No Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_ORDER_NO_SC
          = new FieldDetail("Order No Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_STORE_SC
          = new FieldDetail("Store Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_SCM_SC
          = new FieldDetail("Scm Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_SCM_SEQ_NO_SC
          = new FieldDetail("Scm Seq No Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_CONTAINER_TYPE_SC
          = new FieldDetail("Container Type Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_PLT_PACK_CODE_SC
          = new FieldDetail("Plt Pack Code Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_PLT_LADING_QTY_SC
          = new FieldDetail("Plt Lading Qty Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_PLT_LADING_WGT_SC
          = new FieldDetail("Plt Lading Wgt Sc", "", 8, 3, "", 0, "");
    public static final FieldDetail FIELD_R6_PLT_WGT_MSRE_CD_SC
          = new FieldDetail("Plt Wgt Msre Cd Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_PARENT_SCM_SC
          = new FieldDetail("Parent Scm Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_PARENT_SCM_SEQ_SC
          = new FieldDetail("Parent Scm Seq Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_ERROR_CODE_SC
          = new FieldDetail("Error Code Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_SCM_RCEIV_QUALFRSC
          = new FieldDetail("Scm Rceiv Qualfrsc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_SCM_SAMPLE_IND_SC
          = new FieldDetail("Scm Sample Ind Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_C_USR_UPDT_SC
          = new FieldDetail("C Usr Updt Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_TS_UPDT_SC
          = new FieldDetail("Ts Updt Sc", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_SC_NUM_AP
          = new FieldDetail("Sc Num Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R6_SC_FILLER
          = new FieldDetail("Sc Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt AP"

    public static final FieldDetail FIELD_R7_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_ASN_AP11
          = new FieldDetail("Asn Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_ASN_SEQ_NO_AP
          = new FieldDetail("Asn Seq No Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_ORDER_NO_AP
          = new FieldDetail("Order No Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_STORE_AP
          = new FieldDetail("Store Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_SCM_AP
          = new FieldDetail("Scm Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_SCM_SEQ_NO_AP
          = new FieldDetail("Scm Seq No Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_PROD_NO_AP
          = new FieldDetail("Prod No Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_PROD_SEQ_NO_AP
          = new FieldDetail("Prod Seq No Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_PROD_QUALIFIER_AP
          = new FieldDetail("Prod Qualifier Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_QTY_SHIPPED_AP
          = new FieldDetail("Qty Shipped Ap", "", 8, 2, "", 0, "");
    public static final FieldDetail FIELD_R7_PROD_MEASURE_CD_AP
          = new FieldDetail("Prod Measure Cd Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_USE_BY_DATE_AP
          = new FieldDetail("Use By Date Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_KEYCODE_AP
          = new FieldDetail("Keycode Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_ERROR_CODE_AP
          = new FieldDetail("Error Code Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_DANGR_GOODS_NO_AP
          = new FieldDetail("Dangr Goods No Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_DANGR_GOODS_CLS_AP
          = new FieldDetail("Dangr Goods Cls Ap", "", 8, 1, "", 0, "");
    public static final FieldDetail FIELD_R7_C_USR_UPDT_AP
          = new FieldDetail("C Usr Updt Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_TS_UPDT_AP
          = new FieldDetail("Ts Updt Ap", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R7_AP_FILLER
          = new FieldDetail("Ap Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt AR"

    public static final FieldDetail FIELD_R8_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_BRAND_ID_AR12
          = new FieldDetail("Brand Id Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_ORDER_NO_AR
          = new FieldDetail("Order No Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_LOGISTICS_LOC_NOAR
          = new FieldDetail("Logistics Loc Noar", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_SCM_AR
          = new FieldDetail("Scm Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_PROD_NO_AR
          = new FieldDetail("Prod No Ar", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_PROD_QUALIFIER_AR
          = new FieldDetail("Prod Qualifier Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_QUALITY_CODE_AR
          = new FieldDetail("Quality Code Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_CHECKED_QTY_AR
          = new FieldDetail("Checked Qty Ar", "", 8, 2, "", 0, "");
    public static final FieldDetail FIELD_R8_SUP_ID_AR
          = new FieldDetail("Sup Id Ar", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_ASN_AR
          = new FieldDetail("Asn Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_BRAND_DC_AR
          = new FieldDetail("Brand Dc Ar", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_VALID_PROD_IND_AR
          = new FieldDetail("Valid Prod Ind Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_PROCESSED_IND_AR
          = new FieldDetail("Processed Ind Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_CHECKED_BY_AR
          = new FieldDetail("Checked By Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_CHECKED_DATE_AR
          = new FieldDetail("Checked Date Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_CHECKED_TIME_AR
          = new FieldDetail("Checked Time Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_C_USR_UPDT_AR
          = new FieldDetail("C Usr Updt Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_TS_UPDT_AR
          = new FieldDetail("Ts Updt Ar", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R8_AR_FILLER
          = new FieldDetail("Ar Filler", "", 0, 0, "", 0, "");

  // record fields for "ams Receipt FT File Trailer"

    public static final FieldDetail FIELD_R9_RECORD_TYPE
          = new FieldDetail("Record Type", "", 0, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_RECDS13
          = new FieldDetail("Ft Num Recds", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_FH
          = new FieldDetail("Ft Num Fh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_RH
          = new FieldDetail("Ft Num Rh", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_RS
          = new FieldDetail("Ft Num Rs", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_AS
          = new FieldDetail("Ft Num As", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_SO
          = new FieldDetail("Ft Num So", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_SC
          = new FieldDetail("Ft Num Sc", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_AP
          = new FieldDetail("Ft Num Ap", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_AR
          = new FieldDetail("Ft Num Ar", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_NUM_FT
          = new FieldDetail("Ft Num Ft", "", 7, 0, "", 0, "");
    public static final FieldDetail FIELD_R9_FT_FILLER
          = new FieldDetail("Ft Filler", "", 0, 0, "", 0, "");

    static {

        // record fields for "ams Receipt FH Header"

        FIELD_R0_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R0_FH_DESCRIPTION4.setPosLen(3, 8);
        FIELD_R0_FH_BRAND_ID.setPosLen(11, 3);
        FIELD_R0_CREATION_DATE.setPosLen(14, 10);
        FIELD_R0_CREATION_TIME.setPosLen(24, 8);
        FIELD_R0_FH_FILLER.setPosLen(32, 319);

        // record fields for "ams Receipt RH Receipt Header"

        FIELD_R1_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R1_BRAND_ID_RH5.setPosLen(3, 3);
        FIELD_R1_ORDER_NO_RH.setPosLen(6, 12);
        FIELD_R1_RECEIPT_LOCN_RH.setPosLen(18, 4);
        FIELD_R1_SUP_ID_RH.setPosLen(31, 8);
        FIELD_R1_RECEIPT_DATE_RH.setPosLen(39, 10);
        FIELD_R1_RECEIPT_TIME_RH.setPosLen(49, 8);
        FIELD_R1_BRAND_XREF_RH.setPosLen(57, 5);
        FIELD_R1_BRAND_DC_NO_RH.setPosLen(62, 4);
        FIELD_R1_RCPT_FINAL_STAT_RH.setPosLen(66, 1);
        FIELD_R1_PROCESSED_IND_RH.setPosLen(67, 1);
        FIELD_R1_ASN_RH.setPosLen(68, 30);
        FIELD_R1_ASN_SEQ_NO_RH.setPosLen(98, 9);
        FIELD_R1_SMPL_CHCK_CTN_RH.setPosLen(107, 7);
        FIELD_R1_SMPL_CHCK_U_RH.setPosLen(114, 11);
        FIELD_R1_INVOICE_NO_RH.setPosLen(125, 20);
        FIELD_R1_TOT_RCPT_AMT_RH.setPosLen(145, 9);
        FIELD_R1_TOT_RECV_QTY_RH.setPosLen(154, 9);
        FIELD_R1_TOT_OUTERS_RH.setPosLen(163, 11);
        FIELD_R1_CARRIER_CONNOTE_RH.setPosLen(174, 20);
        FIELD_R1_ERS_STATUS_RH.setPosLen(194, 1);
        FIELD_R1_CHK_RESULT_ADJD_RH.setPosLen(195, 1);
        FIELD_R1_BRAND_STK_UPD_RH.setPosLen(196, 1);
        FIELD_R1_ORGINL_SCM_CNT_RH.setPosLen(197, 7);
        FIELD_R1_PICK_PACK_IND_RH.setPosLen(204, 1);
        FIELD_R1_C_USR_UPDT_RH.setPosLen(205, 8);
        FIELD_R1_TS_UPDT_RH.setPosLen(213, 26);
        FIELD_R1_RH_NUM_RD.setPosLen(239, 9);
        FIELD_R1_RH_NUM_AS.setPosLen(248, 9);
        FIELD_R1_RH_FILLER.setPosLen(257, 94);

        // record fields for "ams Receipt RD Recipt Product"

        FIELD_R2_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R2_BRAND_ID_RD6.setPosLen(3, 3);
        FIELD_R2_ORDER_NO_RD.setPosLen(6, 12);
        FIELD_R2_RECEIPT_LOCN_RD.setPosLen(18, 4);
        FIELD_R2_PROD_NO_RD.setPosLen(31, 14);
        FIELD_R2_KEYCODE_RD.setPosLen(45, 8);
        FIELD_R2_SEQ_NO_RD.setPosLen(53, 5);
        FIELD_R2_PROD_QUALIFIER_RD.setPosLen(58, 2);
        FIELD_R2_RCVD_QTY_RD.setPosLen(60, 9);
        FIELD_R2_ITMS_PER_CTN_QTYRD.setPosLen(69, 6);
        FIELD_R2_UNIT_COST_RD.setPosLen(75, 14);
        FIELD_R2_C_USR_UPDT_RD.setPosLen(89, 8);
        FIELD_R2_TS_UPDT_RD.setPosLen(97, 26);
        FIELD_R2_RD_NUM_RS.setPosLen(123, 9);
        FIELD_R2_RD_FILLER.setPosLen(132, 219);

        // record fields for "ams Receipt RS Recipt Store"

        FIELD_R3_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R3_BRAND_ID7.setPosLen(3, 3);
        FIELD_R3_ORDER_NO.setPosLen(6, 12);
        FIELD_R3_RECEIPT_LOCATION.setPosLen(18, 4);
        FIELD_R3_PROD_NO.setPosLen(31, 14);
        FIELD_R3_KEYCODE.setPosLen(45, 8);
        FIELD_R3_SEQUENCE_NO.setPosLen(53, 5);
        FIELD_R3_STORE_NO.setPosLen(58, 4);
        FIELD_R3_RECEIVE_ALC_QTY.setPosLen(62, 9);
        FIELD_R3_OUTSTANDING_ORD_QTY.setPosLen(71, 9);
        FIELD_R3_C_USR_UPDT.setPosLen(80, 8);
        FIELD_R3_TS_UPDT.setPosLen(88, 26);
        FIELD_R3_ACT_RECV_QTY.setPosLen(114, 9);

        // record fields for "ams Receipt AS"

        FIELD_R4_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R4_ASN_AS8.setPosLen(3, 30);
        FIELD_R4_ASN_SEQ_NO_AS.setPosLen(33, 9);
        FIELD_R4_SUP_EDI_ADDR_AS.setPosLen(42, 15);
        FIELD_R4_BRAND_ID_AS.setPosLen(57, 3);
        FIELD_R4_PROD_TEST_IND_AS.setPosLen(60, 1);
        FIELD_R4_TRANSACTION_DT_AS.setPosLen(61, 10);
        FIELD_R4_TRANSACTION_TM_AS.setPosLen(71, 8);
        FIELD_R4_TRANS_SET_PURP_AS.setPosLen(79, 2);
        FIELD_R4_ASN_STRUCTURE_AS.setPosLen(81, 4);
        FIELD_R4_SHIP_GROSS_VOL_AS.setPosLen(85, 9);
        FIELD_R4_VOL_MEASURE_CD_AS.setPosLen(94, 2);
        FIELD_R4_SHIP_PACK_CODE_AS.setPosLen(96, 5);
        FIELD_R4_SHIP_LDG_QTY_AS.setPosLen(101, 7);
        FIELD_R4_SHIP_LDG_WGT_AS.setPosLen(108, 11);
        FIELD_R4_SHIP_WGT_CODE_AS.setPosLen(119, 2);
        FIELD_R4_SHIP_STATUS_AS.setPosLen(121, 2);
        FIELD_R4_CARRIER_CONNOTE_AS.setPosLen(123, 20);
        FIELD_R4_SHIP_SCHED_DT_AS.setPosLen(143, 10);
        FIELD_R4_DLVY_SCHED_DT_AS.setPosLen(153, 10);
        FIELD_R4_M_DC_DLVY_AS.setPosLen(163, 4);
        FIELD_R4_SUP_ID_AS.setPosLen(167, 8);
        FIELD_R4_SUP_NAME_AS.setPosLen(175, 35);
        FIELD_R4_A_ALLOC_REG_NET_AS.setPosLen(210, 13);
        FIELD_R4_VALID_IND_AS.setPosLen(223, 1);
        FIELD_R4_REASON_CODE_AS.setPosLen(224, 1);
        FIELD_R4_ASN_STATE_AS.setPosLen(225, 2);
        FIELD_R4_FULL_DLVY_IND_AS.setPosLen(227, 1);
        FIELD_R4_SHIP_SSCC_AS.setPosLen(228, 20);
        FIELD_R4_EDI_ERR_SEVER_I_AS.setPosLen(248, 1);
        FIELD_R4_ASN_RCPT_DT_AS.setPosLen(249, 10);
        FIELD_R4_MERCH_RCPT_DT_AS.setPosLen(259, 10);
        FIELD_R4_SUP_RATING_AS.setPosLen(269, 2);
        FIELD_R4_INTEG_FLG_AS.setPosLen(271, 1);
        FIELD_R4_CANCEL_REASON_C_AS.setPosLen(272, 1);
        FIELD_R4_P_CHECKED_CTNS_AS.setPosLen(273, 4);
        FIELD_R4_P_CHECKED_UNITS_AS.setPosLen(277, 4);
        FIELD_R4_ASN_ARRV_TM_AS.setPosLen(281, 8);
        FIELD_R4_EXTRACT_STATUS_AS.setPosLen(289, 1);
        FIELD_R4_C_USR_UPDT_AS.setPosLen(290, 8);
        FIELD_R4_TS_UPDT_AS.setPosLen(298, 26);
        FIELD_R4_AS_DLVY_TYPE.setPosLen(324, 3);
        FIELD_R4_AS_NUM_SO.setPosLen(327, 9);
        FIELD_R4_AS_NUM_AR.setPosLen(336, 9);
        FIELD_R4_AS_FILLER.setPosLen(345, 10);

        // record fields for "ams Receipt SO"

        FIELD_R5_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R5_ASN_SO9.setPosLen(3, 30);
        FIELD_R5_ASN_SEQ_NO_SO.setPosLen(33, 9);
        FIELD_R5_ORDER_NO_SO.setPosLen(42, 12);
        FIELD_R5_STORE_SO.setPosLen(54, 4);
        FIELD_R5_ORDER_DATE_SO.setPosLen(58, 10);
        FIELD_R5_ORDER_PACK_CODE_SO.setPosLen(68, 5);
        FIELD_R5_ORDER_LDG_QTY_SO.setPosLen(73, 7);
        FIELD_R5_ORDER_LDG_WGT_SO.setPosLen(80, 11);
        FIELD_R5_ORDER_WGT_CODE_SO.setPosLen(91, 2);
        FIELD_R5_SHIP_ORD_STATUS_SO.setPosLen(93, 2);
        FIELD_R5_SUP_INV_NO_SO.setPosLen(95, 20);
        FIELD_R5_ERROR_CODE_SO.setPosLen(115, 2);
        FIELD_R5_ORDER_NO_NUM_SO.setPosLen(117, 12);
        FIELD_R5_ORDER_NO_850_SO.setPosLen(129, 12);
        FIELD_R5_C_USR_UPDT_SO.setPosLen(141, 8);
        FIELD_R5_TS_UPDT_SO.setPosLen(149, 26);
        FIELD_R5_SO_NUM_SC.setPosLen(175, 9);
        FIELD_R5_SO_FILLER.setPosLen(184, 167);

        // record fields for "ams Receipt SC"

        FIELD_R6_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R6_ASN_SC10.setPosLen(3, 30);
        FIELD_R6_ASN_SEQ_NO_SC.setPosLen(33, 9);
        FIELD_R6_ORDER_NO_SC.setPosLen(42, 12);
        FIELD_R6_STORE_SC.setPosLen(54, 4);
        FIELD_R6_SCM_SC.setPosLen(58, 20);
        FIELD_R6_SCM_SEQ_NO_SC.setPosLen(78, 9);
        FIELD_R6_CONTAINER_TYPE_SC.setPosLen(87, 1);
        FIELD_R6_PLT_PACK_CODE_SC.setPosLen(88, 5);
        FIELD_R6_PLT_LADING_QTY_SC.setPosLen(93, 7);
        FIELD_R6_PLT_LADING_WGT_SC.setPosLen(100, 11);
        FIELD_R6_PLT_WGT_MSRE_CD_SC.setPosLen(111, 2);
        FIELD_R6_PARENT_SCM_SC.setPosLen(113, 20);
        FIELD_R6_PARENT_SCM_SEQ_SC.setPosLen(133, 9);
        FIELD_R6_ERROR_CODE_SC.setPosLen(142, 2);
        FIELD_R6_SCM_RCEIV_QUALFRSC.setPosLen(144, 1);
        FIELD_R6_SCM_SAMPLE_IND_SC.setPosLen(145, 1);
        FIELD_R6_C_USR_UPDT_SC.setPosLen(146, 8);
        FIELD_R6_TS_UPDT_SC.setPosLen(154, 26);
        FIELD_R6_SC_NUM_AP.setPosLen(180, 9);
        FIELD_R6_SC_FILLER.setPosLen(189, 162);

        // record fields for "ams Receipt AP"

        FIELD_R7_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R7_ASN_AP11.setPosLen(3, 30);
        FIELD_R7_ASN_SEQ_NO_AP.setPosLen(33, 9);
        FIELD_R7_ORDER_NO_AP.setPosLen(42, 12);
        FIELD_R7_STORE_AP.setPosLen(54, 4);
        FIELD_R7_SCM_AP.setPosLen(58, 20);
        FIELD_R7_SCM_SEQ_NO_AP.setPosLen(78, 9);
        FIELD_R7_PROD_NO_AP.setPosLen(87, 14);
        FIELD_R7_PROD_SEQ_NO_AP.setPosLen(101, 5);
        FIELD_R7_PROD_QUALIFIER_AP.setPosLen(106, 2);
        FIELD_R7_QTY_SHIPPED_AP.setPosLen(108, 12);
        FIELD_R7_PROD_MEASURE_CD_AP.setPosLen(120, 2);
        FIELD_R7_USE_BY_DATE_AP.setPosLen(122, 10);
        FIELD_R7_KEYCODE_AP.setPosLen(132, 8);
        FIELD_R7_ERROR_CODE_AP.setPosLen(140, 2);
        FIELD_R7_DANGR_GOODS_NO_AP.setPosLen(142, 4);
        FIELD_R7_DANGR_GOODS_CLS_AP.setPosLen(146, 4);
        FIELD_R7_C_USR_UPDT_AP.setPosLen(150, 8);
        FIELD_R7_TS_UPDT_AP.setPosLen(158, 26);
        FIELD_R7_AP_FILLER.setPosLen(184, 167);

        // record fields for "ams Receipt AR"

        FIELD_R8_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R8_BRAND_ID_AR12.setPosLen(3, 3);
        FIELD_R8_ORDER_NO_AR.setPosLen(6, 12);
        FIELD_R8_LOGISTICS_LOC_NOAR.setPosLen(18, 4);
        FIELD_R8_SCM_AR.setPosLen(31, 20);
        FIELD_R8_PROD_NO_AR.setPosLen(51, 14);
        FIELD_R8_PROD_QUALIFIER_AR.setPosLen(65, 2);
        FIELD_R8_QUALITY_CODE_AR.setPosLen(67, 2);
        FIELD_R8_CHECKED_QTY_AR.setPosLen(69, 12);
        FIELD_R8_SUP_ID_AR.setPosLen(81, 8);
        FIELD_R8_ASN_AR.setPosLen(89, 30);
        FIELD_R8_BRAND_DC_AR.setPosLen(119, 4);
        FIELD_R8_VALID_PROD_IND_AR.setPosLen(123, 1);
        FIELD_R8_PROCESSED_IND_AR.setPosLen(124, 1);
        FIELD_R8_CHECKED_BY_AR.setPosLen(125, 10);
        FIELD_R8_CHECKED_DATE_AR.setPosLen(135, 10);
        FIELD_R8_CHECKED_TIME_AR.setPosLen(145, 8);
        FIELD_R8_C_USR_UPDT_AR.setPosLen(153, 8);
        FIELD_R8_TS_UPDT_AR.setPosLen(161, 26);
        FIELD_R8_AR_FILLER.setPosLen(187, 164);

        // record fields for "ams Receipt FT File Trailer"

        FIELD_R9_RECORD_TYPE.setPosLen(1, 2);
        FIELD_R9_FT_NUM_RECDS13.setPosLen(3, 9);
        FIELD_R9_FT_NUM_FH.setPosLen(12, 9);
        FIELD_R9_FT_NUM_RH.setPosLen(21, 9);
        FIELD_R9_FT_NUM_RS.setPosLen(39, 9);
        FIELD_R9_FT_NUM_AS.setPosLen(48, 9);
        FIELD_R9_FT_NUM_SO.setPosLen(57, 9);
        FIELD_R9_FT_NUM_SC.setPosLen(66, 9);
        FIELD_R9_FT_NUM_AP.setPosLen(75, 9);
        FIELD_R9_FT_NUM_AR.setPosLen(84, 9);
        FIELD_R9_FT_NUM_FT.setPosLen(93, 9);
        FIELD_R9_FT_FILLER.setPosLen(102, 249);
    };


    /**
     * Create Null Line
     *
     * @param group Record Description
     */
    public AmsReceipt(final LayoutDetail group) {
        super(toInit ? group
                     : copyBook);

        commonInit(group);
    }


    /**
     * Create Line from a byte record
     *
     * @param group Record Description
     * @param rec record
     */
    public AmsReceipt(final LayoutDetail group, final byte[] rec) {
        super(toInit ? group
                     : copyBook,
              rec);

        commonInit(group);
    }


    /**
     * Create Line from a String record record
     *
     * @param group Record Description
     * @param rec   record
     */
    public AmsReceipt(final LayoutDetail group, final String rec) {
        super(toInit ? group
                     : copyBook,
              rec);

        commonInit(group);
    }


    /**
     * Common initialisation code
     *
     * @param group record layout
     */
    private static void commonInit(final LayoutDetail group) {

        if (toInit) {
            indexAmsReceiptFhHeader = group.getRecordIndex(RECORD_AMS_RECEIPT_FH_HEADER);
            indexAmsReceiptRhReceiptHeader = group.getRecordIndex(RECORD_AMS_RECEIPT_RH_RECEIPT_HEADER);
            indexAmsReceiptRdReciptProduct = group.getRecordIndex(RECORD_AMS_RECEIPT_RD_RECIPT_PRODUCT);
            indexAmsReceiptRsReciptStore = group.getRecordIndex(RECORD_AMS_RECEIPT_RS_RECIPT_STORE);
            indexAmsReceiptAs = group.getRecordIndex(RECORD_AMS_RECEIPT_AS);
            indexAmsReceiptSo = group.getRecordIndex(RECORD_AMS_RECEIPT_SO);
            indexAmsReceiptSc = group.getRecordIndex(RECORD_AMS_RECEIPT_SC);
            indexAmsReceiptAp = group.getRecordIndex(RECORD_AMS_RECEIPT_AP);
            indexAmsReceiptAr = group.getRecordIndex(RECORD_AMS_RECEIPT_AR);
            indexAmsReceiptFtFileTrailer = group.getRecordIndex(RECORD_AMS_RECEIPT_FT_FILE_TRAILER);

            toInit   = false;
            copyBook = group;
        }
    }


    /**
     * get index of ams Receipt FH Header
     * @return index of ams Receipt FH Header
     */
    public static final int getAmsReceiptFhHeaderIndex() {
        return indexAmsReceiptFhHeader;
    }


    /**
     * get index of ams Receipt RH Receipt Header
     * @return index of ams Receipt RH Receipt Header
     */
    public static final int getAmsReceiptRhReceiptHeaderIndex() {
        return indexAmsReceiptRhReceiptHeader;
    }


    /**
     * get index of ams Receipt RD Recipt Product
     * @return index of ams Receipt RD Recipt Product
     */
    public static final int getAmsReceiptRdReciptProductIndex() {
        return indexAmsReceiptRdReciptProduct;
    }


    /**
     * get index of ams Receipt RS Recipt Store
     * @return index of ams Receipt RS Recipt Store
     */
    public static final int getAmsReceiptRsReciptStoreIndex() {
        return indexAmsReceiptRsReciptStore;
    }


    /**
     * get index of ams Receipt AS
     * @return index of ams Receipt AS
     */
    public static final int getAmsReceiptAsIndex() {
        return indexAmsReceiptAs;
    }


    /**
     * get index of ams Receipt SO
     * @return index of ams Receipt SO
     */
    public static final int getAmsReceiptSoIndex() {
        return indexAmsReceiptSo;
    }


    /**
     * get index of ams Receipt SC
     * @return index of ams Receipt SC
     */
    public static final int getAmsReceiptScIndex() {
        return indexAmsReceiptSc;
    }


    /**
     * get index of ams Receipt AP
     * @return index of ams Receipt AP
     */
    public static final int getAmsReceiptApIndex() {
        return indexAmsReceiptAp;
    }


    /**
     * get index of ams Receipt AR
     * @return index of ams Receipt AR
     */
    public static final int getAmsReceiptArIndex() {
        return indexAmsReceiptAr;
    }


    /**
     * get index of ams Receipt FT File Trailer
     * @return index of ams Receipt FT File Trailer
     */
    public static final int getAmsReceiptFtFileTrailerIndex() {
        return indexAmsReceiptFtFileTrailer;
    }


    /**
     * Get the copybook
     * @return copybook
     */
    public static final LayoutDetail getCopyBook() {
        return copyBook;
    }


    /**
     * Set the copybook
     * @param pCopyBook copybook to be assigned
     */
    public static final void setCopyBook(LayoutDetail pCopyBook) {
	toInit   = true;

	commonInit(pCopyBook);
    }


    /**
     * Set the copybook
     *
     * @param copybook copybook interface
     */
    public static final void setCopyBook(CopyBookInterface copybook) {
        setCopyBook(copybook.getLayout(COPYBOOK_NAME));
    }



}

