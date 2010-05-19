

/*
 * @Author Generate by XmplLineBuilder
 *
 * Purpose: line to access AmsPoDownload records
 */
package net.sf.RecordEditor.examples.genCode;

import net.sf.JRecord.Common.RecordException;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.RecordEditor.utils.CopyBookInterface;

/**
 * Record to Access
 *
 */
public class AmsPoDownload extends Line {

    public static final String COPYBOOK_NAME = "ams PO Download";

    public static final String RECORD_AMS_PO_DOWNLOAD_DETAILnull = "ams PO Download: Detail";
    public static final String RECORD_AMS_PO_DOWNLOAD_HEADER = "ams PO Download: Header";
    public static final String RECORD_AMS_PO_DOWNLOAD_ALLOCATION = "ams PO Download: Allocation";

    private static LayoutDetail copyBook;

    private static boolean toInit = true;
    private static int indexAmsPoDownloadDetail;
    private static int indexAmsPoDownloadHeader;
    private static int indexAmsPoDownloadAllocation;


  // record fields for "ams PO Download: Detail"

    public static final int R0_RECORD_TYPE = 0;
    public static final int R0_PACK_QTY = 1;
    public static final int R0_PACK_COST = 2;
    public static final int R0_APN = 3;
    public static final int R0_PRODUCT = 5;
    public static final int R0_PMG_DTL_TECH_KEY = 6;
    public static final int R0_CASE_PACK_ID = 7;
    public static final int R0_PRODUCT_NAME = 8;

  // record fields for "ams PO Download: Header"

    public static final int R1_RECORD_TYPE = 0;
    public static final int R1_SEQUENCE_NUMBER2 = 1;
    public static final int R1_VENDOR = 2;
    public static final int R1_PO = 3;
    public static final int R1_ENTRY_DATE = 4;
    public static final int R1_BEG01_CODE = 6;
    public static final int R1_BEG02_CODE = 7;
    public static final int R1_DEPARTMENT = 8;
    public static final int R1_EXPECTED_RECIEPT_DATE = 9;
    public static final int R1_CANCEL_BY_DATE = 10;
    public static final int R1_EDI_TYPE = 11;
    public static final int R1_ADD_DATE = 12;
    public static final int R1_DEPARTMENT_NAME = 14;
    public static final int R1_PROCESS_TYPE = 15;
    public static final int R1_ORDER_TYPE = 16;

  // record fields for "ams PO Download: Allocation"

    public static final int R2_RECORD_TYPE = 0;




    /**
     * Create Null Line
     *
     * @param group Record Description
     */
    public AmsPoDownload(final LayoutDetail group) {
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
    public AmsPoDownload(final LayoutDetail group, final byte[] rec) {
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
    public AmsPoDownload(final LayoutDetail group, final String rec) {
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
            indexAmsPoDownloadDetail = group.getRecordIndex(RECORD_AMS_PO_DOWNLOAD_DETAILnull);
            indexAmsPoDownloadHeader = group.getRecordIndex(RECORD_AMS_PO_DOWNLOAD_HEADER);
            indexAmsPoDownloadAllocation = group.getRecordIndex(RECORD_AMS_PO_DOWNLOAD_ALLOCATION);

            toInit   = false;
            copyBook = group;
        }
    }


    /**
     * get index of ams PO Download: Detail
     * @return index of ams PO Download: Detail
     */
    public static final int getAmsPoDownloadDetailIndex() {
        return indexAmsPoDownloadDetail;
    }


    /**
     * get index of ams PO Download: Header
     * @return index of ams PO Download: Header
     */
    public static final int getAmsPoDownloadHeaderIndex() {
        return indexAmsPoDownloadHeader;
    }


    /**
     * get index of ams PO Download: Allocation
     * @return index of ams PO Download: Allocation
     */
    public static final int getAmsPoDownloadAllocationIndex() {
        return indexAmsPoDownloadAllocation;
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



    /**
     * Get index of "ams PO Download: Allocation"
     *
     * @param index array index
     *
     * @return index
     */
    public int getIndexDcNumber(int index) {
        return 2 * index + 1;
    }


    /**
     * Get index of "ams PO Download: Allocation"
     *
     * @param index array index
     *
     * @return index
     */
    public int getIndexPackQuantity(int index) {
        return 2 * index + 2;
    }



   // record fields ams PO Download: Detail



    /**
     * Get
     *
     * @return
     */
    public String getRecordType() {
        return super.getField(indexAmsPoDownloadDetail, R0_RECORD_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setRecordType(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_RECORD_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getPackQty() {
        return super.getField(indexAmsPoDownloadDetail, R0_PACK_QTY).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setPackQty(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_PACK_QTY, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getPackCost() {
        return super.getField(indexAmsPoDownloadDetail, R0_PACK_COST).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setPackCost(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_PACK_COST, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getApn() {
        return super.getField(indexAmsPoDownloadDetail, R0_APN).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setApn(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_APN, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getProduct() {
        return super.getField(indexAmsPoDownloadDetail, R0_PRODUCT).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setProduct(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_PRODUCT, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getPmgDtlTechKey() {
        return super.getField(indexAmsPoDownloadDetail, R0_PMG_DTL_TECH_KEY).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setPmgDtlTechKey(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_PMG_DTL_TECH_KEY, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getCasePackId() {
        return super.getField(indexAmsPoDownloadDetail, R0_CASE_PACK_ID).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setCasePackId(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_CASE_PACK_ID, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getProductName() {
        return super.getField(indexAmsPoDownloadDetail, R0_PRODUCT_NAME).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setProductName(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadDetail, R0_PRODUCT_NAME, val.toString());
    }


   // record fields ams PO Download: Header



    /**
     * Get
     *
     * @return
     */
    public String getRecordType2() {
        return super.getField(indexAmsPoDownloadHeader, R1_RECORD_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setRecordType2(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_RECORD_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getSequenceNumber() {
        return super.getField(indexAmsPoDownloadHeader, R1_SEQUENCE_NUMBER2).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setSequenceNumber(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_SEQUENCE_NUMBER2, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getVendor() {
        return super.getField(indexAmsPoDownloadHeader, R1_VENDOR).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setVendor(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_VENDOR, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getPo() {
        return super.getField(indexAmsPoDownloadHeader, R1_PO).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setPo(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_PO, val.toString());
    }


    /**
     * Get Format YYMMDD
     *
     * @return Format YYMMDD
     */
    public String getEntryDate() {
        return super.getField(indexAmsPoDownloadHeader, R1_ENTRY_DATE).toString();
    }


    /**
     * Set Format YYMMDD
     *
     * @param val Format YYMMDD
     *
     * @throws RecordException - conversion error
     */
    public void setEntryDate(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_ENTRY_DATE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getBeg01Code() {
        return super.getField(indexAmsPoDownloadHeader, R1_BEG01_CODE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setBeg01Code(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_BEG01_CODE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getBeg02Code() {
        return super.getField(indexAmsPoDownloadHeader, R1_BEG02_CODE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setBeg02Code(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_BEG02_CODE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getDepartment() {
        return super.getField(indexAmsPoDownloadHeader, R1_DEPARTMENT).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setDepartment(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_DEPARTMENT, val.toString());
    }


    /**
     * Get Format YYMMDD
     *
     * @return Format YYMMDD
     */
    public String getExpectedRecieptDate() {
        return super.getField(indexAmsPoDownloadHeader, R1_EXPECTED_RECIEPT_DATE).toString();
    }


    /**
     * Set Format YYMMDD
     *
     * @param val Format YYMMDD
     *
     * @throws RecordException - conversion error
     */
    public void setExpectedRecieptDate(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_EXPECTED_RECIEPT_DATE, val.toString());
    }


    /**
     * Get Format YYMMDD
     *
     * @return Format YYMMDD
     */
    public String getCancelByDate() {
        return super.getField(indexAmsPoDownloadHeader, R1_CANCEL_BY_DATE).toString();
    }


    /**
     * Set Format YYMMDD
     *
     * @param val Format YYMMDD
     *
     * @throws RecordException - conversion error
     */
    public void setCancelByDate(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_CANCEL_BY_DATE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getEdiType() {
        return super.getField(indexAmsPoDownloadHeader, R1_EDI_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setEdiType(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_EDI_TYPE, val.toString());
    }


    /**
     * Get Format YYMMDD
     *
     * @return Format YYMMDD
     */
    public String getAddDate() {
        return super.getField(indexAmsPoDownloadHeader, R1_ADD_DATE).toString();
    }


    /**
     * Set Format YYMMDD
     *
     * @param val Format YYMMDD
     *
     * @throws RecordException - conversion error
     */
    public void setAddDate(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_ADD_DATE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getDepartmentName() {
        return super.getField(indexAmsPoDownloadHeader, R1_DEPARTMENT_NAME).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setDepartmentName(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_DEPARTMENT_NAME, val.toString());
    }


    /**
     * Get C/N Conveyable/Non-Conveyable
     *
     * @return C/N Conveyable/Non-Conveyable
     */
    public String getProcessType() {
        return super.getField(indexAmsPoDownloadHeader, R1_PROCESS_TYPE).toString();
    }


    /**
     * Set C/N Conveyable/Non-Conveyable
     *
     * @param val C/N Conveyable/Non-Conveyable
     *
     * @throws RecordException - conversion error
     */
    public void setProcessType(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_PROCESS_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getOrderType() {
        return super.getField(indexAmsPoDownloadHeader, R1_ORDER_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setOrderType(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadHeader, R1_ORDER_TYPE, val.toString());
    }


   // record fields ams PO Download: Allocation



    /**
     * Get
     *
     * @return
     */
    public String getRecordType3() {
        return super.getField(indexAmsPoDownloadAllocation, R2_RECORD_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setRecordType3(Object val) throws RecordException {
        super.setField(indexAmsPoDownloadAllocation, R2_RECORD_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @param index array index
     *
     * @return DC Number 1
     *
     */
    public String getDcNumber(int index) {
        return super.getField(indexAmsPoDownloadAllocation,
                              getIndexDcNumber(index)).toString();
    }


    /**
     * Set
     *
     * @param index array index
     * @param val DC Number 1 value
     *
     *
     * @throws RecordException - conversion error
     */
    public void setDcNumber(int index, Object val)
    throws RecordException {
        super.setField(indexAmsPoDownloadAllocation,
                       getIndexDcNumber(index),
                       val.toString());
    }


    /**
     * Get
     *
     * @param index array index
     *
     * @return Pack Quantity 1
     *
     */
    public String getPackQuantity(int index) {
        return super.getField(indexAmsPoDownloadAllocation,
                              getIndexPackQuantity(index)).toString();
    }


    /**
     * Set
     *
     * @param index array index
     * @param val Pack Quantity 1 value
     *
     *
     * @throws RecordException - conversion error
     */
    public void setPackQuantity(int index, Object val)
    throws RecordException {
        super.setField(indexAmsPoDownloadAllocation,
                       getIndexPackQuantity(index),
                       val.toString());
    }

  }

