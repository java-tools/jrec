

/*
 * @Author Generate by XmplLineBuilder
 *
 * Purpose: line to access EdiPo records
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
public class EdiPo extends Line {

    public static final String COPYBOOK_NAME = "EDI PO";

 //IVR0075D
    public static final String RECORD_IVR0075D = "IVR0075D";
 //IVR0075H
    public static final String RECORD_IVR0075H = "IVR0075H";
 //IVR0075S
    public static final String RECORD_IVR0075S = "IVR0075S";

    private static LayoutDetail copyBook;

    private static boolean toInit = true;
    private static int indexIvr0075d;
    private static int indexIvr0075h;
    private static int indexIvr0075s;


  // record fields for "IVR0075D"

  public static final int R0_DETAIL_REC_TYPE = 0;
  public static final int R0_ORDER_QTY = 1;
  public static final int R0_COST_PRICE = 2;
  public static final int R0_APN_NO = 3;
  public static final int R0_KEYCODE = 4;
  public static final int R0_RETAIL_PRICE2 = 5;
  public static final int R0_SELL_PACK_QTY = 6;
  public static final int R0_CHARGE_INDICATOR = 7;
  public static final int R0_AGENCY_QUAL_CODE = 8;
  public static final int R0_SPECIAL_SERVICES = 9;

  // record fields for "IVR0075H"

  public static final int R1_HEADER_REC_TYPE = 0;
  public static final int R1_TRANS_ID = 1;
  public static final int R1_TP_ID = 2;
  public static final int R1_PO_NUM = 3;
  public static final int R1_PO_DATE = 4;
  public static final int R1_CONTRACT_NO = 5;
  public static final int R1_BEG01_CODE = 6;
  public static final int R1_BEG02_CODE2 = 7;
  public static final int R1_DEPT_NO2 = 8;
  public static final int R1_BEFORE_DATE = 9;
  public static final int R1_AFTER_DATE = 10;
  public static final int R1_DC_NO = 11;
  public static final int R1_EDI_TYPE = 12;
  public static final int R1_AD_NL2 = 13;
  public static final int R1_DD = 15;
  public static final int R1_MM = 16;
  public static final int R1_STORE_DEPT_DESC = 17;

  // record fields for "IVR0075S"

  public static final int R2_STORE_REC_TYPE = 0;




    /**
     * Create Null Line
     *
     * @param group Record Description
     */
    public EdiPo(final LayoutDetail group) {
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
    public EdiPo(final LayoutDetail group, final byte[] rec) {
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
    public EdiPo(final LayoutDetail group, final String rec) {
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
            indexIvr0075d = group.getRecordIndex(RECORD_IVR0075D);
            indexIvr0075h = group.getRecordIndex(RECORD_IVR0075H);
            indexIvr0075s = group.getRecordIndex(RECORD_IVR0075S);

            toInit   = false;
            copyBook = group;
        }
    }


    /**
     * get index of IVR0075D
     * @return index of IVR0075D
     */
    public static final int getIvr0075dIndex() {
        return indexIvr0075d;
    }


    /**
     * get index of IVR0075H
     * @return index of IVR0075H
     */
    public static final int getIvr0075hIndex() {
        return indexIvr0075h;
    }


    /**
     * get index of IVR0075S
     * @return index of IVR0075S
     */
    public static final int getIvr0075sIndex() {
        return indexIvr0075s;
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
     * Get index of "IVR0075S"
     *
     * @param index array index
     *
     * @return index
     */
    public int getIndexStoreNum(int index) {
        return 2 * index + 1;
    }


    /**
     * Get index of "IVR0075S"
     *
     * @param index array index
     *
     * @return index
     */
    public int getIndexOrdQty(int index) {
        return 2 * index + 2;
    }



   // record fields IVR0075D



    /**
     * Get
     *
     * @return
     */
    public String getDetailRecType() {
        return super.getField(indexIvr0075d, R0_DETAIL_REC_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setDetailRecType(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_DETAIL_REC_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getOrderQty() {
        return super.getField(indexIvr0075d, R0_ORDER_QTY).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setOrderQty(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_ORDER_QTY, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getCostPrice() {
        return super.getField(indexIvr0075d, R0_COST_PRICE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setCostPrice(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_COST_PRICE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getApnNo() {
        return super.getField(indexIvr0075d, R0_APN_NO).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setApnNo(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_APN_NO, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getKeycode2() {
        return super.getField(indexIvr0075d, R0_KEYCODE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setKeycode2(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_KEYCODE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getRetailPrice() {
        return super.getField(indexIvr0075d, R0_RETAIL_PRICE2).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setRetailPrice(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_RETAIL_PRICE2, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getSellPackQty() {
        return super.getField(indexIvr0075d, R0_SELL_PACK_QTY).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setSellPackQty(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_SELL_PACK_QTY, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getChargeIndicator() {
        return super.getField(indexIvr0075d, R0_CHARGE_INDICATOR).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setChargeIndicator(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_CHARGE_INDICATOR, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getAgencyQualCode() {
        return super.getField(indexIvr0075d, R0_AGENCY_QUAL_CODE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setAgencyQualCode(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_AGENCY_QUAL_CODE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getSpecialServices() {
        return super.getField(indexIvr0075d, R0_SPECIAL_SERVICES).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setSpecialServices(Object val) throws RecordException {
        super.setField(indexIvr0075d, R0_SPECIAL_SERVICES, val.toString());
    }


   // record fields IVR0075H



    /**
     * Get
     *
     * @return
     */
    public String getHeaderRecType() {
        return super.getField(indexIvr0075h, R1_HEADER_REC_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setHeaderRecType(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_HEADER_REC_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getTransId() {
        return super.getField(indexIvr0075h, R1_TRANS_ID).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setTransId(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_TRANS_ID, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getTpId() {
        return super.getField(indexIvr0075h, R1_TP_ID).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setTpId(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_TP_ID, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getPoNum() {
        return super.getField(indexIvr0075h, R1_PO_NUM).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setPoNum(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_PO_NUM, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getPoDate() {
        return super.getField(indexIvr0075h, R1_PO_DATE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setPoDate(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_PO_DATE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getContractNo() {
        return super.getField(indexIvr0075h, R1_CONTRACT_NO).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setContractNo(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_CONTRACT_NO, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getBeg01Code2() {
        return super.getField(indexIvr0075h, R1_BEG01_CODE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setBeg01Code2(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_BEG01_CODE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getBeg02Code2() {
        return super.getField(indexIvr0075h, R1_BEG02_CODE2).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setBeg02Code2(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_BEG02_CODE2, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getDeptNo() {
        return super.getField(indexIvr0075h, R1_DEPT_NO2).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setDeptNo(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_DEPT_NO2, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getBeforeDate() {
        return super.getField(indexIvr0075h, R1_BEFORE_DATE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setBeforeDate(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_BEFORE_DATE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getAfterDate() {
        return super.getField(indexIvr0075h, R1_AFTER_DATE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setAfterDate(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_AFTER_DATE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getDcNo() {
        return super.getField(indexIvr0075h, R1_DC_NO).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setDcNo(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_DC_NO, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getEdiType2() {
        return super.getField(indexIvr0075h, R1_EDI_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setEdiType2(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_EDI_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getAdNl() {
        return super.getField(indexIvr0075h, R1_AD_NL2).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setAdNl(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_AD_NL2, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getDd() {
        return super.getField(indexIvr0075h, R1_DD).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setDd(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_DD, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getMm() {
        return super.getField(indexIvr0075h, R1_MM).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setMm(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_MM, val.toString());
    }


    /**
     * Get
     *
     * @return
     */
    public String getStoreDeptDesc() {
        return super.getField(indexIvr0075h, R1_STORE_DEPT_DESC).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setStoreDeptDesc(Object val) throws RecordException {
        super.setField(indexIvr0075h, R1_STORE_DEPT_DESC, val.toString());
    }


   // record fields IVR0075S



    /**
     * Get
     *
     * @return
     */
    public String getStoreRecType() {
        return super.getField(indexIvr0075s, R2_STORE_REC_TYPE).toString();
    }


    /**
     * Set
     *
     * @param val
     *
     * @throws RecordException - conversion error
     */
    public void setStoreRecType(Object val) throws RecordException {
        super.setField(indexIvr0075s, R2_STORE_REC_TYPE, val.toString());
    }


    /**
     * Get
     *
     * @param index array index
     *
     * @return Store Num 1
     *
     */
    public String getStoreNum(int index) {
        return super.getField(indexIvr0075s,
                              getIndexStoreNum(index)).toString();
    }


    /**
     * Set
     *
     * @param index array index
     * @param val Store Num 1 value
     *
     *
     * @throws RecordException - conversion error
     */
    public void setStoreNum(int index, Object val)
    throws RecordException {
        super.setField(indexIvr0075s,
                       getIndexStoreNum(index),
                       val.toString());
    }


    /**
     * Get
     *
     * @param index array index
     *
     * @return Ord Qty 1
     *
     */
    public String getOrdQty(int index) {
        return super.getField(indexIvr0075s,
                              getIndexOrdQty(index)).toString();
    }


    /**
     * Set
     *
     * @param index array index
     * @param val Ord Qty 1 value
     *
     *
     * @throws RecordException - conversion error
     */
    public void setOrdQty(int index, Object val)
    throws RecordException {
        super.setField(indexIvr0075s,
                       getIndexOrdQty(index),
                       val.toString());
    }

  }

